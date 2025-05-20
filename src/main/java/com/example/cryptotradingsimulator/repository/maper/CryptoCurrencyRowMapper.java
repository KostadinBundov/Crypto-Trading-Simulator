package com.example.cryptotradingsimulator.repository.maper;

import com.example.cryptotradingsimulator.model.CryptoCurrency;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CryptoCurrencyRowMapper implements RowMapper<CryptoCurrency> {
    @Override
    public CryptoCurrency mapRow(ResultSet rs, int rowNum) throws SQLException {
        CryptoCurrency cryptoCurrency = new CryptoCurrency();
        cryptoCurrency.setSymbol(rs.getString("symbol"));
        cryptoCurrency.setName(rs.getString("name"));
        cryptoCurrency.setCurrency(rs.getString("currency"));

        return cryptoCurrency;
    }
}