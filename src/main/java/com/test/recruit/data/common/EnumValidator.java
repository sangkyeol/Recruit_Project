package com.test.recruit.data.common;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<ValidEnum, Object> {
    private ValidEnum annotation;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object[] enumValues = this.annotation.enumClass().getEnumConstants();

        // 정의한 EnumClass 검증
        if (enumValues != null) {
            for (Object enumValue : enumValues) {
                if (value.equals(enumValue) ) {
                    return true;
                }
            }
        }

        return false;
    }
}
