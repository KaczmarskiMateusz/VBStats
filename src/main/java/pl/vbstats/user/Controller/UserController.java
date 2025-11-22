package pl.vbstats.user.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.vbstats.config.AuthenticationResponse;
import pl.vbstats.config.JwtAuthFilter;
import pl.vbstats.user.Model.ResetPasswordRequest;
import pl.vbstats.user.Model.UserDto;
import pl.vbstats.user.Model.UserRegisterRequest;
import pl.vbstats.user.Service.UserService;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/app/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody UserRegisterRequest request) {
        AuthenticationResponse response = userService.createUser(request);
        URI location = URI.create("/app/users/mainDashboard?" + response.getExternalId());
        return ResponseEntity.created(location).body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{externalId}")
    public ResponseEntity<UserDto> findUserByExternalId(@PathVariable UUID externalId) {
        UserDto userDto = userService.findUserByExternalId(externalId);
        return ResponseEntity.ok(userDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/by-email")
    public ResponseEntity<UserDto> findByEmail(@RequestParam String email,
                                               @AuthenticationPrincipal JwtAuthFilter.ExternalUserPrincipal principal) {
        UserDto userDto = userService.findByEmail(email, principal.externalId());
        return ResponseEntity.ok(userDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/exists")
    public ResponseEntity<Boolean> existsByEmail(@RequestParam String email,
                                                 @AuthenticationPrincipal JwtAuthFilter.ExternalUserPrincipal principal) {
        boolean exists = userService.existsByEmail(email, principal.externalId());
        return ResponseEntity.ok(exists);
    }

    @PostMapping("/activate")
    public ResponseEntity<Void> activateUser(@RequestParam String token,
                                             @AuthenticationPrincipal JwtAuthFilter.ExternalUserPrincipal principal) {
        userService.activateUser(token, principal.externalId());
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{externalId}/reset-password")
    public ResponseEntity<Void> adminResetPassword(@PathVariable UUID externalId,
                                                   @Valid @RequestBody ResetPasswordRequest request) {
        userService.resetPassword(request, externalId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{externalId}")
    public ResponseEntity<Void> deleteUserByExternalId(@PathVariable UUID externalId,
                                                       @AuthenticationPrincipal JwtAuthFilter.ExternalUserPrincipal principal) {
        userService.deleteUserById(externalId, principal.externalId());
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{externalId}/enable")
    public ResponseEntity<Void> enableUser(@PathVariable UUID externalId) {
        userService.enableUser(externalId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{externalId}/disable")
    public ResponseEntity<Void> disableUser(@PathVariable UUID externalId) {
        userService.disableUser(externalId);
        return ResponseEntity.noContent().build();
    }

}
