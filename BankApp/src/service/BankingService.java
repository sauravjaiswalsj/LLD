package service;

import domain.Customer;
import repository.AccountRepository;
import util.IdGenerator;
import util.SequentialIdGenerator;

import java.math.BigDecimal;
import java.time.Clock;
import java.util.Currency;

public class BankingService {
    private final AccountRepository accountRepo;
    private final IdGenerator accountIdDGenerator;
    private final SequentialIdGenerator cifIdDGenerator;
    private final IdGenerator transactionIdGenerator;
    private Clock clock;

    public BankingService(AccountRepository accountRepo, IdGenerator accountIdGenerator, IdGenerator transactionIdGenerator, Clock clock, SequentialIdGenerator cifIdGenerator, SequentialIdGenerator cifIdDGenerator) {
        this.accountRepo = accountRepo;
        this.accountIdDGenerator = accountIdGenerator;
        this.transactionIdGenerator = transactionIdGenerator;
        this.clock = clock;
        this.cifIdDGenerator = cifIdDGenerator;
    }
    public String registerCustomer() {
        String cifId = cifIdDGenerator.next();
        accountRepo.customerSave(new Customer(cifId, "cust-1"));
        return "";
    }
    public void openAccount() {
        String accountId = this.accountIdDGenerator.next();
        Currency ccy = Currency.getInstance("INR");
        Account account = Account.open(accountId, "cust-1", ccy);
        accountRepo.save(account);

        System.out.println("Opening account " + account.accountId());
    }

    public void closeAccount() {}
    public void deposit() {
        Account account = getAccountByName("cust-1");

        if  (account == null) {
            System.out.println("Account not found");
        }
        BigDecimal amount = new BigDecimal("500");
        account.deposit(amount);
    }
    public void withdraw() {
        BigDecimal amount = new BigDecimal("200");
        Account account = getAccountByName("cust-1");
        if (account == null) {
            System.out.println("Account not found");
        }
        account.withdraw(amount);
    }
    public void balance(){
        Account account = getAccountByName("cust-1");
        if (account == null) {
            System.out.println("Account not found");
        }
        System.out.println(account.balance());
    }

    private Account getAccount(String accountId) {
        return accountRepo.findById(accountId).orElse(null);
    }

    private Account getAccountByName(String name){
        return accountRepo.findByName(name).orElse(null).orElse(null);
    }
}
