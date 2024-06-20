package io.hhplus.tdd.exception;

public class InsufficientBalanceException extends  RuntimeException{
    private String code = null;

    public InsufficientBalanceException() {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
