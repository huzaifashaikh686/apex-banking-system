package com.example.demo.repository;

import com.example.demo.model.Transaction;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class TransactionRepository {
    // Thread-safe list optimization for write-heavy data logging
    private final List<Transaction> transactionRepo = new CopyOnWriteArrayList<>();

    public void save(Transaction t) {
        transactionRepo.add(t);
    }

    public ArrayList<Transaction> transactionsFindByAccNo(int accNo) {
        ArrayList<Transaction> result = new ArrayList<>();
        for (Transaction t : transactionRepo) {
            if (t.getFromAcc() == accNo || t.getToAcc() == accNo) {
                result.add(t);
            }
        }
        return result;
    }

    public ArrayList<Transaction> findAllAccountsTransactions() {
        return new ArrayList<>(transactionRepo);
    }
}