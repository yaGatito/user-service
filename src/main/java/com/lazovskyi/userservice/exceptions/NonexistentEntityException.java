package com.lazovskyi.userservice.exceptions;

import com.lazovskyi.userservice.model.enums.ErrorType;

public class NonexistentEntityException extends ServiceException {

    public NonexistentEntityException(String message) {
        super(message);
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.PROCESSING_ERROR_TYPE;
    }
}
