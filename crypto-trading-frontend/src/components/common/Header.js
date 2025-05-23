import React from 'react';

export default function Header({ onNavigate, currentPage, selectedAccountId }) {
    const navButtonBaseStyle = {
        backgroundColor: '#006600',
        border: '1px solid transparent',
        borderRadius: '5px',
        color: 'white',
        textDecoration: 'none',
        fontSize: '1.2rem',
        fontWeight: 'bold',
        padding: '0.75rem 1.25rem',
        cursor: 'pointer',
        transition: 'background-color 0.3s ease-in-out, color 0.3s ease-in-out, border-color 0.3s ease-in-out',
        minWidth: '120px',
        textAlign: 'center',
    };

    const navButtonActiveStyle = {
        backgroundColor: 'white',
        color: '#006600',
        borderColor: 'white',
        boxShadow: '0 2px 5px rgba(0,0,0,0.2)',
    };

    const getButtonStyle = (pageName) => {
        let style = { ...navButtonBaseStyle };
        if (currentPage === pageName) {
            style = { ...style, ...navButtonActiveStyle };
        }
        return style;
    };

    return (
        <header style={headerStyle}>
            <nav style={navStyle}>
                <button onClick={() => onNavigate('home')} style={getButtonStyle('home')}>Home</button>
                <button onClick={() => onNavigate('accounts')} style={getButtonStyle('accounts')}>Accounts</button>
                <button onClick={() => onNavigate('cryptos')} style={getButtonStyle('cryptos')}>Cryptocurrencies</button>
                {selectedAccountId && (
                    <button onClick={() => onNavigate('accountDetails', selectedAccountId)} style={getButtonStyle('accountDetails')}>
                        Account Details
                    </button>
                )}
            </nav>
        </header>
    );
}

const headerStyle = {
    backgroundColor: '#006600',
    padding: '1rem 2rem',
    color: 'white',
    boxShadow: '0 2px 10px rgba(0,0,0,0.1)',
    fontFamily: "'Segoe UI', Tahoma, Geneva, Verdana, sans-serif",
    display: 'flex',
    justifyContent: 'center',
};

const navStyle = {
    display: 'flex',
    justifyContent: 'center',
    gap: '1rem',
    alignItems: 'center',
    maxWidth: '1200px',
    width: '100%',
    flexWrap: 'wrap',
};