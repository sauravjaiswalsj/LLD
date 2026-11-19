package service;

import domain.InsufficientFundsException;
import domain.InvalidAmountException;
import util.IdGenerator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class Account {
    private final String accountId;
    private final String name;
    private BigDecimal balance;
    private final Currency currency;
    private final List<String> transactions;

    private Account(String name, String accountId, Currency currency, BigDecimal balance, List<String> transactions) {
        this.name = name;
        this.accountId = accountId;
        this.currency = currency;
        this.balance = balance;
        this.transactions = new ArrayList<>(transactions);
    }
    public static Account open(String accountId, String name, Currency currency) {
        return new Account(name, accountId, currency, BigDecimal.ZERO.setScale(2), new ArrayList<>());
    }
    public String accountId() {
        return this.accountId;
    }

    public String getName() {
        return this.name;
    }

    public synchronized BigDecimal balance() {
        return this.balance;
    }

    public void deposit(BigDecimal amount) {
        BigDecimal valid = requireValidAmount(amount);
        synchronized (this) {
            balance = balance.add(valid);
        }
    }

    public void withdraw(BigDecimal amount) {
        BigDecimal valid = requireValidAmount(amount);
        synchronized (this) {
            if (balance.compareTo(valid) < 0) {
                throw new InsufficientFundsException("Insufficient funds" + accountId + " " + balance + " " + valid);
            }
            balance = balance.subtract(valid);
        }
    }

    public static BigDecimal requireValidAmount(BigDecimal amount) {
        if (amount == null)
                throw new InvalidAmountException("Amount is required");
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        try {
            return amount.setScale(2, RoundingMode.UNNECESSARY);
        }catch(ArithmeticException e) {
            throw new InvalidAmountException("At most 2 decimal places allowed: " + amount);
        }
    }
}
