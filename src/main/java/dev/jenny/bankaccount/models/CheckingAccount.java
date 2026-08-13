package dev.jenny.bankaccount.models;

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

    @Override
    public void deposit(float amount) {
        super.deposit(amount);
        if (overdraft > 0) {
            overdraft = Math.max(0, overdraft - amount);
        }
    }
}
