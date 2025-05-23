package com.example.cryptotradingsimulator.repository;

import com.example.cryptotradingsimulator.model.Transaction;
import com.example.cryptotradingsimulator.repository.maper.TransactionRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TransactionRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TransactionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Transaction save(Transaction transaction) {
        String sql = "INSERT INTO transactions(account_id, currency_symbol, amount, price, profit, date) VALUES (?, ?, ?, ?, ?, ?);";

        jdbcTemplate.update(
            sql,
            transaction.getAccountId(),
            transaction.getCurrencySymbol(),
            transaction.getAmount(),
            transaction.getPrice(),
            transaction.getProfit(),
            transaction.getDate());

        Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        transaction.setId(id);
        return transaction;
    }

    public List<Transaction> selectAll() {
        String sql = "SELECT * FROM transactions;";
        return jdbcTemplate.query(sql, new TransactionRowMapper());
    }

    public List<Transaction> findByAccountId(long accountId) {
        String sql = "SELECT * FROM transactions WHERE account_id = ?;";
        return jdbcTemplate.query(sql, new TransactionRowMapper(), accountId);
    }

    public Optional<Transaction> findById(long id) {
        String sql = "SELECT * FROM transactions WHERE id = ?;";
        List<Transaction> transactions = jdbcTemplate.query(sql, new TransactionRowMapper(), id);
        return transactions.stream().findFirst();
    }

    public void deleteById(long id) {
        String sql = "DELETE FROM transactions WHERE id = ?;";
        jdbcTemplate.update(sql, id);
    }

    public void deleteByAccountId(long accountId) {
        String sql = "DELETE FROM transactions WHERE account_id = ?;";
        jdbcTemplate.update(sql, accountId);
    }
}