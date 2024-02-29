
package com.progressoft.induction.atm.Impl;

import com.progressoft.induction.atm.*;
import com.progressoft.induction.atm.exceptions.AccountNotFoundException;
import com.progressoft.induction.atm.exceptions.InsufficientFundsException;
import com.progressoft.induction.atm.exceptions.NotEnoughMoneyInATMException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ATMImpl implements ATM {
    private final BankingSystem bankingSystem = new BankingSystemImpl();

    @Override
    public List<Banknote> withdraw(String accountNumber, BigDecimal amount) {
        BigDecimal accountBalance = bankingSystem.getAccountBalance(accountNumber);
        if (accountBalance.compareTo(amount) < 0) {
            throw new InsufficientFundsException(accountNumber);
        }

        BigDecimal atmBalance = bankingSystem.sumOfMoneyInAtm();
        if (atmBalance.compareTo(amount) < 0) {
            throw new NotEnoughMoneyInATMException(accountNumber);
        }

        List<Banknote> banknotesToWithdraw = calculateBanknotesToWithdraw(amount);

       
        bankingSystem.debitAccount(accountNumber, amount);
        updateAtmCash(banknotesToWithdraw);

        return banknotesToWithdraw;
    }

    public void updateAtmCash(List<Banknote> banknotesToWithdraw) {
		// TODO Auto-generated method stub
    	for (Banknote banknote : banknotesToWithdraw) {
            bankingSystem.debitBanknote(banknote);
        }
		
	}

	@Override
    public BigDecimal checkBalance(String accountNumber) {
        return bankingSystem.getAccountBalance(accountNumber);
    }

    private List<Banknote> calculateBanknotesToWithdraw(BigDecimal amount) {
        List<Banknote> banknotes = new ArrayList<>();
        for (Banknote banknote : Banknote.values()) {
            int numOfBanknotes = amount.divide(banknote.getValue()).intValue();
            numOfBanknotes = Math.min(numOfBanknotes, bankingSystem.getAvailableBanknotes(banknote));
            for (int i = 0; i < numOfBanknotes; i++) {
                banknotes.add(banknote);
                amount = amount.subtract(banknote.getValue());
            }
        }

        if (amount.compareTo(BigDecimal.ZERO) > 0) {
          
            throw new NotEnoughMoneyInATMException(null);
        }

        return banknotes;
    }

  
}
