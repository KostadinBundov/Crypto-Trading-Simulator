package com.example.cryptotradingsimulator.service;

import com.example.cryptotradingsimulator.model.CryptoCurrency;
import com.example.cryptotradingsimulator.model.KrakenResponse;
import com.example.cryptotradingsimulator.model.TickerData;
import com.google.gson.Gson;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class KrakenTickerService implements WebSocket.Listener {
    private static final int CONNECTION_TIMEOUT_SECONDS = 10;
    private static final String KRAKEN_API_URI = "wss://ws.kraken.com/v2";
    private static final String SUBSCRIBE_TEMPLATE = """
        {
          "method": "subscribe",
          "params": {
            "channel": "ticker",
            "symbol": [%s]
          }
        }
        """;

    private WebSocket webSocket;
    private final CryptoCurrencyService cryptoCurrencyService;
    private final Map<String, CryptoCurrency> cryptoCurrencies;
    private final Gson gson = new Gson();
    private final Map<String, TickerData> tickerMap = new ConcurrentHashMap<>();

    private boolean subscribed = false;

    private final String currencyPairsString;

    public KrakenTickerService(CryptoCurrencyService cryptoCurrencyService) {
        this.cryptoCurrencyService = cryptoCurrencyService;

        cryptoCurrencies = cryptoCurrencyService
            .getAllCryptoCurrencies()
            .stream()
            .collect(Collectors.toMap(
                CryptoCurrency::getSymbol,
                c -> c
            ));

        this.currencyPairsString = cryptoCurrencies.values()
            .stream()
            .map(c -> "\"%s/%s\"".formatted(c.getSymbol(), c.getCurrency()))
            .collect(Collectors.joining(","));
    }

    @PostConstruct
    public void connect() {
        HttpClient client = HttpClient.newHttpClient();
        client.newWebSocketBuilder()
            .connectTimeout(Duration.ofSeconds(CONNECTION_TIMEOUT_SECONDS))
            .buildAsync(URI.create(KRAKEN_API_URI), this)
            .thenAccept(ws -> {
                this.webSocket = ws;
                sendSubscriptionMessage();
            });
    }

    @PreDestroy
    public void stop() {
        if (webSocket != null) {
            String unsubscribeMsg = String.format("""
                {
                  "method": "unsubscribe",
                  "params": {
                    "channel": "ticker",
                    "symbol": [%s]
                  }
                }
                """, currencyPairsString);

            webSocket.sendText(unsubscribeMsg, true);
            webSocket.sendClose(WebSocket.NORMAL_CLOSURE, "Shutting down");
        }
    }

    public Map<String, TickerData> getTickerMap() {
        return tickerMap;
    }

    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        if (!subscribed) {
            return WebSocket.Listener.super.onText(webSocket, data, last);
        }

        String message = data.toString();

        KrakenResponse krakenResponse = gson.fromJson(message, KrakenResponse.class);

        if (krakenResponse.getData() != null) {
            for (TickerData ticker : krakenResponse.getData()) {
                ticker.setAdditionalProperties(cryptoCurrencies);
                tickerMap.put(ticker.getCryptoCurrencySymbol(), ticker);
            }
        }

        return WebSocket.Listener.super.onText(webSocket, data, last);
    }

    private void sendSubscriptionMessage() {
        String subscriptionMessage = String.format(SUBSCRIBE_TEMPLATE, currencyPairsString);
        webSocket.sendText(subscriptionMessage, true).thenRun(() -> subscribed = true);
    }
}