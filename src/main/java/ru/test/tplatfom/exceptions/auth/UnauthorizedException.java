package ru.test.tplatfom.exceptions.auth;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("Unauthorized");
    }
}
