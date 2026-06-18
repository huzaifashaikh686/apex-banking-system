package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class BankService {
    private final AccountRepository accountRepo;
    private final TransactionRepository txRepo;

    public BankService(AccountRepository accountRepo, TransactionRepository txRepo) {
        this.accountRepo = accountRepo;
        this.txRepo = txRepo;
    }
    
    public void createSavingAccount(String accHolder, double interestRate) {
        Account acc = new SavingAccount(accHolder, interestRate);
        accountRepo.save(acc);
    }

    public void createCurrentAccount(String accHolder) {
        Account acc = new CurrentAccount(accHolder);
        accountRepo.save(acc);
    }

    public Account getAccountByNo(int accNo) {
        Account acc = accountRepo.findByAccNo(accNo);
        if (acc == null) throw new IllegalArgumentException("Account Not Found");
        return acc;
    }

    public void removeAccount(int accNo) {
        if (accountRepo.findByAccNo(accNo) == null) throw new IllegalArgumentException("Account Not Found");
        accountRepo.delete(accNo);
    }

    public ArrayList<Account> getAllAccounts() { 
        return accountRepo.viewAllAccounts(); 
    }

    public void addInterestToAccount(int accNo) {
        Account acc = accountRepo.findByAccNo(accNo);
        if (acc == null) throw new IllegalArgumentException("Account Not Found");
        
        if (acc instanceof SavingAccount) {
            synchronized (acc) {
                double oldBalance = acc.getBalance();
                acc.applyInterest();
                double interestAmount = acc.getBalance() - oldBalance;

                if (interestAmount > 0) {
                    txRepo.save(new Transaction("Interest", interestAmount, 0, accNo));
                }
            }
        } else {
            throw new IllegalArgumentException("Account is not a Saving Account");
        }
    }

    public void depositService(int accNo, double amount) {
        Account acc = accountRepo.findByAccNo(accNo);
        if (acc == null) throw new IllegalArgumentException("Account Not Found");
        
        synchronized (acc) {
            acc.deposit(amount);
            txRepo.save(new Transaction("Deposit", amount, 0, accNo));   
        }
    }

    public void withdrawService(int accNo, double amount) {
        Account acc = accountRepo.findByAccNo(accNo);
        if (acc == null) throw new IllegalArgumentException("Account Not Found");
        
        synchronized (acc) {
            acc.withdraw(amount);
            txRepo.save(new Transaction("Withdraw", amount, accNo, 0));
        }
    }

    public void transfer(int from, int to, double amount) {
        if (from == to) throw new IllegalArgumentException("Cannot Transfer To Same Account");
        if (amount <= 0) throw new IllegalArgumentException("Amount must be > 0");

        Account sender = accountRepo.findByAccNo(from);
        Account receiver = accountRepo.findByAccNo(to);

        if (sender == null) throw new IllegalArgumentException("Sender's Account Not Found");
        if (receiver == null) throw new IllegalArgumentException("Receiver's Account Not Found");

        // Deadlock prevention: Always lock resources in a predictable, ordered sequence
        Account firstLock = from < to ? sender : receiver;
        Account secondLock = from < to ? receiver : sender;

        synchronized (firstLock) {
            synchronized (secondLock) {
                if (amount > sender.getAvailableBalance()) {
                    throw new IllegalArgumentException("Insufficient Balance");
                }
                
                sender.withdraw(amount);
                try {
                    receiver.deposit(amount);
                } catch (Exception e) {
                    sender.deposit(amount); // Rollback state
                    txRepo.save(new Transaction("Transfer Rollback", amount, sender.getAccNo(), receiver.getAccNo()));
                    throw new RuntimeException("Transfer failed and rolled back", e);
                }
                txRepo.save(new Transaction("Transferred", amount, sender.getAccNo(), receiver.getAccNo()));
            }
        }
    }

    public ArrayList<Transaction> getTransactionHistory(int accNo) {
        if (accountRepo.findByAccNo(accNo) == null) throw new IllegalArgumentException("Account Not Found");
        return txRepo.transactionsFindByAccNo(accNo);
    }

    public ArrayList<Transaction> getAllAccountsTransactionHistory() {
        return txRepo.findAllAccountsTransactions();
    }
}