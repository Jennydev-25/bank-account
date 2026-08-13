package dev.jenny.bankaccount.models;

/**
 * Represents a savings account: deposits and withdrawals only work while the
 * account is active. The account becomes active once the balance reaches
 * 10000 or more
 */
public class SavingsAccount extends Account {

    protected boolean active;

    public SavingsAccount(float balance, float annualRate) {
        super(balance, annualRate);
        updateActiveStatus();
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public void deposit(float amount) {
        validateActive();
        super.deposit(amount);
    }

    @Override
    public void withdraw(float amount) {
        validateActive();
        super.withdraw(amount);
    }

    private void updateActiveStatus() {
        active = balance >= 10000;
    }

    private void validateActive() {
        if (!active) {
            throw new IllegalStateException("Account is inactive");
        }
    }
}
