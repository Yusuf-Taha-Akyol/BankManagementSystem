package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Account {
    private int id;
    private int userId;
    private double balance;
    private LocalDateTime createdAt;
    private boolean isDeleted = false;

    public Account(int userId, double balance) {
        this.userId = userId;
        this.balance = balance;
        this.createdAt = LocalDateTime.now();
    }

    public Account(int id, int userId, double balance, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.balance = balance;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
