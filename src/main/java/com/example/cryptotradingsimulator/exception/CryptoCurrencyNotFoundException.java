package com.example.cryptotradingsimulator.exception;

public class CryptoCurrencyNotFoundException extends RuntimeException {
    public CryptoCurrencyNotFoundException(String message) {
        super(message);
    }
}
