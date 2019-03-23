package com.habib.api.rest;


import com.habib.api.CustomException.EmployeeException;
import com.habib.api.domains.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class RestExceptionHandler {
//    @RequestMapping(produces = "application/json")
//    @ExceptionHandler(value = { Exception.class })
//    public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) {
//        return new ResponseEntity<>(
//                ex.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleDefaultException(Exception ex) {
        ErrorResponse response = new ErrorResponse();
        response.setMessage(ex.getMessage());
        return response;
    }

    @ExceptionHandler(EmployeeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleEmployeeDefaultException(Exception ex) {
        ErrorResponse response = new ErrorResponse();
        response.setMessage("employee: "+ex.getMessage());
        return response;
    }
}
