package com.example.cryptotradingsimulator.service;

import com.example.cryptotradingsimulator.exception.CryptoCurrencyNotFoundException;
import com.example.cryptotradingsimulator.model.CryptoCurrency;
import com.example.cryptotradingsimulator.model.TickerData;
import com.example.cryptotradingsimulator.repository.CryptoCurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CryptoCurrencyService {
    private static final String CRYPTO_CURRENCY_NOT_FOUND_ERROR_MESSAGE = "Crypto currency not found";

    private final CryptoCurrencyRepository cryptoCurrencyRepository;

    @Autowired
    public CryptoCurrencyService(CryptoCurrencyRepository cryptoCurrencyRepository) {
        this.cryptoCurrencyRepository = cryptoCurrencyRepository;
    }

    public List<CryptoCurrency> getAllCryptoCurrencies() {
        return cryptoCurrencyRepository.selectAll();
    }

    public CryptoCurrency getCryptoCurrencyBySymbol(String symbol) {
        Optional<CryptoCurrency> cryptoCurrency = cryptoCurrencyRepository.findBySymbol(symbol);

        if (cryptoCurrency.isEmpty()) {
            throw new CryptoCurrencyNotFoundException(CRYPTO_CURRENCY_NOT_FOUND_ERROR_MESSAGE);
        }

        return cryptoCurrency.get();
    }
}