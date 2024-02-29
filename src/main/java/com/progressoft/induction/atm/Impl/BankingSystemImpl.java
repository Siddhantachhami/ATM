package com.progressoft.induction.atm.Impl;

import com.progressoft.induction.atm.BankingSystem;
import com.progressoft.induction.atm.Banknote;
import com.progressoft.induction.atm.exceptions.InsufficientFundsException;
import com.progressoft.induction.atm.exceptions.NotEnoughMoneyInATMException;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class BankingSystemImpl implements BankingSystem {
    Map<String, BigDecimal> accountBalanceMap = new HashMap<>();
    EnumMap<Banknote, Integer> atmCashMap = new EnumMap<>(Banknote.class);

    public BankingSystemImpl() {
        atmCashMap.put(Banknote.FIFTY_JOD, 10);
        atmCashMap.put(Banknote.TWENTY_JOD, 20);
        atmCashMap.put(Banknote.TEN_JOD, 100);
        atmCashMap.put(Banknote.FIVE_JOD, 100);

        accountBalanceMap.put("123456789", BigDecimal.valueOf(1000.0));
        accountBalanceMap.put("111111111", BigDecimal.valueOf(1000.0));
        accountBalanceMap.put("222222222", BigDecimal.valueOf(1000.0));
        accountBalanceMap.put("333333333", BigDecimal.valueOf(1000.0));
        accountBalanceMap.put("444444444", BigDecimal.valueOf(1000.0));
    }

    public BigDecimal sumOfMoneyInAtm() {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (Map.Entry<Banknote, Integer> entry : atmCashMap.entrySet()) {
            totalAmount = totalAmount.add(entry.getKey().getValue().multiply(BigDecimal.valueOf(entry.getValue())));
        }
        return totalAmount;
    }

    @Override
    public BigDecimal getAccountBalance(String accountNumber) {
        return accountBalanceMap.getOrDefault(accountNumber, BigDecimal.ZERO);
    }

    @Override
    public void debitAccount(String accountNumber, BigDecimal amount) throws InsufficientFundsException {
        BigDecimal currentBalance = accountBalanceMap.getOrDefault(accountNumber, BigDecimal.ZERO);
        if (currentBalance.compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient funds in the account");
        }
        accountBalanceMap.put(accountNumber, currentBalance.subtract(amount));
    }

    @Override
    public int getAvailableBanknotes(Banknote banknote) {
        return atmCashMap.getOrDefault(banknote, 0);
    }

    @Override
    public void debitBanknote(Banknote banknote) {
        int currentCount = atmCashMap.getOrDefault(banknote, 0);
        if (currentCount > 0) {
            atmCashMap.put(banknote, currentCount - 1);
        } else {
            throw new NotEnoughMoneyInATMException(null);
        }}
}

