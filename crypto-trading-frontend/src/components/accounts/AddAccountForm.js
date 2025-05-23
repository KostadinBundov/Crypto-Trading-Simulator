import React, { useState } from "react";

export default function AddAccountForm({ onAccountAdded }) {
    const [formData, setFormData] = useState({
        firstName: "",
        lastName: "",
        email: "",
        phoneNumber: ""
    });
    const [message, setMessage] = useState("");
    const [error, setError] = useState(null);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prevFormData => ({
            ...prevFormData,
            [name]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setMessage("");
        setError(null);

        try {
            const response = await fetch("/api/accounts", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(formData),
            });

            if (!response.ok) {
                let errorMessage = `HTTP error! status: ${response.status}`;
                try {
                    const errorData = await response.json();
                    errorMessage = errorData.message || errorData.error || errorMessage;
                } catch (jsonError) {
                    console.error("Response not JSON:", jsonError);
                }
                throw new Error(errorMessage);
            }

            const newAccount = await response.json();
            setMessage("Account created successfully!");
            setFormData({
                firstName: "",
                lastName: "",
                email: "",
                phoneNumber: ""
            });
            if (onAccountAdded) {
                onAccountAdded(newAccount);
            }
        } catch (err) {
            console.error("Failed to create account:", err);
            setError("Failed to create account: " + err.message);
        }
    };

    return (
        <div style={formContainerStyle}>
            <h2 style={formHeaderStyle}>Create New Account</h2>
            <form onSubmit={handleSubmit} style={formStyle}>
                <div style={formGroupStyle}>
                    <label htmlFor="firstName" style={labelStyle}>First Name:</label>
                    <input
                        type="text"
                        id="firstName"
                        name="firstName"
                        value={formData.firstName}
                        onChange={handleChange}
                        style={inputStyle}
                        required
                    />
                </div>
                <div style={formGroupStyle}>
                    <label htmlFor="lastName" style={labelStyle}>Last Name:</label>
                    <input
                        type="text"
                        id="lastName"
                        name="lastName"
                        value={formData.lastName}
                        onChange={handleChange}
                        style={inputStyle}
                        required
                    />
                </div>
                <div style={formGroupStyle}>
                    <label htmlFor="email" style={labelStyle}>Email:</label>
                    <input
                        type="email"
                        id="email"
                        name="email"
                        value={formData.email}
                        onChange={handleChange}
                        style={inputStyle}
                        required
                    />
                </div>
                <div style={formGroupStyle}>
                    <label htmlFor="phoneNumber" style={labelStyle}>Phone Number:</label>
                    <input
                        type="tel"
                        id="phoneNumber"
                        name="phoneNumber"
                        value={formData.phoneNumber}
                        onChange={handleChange}
                        style={inputStyle}
                    />
                </div>
                <button type="submit" style={submitButtonStyle}>Create Account</button>
            </form>
            {message && <p style={successMessageStyle}>{message}</p>}
            {error && <p style={errorMessageStyle}>{error}</p>}
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
    fontSize: "1rem",
};

const submitButtonStyle = {
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