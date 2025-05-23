import React, { useState, useEffect } from 'react';

export default function AccountList({ onSelectAccount }) {
    const [accounts, setAccounts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        fetchAccounts();
    }, []);

    const fetchAccounts = async () => {
        try {
            setLoading(true);
            const response = await fetch('/api/accounts');
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const data = await response.json();
            setAccounts(data);
        } catch (error) {
            setError(error);
            console.error("Error fetching accounts:", error);
        } finally {
            setLoading(false);
        }
    };

    if (loading) {
        return <div style={messageStyle}>Loading accounts...</div>;
    }

    if (error) {
        return <div style={messageStyle}>Error: {error.message}</div>;
    }

    return (
        <div style={accountListStyle}>
            {accounts.length === 0 ? (
                <div style={messageStyle}>No accounts found. Create one to get started!</div>
            ) : (
                <ul style={ulStyle}>
                    {accounts.map(account => (
                        <li key={account.id} style={liStyle}>
                            <div style={accountInfoStyle}>
                                <span style={accountNameStyle}>{account.firstName} {account.lastName}</span>
                                <span style={accountBalanceStyle}>Balance: ${account.balance ? account.balance.toFixed(2) : '0.00'}</span>
                            </div>
                            <div style={buttonGroupStyle}>
                                <button
                                    onClick={() => onSelectAccount('accountDetails', account.id)}
                                    style={viewDetailsButtonStyle}
                                >
                                    View Details
                                </button>
                                {/* Премахнахме бутона за изтриване */}
                            </div>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}

const accountListStyle = {
    backgroundColor: '#fff',
    padding: '20px',
    borderRadius: '8px',
    boxShadow: '0 2px 10px rgba(0,0,0,0.05)',
    maxWidth: '800px',
    margin: '20px auto',
};

const ulStyle = {
    listStyleType: 'none',
    padding: 0,
    margin: 0,
};

const liStyle = {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    padding: '15px 0',
    borderBottom: '1px solid #eee',
};

const accountInfoStyle = {
    display: 'flex',
    flexDirection: 'column',
};

const accountNameStyle = {
    fontSize: '1.2rem',
    fontWeight: 'bold',
    color: '#333',
};

const accountBalanceStyle = {
    fontSize: '1rem',
    color: '#666',
    marginTop: '5px',
};

const buttonGroupStyle = {
    display: 'flex',
    gap: '10px',
};

const viewDetailsButtonStyle = {
    padding: '8px 15px',
    backgroundColor: '#175a23',
    color: 'white',
    border: 'none',
    borderRadius: '5px',
    cursor: 'pointer',
    fontSize: '0.9rem',
    transition: 'background-color 0.2s ease-in-out',
};

const messageStyle = {
    textAlign: 'center',
    fontSize: '1.1rem',
    color: '#555',
    padding: '20px',
};