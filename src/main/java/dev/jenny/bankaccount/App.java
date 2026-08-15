package dev.jenny.bankaccount;

import java.util.ArrayList;
import java.util.List;

import dev.jenny.bankaccount.models.Account;
import dev.jenny.bankaccount.models.CheckingAccount;
import dev.jenny.bankaccount.models.SavingsAccount;

/**
 * Entry point of the console app
 */
public class App {

    public static void main(String[] args) {
        printAccounts(createDemoAccounts());
    }

    static List<Account> createDemoAccounts() {
        List<Account> accounts = new ArrayList<>();

        Account account = new Account(15000f, 3f);
        account.deposit(500f);
        account.withdraw(200f);
        account.generateMonthlyStatement();
        accounts.add(account);

        SavingsAccount savingsAccount = new SavingsAccount(12000f, 3f);
        savingsAccount.deposit(1000f);
        savingsAccount.withdraw(300f);
        savingsAccount.generateMonthlyStatement();
        accounts.add(savingsAccount);

        CheckingAccount checkingAccount = new CheckingAccount(10000f, 3f);
        checkingAccount.withdraw(15000f);
        checkingAccount.deposit(2000f);
        checkingAccount.generateMonthlyStatement();
        accounts.add(checkingAccount);

        return accounts;
    }

    static void printAccounts(List<Account> accounts) {
        for (Account account : accounts) {
            System.out.println(account.print());
        }
    }
}
