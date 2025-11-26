package pl.vbstats.config;

import lombok.Builder;
import lombok.Data;
import pl.vbstats.user.Model.UserDto;

@Data
@Builder
public class AuthenticationResponse {

    private String token;
    private UserDto user;

}
