package dev.jenny.bankaccount;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.List;

import dev.jenny.bankaccount.models.Account;
import dev.jenny.bankaccount.models.CheckingAccount;
import dev.jenny.bankaccount.models.SavingsAccount;

import org.junit.jupiter.api.Test;

class AppTest {

    @Test
    void testCreateDemoAccounts_ShouldReturnThreeAccountsWithExpectedState() {
        List<Account> accounts = App.createDemoAccounts();

        assertThat(accounts, hasSize(3));

        Account account = accounts.get(0);
        assertThat((double) account.getBalance(), is(closeTo(15338.25, 0.01)));

        SavingsAccount savingsAccount = (SavingsAccount) accounts.get(1);
        assertThat((double) savingsAccount.getBalance(), is(closeTo(12731.75, 0.01)));
        assertThat(savingsAccount.isActive(), is(true));

        CheckingAccount checkingAccount = (CheckingAccount) accounts.get(2);
        assertThat((double) checkingAccount.getBalance(), is(closeTo(2005.0, 0.01)));
        assertThat(checkingAccount.getOverdraft(), is(equalTo(3000f)));
    }
}
