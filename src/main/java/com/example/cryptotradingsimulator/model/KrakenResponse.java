package com.example.cryptotradingsimulator.model;

import java.util.List;

public class KrakenResponse {
    private String channel;
    private String type;
    private List<TickerData> data;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<TickerData> getData() {
        return data;
    }

    public void setData(List<TickerData> data) {
        this.data = data;
    }
}