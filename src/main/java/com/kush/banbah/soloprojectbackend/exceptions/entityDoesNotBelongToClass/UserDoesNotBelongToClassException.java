package com.kush.banbah.soloprojectbackend.exceptions.entityDoesNotBelongToClass;

import com.kush.banbah.soloprojectbackend.exceptions.EntityDoesNotBelongException;

public class UserDoesNotBelongToClassException extends EntityDoesNotBelongException {
    public UserDoesNotBelongToClassException(String message) {
        super(message);
    }
}
