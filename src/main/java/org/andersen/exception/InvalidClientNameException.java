package org.andersen.exception;

public class InvalidClientNameException extends RuntimeException {

    public InvalidClientNameException(String message) {
        super(message);
    }
}
