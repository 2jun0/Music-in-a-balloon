package com.musicinaballoon.balloon.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ColorCodeValidator.class)
public @interface ColorCode {
    String message() default "Color code";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
