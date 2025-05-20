package com.example.cryptotradingsimulator.model;

import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

public class Holding {
    @Id
    private long id;

    private long walletId;
    private String cryptoCurrencySymbol;
    private BigDecimal cryptoCurrencyAmount;

    public Holding() {

    }

    public Holding(long id, long walletId, String cryptoCurrencySymbol, BigDecimal cryptoCurrencyAmount) {
        setId(id);
        setWalletId(walletId);
        setCryptoCurrencySymbol(cryptoCurrencySymbol);
        setCryptoCurrencyAmount(cryptoCurrencyAmount);
    }

    public Holding(long walletId, String cryptoCurrencySymbol, BigDecimal cryptoCurrencyAmount) {
        setWalletId(walletId);
        setCryptoCurrencySymbol(cryptoCurrencySymbol);
        setCryptoCurrencyAmount(cryptoCurrencyAmount);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getWalletId() {
        return walletId;
    }

    public void setWalletId(long walletId) {
        this.walletId = walletId;
    }

    public String getCryptoCurrencySymbol() {
        return cryptoCurrencySymbol;
    }

    public void setCryptoCurrencySymbol(String cryptoCurrencySymbol) {
        this.cryptoCurrencySymbol = cryptoCurrencySymbol;
    }

    public BigDecimal getCryptoCurrencyAmount() {
        return cryptoCurrencyAmount;
    }

    public void setCryptoCurrencyAmount(BigDecimal cryptoCurrencyAmount) {
        this.cryptoCurrencyAmount = cryptoCurrencyAmount;
    }
}
