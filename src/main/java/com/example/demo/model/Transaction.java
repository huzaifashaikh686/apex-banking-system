package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tType;
    private double amount;
    private int fromAcc;
    private int toAcc;

    private LocalDateTime timestamp;

    public Transaction() {}

    public Transaction(String tType, double amount, int fromAcc, int toAcc) {
        this.tType = tType;
        this.amount = amount;
        this.fromAcc = fromAcc;
        this.toAcc = toAcc;
    }

    @PrePersist
    public void onCreate() {
        this.timestamp = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return tType;
    }

    public double getAmount() {
        return amount;
    }

    public int getFromAcc() {
        return fromAcc;
    }

    public int getToAcc() {
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
