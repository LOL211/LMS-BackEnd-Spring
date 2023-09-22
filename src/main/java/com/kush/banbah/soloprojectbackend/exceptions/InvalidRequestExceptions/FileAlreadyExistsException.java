package com.kush.banbah.soloprojectbackend.exceptions.InvalidRequestExceptions;

import com.kush.banbah.soloprojectbackend.exceptions.InvalidRequestException;

public class FileAlreadyExistsException extends InvalidRequestException {
    public FileAlreadyExistsException(String message) {
        super(message);
    }
}
