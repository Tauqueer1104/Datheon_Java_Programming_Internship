import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Common account model. Monetary values use BigDecimal to avoid floating-point errors.
 */
public abstract class Account {
    private final String accountNumber;
    private final String customerName;
    private final String pin; // Demo project only: production systems must store a salted hash.
    protected BigDecimal balance;
    private final List<String> transactionLog = new ArrayList<>();

    protected Account(String accountNumber, String customerName, String pin, BigDecimal openingBalance) {
        this.accountNumber = accountNumber;
        this.customerName = customerName;
        this.pin = pin;
        this.balance = money(openingBalance);
        log("ACCOUNT_OPENED", this.balance, "Opening balance");
    }

    public String getAccountNumber() { return accountNumber; }
    public String getCustomerName() { return customerName; }
    public synchronized BigDecimal getBalance() { return balance; }

    public boolean verifyPIN(String enteredPin) {
        return pin.equals(enteredPin);
    }

    public synchronized void deposit(BigDecimal amount) throws BankingException {
        validatePositive(amount);
        balance = money(balance.add(amount));
        log("DEPOSIT", amount, "Deposit successful");
    }

    public synchronized void withdraw(BigDecimal amount) throws BankingException {
        validatePositive(amount);
        if (balance.compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient funds. Available balance: ₹" + balance);
        }
        balance = money(balance.subtract(amount));
        log("WITHDRAWAL", amount, "Withdrawal successful");
    }

    protected void validatePositive(BigDecimal amount) throws BankingException {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BankingException("Amount must be greater than zero.");
        }
    }

    protected static BigDecimal money(BigDecimal amount) {
        return amount.setScale(2, RoundingMode.HALF_UP);
    }

    protected synchronized void log(String type, BigDecimal amount, String note) {
        transactionLog.add(String.format("%s | %-12s | ₹%s | Balance: ₹%s | %s",
                java.time.LocalDateTime.now().withNano(0), type, money(amount), money(balance), note));
    }

    public synchronized List<String> getTransactionLog() {
        return Collections.unmodifiableList(new ArrayList<>(transactionLog));
    }

    public abstract String getAccountType();
    public abstract void applyInterest();
}
