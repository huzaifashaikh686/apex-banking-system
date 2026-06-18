package com.example.demo.controller;

import com.example.demo.model.*;
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

    @PostMapping("/accounts/saving")
    public ResponseEntity<String> createSaving(@RequestParam String holder, @RequestParam double interest) {
        try {
            service.createSavingAccount(holder, interest);
            return ResponseEntity.ok("Saving Account Created!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/accounts/current")
    public ResponseEntity<String> createCurrent(@RequestParam String holder) {
        try {
            service.createCurrentAccount(holder);
            return ResponseEntity.ok("Current Account Created!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/accounts/{accNo}/deposit")
    public ResponseEntity<String> deposit(@PathVariable int accNo, @RequestParam double amount) {
        try {
            service.depositService(accNo, amount);
            return ResponseEntity.ok("Deposited " + amount);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/accounts/{accNo}/withdraw")
    public ResponseEntity<String> withdraw(@PathVariable int accNo, @RequestParam double amount) {
        try {
            service.withdrawService(accNo, amount);
            return ResponseEntity.ok("Withdrawn " + amount);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/accounts/transfer")
    public ResponseEntity<String> transfer(@RequestParam int from, @RequestParam int to, @RequestParam double amount) {
        try {
            service.transfer(from, to, amount);
            return ResponseEntity.ok("Transferred " + amount);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/accounts/{accNo}/apply-interest")
    public ResponseEntity<String> applyInterest(@PathVariable int accNo) {
        try {
            service.addInterestToAccount(accNo);
            return ResponseEntity.ok("Interest Applied Successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/accounts/{accNo}")
    public ResponseEntity<?> getAccount(@PathVariable int accNo) {
        try {
            Account acc = service.getAccountByNo(accNo);
            return ResponseEntity.ok(acc);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(service.getAllAccounts());
    }

    @GetMapping("/accounts/{accNo}/transactions")
    public ResponseEntity<?> getTransactions(@PathVariable int accNo) {
        try {
            List<Transaction> history = service.getTransactionHistory(accNo);
            return ResponseEntity.ok(history);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return ResponseEntity.ok(service.getAllAccountsTransactionHistory());
    }
}