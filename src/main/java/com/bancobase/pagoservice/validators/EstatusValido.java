package com.bancobase.pagoservice.validators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EstatusValidoValidator.class)
public @interface EstatusValido {
	String message() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
