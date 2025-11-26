package pl.vbstats.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.vbstats.config.AuthenticationResponse;
import pl.vbstats.config.JwtService;
import pl.vbstats.user.Model.UserDto;
import pl.vbstats.user.Model.UserEntity;
import pl.vbstats.user.Model.UserMapper;
import pl.vbstats.user.Model.UserRegisterRequest;
import pl.vbstats.user.Repository.UserRepository;
import pl.vbstats.user.Service.UserService;
import pl.vbstats.user.exceptions.UserNotAuthorizedException;
import pl.vbstats.user.exceptions.UserNotFoundException;

@Service("authService")
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public AuthenticationResponse signup(UserRegisterRequest request) {
        UserDto createdUser = userService.createUser(request);

        String token = jwtService.generateToken(
                createdUser.getUserName(),
                createdUser.getExternalId(),
                createdUser.getRole()
        );

        return AuthenticationResponse.builder()
                .token(token)
                .user(createdUser)
                .build();
    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {

        UserEntity userEntity = userRepository.findByUsernameOrEmailIgnoreCase(request.login(), request.login())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.password(), userEntity.getPassword())) {
            throw new UserNotAuthorizedException("Wrong password or username");
        }

        String token = jwtService.generateToken(
                userEntity.getUsername(),
                userEntity.getExternalId(),
                userEntity.getRole()
        );

        return AuthenticationResponse.builder()
                .token(token)
                .user(userMapper.toDto(userEntity))
                .build();
    }

}
