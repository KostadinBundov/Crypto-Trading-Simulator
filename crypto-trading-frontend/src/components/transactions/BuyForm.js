import React, { useState, useEffect } from "react";

export default function BuyForm({ accountId, onTransactionComplete }) {
    const [cryptocurrencies, setCryptocurrencies] = useState([]);
    const [formData, setFormData] = useState({
        cryptoCurrencySymbol: "",
        amount: "",
    });
    const [message, setMessage] = useState("");
    const [error, setError] = useState(null); // Използваме този state за съобщения за грешка
    const [loadingCryptos, setLoadingCryptos] = useState(true);

    useEffect(() => {
        const fetchCryptocurrencies = async () => {
            try {
                const response = await fetch("/api/cryptocurrencies");
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                const data = await response.json();
                setCryptocurrencies(data);
            } catch (err) {
                console.error("Failed to load cryptocurrencies:", err);
                setError("Failed to load available cryptocurrencies.");
            } finally {
                setLoadingCryptos(false);
            }
        };

        fetchCryptocurrencies();
    }, []);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prevFormData => ({
            ...prevFormData,
            [name]: value,
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setMessage("");
        setError(null);

        if (!formData.cryptoCurrencySymbol || !formData.amount || parseFloat(formData.amount) <= 0) {
            setError("Please select a cryptocurrency and enter a valid amount.");
            return;
        }

        try {
            const response = await fetch(`/api/accounts/${accountId}/transactions/buy`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    currencySymbol: formData.cryptoCurrencySymbol, // Уверете се, че това е currencySymbol
                    amount: parseFloat(formData.amount),
                    accountId: accountId,
                    type: "BUY"
                }),
            });

            if (response.ok) {
                const newTransaction = await response.json();
                setMessage("Buy transaction successful!");
                setFormData({
                    cryptoCurrencySymbol: "",
                    amount: "",
                });
                if (onTransactionComplete) {
                    onTransactionComplete(newTransaction);
                }
            } else {
                const errorData = await response.json(); // Опитваме се да парснем JSON отговора на грешката
                setError(errorData.message || `Failed to make buy transaction: ${response.statusText}`);
            }
        } catch (err) {
            console.error("Failed to make buy transaction:", err);
            setError("An unexpected error occurred: " + err.message);
        }
    };

    if (loadingCryptos) return <p style={messageStyle}>Loading cryptocurrency options...</p>;

    return (
        <div style={formContainerStyle}>
            <h2 style={formHeaderStyle}>Buy Cryptocurrency</h2>
            <form onSubmit={handleSubmit} style={formStyle}>
                <div style={formGroupStyle}>
                    <label htmlFor="cryptoCurrencySymbol" style={labelStyle}>Cryptocurrency:</label>
                    <select
                        id="cryptoCurrencySymbol"
                        name="cryptoCurrencySymbol"
                        value={formData.cryptoCurrencySymbol}
                        onChange={handleChange}
                        style={inputStyle}
                        required
                    >
                        <option value="">Select a cryptocurrency</option>
                        {cryptocurrencies.map(crypto => (
                            <option key={crypto.cryptoCurrencySymbol} value={crypto.cryptoCurrencySymbol}>
                                {crypto.cryptoCurrencySymbol} - ${crypto.ask != null ? parseFloat(crypto.ask).toFixed(2) : 'N/A'}
                            </option>
                        ))}
                    </select>
                </div>
                <div style={formGroupStyle}>
                    <label htmlFor="amount" style={labelStyle}>Amount:</label>
                    <input
                        type="number"
                        id="amount"
                        name="amount"
                        value={formData.amount}
                        onChange={handleChange}
                        style={inputStyle}
                        min="0.00000001"
                        step="0.00000001"
                        required
                    />
                </div>
                <button type="submit" style={buySubmitButtonStyle}>Buy</button>
            </form>
            {message && <p style={successMessageStyle}>{message}</p>}
            {error && <p style={errorMessageStyle}>{error}</p>} {/* Тук показваме съобщението за грешка */}
        </div>
    );
}

const formContainerStyle = {
    backgroundColor: "rgba(255,255,255,0.95)",
    borderRadius: "10px",
    boxShadow: "0 0 15px rgba(0,0,0,0.1)",
    padding: "2rem",
    margin: "2rem auto",
    maxWidth: "600px",
    fontFamily: "'Segoe UI', Tahoma, Geneva, Verdana, sans-serif",
    color: "#333",
};

const formHeaderStyle = {
    textAlign: "center",
    color: "#006600",
    marginBottom: "1.5rem",
    fontSize: "2rem",
};

const formStyle = {
    display: "flex",
    flexDirection: "column",
    gap: "1rem",
};

const formGroupStyle = {
    marginBottom: "1rem",
};

const labelStyle = {
    display: "block",
    marginBottom: "0.5rem",
    fontWeight: "bold",
    color: "#555",
};

const inputStyle = {
    width: "calc(100% - 20px)",
    padding: "0.8rem 10px",
    border: "1px solid #ccc",
    borderRadius: "5px",
    // ... останалите стилове
};

const buySubmitButtonStyle = {
    padding: "0.75rem 1.25rem",
    backgroundColor: "#006600",
    color: "white",
    border: "none",
    borderRadius: "5px",
    cursor: "pointer",
    fontSize: "1rem",
    fontWeight: "bold",
    transition: "background-color 0.2s ease-in-out, transform 0.1s ease-in-out",
    marginTop: "1.5rem",
    alignSelf: "center",
};

const successMessageStyle = {
    textAlign: "center",
    marginTop: "1.5rem",
    padding: "0.75rem",
    borderRadius: "5px",
    backgroundColor: "#d4edda",
    color: "#175a23",
    border: "1px solid #c3e6cb",
    fontWeight: "bold",
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

const messageStyle = {
    textAlign: "center",
    fontSize: "1.2rem",
    color: "#555",
    padding: "2rem",
};