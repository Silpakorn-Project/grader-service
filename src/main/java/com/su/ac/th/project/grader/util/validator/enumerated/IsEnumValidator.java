package com.su.ac.th.project.grader.util.validator.enumerated;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.stream.Collectors;


public class IsEnumValidator implements ConstraintValidator<IsEnum, String> {
    private IsEnum annotation;

    @Override
    public void initialize(IsEnum constraintAnnotation) {
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
