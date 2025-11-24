package pl.vbstats.auth;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequest(
        @NotBlank(message = "login cannot be blank")
        String login,
        @NotBlank(message = "password cannot be blank")
        String password) {
}
