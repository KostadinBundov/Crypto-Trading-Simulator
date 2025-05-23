import React, { useState } from 'react';
import Header from './components/common/Header.js';
import AccountsPage from './components/accounts/AccountsPage.js';
import CryptosPage from './components/cryptos/CryptosPage.js';
import AccountDetails from './components/accounts/AccountDetails.js';
import BuyForm from './components/transactions/BuyForm.js';
import SellForm from './components/transactions/SellForm.js';
import Wallet from './components/wallets/Wallet.js';

export default function App() {
    const [currentPage, setCurrentPage] = useState('home');
    const [selectedAccountId, setSelectedAccountId] = useState(null);
    const [selectedWalletId, setSelectedWalletId] = useState(null);

    const navigateTo = (page, accountId = null, walletId = null) => {
        setCurrentPage(page);
        setSelectedAccountId(accountId);
        setSelectedWalletId(walletId);
    };

    const renderPage = () => {
        switch (currentPage) {
            case 'accounts':
                return <AccountsPage onSelectAccount={navigateTo} />;
            case 'cryptos':
                return <CryptosPage />;
            case 'accountDetails':
                return selectedAccountId ? (
                    <AccountDetails
                        accountId={selectedAccountId}
                        onBuyClick={() => navigateTo('buyForm', selectedAccountId)}
                        onSellClick={() => navigateTo('sellForm', selectedAccountId)}
                        onViewWallet={(walletId) => navigateTo('walletDetails', selectedAccountId, walletId)}
                        onTransactionComplete={() => navigateTo('accountDetails', selectedAccountId)}
                    />
                ) : (
                    <p style={messageStyle}>Please select an account to view details.</p>
                );
            case 'buyForm':
                return selectedAccountId ? (
                    <BuyForm accountId={selectedAccountId} onTransactionComplete={() => navigateTo('accountDetails', selectedAccountId)} />
                ) : (
                    <p style={messageStyle}>Please select an account to buy cryptocurrency.</p>
                );
            case 'sellForm':
                return selectedAccountId ? (
                    <SellForm accountId={selectedAccountId} onTransactionComplete={() => navigateTo('accountDetails', selectedAccountId)} />
                ) : (
                    <p style={messageStyle}>Please select an account to sell cryptocurrency.</p>
                );
            case 'walletDetails':
                return selectedAccountId && selectedWalletId ? (
                    <Wallet accountId={selectedAccountId} walletId={selectedWalletId} />
                ) : (
                    <p style={messageStyle}>Please select an account and wallet to view details.</p>
                );
            case 'home':
            default:
                return (
                    <div style={homePageStyle}>
                        <h1 style={homePageHeader}>Welcome to Crypto Trading Simulator</h1>
                        <p style={homePageText}>Use the navigation above to manage accounts, view crypto prices, and perform transactions.</p>
                        <div style={homeButtonsContainer}>
                            <button style={homeButtonStyle} onClick={() => navigateTo('accounts')}>Manage Accounts</button>
                            <button style={homeButtonStyle} onClick={() => navigateTo('cryptos')}>View Crypto Prices</button>
                        </div>
                    </div>
                );
        }
    };

    return (
        <div style={appContainerStyle}>
            <Header onNavigate={navigateTo} currentPage={currentPage} selectedAccountId={selectedAccountId} />
            <main style={mainContentStyle}>
                {renderPage()}
            </main>
        </div>
    );
}

const appContainerStyle = {
    minHeight: '100vh',
    backgroundColor: '#f0f2f5',
    display: 'flex',
    flexDirection: 'column',
};

const mainContentStyle = {
    flexGrow: 1,
    padding: '1rem',
};

const homePageStyle = {
    textAlign: 'center',
    marginTop: '5rem',
    padding: '2rem',
    backgroundColor: 'rgba(255,255,255,0.95)',
    borderRadius: '10px',
    boxShadow: '0 0 15px rgba(0,0,0,0.1)',
    maxWidth: '800px',
    margin: '5rem auto',
};

const homePageHeader = {
    color: '#006600',
    fontSize: '2.5rem',
    marginBottom: '1.5rem',
};

const homePageText = {
    fontSize: '1.2rem',
    color: '#555',
    lineHeight: '1.6',
    marginBottom: '2rem',
};

const homeButtonsContainer = {
    display: 'flex',
    justifyContent: 'center',
    gap: '1.5rem',
    flexWrap: 'wrap',
};

const homeButtonStyle = {
    padding: '1rem 2rem',
    backgroundColor: '#006600',
    color: 'white',
    border: 'none',
    borderRadius: '8px',
    cursor: 'pointer',
    fontSize: '1.1rem',
    fontWeight: 'bold',
    transition: 'background-color 0.2s ease-in-out, transform 0.1s ease-in-out',
};

const messageStyle = {
    textAlign: "center",
    fontSize: "1.2rem",
    color: "#555",
    padding: "2rem",
};