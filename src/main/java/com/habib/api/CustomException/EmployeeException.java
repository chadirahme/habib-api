package com.habib.api.CustomException;


public class EmployeeException extends RuntimeException {
    public EmployeeException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
