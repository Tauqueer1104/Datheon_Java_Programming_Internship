import java.math.BigDecimal;

/** Savings account earns interest and does not permit overdrafts. */
public class SavingsAccount extends Account {
    private final BigDecimal annualInterestRate;

    public SavingsAccount(String accountNumber, String customerName, String pin,
                          BigDecimal openingBalance, BigDecimal annualInterestRate) {
        super(accountNumber, customerName, pin, openingBalance);
        this.annualInterestRate = annualInterestRate;
    }

    @Override
    public synchronized void applyInterest() {
        BigDecimal interest = money(getBalance().multiply(annualInterestRate)
                .divide(new BigDecimal("100"), 4, java.math.RoundingMode.HALF_UP));
        balance = money(balance.add(interest));
        log("INTEREST", interest, "Savings interest credited at " + annualInterestRate + "%");
    }

    @Override
    public String getAccountType() { return "Savings"; }
}
