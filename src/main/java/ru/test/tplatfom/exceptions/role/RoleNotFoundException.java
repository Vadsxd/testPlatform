package ru.test.tplatfom.exceptions.role;

public class RoleNotFoundException extends RuntimeException{
    public RoleNotFoundException() {
    }

    public RoleNotFoundException(String message) {
        super(message);
    }
}
