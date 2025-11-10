package pl.vbstats.User.Model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import pl.vbstats.Club.Model.Address;

import java.time.LocalDate;

@Data
public class UserUpdateRequest {

    @Email(message = "email is not correct")
    @Size(max = 100, message = "email must be shorter than 100 characters")
    private String email;

    @Size(min = 3, max = 30, message = "username must be between 3 and 30 characters")
    @Pattern(
            regexp = "^[a-zA-Z0-9._-]+$",
            message = "username can only contain letters, numbers, dots, underscores, and hyphens"
    )
    private String username;

    private String firstName;
    private String lastName;

    @Pattern(
            regexp = "^\\+?[0-9]{7,15}$",
            message = "phone number must contain only digits and optionally start with +"
    )
    private String phone;

    @Past(message = "birth date must be in the past")
    private LocalDate birthDate;

    @Valid
    private Address address;

}
