package com.financeapp.model;

public class SummaryResponse {

    private double totalIncome;
    private double totalExpense;
    private double balance;

    public SummaryResponse(double totalIncome, double totalExpense, double balance) {
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.balance = balance;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public double getBalance() {
        return balance;
    }
}