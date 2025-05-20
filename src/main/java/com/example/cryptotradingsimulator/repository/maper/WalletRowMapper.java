package com.example.cryptotradingsimulator.repository.maper;

import com.example.cryptotradingsimulator.model.Wallet;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WalletRowMapper implements RowMapper<Wallet> {
    @Override
    public Wallet mapRow(ResultSet rs, int rowNum) throws SQLException {
        Wallet wallet = new Wallet();
        wallet.setId(rs.getLong("id"));
        wallet.setAccountId(rs.getLong("account_id"));

        return wallet;
    }
}