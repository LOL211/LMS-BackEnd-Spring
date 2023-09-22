package com.kush.banbah.soloprojectbackend.exceptions.entityNotFound;

import com.kush.banbah.soloprojectbackend.exceptions.EntityNotFoundException;

public class FileNotExistException extends EntityNotFoundException {
    public FileNotExistException(String s) {
        super(s);
    }
}
