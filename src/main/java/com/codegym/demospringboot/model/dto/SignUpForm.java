package com.codegym.demospringboot.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.codegym.demospringboot.validator.PasswordConfirm;
import com.codegym.demospringboot.validator.UniqueUsername;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpForm {
    @NotEmpty
    @UniqueUsername
    @Size(min = 5, max = 12)
    private String username;

    @PasswordConfirm
    private PasswordForm passwordForm;
}
