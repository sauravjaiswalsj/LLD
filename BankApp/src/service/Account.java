import util.IdGenerator;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public abstract class Account {
    private IdGenerator cifId;
    private String name;
    private double balance;
    private Currency currency;
    private List<String> transactions;

    public Account(IdGenerator cifId, String name, double balance, Currency currency) {
        this.cifId = cifId;
        this.name = name;
        this.balance = balance;
        this.currency = currency;
        this.transactions = new ArrayList<>();
    }

}
