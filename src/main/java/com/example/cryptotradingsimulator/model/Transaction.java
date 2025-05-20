package com.example.cryptotradingsimulator.model;

import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    @Id
    private long id;

    private String currencySymbol;
    private BigDecimal amount;
    private BigDecimal price;
    private BigDecimal profit;
    private LocalDateTime date;

    private long accountId;

    public Transaction() {

    }

    public Transaction(long id, String currencySymbol, BigDecimal amount, BigDecimal price, BigDecimal profit,
                       LocalDateTime date, long accountId) {
        setId(id);
        setCurrencySymbol(currencySymbol);
        setAmount(amount);
        setPrice(price);
        setProfit(profit);
        setDate(date);
        setAccountId(accountId);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
