package com.example.cryptotradingsimulator.service;

import com.example.cryptotradingsimulator.exception.AccountNotFoundException;
import com.example.cryptotradingsimulator.exception.EmailAlreadyTakenException;
import com.example.cryptotradingsimulator.model.Account;
import com.example.cryptotradingsimulator.model.Transaction;
import com.example.cryptotradingsimulator.model.Wallet;
import com.example.cryptotradingsimulator.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private static final String EMAIL_ALREADY_TAKEN_ERROR_MESSAGE = "Email already taken";
    private static final String ACCOUNT_NOT_FOUND_ERROR_MESSAGE = "Account not found";

    private final AccountRepository accountRepository;
    private final WalletService walletService;
    private final TransactionService transactionService;
    private final HoldingService holdingService;

    @Autowired
    public AccountService(AccountRepository accountRepository,
                          WalletService walletService,
                          TransactionService transactionService,
                          HoldingService holdingService) {
        this.accountRepository = accountRepository;
        this.walletService = walletService;
        this.transactionService = transactionService;
        this.holdingService = holdingService;
    }

    public Account saveNewAccount(Account account) {
        validateAccountExists(account.getEmail());
        Account savedAccount = accountRepository.save(account);

        Wallet wallet = walletService.createWallet(savedAccount.getId());
        savedAccount.setWallet(wallet);

        return savedAccount;
    }

    public List<Account> getAllAccounts() {
        List<Account> accounts = accountRepository.selectAll();

        for (Account account : accounts) {
            setAccountWallet(account);
            setAccountTransactions(account);
        }

        return accounts;
    }

    public Account getAccountById(long id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);

        if (optionalAccount.isEmpty()) {
            throw new AccountNotFoundException(ACCOUNT_NOT_FOUND_ERROR_MESSAGE);
        }

        Account account = optionalAccount.get();

        setAccountWallet(account);
        setAccountTransactions(account);

        return account;
    }

    public void updateAccount(long id, Account updatedAccount)
        throws AccountNotFoundException, EmailAlreadyTakenException {
        Account account = getAccountById(id);

        if (!account.getEmail().equals(updatedAccount.getEmail())) {
            validateAccountExists(updatedAccount.getEmail());
        }

        account.setFirstName(updatedAccount.getFirstName());
        account.setLastName(updatedAccount.getLastName());
        account.setEmail(updatedAccount.getEmail());
        account.setPhoneNumber(updatedAccount.getPhoneNumber());

        accountRepository.update(account);
    }

    public void deleteAccount(long id) {
        getAccountById(id);
        accountRepository.deleteById(id);
    }

    public void resetAccountBalance(long id) {
        Account account = getAccountById(id);
        account.setBalance(Account.INITIAL_BALANCE);

        holdingService.deleteHoldingByWalletId(account.getWallet().getId());
        accountRepository.update(account);
    }

    private void validateAccountExists(String email) {
        Optional<Account> optionalAccount = accountRepository.findByEmail(email);

        if (optionalAccount.isPresent()) {
            throw new EmailAlreadyTakenException(EMAIL_ALREADY_TAKEN_ERROR_MESSAGE);
        }
    }

    private void setAccountWallet(Account account) {
        Wallet wallet = walletService.getWalletByAccountId(account.getId());
        account.setWallet(wallet);
    }

    private void setAccountTransactions(Account account) {
        List<Transaction> transactions = transactionService.getTransactionsByAccountId(account.getId());
        account.setTransactions(transactions);
    }
}