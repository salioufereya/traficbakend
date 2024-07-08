package com.itma.gestionProjet.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ContactMobileAlreadyExistsException  extends  RuntimeException{
    private String message;

    public ContactMobileAlreadyExistsException(String message)
    {
        super(message);
    }
}
