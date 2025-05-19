package com.example.cryptotradingsimulator.model;

import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.Map;

public class Wallet {
    private static final BigDecimal INITIAL_BALANCE = new BigDecimal(10_000);

    @Id
    private long id;

    private long accountId;
    private BigDecimal balance;
    private Map<String, BigDecimal> cryptoCurrenciesHoldings;

    public Wallet() {

    }

    public Wallet(Long id, Long accountId, BigDecimal fiatBalance, Map<String, BigDecimal> cryptoHoldings) {
        setId(id);
        setAccountId(accountId);
        setBalance(fiatBalance);
        setCryptoCurrenciesHoldings(cryptoHoldings);
    }

    public Wallet(Long accountId, BigDecimal fiatBalance, Map<String, BigDecimal> cryptoHoldings) {
        setAccountId(accountId);
        setBalance(fiatBalance);
        setCryptoCurrenciesHoldings(cryptoHoldings);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Map<String, BigDecimal> getCryptoCurrenciesHoldings() {
        return cryptoCurrenciesHoldings;
    }

    public void setCryptoCurrenciesHoldings(Map<String, BigDecimal> cryptoCurrenciesHoldings) {
        this.cryptoCurrenciesHoldings = cryptoCurrenciesHoldings;
    }
}
