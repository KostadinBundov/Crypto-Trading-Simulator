package com.example.cryptotradingsimulator.repository.maper;

import com.example.cryptotradingsimulator.model.Holding;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HoldingRowMapper implements RowMapper<Holding> {
    @Override
    public Holding mapRow(ResultSet rs, int rowNum) throws SQLException {
        Holding holding = new Holding();
        holding.setId(rs.getLong("id"));
        holding.setWalletId(rs.getLong("wallet_id"));
        holding.setCryptoCurrencySymbol(rs.getString("crypto_currency_symbol"));
        holding.setCryptoCurrencyAmount(rs.getBigDecimal("crypto_currency_amount"));

        return holding;
    }
}