package pl.vbstats.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthenticationRequest(
        @NotBlank(message = "login  cannot be blank")
        String login,
        @NotBlank(message = "password cannot be blank")
        String password) {
}
