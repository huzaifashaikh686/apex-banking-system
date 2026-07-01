package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String accHolder;

    @Column(nullable = false)
    private double balance;

    public Account() {
    }

    public Account(String accHolder) {
        this.accHolder = accHolder;
        this.balance = 0;
    }

    public Long getId() {
        return id;
    }

    public String getAccHolder() {
        return accHolder;
    }

    public void setAccHolder(String accHolder) {
        this.accHolder = accHolder;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getAvailableBalance() {
        return balance;
    }

    protected void credit(double amount) {
        balance += amount;
    }

    protected void debit(double amount) {
        balance -= amount;
    }

    public abstract void deposit(double amount);

    public abstract void withdraw(double amount);

    public abstract String getAccountType();

    public abstract void applyInterest();

    @Override
    public String toString() {
        return getAccountType() + " Account | Holder: " + accHolder + " | Id: " + id + " | Balance: "
                + balance;
    }
}
