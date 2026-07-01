package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class CurrentAccount extends Account {

    @Column(nullable = false)
    private double overdraft = 10000;

    public CurrentAccount() {
    }

    public CurrentAccount(String accHolder) {
        super(accHolder);
    }

    @Override
    public String getAccountType() {
        return "Current";
    }

    @Override
    public double getAvailableBalance() {
        return getBalance() + overdraft;
    }

    public double getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(double overdraft) {
        this.overdraft = overdraft;
    }

    @Override
    public void applyInterest() {
        // Current accounts do not earn interest
    }

    @Override
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0.");
        }

        credit(amount);
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0.");
        }

        if (amount > getAvailableBalance()) {
            throw new IllegalArgumentException("Insufficient balance.");
        }

        debit(amount);
    }
}
