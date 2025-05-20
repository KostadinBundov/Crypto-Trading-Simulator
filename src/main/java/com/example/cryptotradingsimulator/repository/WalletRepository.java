package com.example.cryptotradingsimulator.repository;

import com.example.cryptotradingsimulator.model.Wallet;
import com.example.cryptotradingsimulator.repository.maper.WalletRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class WalletRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public WalletRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Wallet save(Wallet wallet) {
        String sql = "INSERT INTO wallets (account_id) VALUES (?);";
        jdbcTemplate.update(sql, wallet.getAccountId());

        Long walletId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        wallet.setId(walletId);

        return wallet;
    }

    public List<Wallet> selectAll() {
        String sql = "SELECT * FROM wallets;";
        return jdbcTemplate.query(sql, new WalletRowMapper());
    }

    public void update(Wallet account) {
        String sql = "UPDATE wallets SET account_id = ? WHERE id = ?;";
        jdbcTemplate.update(sql, account.getAccountId(), account.getId());
    }

    public Optional<Wallet> findByAccountId(long accountId) {
        String sql = "SELECT * FROM wallets WHERE account_id = ?;";
        List<Wallet> wallets = jdbcTemplate.query(sql, new WalletRowMapper(), accountId);

        return wallets.stream().findFirst();
    }

    public Optional<Wallet> findById(long id) {
        String sql = "SELECT * FROM wallets WHERE id = ?;";
        List<Wallet> wallets = jdbcTemplate.query(sql, new WalletRowMapper(), id);
        return wallets.stream().findFirst();
    }

    public void deleteById(long id) {
        String sql = "DELETE FROM wallets WHERE id = ?;";
        jdbcTemplate.update(sql, id);
    }

    private Long getWalletId(long accountId) {
        String sql = "SELECT id FROM wallets WHERE account_id = ?;";
        return jdbcTemplate.queryForObject(sql, Long.class, accountId);
    }
}