import React, { useEffect, useState } from "react";

export default function HoldingList({ accountId, walletId }) {
    const [holdings, setHoldings] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        if (!accountId || !walletId) {
            setError("Account ID and Wallet ID are required to load holdings.");
            setLoading(false);
            return;
        }

        const fetchHoldings = async () => {
            try {
                const response = await fetch(`/api/accounts/${accountId}/wallet/${walletId}/holdings`);
                if (!response.ok) {
                    if (response.status === 404) {
                        setError("No holdings found for this wallet.");
                    } else {
                        throw new Error(`HTTP error! status: ${response.status}`);
                    }
                }
                const data = await response.json();
                setHoldings(data);
            } catch (err) {
                console.error("Failed to load holdings:", err);
                setError("Failed to load holdings.");
            } finally {
                setLoading(false);
            }
        };

        fetchHoldings();
    }, [accountId, walletId]);

    if (loading) return <p style={messageStyle}>Loading holdings...</p>;
    if (error) return <p style={errorMessageStyle}>{error}</p>;
    if (holdings.length === 0) return <p style={noHoldingsStyle}>No cryptocurrency holdings in this wallet yet.</p>;

    return (
        <div style={holdingsContainerStyle}>
            <table style={tableStyle}>
                <thead>
                <tr>
                    <th style={tableHeaderStyle}>Cryptocurrency</th>
                    <th style={tableHeaderStyle}>Symbol</th>
                    <th style={tableHeaderStyle}>Amount Held</th>
                </tr>
                </thead>
                <tbody>
                {holdings.map((holding) => (
                    <tr key={holding.id} style={tableRowStyle}>
                        <td style={tableCellStyle}>{holding.cryptoCurrencyName}</td>
                        <td style={tableCellStyle}>{holding.cryptoCurrencySymbol}</td>
                        <td style={tableCellStyle}>{holding.amount.toFixed(8)}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}

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

const noHoldingsStyle = {
    textAlign: "center",
    color: "#777",
    fontStyle: "italic",
    marginTop: "1rem",
    padding: "1rem",
    backgroundColor: "#f0f0f0",
    borderRadius: "5px",
};

const holdingsContainerStyle = {
    marginTop: "1.5rem",
    overflowX: "auto",
};

const tableStyle = {
    width: "100%",
    borderCollapse: "collapse",
    marginTop: "1rem",
    boxShadow: "0 2px 8px rgba(0,0,0,0.1)",
    borderRadius: "8px",
    overflow: "hidden",
};

const tableHeaderStyle = {
    backgroundColor: "#006600",
    color: "white",
    padding: "1rem",
    textAlign: "left",
    borderBottom: "1px solid #ddd",
};

const tableCellStyle = {
    padding: "1rem",
    borderBottom: "1px solid #eee",
    textAlign: "left",
    verticalAlign: "middle",
};

const tableRowStyle = {
    backgroundColor: "#f9f9f9",
};