import java.math.BigDecimal;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();

        // Demo accounts. PINs are for local learning/testing only.
        bank.addAccount(new SavingsAccount(
                "SB1001", "Aarav Sharma", "1234",
                new BigDecimal("10000.00"), new BigDecimal("4.0")));

        bank.addAccount(new CheckingAccount(
                "CA2001", "Priya Patil", "4321",
                new BigDecimal("5000.00"), new BigDecimal("2000.00")));

        try (Scanner scanner = new Scanner(System.in)) {
            new ATM(bank, scanner).start();
        }
    }
}
