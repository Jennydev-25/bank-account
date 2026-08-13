package dev.jenny.bankaccount.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class SavingsAccountTest {

    @Test
    void testConstructor_BalanceAboveThreshold_ShouldBeActive() {
        SavingsAccount savingsAccount = new SavingsAccount(15000f, 3f);
        assertThat(savingsAccount.getBalance(), is(equalTo(15000f)));
        assertThat(savingsAccount.getAnnualRate(), is(equalTo(3f)));
        assertThat(savingsAccount.isActive(), is(true));
    }

    @Test
    void testConstructor_BalanceBelowThreshold_ShouldBeInactive() {
        SavingsAccount savingsAccount = new SavingsAccount(5000f, 3f);
        assertThat(savingsAccount.isActive(), is(false));
    }

    @Test
    void testDeposit_ActiveAccount_ShouldIncreaseBalance() {
        SavingsAccount savingsAccount = new SavingsAccount(15000f, 3f);
        savingsAccount.deposit(500f);
        assertThat(savingsAccount.getBalance(), is(equalTo(15500f)));
    }

    @Test
    void testDeposit_InactiveAccount_ShouldThrowException() {
        SavingsAccount savingsAccount = new SavingsAccount(5000f, 3f);
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> savingsAccount.deposit(500f));
        assertThat(exception.getMessage(), is(equalTo("Account is inactive")));
    }

    @Test
    void testWithdraw_ActiveAccount_ShouldDecreaseBalance() {
        SavingsAccount savingsAccount = new SavingsAccount(15000f, 3f);
        savingsAccount.withdraw(500f);
        assertThat(savingsAccount.getBalance(), is(equalTo(14500f)));
    }
}
