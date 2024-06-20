package io.hhplus.tdd.exception;

public class UserNotFoundException extends RuntimeException{
    private final String code;

    public UserNotFoundException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
