package com.kush.banbah.soloprojectbackend.exceptions;

public class ClassDoesNotExistException extends Exception {
    public ClassDoesNotExistException(String className) {
        super("Class " + className + " does not exist");
    }
}
