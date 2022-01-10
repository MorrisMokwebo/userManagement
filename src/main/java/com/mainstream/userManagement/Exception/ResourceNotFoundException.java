package com.mainstream.userManagement.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * handles exceptions when date is not found
 */

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
    private static  final long  serialVersionUID = 11;

    public ResourceNotFoundException(String message){
        super(message);
    }
}
