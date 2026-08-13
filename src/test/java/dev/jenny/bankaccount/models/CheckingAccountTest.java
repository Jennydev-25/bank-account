package dev.jenny.bankaccount.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CheckingAccountTest {

    @Test
    void testConstructor_ValidValues_ShouldInitializeFields() {
        CheckingAccount checkingAccount = new CheckingAccount(15000f, 3f);
        assertThat(checkingAccount.getBalance(), is(equalTo(15000f)));
        assertThat(checkingAccount.getAnnualRate(), is(equalTo(3f)));
        assertThat(checkingAccount.getOverdraft(), is(equalTo(0f)));
    }

    @ParameterizedTest(name = "withdrawing {0} from a balance of 15000 should leave balance {1} without overdraft")
    @MethodSource("withdrawWithinBalanceTestCases")
    void testWithdraw_AmountWithinBalance_ShouldUpdateBalanceOnly(float amount, float expectedBalance) {
        CheckingAccount checkingAccount = new CheckingAccount(15000f, 3f);

        checkingAccount.withdraw(amount);

        assertThat(checkingAccount.getBalance(), is(equalTo(expectedBalance)));
        assertThat(checkingAccount.getOverdraft(), is(equalTo(0f)));
    }

    private static Stream<Arguments> withdrawWithinBalanceTestCases() {
        return Stream.of(
                Arguments.of(5000f, 10000f),
                Arguments.of(15000f, 0f));
    }

    @Test
    void testWithdraw_AmountExceedsBalance_ShouldCreateOverdraft() {
        CheckingAccount checkingAccount = new CheckingAccount(15000f, 3f);

        checkingAccount.withdraw(20000f);

        assertThat(checkingAccount.getBalance(), is(equalTo(0f)));
        assertThat(checkingAccount.getOverdraft(), is(equalTo(5000f)));
    }
}
