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

    @Test
    void testWithdraw_InactiveAccount_ShouldThrowException() {
        SavingsAccount savingsAccount = new SavingsAccount(5000f, 3f);
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> savingsAccount.withdraw(500f));
        assertThat(exception.getMessage(), is(equalTo("Account is inactive")));
    }

    @Test
    void testGenerateMonthlyStatement_FourOrFewerWithdrawals_ShouldNotAddFee() {
        SavingsAccount savingsAccount = new SavingsAccount(15000f, 3f);
        savingsAccount.generateMonthlyStatement();
        assertThat(savingsAccount.getMonthlyFee(), is(equalTo(0f)));
    }

    @Test
    void testGenerateMonthlyStatement_MoreThanFourWithdrawals_ShouldAddFee() {
        SavingsAccount savingsAccount = new SavingsAccount(15000f, 3f);
        for (int i = 0; i < 5; i++) {
            savingsAccount.withdraw(100f);
        }
        savingsAccount.generateMonthlyStatement();
        assertThat(savingsAccount.getMonthlyFee(), is(equalTo(1000f)));
    }

    @Test
    void testPrint_NewAccount_ShouldReturnInitialValues() {
        SavingsAccount savingsAccount = new SavingsAccount(15000f, 3f);
        assertThat(savingsAccount.print(),
                is(equalTo("Balance: 15000.00, Monthly fee: 0.00, Transactions: 0")));
    }

    @Test
    void testPrint_AfterOperations_ShouldReturnUpdatedValues() {
        SavingsAccount savingsAccount = new SavingsAccount(15000f, 3f);
        savingsAccount.deposit(500f);
        savingsAccount.withdraw(200f);
        assertThat(savingsAccount.print(),
                is(equalTo("Balance: 15300.00, Monthly fee: 0.00, Transactions: 2")));
    }
}
