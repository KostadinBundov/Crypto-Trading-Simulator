import React, { useState, useEffect } from 'react';
import TransactionList from '../transactions/TransactionTable.js';
import Wallet from '../wallets/Wallet.js';

export default function AccountDetails({ accountId, onBuyClick, onSellClick, onNavigate, onTransactionComplete }) {
    const [account, setAccount] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [walletId, setWalletId] = useState(null);
    const [isEditing, setIsEditing] = useState(false);
    const [editableAccount, setEditableAccount] = useState({});
    const [showWallet, setShowWallet] = useState(false);

    useEffect(() => {
        if (accountId) {
            fetchAccountDetails(accountId);
            fetchWalletDetails(accountId);
        }
    }, [accountId, onTransactionComplete]);

    const fetchAccountDetails = async (id) => {
        try {
            setLoading(true);
            const response = await fetch(`/api/accounts/${id}`);
            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message || `HTTP error! status: ${response.status}`);
            }
            const data = await response.json();
            setAccount(data);
            setEditableAccount({
                firstName: data.firstName,
                lastName: data.lastName,
                email: data.email,
                phoneNumber: data.phoneNumber
            });
        } catch (error) {
            setError(error);
            console.error("Error fetching account details:", error);
        } finally {
            setLoading(false);
        }
    };

    const fetchWalletDetails = async (accountId) => {
        try {
            const response = await fetch(`/api/accounts/${accountId}/wallet`);
            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message || `HTTP error! status: ${response.status}`);
            }
            const data = await response.json();
            setWalletId(data.id);
        } catch (error) {
            console.error("Error fetching wallet details:", error);
        }
    };

    const handleResetBalance = async () => {
        if (!window.confirm("Are you sure you want to reset this account's balance to 10000? All transactions and holdings will be cleared.")) {
            return;
        }
        try {
            const response = await fetch(`/api/accounts/${accountId}/reset`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message || `HTTP error! status: ${response.status}`);
            }
            fetchAccountDetails(accountId);
            fetchWalletDetails(accountId);
            if (onTransactionComplete) {
                onTransactionComplete();
            }
            alert("Account balance reset successfully!");
        } catch (error) {
            setError(error);
            console.error("Error resetting account balance:", error);
            alert("Failed to reset account balance. Please try again: " + error.message);
        }
    };

    const handleEditClick = () => {
        setIsEditing(true);
    };

    const handleCancelEdit = () => {
        setIsEditing(false);
        if (account) {
            setEditableAccount({
                firstName: account.firstName,
                lastName: account.lastName,
                email: account.email,
                phoneNumber: account.phoneNumber
            });
        }
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setEditableAccount(prev => ({ ...prev, [name]: value }));
    };

    const handleUpdateAccount = async () => {
        try {
            const response = await fetch(`/api/accounts/${accountId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(editableAccount),
            });
            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message || `HTTP error! status: ${response.status}`);
            }
            setIsEditing(false);
            fetchAccountDetails(accountId);
            alert("Account updated successfully!");
        } catch (error) {
            setError(error);
            console.error("Error updating account:", error);
            alert("Failed to update account. Please try again: " + error.message);
        }
    };

    const handleViewWalletClick = () => {
        setShowWallet(true);
    };

    const handleBackToAccountDetails = () => {
        setShowWallet(false);
        fetchAccountDetails(accountId);
        fetchWalletDetails(accountId);
    };

    if (loading) {
        return <div style={messageStyle}>Loading account details...</div>;
    }

    if (error) {
        return <div style={messageStyle}>Error: {error.message}</div>;
    }

    if (!account) {
        return <div style={messageStyle}>Account not found.</div>;
    }

    if (showWallet) {
        return (
            <Wallet
                accountId={accountId}
                walletId={walletId}
                onBackToAccountDetails={handleBackToAccountDetails}
            />
        );
    }

    return (
        <div style={accountDetailsContainerStyle}>
            <h2 style={headerStyle}>Account Details: {account.firstName} {account.lastName}</h2>
            <div style={detailsSectionStyle}>
                <p style={detailRowStyle}>Balance: <span style={balanceValueStyle}>${account.balance ? account.balance.toFixed(2) : '0.00'}</span></p>

                {isEditing ? (
                    <>
                        <div style={inputGroupStyle}>
                            <label style={labelStyle} htmlFor="firstName">First Name:</label>
                            <input
                                type="text"
                                name="firstName"
                                value={editableAccount.firstName || ''}
                                onChange={handleInputChange}
                                style={inputStyle}
                                required
                            />
                        </div>
                        <div style={inputGroupStyle}>
                            <label style={labelStyle} htmlFor="lastName">Last Name:</label>
                            <input
                                type="text"
                                name="lastName"
                                value={editableAccount.lastName || ''}
                                onChange={handleInputChange}
                                style={inputStyle}
                                required
                            />
                        </div>
                        <div style={inputGroupStyle}>
                            <label style={labelStyle} htmlFor="email">Email:</label>
                            <input
                                type="email"
                                name="email"
                                value={editableAccount.email || ''}
                                onChange={handleInputChange}
                                style={inputStyle}
                                required
                            />
                        </div>
                        <div style={inputGroupStyle}>
                            <label style={labelStyle} htmlFor="phoneNumber">Phone:</label>
                            <input
                                type="text"
                                name="phoneNumber"
                                value={editableAccount.phoneNumber || ''}
                                onChange={handleInputChange}
                                style={inputStyle}
                                required
                            />
                        </div>
                        <div style={editButtonGroupStyle}>
                            <button onClick={handleUpdateAccount} style={saveButtonStyle}>Save</button>
                            <button onClick={handleCancelEdit} style={cancelButtonStyle}>Cancel</button>
                        </div>
                    </>
                ) : (
                    <>
                        <p style={detailRowStyle}>First Name: <span style={valueStyle}>{account.firstName}</span></p>
                        <p style={detailRowStyle}>Last Name: <span style={valueStyle}>{account.lastName}</span></p>
                        <p style={detailRowStyle}>Email: <span style={valueStyle}>{account.email}</span></p>
                        <p style={detailRowStyle}>Phone: <span style={valueStyle}>{account.phoneNumber}</span></p>
                        <div style={editButtonGroupStyle}>
                            <button onClick={handleEditClick} style={editButtonStyle}>Edit Account Info</button>
                        </div>
                    </>
                )}
            </div>

            <div style={buttonGroupStyle}>
                <button onClick={onBuyClick} style={buyButtonStyle}>Buy Crypto</button>
                <button onClick={onSellClick} style={sellButtonStyle}>Sell Crypto</button>
                {walletId && (
                    <button onClick={handleViewWalletClick} style={walletButtonStyle}>View Wallet</button>
                )}
                <button onClick={handleResetBalance} style={resetButtonStyle}>Reset Balance</button>
            </div>

            <h3 style={sectionHeaderStyle}>Recent Transactions</h3>
            <TransactionList accountId={accountId} />
        </div>
    );
}

const accountDetailsContainerStyle = {
    backgroundColor: '#fff',
    padding: '30px',
    borderRadius: '10px',
    boxShadow: '0 4px 20px rgba(0,0,0,0.1)',
    maxWidth: '900px',
    margin: '30px auto',
    fontFamily: "'Segoe UI', Tahoma, Geneva, Verdana, sans-serif', sans-serif",
};

const headerStyle = {
    color: '#006600',
    textAlign: 'center',
    marginBottom: '25px',
    fontSize: '2.2rem',
    borderBottom: '2px solid #eee',
    paddingBottom: '15px',
};

const detailsSectionStyle = {
    marginBottom: '25px',
    padding: '15px',
    backgroundColor: '#f9f9f9',
    borderRadius: '8px',
    border: '1px solid #eee',
};

const detailRowStyle = {
    fontSize: '1.2rem',
    color: '#333',
    marginBottom: '10px',
    display: 'flex',
    justifyContent: 'flex-start',
    alignItems: 'center',
};

const balanceValueStyle = {
    fontSize: '1.8rem',
    fontWeight: 'bold',
    color: '#006600',
    marginLeft: '10px',
};

const valueStyle = {
    fontSize: '1.2rem',
    fontWeight: 'bold',
    color: '#333',
    marginLeft: '10px',
};

const buttonGroupStyle = {
    display: 'flex',
    justifyContent: 'center',
    gap: '15px',
    marginBottom: '30px',
    flexWrap: 'wrap',
};

const editButtonGroupStyle = {
    display: 'flex',
    justifyContent: 'center',
    gap: '10px',
    marginTop: '20px',
};

const inputGroupStyle = {
    display: 'flex',
    alignItems: 'center',
    marginBottom: '10px',
};

const labelStyle = {
    fontWeight: 'bold',
    marginRight: '10px',
    minWidth: '100px',
    textAlign: 'right',
};

const inputStyle = {
    flex: 1,
    padding: '8px',
    borderRadius: '4px',
    border: '1px solid #ddd',
    fontSize: '1rem',
};

const baseButtonStyle = {
    padding: '12px 25px',
    borderRadius: '8px',
    border: 'none',
    cursor: 'pointer',
    fontSize: '1.1rem',
    fontWeight: 'bold',
    transition: 'background-color 0.2s ease-in-out, transform 0.1s ease-in-out',
    boxShadow: '0 2px 5px rgba(0,0,0,0.1)',
};

const buyButtonStyle = {
    ...baseButtonStyle,
    backgroundColor: '#28a745',
    color: 'white',
    '&:hover': {
        backgroundColor: '#218838',
        transform: 'translateY(-2px)',
    },
};

const sellButtonStyle = {
    ...baseButtonStyle,
    backgroundColor: '#dc3545',
    color: 'white',
    '&:hover': {
        backgroundColor: '#c82333',
        transform: 'translateY(-2px)',
    },
};

const walletButtonStyle = {
    ...baseButtonStyle,
    backgroundColor: '#007bff',
    color: 'white',
    '&:hover': {
        backgroundColor: '#0056b3',
        transform: 'translateY(-2px)',
    },
};

const resetButtonStyle = {
    ...baseButtonStyle,
    backgroundColor: '#ffc107',
    color: '#333',
    '&:hover': {
        backgroundColor: '#e0a800',
        transform: 'translateY(-2px)',
    },
};

const editButtonStyle = {
    ...baseButtonStyle,
    backgroundColor: '#6c757d',
    color: 'white',
    '&:hover': {
        backgroundColor: '#5a6268',
        transform: 'translateY(-2px)',
    },
};

const saveButtonStyle = {
    ...baseButtonStyle,
    backgroundColor: '#28a745',
    color: 'white',
    '&:hover': {
        backgroundColor: '#218838',
        transform: 'translateY(-2px)',
    },
};

const cancelButtonStyle = {
    ...baseButtonStyle,
    backgroundColor: '#dc3545',
    color: 'white',
    '&:hover': {
        backgroundColor: '#c82333',
        transform: 'translateY(-2px)',
    },
};

const sectionHeaderStyle = {
    color: '#006600',
    marginBottom: '20px',
    fontSize: '1.8rem',
    borderBottom: '1px solid #eee',
    paddingBottom: '10px',
};

const messageStyle = {
    textAlign: 'center',
    fontSize: '1.2rem',
    color: '#555',
    padding: '2rem',
};