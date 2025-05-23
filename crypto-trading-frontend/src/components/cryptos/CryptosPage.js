import React, { useEffect, useState } from "react";

export default function CryptosPage() {
    const [cryptos, setCryptos] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        fetch("/api/cryptocurrencies")
            .then((res) => {
                if (!res.ok) {
                    throw new Error(`HTTP error! status: ${res.status}`);
                }
                return res.json();
            })
            .then((data) => {
                setCryptos(data);
                setLoading(false);
            })
            .catch((err) => {
                console.error(err);
                setError("Failed to load cryptocurrency data.");
                setLoading(false);
            });
    }, []);

    if (loading) return <p style={messageStyle}>Loading cryptocurrency data...</p>;
    if (error) return <p style={errorMessageStyle}>{error}</p>;
    if (!cryptos.length) return <p style={noDataStyle}>No cryptocurrency data available.</p>;

    return (
        <div style={pageContainerStyle}>
            <h2 style={headerStyle}>Current Cryptocurrency Prices</h2>
            <table style={tableStyle}>
                <thead>
                <tr style={tableHeaderRowStyle}>
                    <th style={thTdStyle}>Symbol</th>
                    <th style={thTdStyle}>Name</th>
                    <th style={thTdStyle}>Price (USD)</th>
                    <th style={thTdStyle}>Volume</th>
                </tr>
                </thead>
                <tbody>
                {cryptos.map((crypto) => (
                    <tr key={crypto.symbol} style={tableRowStyle}>
                        <td style={thTdStyle}>{crypto.cryptoCurrencySymbol}</td>
                        <td style={thTdStyle}>{crypto.cryptoCurrencyName || crypto.cryptoCurrencySymbol}</td>
                        <td style={thTdStyle}>
                            {crypto.last ? `$${parseFloat(crypto.last).toFixed(2)}` : "N/A"}
                        </td>
                        <td style={thTdStyle}>{crypto.volume ?? "N/A"}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}

const pageContainerStyle = {
    maxWidth: "900px",
    width: "100%",
    backgroundColor: "rgba(255,255,255,0.95)",
    padding: "2rem",
    borderRadius: "10px",
    boxShadow: "0 0 15px rgba(0,0,0,0.2)",
    margin: "2rem auto",
    fontFamily: "'Segoe UI', Tahoma, Geneva, Verdana, sans-serif",
    color: "#333",
};

const headerStyle = {
    textAlign: "center",
    color: "#006600",
    marginBottom: "2rem",
    fontSize: "2rem",
};

const tableStyle = {
    width: "100%",
    borderCollapse: "collapse",
    marginTop: "1rem",
    boxShadow: "0 2px 8px rgba(0,0,0,0.1)",
    borderRadius: "8px",
    overflow: "hidden",
};

const tableHeaderRowStyle = {
    backgroundColor: "#006600",
    color: "white",
};

const thTdStyle = {
    padding: "1rem",
    textAlign: "center",
    borderBottom: "1px solid #ddd",
};

const tableRowStyle = {
    backgroundColor: "#f9f9f9",
};

const messageStyle = {
    textAlign: "center",
    fontSize: "1.2rem",
    color: "#555",
    padding: "2rem",
};

const errorMessageStyle = {
    textAlign: "center",
    fontSize: "1.2rem",
    color: "#dc3545",
    padding: "2rem",
};

const noDataStyle = {
    textAlign: "center",
    color: "#777",
    fontStyle: "italic",
    marginTop: "1rem",
    padding: "1rem",
    backgroundColor: "#f0f0f0",
    borderRadius: "5px",
};