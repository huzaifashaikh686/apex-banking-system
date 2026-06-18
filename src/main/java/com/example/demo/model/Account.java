package com.example.demo.model;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class Account {

    private static AtomicInteger counter = new AtomicInteger(1000);
    private String accHolder;
    private int accNo;
    private double balance;
    
    public Account(String accHolder){
        this.accHolder = accHolder;
        this.accNo = counter.getAndIncrement();
        this.balance = 0;
    }

    public String getAccHolder(){
        return accHolder;
    }

    public int getAccNo(){
        return accNo;
    }

    public double getBalance(){
        return balance;
    }

    public double getAvailableBalance() {
        return balance;
    }
    
    protected void credit(double amount){
        balance += amount;
    }

    protected void debit(double amount){
        balance -= amount;
    }
    
    public abstract void deposit(double amount);
    public abstract void withdraw(double amount);
    public abstract String getAccountType();
    
    // Abstract contract to allow dynamic execution in service layers safely
    public abstract void applyInterest();

    @Override
    public String toString(){
        return getAccountType() + " Account | Holder: " + accHolder + " | AccNo: " + accNo + " | Balance: " + balance;
    }
}