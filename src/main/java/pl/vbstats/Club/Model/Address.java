package pl.vbstats.Club.Model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    @NotBlank(message = "street cannot be blank")
    @Size(max = 100, message = "street name must be shorter than 100 characters")
    private String street;

    @NotBlank(message = "number cannot be blank")
    @Size(max = 10, message = "number must be shorter than 10 characters")
    private String number;

    @NotBlank(message = "city cannot be blank")
    @Size(max = 50, message = "city name must be shorter than 50 characters")
    private String city;

    @NotBlank(message = "zip code cannot be blank")
    @Pattern(regexp = "^[0-9]{2}-[0-9]{3}$", message = "zip code must be in format XX-XXX")
    private String zip;

}
