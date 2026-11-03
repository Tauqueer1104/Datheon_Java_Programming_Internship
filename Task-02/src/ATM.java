import java.math.BigDecimal;
import java.util.Scanner;

/** Console ATM interface. Authentication is required before account operations. */
public class ATM {
    private final Bank bank;
    private final Scanner scanner;

    public ATM(Bank bank, Scanner scanner) {
        this.bank = bank;
        this.scanner = scanner;
    }

    public void start() {
        System.out.println("======================================");
        System.out.println("       BANK ACCOUNT & ATM SYSTEM      ");
        System.out.println("======================================");

        while (true) {
            System.out.print("\nEnter account number (or 0 to exit): ");
            String accountNumber = scanner.nextLine().trim();
            if (accountNumber.equals("0")) {
                System.out.println("Thank you for using the ATM. Goodbye!");
                return;
            }

            try {
                Account account = bank.findAccount(accountNumber);
                authenticate(account);
                accountMenu(account);
            } catch (BankingException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    private void authenticate(Account account) throws InvalidPINException {
        for (int attempt = 1; attempt <= 3; attempt++) {
            System.out.print("Enter 4-digit PIN: ");
            String entered = scanner.nextLine().trim();
            if (account.verifyPIN(entered)) {
                System.out.println("Login successful. Welcome, " + account.getCustomerName() + "!");
                return;
            }
            System.out.println("Incorrect PIN. Attempts remaining: " + (3 - attempt));
        }
        throw new InvalidPINException("Maximum PIN attempts exceeded.");
    }

    private void accountMenu(Account account) {
        while (true) {
            System.out.println("\\n--- " + account.getAccountType() + " Account: " +
                    account.getAccountNumber() + " ---");
            System.out.println("1. Check balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Apply interest");
            System.out.println("5. View transaction history");
            System.out.println("6. Logout");
            System.out.print("Choose option: ");

            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> System.out.println("Available balance: " + account.getBalance());
                    case "2" -> {
                        BigDecimal amount = readAmount();
                        account.deposit(amount);
                        System.out.println("Deposit successful. New balance: ₹" + account.getBalance());
                    }
                    case "3" -> {
                        BigDecimal amount = readAmount();
                        account.withdraw(amount);
                        System.out.println("Withdrawal successful. New balance: ₹" + account.getBalance());
                    }
                    case "4" -> {
                        account.applyInterest();
                        System.out.println("Interest operation completed. Balance: ₹" + account.getBalance());
                    }
                    case "5" -> {
                        System.out.println("\\n--- Transaction Log ---");
                        for (String entry : account.getTransactionLog()) System.out.println(entry);
                    }
                    case "6" -> {
                        System.out.println("Logged out.");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please select 1-6.");
                }
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid numeric amount.");
            } catch (BankingException ex) {
                System.out.println("Transaction failed: " + ex.getMessage());
            }
        }
    }

    private BigDecimal readAmount() {
        System.out.print("Enter amount (₹): ");
        return new BigDecimal(scanner.nextLine().trim());
    }
}
