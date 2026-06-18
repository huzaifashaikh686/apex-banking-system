package com.example.demo.model;

public class CurrentAccount extends Account {

    private int overdraft = 10000;

    public CurrentAccount(String accHolder){
        super(accHolder);
    }

    @Override
    public String getAccountType(){
        return "Current";
    }
 
    @Override
    public double getAvailableBalance(){
        return getBalance() + overdraft;
    }

    public double getOverDraft(){ 
        return overdraft; 
    }

    @Override
    public void applyInterest() {
        // Current accounts do not earn interest in standard business banking models
    }

    @Override
    public void deposit(double amount){
        if(amount <= 0){
            throw new IllegalArgumentException("Amount must be > 0");
        }
        credit(amount);
    }

    @Override
    public void withdraw(double amount){
        if(amount <= 0){
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        if(amount > getAvailableBalance()) throw new IllegalArgumentException("Insufficient Balance");
        debit(amount);
    }
}