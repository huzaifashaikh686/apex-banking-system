package com.example.demo.controller;

import com.example.demo.model.Account;
import com.example.demo.model.Transaction;
import com.example.demo.service.BankService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BankController {

    private final BankService service;

    public BankController(BankService service) {
        this.service = service;
    }

    // Create Saving Account
    @PostMapping("/accounts/saving")
    public ResponseEntity<Account> createSaving(
            @RequestParam String accHolder,
            @RequestParam double interestRate) {

        return ResponseEntity.ok(
                service.createSavingAccount(accHolder, interestRate)
        );
    }

    // Create Current Account
    @PostMapping("/accounts/current")
    public ResponseEntity<Account> createCurrent(
            @RequestParam String accHolder) {

        return ResponseEntity.ok(
                service.createCurrentAccount(accHolder)
        );
    }

    // Deposit
    @PostMapping("/accounts/{id}/deposit")
    public ResponseEntity<String> deposit(
            @PathVariable Long id,
            @RequestParam double amount) {

        service.deposit(id, amount);

        return ResponseEntity.ok("Amount deposited successfully.");
    }

    // Withdraw
    @PostMapping("/accounts/{id}/withdraw")
    public ResponseEntity<String> withdraw(
            @PathVariable Long id,
            @RequestParam double amount) {

        service.withdraw(id, amount);

        return ResponseEntity.ok("Amount withdrawn successfully.");
    }

    // Transfer
    @PostMapping("/accounts/transfer")
    public ResponseEntity<String> transfer(
            @RequestParam Long fromId,
            @RequestParam Long toId,
            @RequestParam double amount) {

        service.transfer(fromId, toId, amount);

        return ResponseEntity.ok("Transfer completed successfully.");
    }

    // Apply Interest
    @PostMapping("/accounts/{id}/apply-interest")
    public ResponseEntity<String> applyInterest(
            @PathVariable Long id) {

        service.applyInterest(id);

        return ResponseEntity.ok("Interest applied successfully.");
    }

    // Get One Account
    @GetMapping("/accounts/{id}")
    public ResponseEntity<Account> getAccount(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.getAccountById(id)
        );
    }

    // Get All Accounts
    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> getAllAccounts() {

        return ResponseEntity.ok(
                service.getAllAccounts()
        );
    }
    
    @GetMapping("/accounts/{id}/transactions")
    public ResponseEntity<List<Transaction>> getTransactionHistory(@PathVariable Long id) {

    return ResponseEntity.ok(service.getTransactionHistory(id));
}

    // Get All Transactions
    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> getAllTransactions() {

        return ResponseEntity.ok(
                service.getAllTransactions()
        );
    }
}
