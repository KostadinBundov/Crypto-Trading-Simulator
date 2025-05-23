package com.example.cryptotradingsimulator.controller;

import com.example.cryptotradingsimulator.model.TickerData;
import com.example.cryptotradingsimulator.service.KrakenTickerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api/cryptocurrencies")
public class CryptoCurrencyController {
    private final KrakenTickerService krakenTickerService;

    @Autowired
    public CryptoCurrencyController(KrakenTickerService krakenTickerService) {
        this.krakenTickerService = krakenTickerService;
    }

    @ResponseBody
    @GetMapping
    public ResponseEntity<List<TickerData>> getAllCryptoCurrencies() {
        List<TickerData> data = krakenTickerService.getTickerMap().values().stream().toList();
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}