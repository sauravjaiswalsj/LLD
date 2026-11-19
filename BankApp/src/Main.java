import repository.AccountRepository;
import repository.InMemoryAccountRepository;
import service.BankingService;
import util.IdGenerator;
import util.SequentialIdGenerator;
import util.UuidIdGenerator;

import java.time.Clock;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
/*
You work at a fintech company. Build an in-memory banking service.
Customers can open accounts, deposit, withdraw, and check their balance.
No frameworks, no database, plain Java. I want to see it work, so show me tests. We’ll add requirements as we go.
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        // Build dependencies
        AccountRepository accountRepository = new InMemoryAccountRepository();
        IdGenerator accountIds = new UuidIdGenerator();
        IdGenerator txsIds = new UuidIdGenerator();
        Clock clock = Clock.systemUTC();

        BankingService service = new BankingService(accountRepository, accountIds, txsIds, clock, new SequentialIdGenerator("account"));

        System.out.println("------------------------------------");

        System.out.println("Welcome to Banking App");
        do {

            System.out.println("Choose your option:");

            System.out.println("1: Open Account");
            System.out.println("2: Deposit");
            System.out.println("3: Withdraw");
            System.out.println("4: balance");
            System.out.println("5: Exit");
            System.out.print("Enter your choice: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1 -> {
                    service.registerCustomer();
                    service.openAccount();
                }
                case 2 -> service.deposit();
                case 3 -> service.withdraw();
                case 4 -> service.balance();
                case 5 -> System.exit(0);
                default -> System.out.println("Invalid option");
            }

            System.out.println("------------------------------------");
        }while(true);
    }
}