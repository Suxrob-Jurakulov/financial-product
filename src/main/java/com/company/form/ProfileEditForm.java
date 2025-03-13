package com.company.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileEditForm {

    @NotBlank(message = "Firstname cannot be empty!")
    @Size(min = 3, max = 50, message = "The firstname must be between 3 and 50 characters long.")
    private String firstname;

    @Size(min = 3, max = 50, message = "The lastname must be between 3 and 50 characters long.")
    private String lastname;

    @Email(message = "Email entered in incorrect format")
    private String email;

    // Other element
    private String id;
}
