package com.example.cryptotradingsimulator.exception;

public class InsufficientCurrencyAmountException extends RuntimeException {
    public InsufficientCurrencyAmountException(String message) {
        super(message);
    }
}
