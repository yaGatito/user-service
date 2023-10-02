package com.lazovskyi.userservice.dto.validation.string;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StringValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
/**
 * Used to validate specified strings in com.lazovskyi.dto.validation.string.StringType
 * When the string is null return true
 */
public @interface ValidateString {

    StringType value();

    String message() default "invalid string";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
