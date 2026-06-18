package com.example.demo.model;

public class SavingAccount extends Account {

    private double interestRate;

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
        // Uses getBalance() and credit() since balance is private in Account class
        double interest = getBalance() * interestRate / 100;
        credit(interest);
    }

    @Override
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be > 0");
        }
        credit(amount);
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be > 0");
        }
        if (amount > getBalance()) {
            throw new IllegalArgumentException("Insufficient Balance");
        }
        debit(amount);
    }

    public double getInterestRate() {
        return interestRate;
    }
}