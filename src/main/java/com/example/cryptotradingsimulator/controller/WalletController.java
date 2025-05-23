package com.example.cryptotradingsimulator.controller;

import com.example.cryptotradingsimulator.model.Wallet;
import com.example.cryptotradingsimulator.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/accounts/{accountId}/wallet")
public class WalletController {

    private final WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @ResponseBody
    @GetMapping
    public ResponseEntity<Wallet> getWalletByAccountId(@PathVariable long accountId) {
        Wallet wallet = walletService.getWalletByAccountId(accountId);
        return new ResponseEntity<>(wallet, HttpStatus.OK);
    }
}