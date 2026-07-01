package com.example.demo.service;

import com.example.demo.model.Account;
import com.example.demo.model.CurrentAccount;
import com.example.demo.model.SavingAccount;
import com.example.demo.model.Transaction;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class BankService {

    private final AccountRepository accountRepo;
    private final TransactionRepository transactionRepo;

    public BankService(AccountRepository accountRepo, TransactionRepository transactionRepo) {
        this.accountRepo = accountRepo;
        this.transactionRepo = transactionRepo;
    }

    // Create Saving Account
    public Account createSavingAccount(String accHolder, double interestRate) {

        Account account = new SavingAccount(accHolder, interestRate);

        return accountRepo.save(account);
    }

    // Create Current Account
    public Account createCurrentAccount(String accHolder) {

        Account account = new CurrentAccount(accHolder);

        return accountRepo.save(account);
    }

    // Get Account By Id
    public Account getAccountById(Long id) {

        return accountRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Account not found with id : " + id));
    }

    // Get All Accounts
    public List<Account> getAllAccounts() {
        return accountRepo.findAll();
    }

    // Deposit
    public void deposit(Long id, double amount) {

        Account account = getAccountById(id);

        account.deposit(amount);

        accountRepo.save(account);

        transactionRepo.save(
                new Transaction(
                        "DEPOSIT ", amount, 0L, id)
        );
    }

    // Withdraw
    public void withdraw(Long id, double amount) {

        Account account = getAccountById(id);

        account.withdraw(amount);

        accountRepo.save(account);

        transactionRepo.save(
                new Transaction(
                        "WITHDRAW ", amount, id, 0L)
        );
    }

    // Transfer
    public void transfer(Long fromId, Long toId, double amount) {

        if (fromId.equals(toId)) {
            throw new RuntimeException("Cannot transfer to the same account.");
        }

        if (amount <= 0) {
            throw new RuntimeException("Amount must be greater than 0.");
        }

        Account sender = getAccountById(fromId);
        Account receiver = getAccountById(toId);

        sender.withdraw(amount);
        receiver.deposit(amount);

        accountRepo.save(sender);
        accountRepo.save(receiver);

        transactionRepo.save(
                new Transaction(
                        "TRANSFER ", amount, fromId, toId)
        );
    }

    // Apply Interest
    public void applyInterest(Long id) {

        Account account = getAccountById(id);

        if (!(account instanceof SavingAccount)) {
            throw new RuntimeException("Interest can only be applied to Saving Accounts.");
        }

        double oldBalance = account.getBalance();

        account.applyInterest();

        accountRepo.save(account);

        double interest = account.getBalance() - oldBalance;

        if (interest > 0) {
            transactionRepo.save(
                    new Transaction("INTEREST ", interest, 0L, id)
            );
        }
    }

    // Get All Transactions
    public List<Transaction> getAllTransactions() {
        return transactionRepo.findAll();
    }
}
