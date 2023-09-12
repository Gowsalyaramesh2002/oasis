import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;

// Class representing a bank account
class Account {
    private int accountId;
    private double balance;
    private ArrayList<Transaction> transactionHistory;

    public Account(int accountId, double balance) {
        this.accountId = accountId;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add(new Transaction("Deposit", amount));
    }

    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            transactionHistory.add(new Transaction("Withdrawal", -amount));
            return true;
        }
        return false;
    }

    public boolean transfer(Account recipient, double amount) {
        if (balance >= amount) {
            balance -= amount;
            recipient.deposit(amount);
            transactionHistory.add(new Transaction("Transfer to " + recipient.getAccountId(), -amount));
            recipient.transactionHistory.add(new Transaction("Transfer from " + this.getAccountId(), amount));
            return true;
        }
        return false;
    }

    public int getAccountId() {
        return accountId;
    }

    public ArrayList<Transaction> getTransactionHistory() {
        return transactionHistory;
    }
}

// Class representing a transaction
class Transaction {
    private String description;
    private double amount;

    public Transaction(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return description + ": " + amount;
    }
}

// Class representing an ATM
class ATM {
    private Account currentAccount;
    private Scanner scanner;

    public ATM() {
        scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Welcome to the ATM!");
        login();

        boolean quit = false;
        while (!quit) {
            System.out.println("\nOptions:");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    showTransactionHistory();
                    break;
                case 2:
                    withdraw();
                    break;
                case 3:
                    deposit();
                    break;
                case 4:
                    transfer();
                    break;
                case 5:
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        System.out.println("Thank you for using the ATM!");
        scanner.close();
    }

    private void login() {
        System.out.print("Enter your Account ID: ");
        int accountId = scanner.nextInt();
        System.out.print("Enter your PIN: ");
        int pin = scanner.nextInt();

        // You would typically validate the account ID and PIN against a database here.
        // For simplicity, let's assume there's only one account with ID 1234 and PIN 5678.
        if (accountId == 1234 && pin == 5678) {
            currentAccount = new Account(accountId, 1000.0); // Initialize with some initial balance
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid Account ID or PIN. Please try again.");
            login(); // Recursively call login until successful login or user quits
        }
    }

    private void showTransactionHistory() {
        // Check if the user is logged in
        if (currentAccount == null) {
            System.out.println("Please log in first.");
            return;
        }

        // Fetch and display the transaction history for the current user
        ArrayList<Transaction> transactions = currentAccount.getTransactionHistory();

        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.println("Transaction History:");
            for (Transaction transaction : transactions) {
                System.out.println(transaction.toString());
            }
        }
    }

    private void withdraw() {
        // Check if the user is logged in
        if (currentAccount == null) {
            System.out.println("Please log in first.");
            return;
        }

        // Prompt the user for the withdrawal amount
        double withdrawalAmount;
        try {
            System.out.print("Enter the withdrawal amount: ");
            withdrawalAmount = scanner.nextDouble();
            scanner.nextLine();  // Consume the newline character
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid amount.");
            scanner.nextLine();  // Consume the invalid input
            return;
        }

        // Check if the withdrawal amount is valid (positive and not exceeding the balance)
        if (withdrawalAmount <= 0) {
            System.out.println("Invalid withdrawal amount. Please enter a positive amount.");
        } else if (withdrawalAmount > currentAccount.getBalance()) {
            System.out.println("Insufficient funds.");
        } else {
            // Perform the withdrawal
            currentAccount.withdraw(withdrawalAmount);
            System.out.println("Withdrawal successful. Remaining balance: " + currentAccount.getBalance());
        }
    }

    private void deposit() {
        // Check if the user is logged in
        if (currentAccount == null) {
            System.out.println("Please log in first.");
            return;
        }

        // Prompt the user for the deposit amount
        double depositAmount;
        try {
            System.out.print("Enter the deposit amount: ");
            depositAmount = scanner.nextDouble();
            scanner.nextLine();  // Consume the newline character
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid amount.");
            scanner.nextLine();  // Consume the invalid input
            return;
        }

        // Check if the deposit amount is valid (positive)
        if (depositAmount <= 0) {
            System.out.println("Invalid deposit amount. Please enter a positive amount.");
        } else {
            // Perform the deposit
            currentAccount.deposit(depositAmount);
            System.out.println("Deposit successful. New balance: " + currentAccount.getBalance());
        }
    }

    private void transfer() {
        // Check if the user is logged in
        if (currentAccount == null) {
            System.out.println("Please log in first.");
            return;
        }

        // Prompt the user for the recipient's account number
        System.out.print("Enter the recipient's account number: ");
        String recipientAccountNumber = scanner.nextLine();

        // Check if the recipient's account exists
        // In a real system, you would need to implement a way to look up accounts by account number.
        // Here, we'll assume there's only one recipient account with account number "5678".
        if (!recipientAccountNumber.equals("5678")) {
            System.out.println("Recipient's account not found.");
            return;
        }

        // Prompt the user for the transfer amount
        double transferAmount;
        try {
            System.out.print("Enter the transfer amount: ");
            transferAmount = scanner.nextDouble();
            scanner.nextLine();  // Consume the newline character
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid amount.");
            scanner.nextLine();  // Consume the invalid input
            return;
        }

        // Check if the transfer amount is valid (positive and not exceeding the balance)
        if (transferAmount <= 0) {
            System.out.println("Invalid transfer amount. Please enter a positive amount.");
        } else if (transferAmount > currentAccount.getBalance()) {
            System.out.println("Insufficient funds.");
        } else {
            // Perform the transfer
            currentAccount.transfer(new Account(5678, 1000.0), transferAmount);
            System.out.println("Transfer successful. Remaining balance: " + currentAccount.getBalance());
        }
    }

    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.start();
    }
}
