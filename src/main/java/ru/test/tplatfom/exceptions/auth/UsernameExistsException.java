package ru.test.tplatfom.exceptions.auth;

public class UsernameExistsException extends RuntimeException{
    public UsernameExistsException() {
    }

    public UsernameExistsException(String message) {
        super(message);
    }
}
