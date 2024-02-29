
package com.progressoft.induction.atm;

import com.progressoft.induction.atm.Impl.ATMImpl;
import com.progressoft.induction.atm.Impl.BankingSystemImpl;
import com.progressoft.induction.atm.exceptions.InsufficientFundsException;
import com.progressoft.induction.atm.exceptions.NotEnoughMoneyInATMException;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    public static void main(String args[]) {
       
        ATM atm = new ATMImpl();
        BankingSystem bankingSystem = new BankingSystemImpl();

       
        String accountNumber1 = "123456789";
        String accountNumber2 = "111111111";

        try {
            
            List<Banknote> withdrawnBanknotes = atm.withdraw(accountNumber1, new BigDecimal("100.0"));
            System.out.println("Withdrawn banknotes: " + withdrawnBanknotes);
            System.out.println("Account balance after withdrawal: " + atm.checkBalance(accountNumber1));

            atm.withdraw(accountNumber1, new BigDecimal("2000.0"));

        } catch (InsufficientFundsException e) {
            System.out.println("Insufficient funds in the account.");

        } catch (NotEnoughMoneyInATMException e) {
            System.out.println("Not enough money in the ATM.");
        }

        System.out.println("Account balance for " + accountNumber1 + ": " + atm.checkBalance(accountNumber1));
        System.out.println("Account balance for " + accountNumber2 + ": " + atm.checkBalance(accountNumber2));
    }
}
