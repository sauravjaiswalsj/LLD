package repository;

import service.Account;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryAccountRepository implements AccountRepository {
    private final ConcurrentHashMap<String, Account> accountsMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, String> customerNameIdMap = new ConcurrentHashMap<>();
    @Override
    public void save(Account account) {
        if (accountsMap.putIfAbsent(account.accountId(),  account) != null) {
            throw new IllegalStateException("Account already exists" + account.accountId());
        }
        customerNameIdMap.putIfAbsent(account.getName(), account.accountId());
    }

    @Override
    public Optional<Account> findById(String customerId) {
        return Optional.ofNullable(accountsMap.get(customerId));
    }

    @Override
    public Optional<Optional<Account>> findByName(String customerName) {
        return Optional.ofNullable(findById(customerNameIdMap.get(customerName)));
    }
}
