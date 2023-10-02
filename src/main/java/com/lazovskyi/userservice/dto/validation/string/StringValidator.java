package com.lazovskyi.userservice.dto.validation.string;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashMap;

public class StringValidator implements ConstraintValidator<ValidateString, String> {

    private final String NAME = "^[-' \\p{L}]{2,20}$";
    private final String LASTNAME = "^[-' \\p{L}]{2,20}$";
    private final String PASSWORD = "^\\w{8,20}$";
    private final String NUMBER = "^(\\d{9})$";
    private final String ADDRESS = "[-' ,.\\p{L}\\d]{5,50}$";
    private final HashMap<StringType, String> validations = new HashMap<>();
    private StringType type;

    @Override
    public void initialize(ValidateString constraintAnnotation) {
        validations.put(StringType.NAME, NAME);
        validations.put(StringType.LASTNAME, LASTNAME);
        validations.put(StringType.PASSWORD, PASSWORD);
        validations.put(StringType.NUMBER, NUMBER);
        validations.put(StringType.ADDRESS, ADDRESS);

        type = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String string, ConstraintValidatorContext constraintValidatorContext) {
        if (string == null) {
            return true;
        }
        if (validations.containsKey(type)) {
            return string.matches(validations.get(type));
        } else {
            return false;
        }
    }
}
