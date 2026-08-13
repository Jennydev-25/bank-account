package dev.jenny.bankaccount.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class AccountTest {

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account(15000f, 3f);
    }

    @Test
    void testConstructor_ValidValues_ShouldInitializeFields() {
        assertThat(account.getBalance(), is(equalTo(15000f)));
        assertThat(account.getAnnualRate(), is(equalTo(3f)));
        assertThat(account.getDepositCount(), is(equalTo(0)));
        assertThat(account.getWithdrawalCount(), is(equalTo(0)));
        assertThat(account.getMonthlyFee(), is(equalTo(0f)));
    }

    @Test
    void testDeposit_ValidAmount_ShouldIncreaseBalanceAndCount() {
        account.deposit(500f);
        assertThat(account.getBalance(), is(equalTo(15500f)));
        assertThat(account.getDepositCount(), is(equalTo(1)));
    }

    @ParameterizedTest(name = "deposit({0}) should throw exception")
    @ValueSource(floats = { 0f, -100f })
    void testDeposit_InvalidAmount_ShouldThrowException(float amount) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> account.deposit(amount));
        assertThat(exception.getMessage(), is(equalTo("Amount must be greater than zero")));
    }

    @Test
    void testWithdraw_ValidAmount_ShouldDecreaseBalanceAndCount() {
        account.withdraw(500f);
        assertThat(account.getBalance(), is(equalTo(14500f)));
        assertThat(account.getWithdrawalCount(), is(equalTo(1)));
    }

    @ParameterizedTest(name = "withdraw({0}) should throw exception")
    @ValueSource(floats = { 0f, -100f })
    void testWithdraw_InvalidAmount_ShouldThrowException(float amount) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> account.withdraw(amount));
        assertThat(exception.getMessage(), is(equalTo("Amount must be greater than zero")));
    }

    @Test
    void testWithdraw_AmountGreaterThanBalance_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> account.withdraw(20000f));
        assertThat(exception.getMessage(), is(equalTo("Insufficient funds to complete the withdrawal")));
    }

    @Test
    void testCalculateMonthlyInterest_ShouldIncreaseBalance() {
        account.calculateMonthlyInterest();
        assertThat((double) account.getBalance(), is(closeTo(15037.5, 0.01)));
    }

    @Test
    void testGenerateMonthlyStatement_ShouldSubtractFeeAndApplyInterest() {
        account.generateMonthlyStatement();
        assertThat((double) account.getBalance(), is(closeTo(15037.5, 0.01)));
    }

    @Test
    void testPrint_NewAccount_ShouldReturnInitialValues() {
        assertThat(account.print(),
                is(equalTo(
                        "Balance: 15000.00, Deposits: 0, Withdrawals: 0, Monthly fee: 0.00, Annual rate: 3.00%")));
    }

    @Test
    void testPrint_AfterOperations_ShouldReturnUpdatedValues() {
        account.deposit(500f);
        account.withdraw(200f);
        assertThat(account.print(),
                is(equalTo(
                        "Balance: 15300.00, Deposits: 1, Withdrawals: 1, Monthly fee: 0.00, Annual rate: 3.00%")));
    }
}
