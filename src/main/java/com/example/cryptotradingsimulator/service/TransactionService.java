package com.example.cryptotradingsimulator.service;

import com.example.cryptotradingsimulator.exception.AccountNotFoundException;
import com.example.cryptotradingsimulator.exception.InsufficientBalanceException;
import com.example.cryptotradingsimulator.exception.InsufficientCurrencyAmountException;
import com.example.cryptotradingsimulator.exception.NegativeAmountException;
import com.example.cryptotradingsimulator.model.Account;
import com.example.cryptotradingsimulator.model.Holding;
import com.example.cryptotradingsimulator.model.TickerData;
import com.example.cryptotradingsimulator.model.Transaction;
import com.example.cryptotradingsimulator.model.Wallet;
import com.example.cryptotradingsimulator.repository.AccountRepository;
import com.example.cryptotradingsimulator.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TransactionService {
    private static final String TRANSACTION_NOT_FOUND_ERROR_MESSAGE = "Transaction not found";
    private static final String NEGATIVE_AMOUNT_ERROR_MESSAGE = "Amount cannot be negative";
    private static final String INSUFFICIENT_BALANCE_ERROR_MESSAGE = "Insufficient balance";
    private static final String INSUFFICIENT_CURRENCY_AMOUNT_ERROR_MESSAGE = "Insufficient currency amounts";

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final HoldingService holdingService;
    private final KrakenTickerService krakenTickerService;
    private final WalletService walletService;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository,
                              AccountRepository accountRepository,
                              HoldingService holdingService,
                              KrakenTickerService krakenTickerService,
                              WalletService walletService) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.holdingService = holdingService;
        this.krakenTickerService = krakenTickerService;
        this.walletService = walletService;
    }

    public Transaction saveNewTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.selectAll();
    }

    public List<Transaction> getTransactionsByAccountId(long accountId) {
        return transactionRepository.findByAccountId(accountId);
    }

    public void deleteTransactionById(Long id) {
        transactionRepository.deleteById(id);
    }

    public void deleteTransactionsByAccountId(long accountId) {
        transactionRepository.deleteByAccountId(accountId);
    }

    public Transaction buyCryptoCurrency(Transaction transaction) {
        Account account = getAccount(transaction);

        TickerData ticker = getTickerData(transaction.getCurrencySymbol());

        BigDecimal pricePerUnit = ticker.getAsk();
        BigDecimal totalPrice = pricePerUnit.multiply(transaction.getAmount());

        if (account.getBalance().compareTo(totalPrice) < 0) {
            throw new InsufficientBalanceException(INSUFFICIENT_BALANCE_ERROR_MESSAGE);
        }

        transaction.setDate(LocalDateTime.now());
        transaction.setPrice(pricePerUnit);
        transaction.setProfit(BigDecimal.ZERO);
        transactionRepository.save(transaction);

        account.setBalance(account.getBalance().subtract(totalPrice));
        accountRepository.update(account);

        holdingService.increaseHolding(
            account.getWallet().getId(),
            transaction.getCurrencySymbol(),
            transaction.getAmount());

        return transaction;
    }

    public Transaction sellCryptoCurrency(Transaction transaction) {
        Account account = getAccount(transaction);

        TickerData ticker = getTickerData(transaction.getCurrencySymbol());

        BigDecimal pricePerUnit = ticker.getBid();
        BigDecimal totalPrice = pricePerUnit.multiply(transaction.getAmount());

        Wallet wallet = account.getWallet();
        Map<String, Holding> holdings = wallet.getCryptoCurrenciesHoldings();
        Holding holding = holdings.get(transaction.getCurrencySymbol());

        if (holding == null || holding.getCryptoCurrencyAmount().compareTo(transaction.getAmount()) < 0) {
            throw new InsufficientCurrencyAmountException(INSUFFICIENT_CURRENCY_AMOUNT_ERROR_MESSAGE);
        }

        transaction.setDate(LocalDateTime.now());
        transaction.setPrice(pricePerUnit);
        transaction.setProfit(totalPrice);
        transactionRepository.save(transaction);

        account.setBalance(account.getBalance().add(totalPrice));
        accountRepository.update(account);

        holdingService.updateHoldingAmount(holding.getId(),
            holding.getCryptoCurrencyAmount().subtract(transaction.getAmount()));

        return transaction;
    }

    private Account getAccount(Transaction transaction) {
        Optional<Account> account = accountRepository.findById(transaction.getAccountId());

        if (account.isEmpty()) {
            throw new AccountNotFoundException("Account not found");
        }

        if (transaction.getAmount() == null || transaction.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new NegativeAmountException(NEGATIVE_AMOUNT_ERROR_MESSAGE);
        }

        Account foundAccount = account.get();
        Wallet wallet = walletService.getWalletByAccountId(foundAccount.getId());
        foundAccount.setWallet(wallet);

        return foundAccount;
    }

    private TickerData getTickerData(String currencySymbol) {
        TickerData ticker = krakenTickerService.getTickerMap().get(currencySymbol);
        if (ticker == null || ticker.getBid() == null) {
            throw new IllegalStateException("No price data available for: " + currencySymbol);
        }

        return ticker;
    }
}