import React, { useState, useEffect } from 'react';

export default function AccountTransactions({ accountId }) {
    const [transactions, setTransactions] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchTransactions = async () => {
            try {
                const response = await fetch(`/api/accounts/${accountId}/transactions`);
                if (!response.ok) {
                    const errorData = await response.json();
                    throw new Error(errorData.message || `HTTP error! status: ${response.status}`);
                }
                const data = await response.json();
                setTransactions(data);
            } catch (err) {
                console.error("Failed to fetch transactions:", err);
                setError("Failed to load transactions: " + err.message);
            } finally {
                setLoading(false);
            }
        };

        if (accountId) {
            fetchTransactions();
        }
    }, [accountId]);

    if (loading) {
        return <p style={messageStyle}>Loading transactions...</p>;
    }

    if (error) {
        return <p style={errorMessageStyle}>{error}</p>;
    }

    if (transactions.length === 0) {
        return <p style={messageStyle}>No transactions found for this account.</p>;
    }

    return (
        <div style={transactionsContainerStyle}>
            <h3 style={transactionsHeaderStyle}>Transaction History</h3>
            <table style={tableStyle}>
                <thead>
                <tr>
                    <th style={thStyle}>ID</th>
                    <th style={thStyle}>Type</th>
                    <th style={thStyle}>Currency</th>
                    <th style={thStyle}>Amount</th>
                    <th style={thStyle}>Price (per unit)</th>
                    <th style={thStyle}>Total Value</th>
                    <th style={thStyle}>Date</th>
                </tr>
                </thead>
                <tbody>
                {transactions.map(transaction => (
                    <tr key={transaction.id} style={trStyle}>
                        <td style={tdStyle}>{transaction.id}</td>
                        <td style={tdStyle}>{transaction.type}</td>
                        <td style={tdStyle}>{transaction.currencySymbol}</td>
                        <td style={tdStyle}>{transaction.amount.toFixed(8)}</td>
                        <td style={tdStyle}>${transaction.price.toFixed(2)}</td>
                        <td style={tdStyle}>${transaction.profit.toFixed(2)}</td>
                        <td style={tdStyle}>{new Date(transaction.date).toLocaleString()}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}

const transactionsContainerStyle = {
    backgroundColor: "rgba(255,255,255,0.95)",
    borderRadius: "10px",
    boxShadow: "0 0 15px rgba(0,0,0,0.1)",
    padding: "2rem",
    margin: "2rem auto",
    maxWidth: "800px",
    fontFamily: "'Segoe UI', Tahoma, Geneva, Verdana, sans-serif",
    color: "#333",
};

const transactionsHeaderStyle = {
    textAlign: "center",
    color: "#0056b3",
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