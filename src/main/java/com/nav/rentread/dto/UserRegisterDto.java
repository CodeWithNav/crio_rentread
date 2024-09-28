package com.nav.rentread.dto;

import com.nav.rentread.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterDto {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Length(min = 6,max = 50)
    private String password;

    @NotBlank
    @Pattern(regexp = "ADMIN|USER", message = "Role must be either ADMIN or USER")
    private User.Role role = User.Role.USER;
}
