package pl.vbstats.auth;

import pl.vbstats.config.AuthenticationResponse;
import pl.vbstats.user.Model.UserDto;
import pl.vbstats.user.Model.UserRegisterRequest;

public interface AuthService {

    AuthenticationResponse signup(UserRegisterRequest request);

    AuthenticationResponse login(AuthenticationRequest request);

}
