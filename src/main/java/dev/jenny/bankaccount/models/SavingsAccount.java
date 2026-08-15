package dev.jenny.bankaccount.models;

import java.util.Locale;

/**
 * Represents a savings account: deposits and withdrawals only work while the
 * account is active. The account becomes active once the balance reaches
 * 10000 or more
 */
public class SavingsAccount extends Account {

    private static final float ACTIVE_BALANCE_THRESHOLD = 10000f;
    private static final int FREE_WITHDRAWALS_PER_STATEMENT = 4;
    private static final float EXTRA_WITHDRAWAL_FEE = 1000f;

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

    @Override
    public void generateMonthlyStatement() {
        if (withdrawalCount > FREE_WITHDRAWALS_PER_STATEMENT) {
            monthlyFee += EXTRA_WITHDRAWAL_FEE * (withdrawalCount - FREE_WITHDRAWALS_PER_STATEMENT);
        }
        updateActiveStatus();
        super.generateMonthlyStatement();
    }

    @Override
    public String print() {
        return String.format(Locale.US, "Balance: %.2f, Monthly fee: %.2f, Transactions: %d",
                balance, monthlyFee, depositCount + withdrawalCount);
    }

    private void updateActiveStatus() {
        active = balance >= ACTIVE_BALANCE_THRESHOLD;
    }

    private void validateActive() {
        if (!active) {
            throw new IllegalStateException("Account is inactive");
        }
    }
}
