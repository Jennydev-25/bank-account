package dev.jenny.bankaccount.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

public class SavingsAccountTest {

    @Test
    void testConstructor_BalanceAboveThreshold_ShouldBeActive() {
        SavingsAccount savingsAccount = new SavingsAccount(15000f, 3f);
        assertThat(savingsAccount.getBalance(), is(equalTo(15000f)));
        assertThat(savingsAccount.getAnnualRate(), is(equalTo(3f)));
        assertThat(savingsAccount.isActive(), is(true));
    }
}
