package com.example.demo.model;

import java.time.LocalDateTime;

public class Transaction {

    private LocalDateTime timestamp;
    private String tType;
    private double amount;
    private int fromAcc;
    private int toAcc;

    public Transaction(String tType, double amount, int fromAcc, int toAcc){
        this.tType = tType;
        this.amount = amount;
        this.fromAcc = fromAcc;
        this.toAcc = toAcc;
        this.timestamp = LocalDateTime.now();
    }

    public String getType(){
        return tType;
    }

    public double getAmount(){
        return amount;
    }

    public int getFromAcc(){
        return fromAcc;
    }

    public int getToAcc(){
        return toAcc;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
public String toString() {
    return "[" + timestamp + "] " + tType + " of " + amount + " from Account #" + fromAcc + " to Account #" + toAcc;
}
}
