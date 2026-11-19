import service.AccountRepository;
import util.IdGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BankingService {
    private final AccountRepository accountRepo;
    private final IdGenerator accountIdGenerator;
    private final IdGenerator transactionIdGenerator;
    private final LocalDate date;
    private LocalDateTime clock;

    public BankingService(AccountRepository accountRepo, IdGenerator accountIdGenerator, IdGenerator transactionIdGenerator, LocalDate date) {
        this.accountRepo = accountRepo;
        this.accountIdGenerator = accountIdGenerator;
        this.transactionIdGenerator = transactionIdGenerator;
        this.date = date;
    }
}
