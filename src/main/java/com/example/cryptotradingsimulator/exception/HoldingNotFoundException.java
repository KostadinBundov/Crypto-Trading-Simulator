package com.example.cryptotradingsimulator.exception;

public class HoldingNotFoundException extends RuntimeException {
    public HoldingNotFoundException(String message) {
        super(message);
    }
}
