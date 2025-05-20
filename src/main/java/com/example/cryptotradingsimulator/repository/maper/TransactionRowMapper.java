package com.example.cryptotradingsimulator.repository.maper;

import com.example.cryptotradingsimulator.model.Transaction;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionRowMapper implements RowMapper<Transaction> {
    @Override
    public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
        Transaction transaction = new Transaction();
        transaction.setId(rs.getLong("id"));
        transaction.setAccountId(rs.getLong("account_id"));
        transaction.setCurrencySymbol(rs.getString("currency_symbol"));
        transaction.setAmount(rs.getBigDecimal("amount"));
        transaction.setPrice(rs.getBigDecimal("price"));
        transaction.setProfit(rs.getBigDecimal("profit"));
        transaction.setDate(rs.getTimestamp("date").toLocalDateTime());

        return transaction;
    }
}