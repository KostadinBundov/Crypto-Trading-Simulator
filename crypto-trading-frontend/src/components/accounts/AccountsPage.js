import React, { useState } from 'react';
import AccountList from './AccountList.js';
import AddAccountForm from './AddAccountForm.js';

export default function AccountsPage({ onSelectAccount }) {
    const [refreshAccounts, setRefreshAccounts] = useState(false);

    const handleAccountAdded = () => {
        setRefreshAccounts(prev => !prev);
    };

    return (
        <div style={pageContainer}>
            <AddAccountForm onAccountAdded={handleAccountAdded} />
            <AccountList key={refreshAccounts ? 'refreshed' : 'initial'} onSelectAccount={onSelectAccount} />
        </div>
    );
}

const pageContainer = {
    maxWidth: "1200px",
    margin: "2rem auto",
    padding: "1.5rem",
    backgroundColor: "rgba(255,255,255,0.95)",
    borderRadius: "10px",
    boxShadow: "0 0 15px rgba(0,0,0,0.2)",
    fontFamily: "'Segoe UI', Tahoma, Geneva, Verdana, sans-serif",
    color: "#333",
};