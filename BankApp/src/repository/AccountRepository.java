package service;

import java.util.Optional;

public interface AccountRepository {
    void save();
    Optional<Account> findById(int accountId);
}
