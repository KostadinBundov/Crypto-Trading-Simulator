package com.example.cryptotradingsimulator.service;

import com.example.cryptotradingsimulator.exception.WalletNotFoundException;
import com.example.cryptotradingsimulator.model.Holding;
import com.example.cryptotradingsimulator.model.Wallet;
import com.example.cryptotradingsimulator.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WalletService {
    private static final String WALLET_NOT_FOUND_ERROR_MESSAGE = "Wallet not found";

    private final WalletRepository walletRepository;
    private final HoldingService holdingService;

    @Autowired
    public WalletService(WalletRepository walletRepository, HoldingService holdingService) {
        this.walletRepository = walletRepository;
        this.holdingService = holdingService;
    }

    public Wallet createWallet(long accountId) {
        Wallet wallet = new Wallet();
        wallet.setAccountId(accountId);
        return walletRepository.save(wallet);
    }

    public List<Wallet> getAllWallets() {
        List<Wallet> wallets = walletRepository.selectAll();
        wallets.forEach(this::setHoldings);
        return wallets;
    }

    public Wallet getWalletById(long id) {
        Optional<Wallet> wallet = walletRepository.findById(id);
        if (wallet.isEmpty()) {
            throw new WalletNotFoundException(WALLET_NOT_FOUND_ERROR_MESSAGE);
        }

        Wallet foundWallet = wallet.get();
        setHoldings(foundWallet);
        return foundWallet;
    }

    public Wallet getWalletByAccountId(long accountId) {
        Optional<Wallet> wallet = walletRepository.findByAccountId(accountId);
        if (wallet.isEmpty()) {
            throw new WalletNotFoundException(WALLET_NOT_FOUND_ERROR_MESSAGE);
        }

        Wallet foundWallet = wallet.get();
        setHoldings(foundWallet);
        return foundWallet;
    }

    public void deleteWalletById(Long id) {
        getWalletById(id);
        walletRepository.deleteById(id);
    }

    public void deleteWalletByAccountId(Long accountId) {
        getWalletByAccountId(accountId);
        walletRepository.deleteByAccountId(accountId);
    }

    private void setHoldings(Wallet wallet) {
        List<Holding> holdings = holdingService.getHoldingsByWalletId(wallet.getId());

        Map<String, Holding> holdingsMap = holdings
            .stream()
            .collect(
                Collectors.toMap(
                    Holding::getCryptoCurrencySymbol,
                    h -> h
                )
            );

        wallet.setCryptoCurrenciesHoldings(holdingsMap);
    }
}