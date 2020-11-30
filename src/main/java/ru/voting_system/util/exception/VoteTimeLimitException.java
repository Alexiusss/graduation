package ru.voting_system.util.exception;

public class VoteTimeLimitException extends RuntimeException {
    public VoteTimeLimitException(String message) {
        super(message);
    }
}
