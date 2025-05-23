import React, { useState, useEffect } from 'react';

export default function Wallet({ accountId, walletId, onBackToAccountDetails }) {
    const [holdings, setHoldings] = useState([]);
    const [cryptocurrencyPrices, setCryptocurrencyPrices] = useState({});
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            if (!accountId || !walletId) {
                setError("Account ID or Wallet ID is missing.");
                setLoading(false);
                return;
            }

            try {
                const holdingsResponse = await fetch(`/api/accounts/${accountId}/wallet/${walletId}/holdings`);
                if (!holdingsResponse.ok) {
                    const errorData = await holdingsResponse.json();
                    throw new Error(errorData.message || `Failed to fetch holdings: ${holdingsResponse.status}`);
                }
                const holdingsData = await holdingsResponse.json();
                setHoldings(holdingsData);

                const pricesResponse = await fetch("/api/cryptocurrencies");
                if (!pricesResponse.ok) {
                    const errorData = await pricesResponse.json();
                    throw new Error(errorData.message || `Failed to fetch crypto prices: ${pricesResponse.status}`);
                }
                const pricesData = await pricesResponse.json();
                const pricesMap = {};
                pricesData.forEach(crypto => {
                    pricesMap[crypto.cryptoCurrencySymbol] = {
                        ask: crypto.ask,
                        bid: crypto.bid
                    };
                });
                setCryptocurrencyPrices(pricesMap);

            } catch (err) {
                console.error("Error fetching wallet data:", err);
                setError("Failed to load wallet data: " + err.message);
            } finally {
                setLoading(false);
            }
        };

        fetchData();
    }, [accountId, walletId]);

    const calculateCurrentValue = (symbol, amount) => {
        if (cryptocurrencyPrices[symbol] && cryptocurrencyPrices[symbol].ask) {
            return (amount * cryptocurrencyPrices[symbol].ask).toFixed(2);
        }
        return "N/A";
    };

    if (loading) {
        return <p style={messageStyle}>Loading wallet and holdings...</p>;
    }

    if (error) {
        return <p style={errorMessageStyle}>Error: {error}</p>;
    }

    if (holdings.length === 0) {
        return (
            <div style={walletContainerStyle}>
                <button onClick={onBackToAccountDetails} style={backButtonStyle}>Back to Account Details</button>
                <h2 style={walletHeaderStyle}>Your Crypto Wallet</h2>
                <h3 style={holdingsHeaderStyle}>Your Holdings</h3>
                <p style={messageStyle}>You currently have no cryptocurrency holdings.</p>
            </div>
        );
    }

    return (
        <div style={walletContainerStyle}>
            <button onClick={onBackToAccountDetails} style={backButtonStyle}>Back to Account Details</button>
            <h2 style={walletHeaderStyle}>Your Crypto Wallet</h2>

            <h3 style={holdingsHeaderStyle}>Your Holdings</h3>
            <table style={tableStyle}>
                <thead>
                <tr>
                    <th style={thStyle}>Currency</th>
                    <th style={thStyle}>Amount</th>
                    <th style={thStyle}>Current Ask Price</th>
                    <th style={thStyle}>Current Bid Price</th>
                    <th style={thStyle}>Current Value (at Ask)</th>
                </tr>
                </thead>
                <tbody>
                {holdings.map(holding => {
                    const currentPrices = cryptocurrencyPrices[holding.cryptoCurrencySymbol] || {};
                    const currentAsk = currentPrices.ask ? parseFloat(currentPrices.ask).toFixed(2) : "N/A";
                    const currentBid = currentPrices.bid ? parseFloat(currentPrices.bid).toFixed(2) : "N/A";
                    const currentValue = calculateCurrentValue(holding.cryptoCurrencySymbol, holding.cryptoCurrencyAmount);

                    return (
                        <tr key={holding.id} style={trStyle}>
                            <td style={tdStyle}>{holding.cryptoCurrencySymbol}</td>
                            <td style={tdStyle}>{holding.cryptoCurrencyAmount.toFixed(8)}</td>
                            {/* <td style={tdStyle}>${holding.averageBuyPrice.toFixed(2)}</td> -- Премахнато */}
                            <td style={tdStyle}>${currentAsk}</td>
                            <td style={tdStyle}>${currentBid}</td>
                            <td style={tdStyle}>${currentValue}</td>
                        </tr>
                    );
                })}
                </tbody>
            </table>
        </div>
    );
}

const walletContainerStyle = {
    backgroundColor: "rgba(255,255,255,0.95)",
    borderRadius: "10px",
    boxShadow: "0 0 15px rgba(0,0,0,0.1)",
    padding: "2rem",
    margin: "2rem auto",
    maxWidth: "900px",
    fontFamily: "'Segoe UI', Tahoma, Geneva, Verdana, sans-serif",
    color: "#333",
    position: 'relative',
};

const backButtonStyle = {
    position: 'absolute',
    top: '20px',
    left: '20px',
    padding: '8px 15px',
    backgroundColor: '#007bff',
    color: 'white',
    border: 'none',
    borderRadius: '5px',
    cursor: 'pointer',
    fontSize: '0.9rem',
    fontWeight: 'bold',
    transition: 'background-color 0.2s ease-in-out',
};

const walletHeaderStyle = {
    textAlign: "center",
    color: "#0056b3",
    marginBottom: "1.5rem",
    fontSize: "2rem",
};

const holdingsHeaderStyle = {
    textAlign: "center",
    color: "#008cba",
    marginBottom: "1.5rem",
    fontSize: "1.8rem",
};

const tableStyle = {
    width: "100%",
    borderCollapse: "collapse",
    marginTop: "1rem",
};

const thStyle = {
    padding: "12px 15px",
    textAlign: "left",
    borderBottom: "2px solid #ddd",
    backgroundColor: "#f2f2f2",
    color: "#555",
    fontWeight: "bold",
};

const tdStyle = {
    padding: "10px 15px",
    textAlign: "left",
    borderBottom: "1px solid #eee",
};

const trStyle = {
    "&:nth-child(even)": {
        backgroundColor: "#f9f9f9",
    },
};

const messageStyle = {
    textAlign: "center",
    fontSize: "1.2rem",
    color: "#555",
    padding: "2rem",
};

const errorMessageStyle = {
    textAlign: "center",
    marginTop: "1.5rem",
    padding: "0.75rem",
    borderRadius: "5px",
    backgroundColor: "#f8d7da",
    color: "#721c24",
    border: "1px solid #f5c6cb",
    fontWeight: "bold",
};