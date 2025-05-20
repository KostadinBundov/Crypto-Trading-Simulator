package com.example.cryptotradingsimulator.model;

import org.springframework.data.annotation.Id;

public class CryptoCurrency {
    @Id
    private String symbol;

    private String name;
    private String currency;

    public CryptoCurrency() {

    }

    public CryptoCurrency(String symbol, String name, String currency) {
        setSymbol(symbol);
        setName(name);
        setCurrency(currency);
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}