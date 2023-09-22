package com.kush.banbah.soloprojectbackend.exceptions.EntityDoesNotBelongToClass;

import com.kush.banbah.soloprojectbackend.exceptions.EntityDoesNotBelongException;

public class UserDoesNotBelongToClassException extends EntityDoesNotBelongException {
    public UserDoesNotBelongToClassException(String message) {
        super(message);
    }
}
