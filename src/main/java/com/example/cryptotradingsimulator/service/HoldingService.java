package com.example.cryptotradingsimulator.service;

import com.example.cryptotradingsimulator.exception.HoldingNotFoundException;
import com.example.cryptotradingsimulator.model.Holding;
import com.example.cryptotradingsimulator.repository.HoldingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class HoldingService {
    private static final String HOLDING_NOT_FOUND_ERROR_MESSAGE = "Holding not found";

    private final HoldingRepository holdingRepository;

    @Autowired
    public HoldingService(HoldingRepository holdingRepository) {
        this.holdingRepository = holdingRepository;
    }

    public Holding saveNewHolding(Holding holding) {
        return holdingRepository.save(holding);
    }

    public List<Holding> getAllHoldings() {
        return holdingRepository.selectAll();
    }

    public Holding getHoldingById(long id) {
        Optional<Holding> holding = holdingRepository.findById(id);
        if (holding.isEmpty()) {
            throw new HoldingNotFoundException(HOLDING_NOT_FOUND_ERROR_MESSAGE);
        }

        return holding.get();
    }

    public List<Holding> getHoldingsByWalletId(long walletId) {
        return holdingRepository.findByWalletId(walletId);
    }

    public void updateHoldingAmount(long id, BigDecimal amount) {
        Holding holding = getHoldingById(id);
        holding.setCryptoCurrencyAmount(amount);
        holdingRepository.updateAmount(holding);
    }

    public void deleteHoldingById(Long id) {
        getHoldingById(id);
        holdingRepository.deleteById(id);
    }

    public void increaseHolding(long walletId, String currencySymbol, BigDecimal amount) {
        List<Holding> holdings = getHoldingsByWalletId(walletId);
        Optional<Holding> optionalHolding = holdings
            .stream()
            .filter(h -> h.getCryptoCurrencySymbol().equals(currencySymbol))
            .findFirst();

        if (optionalHolding.isPresent()) {
            Holding holding = optionalHolding.get();
            holding.setCryptoCurrencyAmount(holding.getCryptoCurrencyAmount().add(amount));
            holdingRepository.updateAmount(holding);
        } else {
            Holding holding = new Holding();
            holding.setWalletId(walletId);
            holding.setCryptoCurrencySymbol(currencySymbol);
            holding.setCryptoCurrencyAmount(amount);
            saveNewHolding(holding);
        }
    }
}
