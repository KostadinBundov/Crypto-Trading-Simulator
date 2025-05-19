package com.example.cryptotradingsimulator.model;

import org.springframework.data.annotation.Id;

public class CryptoCurrency {
    @Id
    private String name;

    private String symbol;

    public CryptoCurrency() {

    }

    public CryptoCurrency(String name, String symbol) {
        setName(name);
        setSymbol(symbol);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
