package com.example.cryptotradingsimulator.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.HashMap;
import java.util.Map;

@Table("wallets")
public class Wallet {
    @Id
    private long id;

    private long accountId;

    private Map<String, Holding> cryptoCurrenciesHoldings = new HashMap<>();

    public Wallet() {

    }

    public Wallet(Long id, Long accountId) {
        setId(id);
        setAccountId(accountId);
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

    public Map<String, Holding> getCryptoCurrenciesHoldings() {
        return cryptoCurrenciesHoldings;
    }

    public void setCryptoCurrenciesHoldings(Map<String, Holding> cryptoCurrenciesHoldings) {
        this.cryptoCurrenciesHoldings = cryptoCurrenciesHoldings;
    }
}
