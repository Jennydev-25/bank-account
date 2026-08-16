package dev.jenny.bankaccount.models;

import java.util.Locale;

/**
 * Represents a checking account: withdrawals can exceed the balance, the
 * difference is tracked as overdraft
 */
public class CheckingAccount extends Account {

    protected float overdraft;

    public CheckingAccount(float balance, float annualRate) {
        super(balance, annualRate);
        this.overdraft = 0f;
    }

    public float getOverdraft() {
        return overdraft;
    }

    /**
     * Withdraws the given amount; any excess over the balance is added to
     * the overdraft instead of throwing an exception
     */
    @Override
    public void withdraw(float amount) {
        validateAmount(amount);
        if (amount > balance) {
            overdraft += amount - balance;
            balance = 0f;
        } else {
            balance -= amount;
        }
        withdrawalCount++;
    }

    /**
     * Deposits the given amount, applying it to the overdraft first; only
     * the leftover increases the balance
     */
    @Override
    public void deposit(float amount) {
        validateAmount(amount);
        if (amount > overdraft) {
            balance += amount - overdraft;
            overdraft = 0f;
        } else {
            overdraft -= amount;
        }
        depositCount++;
    }

    @Override
    public void generateMonthlyStatement() {
        super.generateMonthlyStatement();
    }

    @Override
    public String print() {
        return String.format(Locale.US, "Balance: %.2f, Monthly fee: %.2f, Transactions: %d, Overdraft: %.2f",
                balance, monthlyFee, depositCount + withdrawalCount, overdraft);
    }
}
