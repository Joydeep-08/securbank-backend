package com.bank.banking_system.model;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private String accountId;
    private String ownerName;
    private double balance;
    private List<String> transactionHistory;

    public Account(String accountId, String ownerName, double initialBalance) {
        this.accountId = accountId;
        this.ownerName = ownerName;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
        transactionHistory.add("Account created with balance: ₹" + initialBalance);
    }

    public String getAccountId() { return accountId; }
    public String getOwnerName() { return ownerName; }
    public double getBalance() { return balance; }
    public List<String> getTransactionHistory() { return transactionHistory; }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add("Deposited: ₹" + amount + " | Balance: ₹" + balance);
    }

    public void withdraw(double amount) {
        balance -= amount;
        transactionHistory.add("Withdrew: ₹" + amount + " | Balance: ₹" + balance);
    }
}
