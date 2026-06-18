package com.example.demo.repository;

import com.example.demo.model.Account;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AccountRepository {
    // Thread-safe map for concurrent data access
    private final Map<Integer, Account> accountRepo = new ConcurrentHashMap<>();

    public void save(Account acc) {
        accountRepo.put(acc.getAccNo(), acc);
    }

    public Account findByAccNo(int accNo) {
        return accountRepo.get(accNo);
    }

    public ArrayList<Account> viewAllAccounts() {
        return new ArrayList<>(accountRepo.values());
    }

    public void delete(int accNo) {
        accountRepo.remove(accNo);
    }
}