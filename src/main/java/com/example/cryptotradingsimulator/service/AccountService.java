package com.example.cryptotradingsimulator.service;

import com.example.cryptotradingsimulator.model.Account;
import com.example.cryptotradingsimulator.model.Transaction;
import com.example.cryptotradingsimulator.model.Wallet;
import com.example.cryptotradingsimulator.repository.AccountRepository;
import com.example.cryptotradingsimulator.repository.TransactionRepository;
import com.example.cryptotradingsimulator.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository,
                          WalletRepository walletRepository,
                          TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
    }

    public Account save(Account account) {
        Optional<Account> optionalAccount = accountRepository.findByEmail(account.getEmail());

        if (optionalAccount.isPresent()) {
            // throw custom exception
        }

        return accountRepository.save(account);
    }

    public List<Account> getAllAccounts() {
        List<Account> accounts = accountRepository.selectAll();

        for (Account account : accounts) {
            getAccountById(account.getId());
        }

        return accounts;
    }

    public Account getAccountById(long id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);

        if (optionalAccount.isEmpty()) {
            // throw custom exception
        }

        Account account = optionalAccount.get();

        Optional<Wallet> optionalWallet = walletRepository.findByAccountId(account.getId());
        optionalWallet.ifPresent(account::setWallet);

        List<Transaction> transactions = transactionRepository.findByAccountId(account.getId());
        account.setTransactions(transactions);

        return account;
    }

    public void update(long id, Account updatedAccount) {
        Account account = getAccountById(id);

        account.setFirstName(updatedAccount.getFirstName());
        account.setLastName(updatedAccount.getLastName());
        account.setEmail(updatedAccount.getEmail());
        account.setPhoneNumber(updatedAccount.getPhoneNumber());
        account.setBalance(updatedAccount.getBalance());

        accountRepository.update(account);
    }

    public void deleteById(long id) {
        Account account = getAccountById(id);
        accountRepository.deleteById(id);
    }
}