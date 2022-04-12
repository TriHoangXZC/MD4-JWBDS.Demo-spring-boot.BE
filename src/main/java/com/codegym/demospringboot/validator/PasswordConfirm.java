package com.codegym.demospringboot.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ConfirmPasswordValidator.class})
public @interface PasswordConfirm {
    String message() default "RePassword is not match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
