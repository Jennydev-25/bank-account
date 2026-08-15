package dev.jenny.bankaccount;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import dev.jenny.bankaccount.models.Account;
import dev.jenny.bankaccount.models.CheckingAccount;
import dev.jenny.bankaccount.models.SavingsAccount;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AppTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private List<Account> accounts;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStream));
        accounts = App.createDemoAccounts();
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testConstructor_ShouldCreateInstance() {
        assertThat(new App(), is(notNullValue()));
    }

    @Test
    void testCreateDemoAccounts_ShouldReturnThreeAccountsWithExpectedState() {
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

    @Test
    void testPrintAccounts_GivenListOfAccounts_ShouldPrintEachAccount() {
        App.printAccounts(accounts);

        String output = outputStream.toString().trim();
        assertThat(output, containsString(accounts.get(0).print()));
        assertThat(output, containsString(accounts.get(1).print()));
        assertThat(output, containsString(accounts.get(2).print()));
    }

    @Test
    void testMain_ShouldPrintDemoAccounts() {
        App.main(new String[] {});

        String output = outputStream.toString().trim();
        assertThat(output, containsString(accounts.get(0).print()));
        assertThat(output, containsString(accounts.get(1).print()));
        assertThat(output, containsString(accounts.get(2).print()));
    }
}
