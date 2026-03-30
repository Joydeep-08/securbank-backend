package com.bank.banking_system.controller;

import com.bank.banking_system.exception.InsufficientFundsException;
import com.bank.banking_system.model.Account;
import com.bank.banking_system.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bank")
@CrossOrigin(origins = "*")
public class BankController {

    @Autowired
    private BankService bankService;

    @PostMapping("/create")
    public ResponseEntity<Account> createAccount(@RequestBody Map<String, Object> body) {
        String accountId = (String) body.get("accountId");
        String ownerName = (String) body.get("ownerName");
        double initialBalance = Double.parseDouble(body.get("initialBalance").toString());
        Account account = bankService.createAccount(accountId, ownerName, initialBalance);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/accounts")
    public ResponseEntity<Collection<Account>> getAllAccounts() {
        return ResponseEntity.ok(bankService.getAllAccounts());
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccount(@PathVariable String accountId) {
        return ResponseEntity.ok(bankService.getAccount(accountId));
    }

    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<String> deposit(@PathVariable String accountId,
                                          @RequestBody Map<String, Object> body) {
        double amount = Double.parseDouble(body.get("amount").toString());
        bankService.deposit(accountId, amount);
        return ResponseEntity.ok("Deposit successful");
    }

    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<String> withdraw(@PathVariable String accountId,
                                           @RequestBody Map<String, Object> body) {
        try {
            double amount = Double.parseDouble(body.get("amount").toString());
            bankService.withdraw(accountId, amount);
            return ResponseEntity.ok("Withdrawal successful");
        } catch (InsufficientFundsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{accountId}/history")
    public ResponseEntity<List<String>> getHistory(@PathVariable String accountId) {
        return ResponseEntity.ok(bankService.getTransactionHistory(accountId));
    }
}
