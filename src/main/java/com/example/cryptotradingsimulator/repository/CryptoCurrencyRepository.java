package com.example.cryptotradingsimulator.repository;

import com.example.cryptotradingsimulator.model.CryptoCurrency;
import com.example.cryptotradingsimulator.repository.maper.CryptoCurrencyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CryptoCurrencyRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    CryptoCurrencyRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CryptoCurrency> selectAll() {
        String sql = "SELECT * FROM crypto_currencies ORDER BY symbol;";
        return jdbcTemplate.query(sql, new CryptoCurrencyRowMapper());
    }

    public Optional<CryptoCurrency> findBySymbol(String symbol) {
        String sql = "SELECT * FROM crypto_currencies WHERE symbol = ?;";
        List<CryptoCurrency> currencies = jdbcTemplate.query(sql, new CryptoCurrencyRowMapper(), symbol);
        return currencies.stream().findFirst();
    }
}