package pl.vbstats.user.Model;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Data;
import pl.vbstats.club.Model.Address;

import java.time.LocalDate;

@Data
public class UserRegisterRequest {

    @NotBlank(message = "email cannot be empty")
    @Email(message = "email is not correct")
    @Size(max = 32)
    private String email;
    @Size(min = 3, max = 30, message = "username must be between 3 and 30 characters")
    @Pattern(
            regexp = "^[a-zA-Z0-9._-]+$",
            message = "username can only contain letters, numbers, dots, underscores, and hyphens"
    )
    private String username;
    @NotBlank(message = "password cannot be blank")
    @Size(min = 8, max = 64, message = "password must be between 8 and 64 characters")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$",
            message = "password must contain at least one letter and one number"
    )
    private String password;

    private String firstName;
    private String lastName;

    @Pattern(
            regexp = "^\\+?[0-9]{7,15}$",
            message = "phone number must contain only digits and optionally start with +"
    )
    private String phone;

    @Past(message = "birth date must be in the past")
    private LocalDate birthDate;

    private Address address;

    @Enumerated
    private UserRole role;

}
