package com.example.cryptotradingsimulator.model;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.Map;

public class TickerData {
    private String cryptoCurrencySymbol;
    private String cryptoCurrencyName;
    private String cryptoCurrencyCurrency;

    private String symbol;
    private BigDecimal bid;

    @SerializedName("bid_qty")
    private BigDecimal bidQty;

    private BigDecimal ask;

    @SerializedName("ask_qty")
    private BigDecimal askQty;

    private BigDecimal last;
    private BigDecimal volume;
    private BigDecimal vwap;
    private BigDecimal low;
    private BigDecimal high;
    private BigDecimal change;

    @SerializedName("change_pct")
    private BigDecimal changePct;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getBid() {
        return bid;
    }

    public void setBid(BigDecimal bid) {
        this.bid = bid;
    }

    public BigDecimal getBidQty() {
        return bidQty;
    }

    public void setBidQty(BigDecimal bidQty) {
        this.bidQty = bidQty;
    }

    public BigDecimal getAsk() {
        return ask;
    }

    public void setAsk(BigDecimal ask) {
        this.ask = ask;
    }

    public BigDecimal getAskQty() {
        return askQty;
    }

    public void setAskQty(BigDecimal askQty) {
        this.askQty = askQty;
    }

    public BigDecimal getLast() {
        return last;
    }

    public void setLast(BigDecimal last) {
        this.last = last;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public BigDecimal getVwap() {
        return vwap;
    }

    public void setVwap(BigDecimal vwap) {
        this.vwap = vwap;
    }

    public BigDecimal getLow() {
        return low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public BigDecimal getChange() {
        return change;
    }

    public void setChange(BigDecimal change) {
        this.change = change;
    }

    public BigDecimal getChangePct() {
        return changePct;
    }

    public void setChangePct(BigDecimal changePct) {
        this.changePct = changePct;
    }

    public void setAdditionalProperties(Map<String, CryptoCurrency> map) {
        String[] args = symbol.split("/");
        this.cryptoCurrencySymbol = args[0];
        this.cryptoCurrencyCurrency = args[1];
        this.cryptoCurrencyName = map.get(cryptoCurrencySymbol).getName();
    }

    public String getCryptoCurrencySymbol() {
        return cryptoCurrencySymbol;
    }

    public String getCryptoCurrencyName() {
        return cryptoCurrencyName;
    }

    public String getCryptoCurrencyCurrency() {
        return cryptoCurrencyCurrency;
    }
}
