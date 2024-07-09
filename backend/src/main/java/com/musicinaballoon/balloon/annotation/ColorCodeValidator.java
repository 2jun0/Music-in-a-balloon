package com.musicinaballoon.balloon.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ColorCodeValidator implements ConstraintValidator<ColorCode, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return value.matches("^#[A-Fa-f0-9]{6}$");
    }
}
