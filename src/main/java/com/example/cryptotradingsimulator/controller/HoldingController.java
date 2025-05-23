package com.example.cryptotradingsimulator.controller;

import com.example.cryptotradingsimulator.model.Holding;
import com.example.cryptotradingsimulator.service.HoldingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api/accounts/{accountId}/wallet/{walletId}/holdings")
public class HoldingController {

    private final HoldingService holdingService;

    @Autowired
    public HoldingController(HoldingService holdingService) {
        this.holdingService = holdingService;
    }

    @ResponseBody
    @GetMapping
    public ResponseEntity<List<Holding>> getHoldingsByWalletId(@PathVariable long walletId) {
        List<Holding> holdings = holdingService.getHoldingsByWalletId(walletId);
        return new ResponseEntity<>(holdings, HttpStatus.OK);
    }
}
