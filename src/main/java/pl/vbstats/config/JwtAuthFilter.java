package pl.vbstats.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.vbstats.user.Model.UserEntity;
import pl.vbstats.user.Repository.UserRepository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = req.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(req, res);
            return;
        }

        String token = authHeader.substring(7);

        Claims claims;
        try {
            claims = jwtService.parseClaims(token); // jeden parse
        } catch (JwtException | IllegalArgumentException e) {
            sendUnauthorized(res, "INVALID_OR_EXPIRED_TOKEN", "Invalid or expired JWT token");
            return;
        }

        UUID externalId = jwtService.getExternalId(claims);
        if (externalId == null) {
            sendUnauthorized(res, "INVALID_TOKEN_PAYLOAD", "Token payload does not contain externalId");
            return;
        }

        UserEntity user = userRepository.findByExternalId(externalId)
                .orElse(null);

        if (user == null) {
            sendUnauthorized(res, "USER_NOT_FOUND", "User for given token not found");
            return;
        }

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            List<SimpleGrantedAuthority> authorities = jwtService.getRoles(claims).stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList();

            ExternalUserPrincipal principal = new ExternalUserPrincipal(
                    user.getId(),
                    user.getExternalId()
            );

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(principal, null, authorities);
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));

            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(req, res);
    }

    private void sendUnauthorized(HttpServletResponse res, String error, String message) throws IOException {
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        res.setCharacterEncoding(StandardCharsets.UTF_8.name());
        res.setContentType("application/json");

        String body = """
                {
                  "error": "%s",
                  "message": "%s",
                  "status": 401
                }
                """.formatted(error, message);

        res.getWriter().write(body);
    }

    public record ExternalUserPrincipal(Long id, UUID externalId) {
    }

}
