package ru.test.tplatfom.exceptions.test;

public class TestFinishedException extends RuntimeException {
    public TestFinishedException(Long id) {
        super("Test " + id + " already finished");
    }
}
