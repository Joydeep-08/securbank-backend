package com.bank.banking_system.exception;

public class InsufficientFundsException extends RuntimeException {
    private double amount;

    public InsufficientFundsException(double amount) {
        super("Insufficient funds! Tried to withdraw: ₹" + amount);
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
}
