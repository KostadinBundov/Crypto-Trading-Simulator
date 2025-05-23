package com.example.cryptotradingsimulator.controller;

import com.example.cryptotradingsimulator.model.Transaction;
import com.example.cryptotradingsimulator.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api/accounts/{accountId}/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @ResponseBody
    @GetMapping
    public ResponseEntity<List<Transaction>> getTransactionsByAccountId(@PathVariable long accountId) {
        List<Transaction> transactions = transactionService.getTransactionsByAccountId(accountId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/buy")
    public ResponseEntity<Transaction> buyCryptoCurrency(@RequestBody Transaction transaction, @PathVariable long accountId) {
        transaction.setAccountId(accountId);
        setFormattedCurrencySymbol(transaction);
        Transaction savedTransaction = transactionService.buyCryptoCurrency(transaction);
        return new ResponseEntity<>(savedTransaction, HttpStatus.CREATED);
    }

    @ResponseBody
    @PostMapping("/sell")
    public ResponseEntity<Transaction> sellCryptoCurrency(@RequestBody Transaction transaction, @PathVariable long accountId) {
        transaction.setAccountId(accountId);
        setFormattedCurrencySymbol(transaction);
        Transaction savedTransaction = transactionService.sellCryptoCurrency(transaction);
        return new ResponseEntity<>(savedTransaction, HttpStatus.CREATED);
    }

    private void setFormattedCurrencySymbol(Transaction transaction) {
        String[] args = transaction.getCurrencySymbol().split("/");
        transaction.setCurrencySymbol(args[0]);
    }
}
