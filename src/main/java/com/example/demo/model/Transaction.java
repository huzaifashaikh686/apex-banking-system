package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tType;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private Long fromAcc;

    @Column(nullable = false)
    private Long toAcc;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    public Transaction() {
    }

    public Transaction(String tType, double amount, Long fromAcc, Long toAcc) {
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

    public void setType(String tType) {
        this.tType = tType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Long getFromAcc() {
        return fromAcc;
    }

    public void setFromAcc(Long fromAcc) {
        this.fromAcc = fromAcc;
    }

    public Long getToAcc() {
        return toAcc;
    }

    public void setToAcc(Long toAcc) {
        this.toAcc = toAcc;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "[" + timestamp + "] " + tType + " of " + amount + " from Account #" + fromAcc + " to Account #"
                + toAcc;
    }
}
