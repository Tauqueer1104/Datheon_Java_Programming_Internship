# Bank Account & ATM Simulation Engine

A Java 17 console project demonstrating OOP inheritance, polymorphism, encapsulation,
custom checked exceptions, PIN verification, overdraft handling, interest calculation,
and transaction logging.

## Requirements
- Java JDK 17 or later
- Command Prompt / Terminal, IntelliJ IDEA, Eclipse, or VS Code

## Project structure
```text
BankAccount_ATM_Simulation/
├── src/
│   ├── Account.java
│   ├── SavingsAccount.java
│   ├── CheckingAccount.java
│   ├── Customer.java
│   ├── Bank.java
│   ├── ATM.java
│   ├── BankingException.java
│   ├── InsufficientFundsException.java
│   ├── InvalidPINException.java
│   ├── AccountNotFoundException.java
│   └── Main.java
└── README.md
```

## Run using Command Prompt

1. Extract the ZIP file.
2. Open Command Prompt in the extracted `BankAccount_ATM_Simulation` folder.
3. Compile:
   ```bat
   javac -d out src\\*.java
   ```
4. Run:
   ```bat
   java -cp out Main
   ```

## Demo login accounts

| Account type | Account number | PIN | Opening balance |
|---|---|---|---:|
| Savings | SB1001 | 1234 | ₹10,000.00 |
| Checking | CA2001 | 4321 | ₹5,000.00 |

The savings account earns 4% interest when **Apply interest** is selected.
The checking account supports an overdraft limit of ₹2,000.

## Features
- Abstract `Account` class with `SavingsAccount` and `CheckingAccount` subclasses
- Runtime polymorphism for withdrawal and interest behavior
- PIN authentication with a maximum of three attempts
- Deposits, withdrawals, balance enquiry, and transaction history
- Custom exceptions for insufficient funds, invalid PIN, and missing accounts
- Transaction log entries containing timestamps, transaction type, amount, and resulting balance
- `BigDecimal` used for monetary calculations

## Important security note
This is an educational simulation, not production banking software. The demo stores PINs
in memory as plain text and does not connect to a real bank or payment network. A real
system must use secure PIN verification, encryption, access controls, persistent audited
storage, and appropriate regulatory/security controls.
