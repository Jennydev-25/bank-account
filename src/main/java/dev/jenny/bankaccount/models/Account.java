package dev.jenny.bankaccount.models;

import java.util.Locale;

/**
 * Represents a generic bank account with the operations shared by every account
 * type
 */
public class Account {

    protected float balance;
    protected int depositCount;
    protected int withdrawalCount;
    protected float annualRate;
    protected float monthlyFee;

    /**
     * Creates an account with the given balance and annual interest rate as a
     * percentage (3 means 3%)
     */
    public Account(float balance, float annualRate) {
        this.balance = balance;
        this.annualRate = annualRate;
        this.depositCount = 0;
        this.withdrawalCount = 0;
        this.monthlyFee = 0f;
    }

    public float getBalance() {
        return balance;
    }

    public int getDepositCount() {
        return depositCount;
    }

    public int getWithdrawalCount() {
        return withdrawalCount;
    }

    public float getAnnualRate() {
        return annualRate;
    }

    public float getMonthlyFee() {
        return monthlyFee;
    }

    public void deposit(float amount) {
        validateAmount(amount);
        balance += amount;
        depositCount++;
    }

    public void withdraw(float amount) {
        validateAmount(amount);
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient funds to complete the withdrawal");
        }
        balance -= amount;
        withdrawalCount++;
    }

    public void calculateMonthlyInterest() {
        balance += balance * (annualRate / 12) / 100;
    }

    public void generateMonthlyStatement() {
        balance -= monthlyFee;
        calculateMonthlyInterest();
    }

    public String print() {
        return String.format(Locale.US,
                "Balance: %.2f, Deposits: %d, Withdrawals: %d, Monthly fee: %.2f, Annual rate: %.2f%%",
                balance, depositCount, withdrawalCount, monthlyFee, annualRate);
    }

    protected void validateAmount(float amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
    }
}
