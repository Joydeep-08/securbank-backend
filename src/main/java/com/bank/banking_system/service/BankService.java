package com.bank.banking_system.service;

import com.bank.banking_system.exception.InsufficientFundsException;
import com.bank.banking_system.model.Account;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BankService {

    private Map<String, Account> accounts = new HashMap<>();

    public synchronized Account createAccount(String accountId, String ownerName, double initialBalance) {
        Account account = new Account(accountId, ownerName, initialBalance);
        accounts.put(accountId, account);
        logToFile("Account created: " + accountId + " for " + ownerName);
        return account;
    }

    public synchronized void deposit(String accountId, double amount) {
        Account account = getAccount(accountId);
        account.deposit(amount);
        logToFile("Deposit: ₹" + amount + " to " + accountId);
    }

    public synchronized void withdraw(String accountId, double amount) {
        Account account = getAccount(accountId);
        if (account.getBalance() < amount) {
            throw new InsufficientFundsException(amount);
        }
        account.withdraw(amount);
        logToFile("Withdrawal: ₹" + amount + " from " + accountId);
    }

    public Account getAccount(String accountId) {
        Account account = accounts.get(accountId);
        if (account == null) {
            throw new RuntimeException("Account not found: " + accountId);
        }
        return account;
    }

    public Collection<Account> getAllAccounts() {
        return accounts.values();
    }

    public List<String> getTransactionHistory(String accountId) {
        return getAccount(accountId).getTransactionHistory();
    }

    private void logToFile(String message) {
        try (FileWriter fw = new FileWriter("transactions.log", true)) {
            fw.write(message + "\n");
        } catch (IOException e) {
            System.out.println("Logging failed: " + e.getMessage());
        }
    }
}

