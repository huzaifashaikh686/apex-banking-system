package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class SavingAccount extends Account {

    @Column(nullable = false)
    private double interestRate;

    public SavingAccount() {
    }

    public SavingAccount(String accHolder, double interestRate) {
        super(accHolder);

        if (interestRate < 0) {
            throw new IllegalArgumentException("Interest rate cannot be negative.");
        }

        this.interestRate = interestRate;
    }

    @Override
    public String getAccountType() {
        return "Saving";
    }

    @Override
    public void applyInterest() {
        double interest = getBalance() * interestRate / 100;
        credit(interest);
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

        if (amount > getBalance()) {
            throw new IllegalArgumentException("Insufficient balance.");
        }

        debit(amount);
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
}
