import java.util.LinkedHashMap;
import java.util.Map;

public class Bank {
    private final Map<String, Account> accounts = new LinkedHashMap<>();

    public void addAccount(Account account) {
        if (accounts.containsKey(account.getAccountNumber())) {
            throw new IllegalArgumentException("Duplicate account number.");
        }
        accounts.put(account.getAccountNumber(), account);
    }

    public Account findAccount(String accountNumber) throws AccountNotFoundException {
        Account account = accounts.get(accountNumber);
        if (account == null) throw new AccountNotFoundException("Account not found.");
        return account;
    }
}
