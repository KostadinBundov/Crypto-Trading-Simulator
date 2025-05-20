package com.example.cryptotradingsimulator.repository;

import com.example.cryptotradingsimulator.model.Account;
import com.example.cryptotradingsimulator.repository.maper.AccountRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AccountRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AccountRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Account save(Account account) {
        String sql = """
            INSERT INTO accounts (first_name, last_name, email, phone_number, balance) VALUES (?, ?, ?, ?, ?);
            """;

        jdbcTemplate.update(
            sql,
            account.getFirstName(),
            account.getLastName(),
            account.getEmail(),
            account.getPhoneNumber(),
            account.getBalance());

        Long id = getAccountId(account.getEmail());
        account.setId(id);

        return account;
    }

    public List<Account> selectAll() {
        String sql = "SELECT * FROM accounts;";
        return jdbcTemplate.query(sql, new AccountRowMapper());
    }

    public void update(Account account) {
        String sql = """
            UPDATE accounts SET first_name = ?, last_name = ?, email = ?, phone_number = ?, balance = ? WHERE id = ?;
            """;

        jdbcTemplate.update(sql,
            account.getFirstName(),
            account.getLastName(),
            account.getEmail(),
            account.getPhoneNumber(),
            account.getBalance(),
            account.getId());
    }

    public Optional<Account> findByEmail(String email) {
        String sql = "SELECT * FROM accounts WHERE email = ?;";

        List<Account> accounts = jdbcTemplate.query(sql, new AccountRowMapper(), email);
        return accounts.stream().findFirst();
    }

    public Optional<Account> findById(long id) {
        String sql = "SELECT * FROM accounts WHERE id = ?;";

        // Prevent queryForObject to throw an EmptyResultDataAccessException if there are no rows matching in the table
        List<Account> accounts = jdbcTemplate.query(sql, new AccountRowMapper(), id);
        return accounts.stream().findFirst();
    }

    public void deleteById(long id) {
        String sql = "DELETE FROM accounts WHERE id = ?;";
        jdbcTemplate.update(sql, id);
    }

    private Long getAccountId(String email) {
        String sql = "SELECT id FROM accounts WHERE email = ?;";
        return jdbcTemplate.queryForObject(sql, Long.class, email);
    }
}