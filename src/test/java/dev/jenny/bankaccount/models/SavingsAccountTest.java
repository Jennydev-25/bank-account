package dev.jenny.bankaccount.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class SavingsAccountTest {

    @ParameterizedTest(name = "balance {0} should result in an active account")
    @ValueSource(floats = { 10000f, 15000f })
    void testConstructor_BalanceAtOrAboveThreshold_ShouldBeActive(float balance) {
        SavingsAccount savingsAccount = new SavingsAccount(balance, 3f);
        assertThat(savingsAccount.getBalance(), is(equalTo(balance)));
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

    @ParameterizedTest(name = "{0} withdrawals should not add a fee")
    @ValueSource(ints = { 0, 4 })
    void testGenerateMonthlyStatement_FourOrFewerWithdrawals_ShouldNotAddFee(int withdrawalCount) {
        SavingsAccount savingsAccount = new SavingsAccount(15000f, 3f);
        for (int i = 0; i < withdrawalCount; i++) {
            savingsAccount.withdraw(100f);
        }

        savingsAccount.generateMonthlyStatement();

        assertThat(savingsAccount.getMonthlyFee(), is(equalTo(0f)));
    }

    @ParameterizedTest(name = "{0} withdrawals should add a fee of {1}")
    @MethodSource("excessWithdrawalFeeTestCases")
    void testGenerateMonthlyStatement_MoreThanFourWithdrawals_ShouldAddFee(int withdrawalCount, float expectedFee) {
        SavingsAccount savingsAccount = new SavingsAccount(15000f, 3f);
        for (int i = 0; i < withdrawalCount; i++) {
            savingsAccount.withdraw(100f);
        }

        savingsAccount.generateMonthlyStatement();

        assertThat(savingsAccount.getMonthlyFee(), is(equalTo(expectedFee)));
    }

    private static Stream<Arguments> excessWithdrawalFeeTestCases() {
        return Stream.of(
                Arguments.of(5, 1000f),
                Arguments.of(6, 2000f));
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
