# Crypto Trading Simulator

A full-stack web application designed to simulate cryptocurrency trading, featuring a **React.js frontend** and a **Spring Boot backend**. It integrates with a relational database and leverages the Kraken API to provide real-time cryptocurrency market data.

## Table of Contents

- [About the Project](#about-the-project)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [SQL Queries](#sql-queries)


## About the Project

This project provides a robust environment for simulating cryptocurrency trading. Users can create accounts, virtually buy and sell cryptocurrencies, track their holdings, and review their transaction history, all powered by real-time market data sourced from the Kraken API.

## Features

* **Account Management:** Create, view, and update user accounts.
* **Wallet System:** Each account has a dedicated wallet for managing cryptocurrency holdings.
* **Holdings Tracking:** Monitor the quantities of various cryptocurrencies held by the user.
* **Simulated Transactions:** Perform virtual buy and sell operations for cryptocurrencies.
* **Real-time Market Data:** Integration with the Kraken API to fetch up-to-date cryptocurrency prices.
* **Transaction History:** Comprehensive logging and viewing of all past user transactions.

## Technologies Used

### Frontend

* **React.js:** A JavaScript library for building dynamic user interfaces.
* **HTML5 / CSS3:** Core web technologies for structuring and styling the UI.

### Backend

* **Java 23:** The primary programming language.
* **Spring Boot:** A framework for building robust, stand-alone, production-ready Spring applications.
* **Spring JDBC:** Simplifies database access operations.
* **Maven:** A powerful project management and build automation tool.
* **Gson:** A Java library used for serializing and deserializing Java objects to/from JSON (specifically for Kraken API integration).
* **Kraken WebSocket API:** For real-time cryptocurrency ticker data.

### Database

* **MySQL:** A widely used open-source relational database management system.

## SQL Queries

A detailed documentation of all SQL queries used within the repository layer can be found.