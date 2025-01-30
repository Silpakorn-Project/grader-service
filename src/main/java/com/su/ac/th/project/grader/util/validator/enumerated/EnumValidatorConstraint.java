package com.su.ac.th.project.grader.util.validator.enumerated;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.stream.Collectors;


public class EnumValidatorConstraint implements ConstraintValidator<EnumValidator, String> {
    private EnumValidator annotation;

    @Override
    public void initialize(EnumValidator constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Class<? extends java.lang.Enum<?>> enumClass = annotation.enumClass();
        boolean isValid = Arrays.stream(enumClass.getEnumConstants())
                .anyMatch(e -> e.name().equals(value));

        if (!isValid) {
            String allowedValues = Arrays.stream(enumClass.getEnumConstants())
                    .map(java.lang.Enum::name)
                    .collect(Collectors.joining(", "));

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    annotation.message().replace("{allowedValues}", allowedValues)
            ).addConstraintViolation();
        }

        return isValid;
    }
}
