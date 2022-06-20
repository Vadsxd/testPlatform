package ru.test.tplatfom.exceptions.question;

public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(Long id) {
        super("No question with id " + id);
    }
}
