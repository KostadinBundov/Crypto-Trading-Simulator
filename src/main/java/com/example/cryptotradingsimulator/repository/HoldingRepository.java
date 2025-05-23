package com.example.cryptotradingsimulator.repository;

import com.example.cryptotradingsimulator.model.Holding;
import com.example.cryptotradingsimulator.repository.maper.HoldingRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class HoldingRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    HoldingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Holding save(Holding holding) {
        String sql = "INSERT INTO holdings (wallet_id, crypto_currency_symbol, crypto_currency_amount) VALUES (?, ?, ?);";
        jdbcTemplate.update(sql, holding.getWalletId(), holding.getCryptoCurrencySymbol(), holding.getCryptoCurrencyAmount());

        Long holdingId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        holding.setId(holdingId);

        return holding;
    }

    public List<Holding> selectAll() {
        String sql = "SELECT * FROM holdings;";
        return jdbcTemplate.query(sql, new HoldingRowMapper());
    }

    public void updateAmount(Holding holding) {
        String sql = "UPDATE holdings SET crypto_currency_amount = ? WHERE id = ?";
        jdbcTemplate.update(sql, holding.getCryptoCurrencyAmount(), holding.getId());
    }

    public List<Holding> findByWalletId(long walletId) {
        String sql = "SELECT * FROM holdings WHERE wallet_id = ?;";
        return jdbcTemplate.query(sql, new HoldingRowMapper(), walletId);
    }

    public Optional<Holding> findById(long id) {
        String sql = "SELECT * FROM holdings WHERE id = ?;";
        List<Holding> holdings = jdbcTemplate.query(sql, new HoldingRowMapper(), id);
        return holdings.stream().findFirst();
    }

    public void deleteByWalletId(long walletId) {
        String sql = "DELETE FROM holdings WHERE wallet_id = ?;";
        jdbcTemplate.update(sql, walletId);
    }
}