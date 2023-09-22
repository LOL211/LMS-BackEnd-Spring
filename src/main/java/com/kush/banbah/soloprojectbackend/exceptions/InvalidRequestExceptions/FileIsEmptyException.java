package com.kush.banbah.soloprojectbackend.exceptions.InvalidRequestExceptions;

import com.kush.banbah.soloprojectbackend.exceptions.InvalidRequestException;

public class FileIsEmptyException extends InvalidRequestException {
    public FileIsEmptyException(String message) {
        super(message);
    }
}
