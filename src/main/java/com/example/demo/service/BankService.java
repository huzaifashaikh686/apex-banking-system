package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {

    private final AccountRepository accountRepo;
    private final TransactionRepository txRepo;

    public BankService(AccountRepository accountRepo, TransactionRepository txRepo) {
        this.accountRepo = accountRepo;
        this.txRepo = txRepo;
    }

    // CREATE SAVING ACCOUNT
    public SavingAccount createSavingAccount(String accHolder, double interestRate) {
        SavingAccount acc = new SavingAccount(accHolder, interestRate);
        return accountRepo.save(acc);
    }

    // CREATE CURRENT ACCOUNT
    public CurrentAccount createCurrentAccount(String accHolder) {
        CurrentAccount acc = new CurrentAccount(accHolder);
        return accountRepo.save(acc);
    }

    // GET ACCOUNT BY ID
    public Account getAccountById(Long id) {
        return accountRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Account Not Found"));
    }

    // GET ALL ACCOUNTS
    public List<Account> getAllAccounts() {
        return accountRepo.findAll();
    }

    // DEPOSIT
    public void deposit(Long id, double amount) {
        Account acc = getAccountById(id);

        synchronized (acc) {
            acc.deposit(amount);
            accountRepo.save(acc);
        }

        txRepo.save(new Transaction("DEPOSIT", amount, 0, id.intValue()));
    }

    // WITHDRAW
    public void withdraw(Long id, double amount) {
        Account acc = getAccountById(id);

        synchronized (acc) {
            acc.withdraw(amount);
            accountRepo.save(acc);
        }

        txRepo.save(new Transaction("WITHDRAW", amount, id.intValue(), 0));
    }

    // TRANSFER
    public void transfer(Long fromId, Long toId, double amount) {

        if (fromId.equals(toId)) {
            throw new RuntimeException("Cannot transfer to same account");
        }

        Account sender = getAccountById(fromId);
        Account receiver = getAccountById(toId);

        // deadlock prevention
        Account first = fromId < toId ? sender : receiver;
        Account second = fromId < toId ? receiver : sender;

        synchronized (first) {
            synchronized (second) {

                if (amount > sender.getBalance()) {
                    throw new RuntimeException("Insufficient Balance");
                }

                sender.withdraw(amount);
                receiver.deposit(amount);

                accountRepo.save(sender);
                accountRepo.save(receiver);

                txRepo.save(
                        new Transaction("TRANSFER", amount,
                                fromId.intValue(),
                                toId.intValue())
                );
            }
        }
    }

    // APPLY INTEREST
    public void applyInterest(Long id) {
        Account acc = getAccountById(id);

        if (!(acc instanceof SavingAccount)) {
            throw new RuntimeException("Not a Saving Account");
        }

        double oldBalance = acc.getBalance();

        synchronized (acc) {
            acc.applyInterest();
            accountRepo.save(acc);
        }

        double interest = acc.getBalance() - oldBalance;

        if (interest > 0) {
            txRepo.save(new Transaction("INTEREST", interest, 0, id.intValue()));
        }
    }

    // TRANSACTION HISTORY (ALL)
    public List<Transaction> getAllTransactions() {
        return txRepo.findAll();
    }
}
