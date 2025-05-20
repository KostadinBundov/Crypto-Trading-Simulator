package com.example.cryptotradingsimulator.repository.maper;

import com.example.cryptotradingsimulator.model.Account;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRowMapper implements RowMapper<Account> {
    @Override
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
        Account account = new Account();
        account.setId(rs.getLong("id"));
        account.setFirstName(rs.getString("first_name"));
        account.setLastName(rs.getString("last_name"));
        account.setEmail(rs.getString("email"));
        account.setPhoneNumber(rs.getString("phone_number"));
        account.setBalance(rs.getBigDecimal("balance"));

        return account;
    }
}