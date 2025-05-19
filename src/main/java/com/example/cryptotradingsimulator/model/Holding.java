package com.example.cryptotradingsimulator.model;

import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

public class Holding {
    @Id
    private long id;

    private long walletId;
    private String cryptoCurrencyName;
    private BigDecimal cryptoCurrencyAmount;

    public Holding() {

    }

    public Holding(long id, long walletId, String cryptoCurrencyName, BigDecimal cryptoCurrencyAmount) {
        setId(id);
        setWalletId(walletId);
        setCryptoCurrencyName(cryptoCurrencyName);
        setCryptoCurrencyAmount(cryptoCurrencyAmount);
    }

    public Holding(long walletId, String cryptoCurrencyName, BigDecimal cryptoCurrencyAmount) {
        setWalletId(walletId);
        setCryptoCurrencyName(cryptoCurrencyName);
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

    public String getCryptoCurrencyName() {
        return cryptoCurrencyName;
    }

    public void setCryptoCurrencyName(String cryptoCurrencyName) {
        this.cryptoCurrencyName = cryptoCurrencyName;
    }

    public BigDecimal getCryptoCurrencyAmount() {
        return cryptoCurrencyAmount;
    }

    public void setCryptoCurrencyAmount(BigDecimal cryptoCurrencyAmount) {
        this.cryptoCurrencyAmount = cryptoCurrencyAmount;
    }
}
