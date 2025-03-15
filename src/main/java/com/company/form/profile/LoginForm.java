package com.company.form.profile;

import com.company.form.DefaultForm;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class LoginForm extends DefaultForm {

    @NotBlank(message = "Firstname cannot be empty!")
    private String phone;

    @NotBlank(message = "Firstname cannot be empty!")
    private String password;
}
