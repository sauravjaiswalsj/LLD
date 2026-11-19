package repository;

import service.Account;

import java.util.Optional;

public interface AccountRepository {
    void save(Account account);
    Optional<Account> findById(String customerId);
    Optional<Optional<Account>> findByName(String customerName);
}
