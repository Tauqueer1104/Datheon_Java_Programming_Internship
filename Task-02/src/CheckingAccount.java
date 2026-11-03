import java.math.BigDecimal;

/** Checking account supports a configured overdraft limit. */
public class CheckingAccount extends Account {
    private final BigDecimal overdraftLimit;

    public CheckingAccount(String accountNumber, String customerName, String pin,
                           BigDecimal openingBalance, BigDecimal overdraftLimit) {
        super(accountNumber, customerName, pin, openingBalance);
        this.overdraftLimit = money(overdraftLimit);
    }

    @Override
    public synchronized void withdraw(BigDecimal amount) throws BankingException {
        validatePositive(amount);
        BigDecimal lowestAllowedBalance = overdraftLimit.negate();
        if (balance.subtract(amount).compareTo(lowestAllowedBalance) < 0) {
            throw new InsufficientFundsException(
                    "Overdraft limit exceeded. Available including overdraft: ₹" +
                    money(balance.add(overdraftLimit)));
        }
        balance = money(balance.subtract(amount));
        log("WITHDRAWAL", amount, "Checking withdrawal (overdraft may apply)");
    }

    @Override
    public void applyInterest() {
        // No interest is applied to this demo checking account.
    }

    @Override
    public String getAccountType() { return "Checking"; }
}
