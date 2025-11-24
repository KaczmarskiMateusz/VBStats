package pl.vbstats.config;

import lombok.Builder;
import lombok.Data;
import pl.vbstats.user.Model.UserDto;

import java.util.UUID;

@Data
@Builder
public class AuthenticationResponse {

    private String token;
    private UserDto user;
    private UUID externalId;

}
