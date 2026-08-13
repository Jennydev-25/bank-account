package dev.jenny.bankaccount.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

public class CheckingAccountTest {

    @Test
    void testConstructor_ValidValues_ShouldInitializeFields() {
        CheckingAccount checkingAccount = new CheckingAccount(15000f, 3f);
        assertThat(checkingAccount.getBalance(), is(equalTo(15000f)));
        assertThat(checkingAccount.getAnnualRate(), is(equalTo(3f)));
        assertThat(checkingAccount.getOverdraft(), is(equalTo(0f)));
    }
}
