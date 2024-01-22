package com.bancobase.pagoservice.validators;

import java.util.Arrays;

import com.bancobase.pagoservice.model.enums.EstatusPago;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EstatusValidoValidator implements ConstraintValidator<EstatusValido, String>{

	/**
     * Método que realiza la validación.
     *
     * @param value   El valor a ser validado.
     * @param context Contexto de validación que puede ser utilizado para personalizar el resultado de la validación.
     * @return true si el valor es válido, false de lo contrario.
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

    	return Arrays.stream(EstatusPago.values())
         .anyMatch(valor -> value!=null && valor.name().equals(value));
    }
}
