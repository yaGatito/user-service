package com.lazovskyi.userservice.config.bean;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class UserValidationProperties {

    @Value("${management.business.permitted-age}")
    private int permittedAge;
}
