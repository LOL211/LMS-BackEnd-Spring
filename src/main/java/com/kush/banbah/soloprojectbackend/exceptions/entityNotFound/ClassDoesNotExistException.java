package com.kush.banbah.soloprojectbackend.exceptions.entityNotFound;

import com.kush.banbah.soloprojectbackend.exceptions.EntityNotFoundException;

public class ClassDoesNotExistException extends EntityNotFoundException {
    public ClassDoesNotExistException(String className) {
        super("Class " + className + " does not exist");
    }
}
