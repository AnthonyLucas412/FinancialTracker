package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class FinancialTracker {

    private static ArrayList<Transaction> transactions = new ArrayList();
    private static final String FILE_NAME = "transactions.csv";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm:ss";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);

    public static void main(String[] args) {
        loadTransactions(FILE_NAME);

        int i = 1;

        for (Transaction transaction : transactions) {

            System.out.println(i + ". Date: " + transaction.getDate() + " - " + transaction.getTime() + " - " + transaction.getType() + " - " + transaction.getVendor() + " - $" + transaction.getPrice());
            i++;

        }
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Welcome to TransactionApp");
            System.out.println("Choose an option:");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "D":
                    addDeposit(scanner);
                    break;
                case "P":
                    addPayment(scanner);
                    break;
                case "L":
                    ledgerMenu(scanner);
                    break;
                case "X":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }

        scanner.close();
    }

    public static void loadTransactions(String filename) {
        // This method should load transactions from a file with the given file name.
        // If the file does not exist, it should be created.
        // The transactions should be stored in the `transactions` ArrayList.
        // Each line of the file represents a single transaction in the following format:
        // <date>,<time>,<vendor>,<type>,<amount>
        // For example: 2023-04-29,13:45:00,Amazon,PAYMENT,29.99
        // After reading all the transactions, the file should be closed.
        // If any errors occur, an appropriate error message should be displayed.

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_NAME));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    LocalDate date = LocalDate.parse(parts[0]);
                    LocalTime time = LocalTime.parse(parts[1]);
                    String type = parts[2].trim();
                    String vendor = parts[3].trim();
                    double price = Double.parseDouble(parts[4]);
                    transactions.add(new Transaction(date, time, type, vendor, price));
                }
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error loading inventory: " + e.getMessage());
        } catch (IOException e) {

        }

    }


    private static void addDeposit(Scanner scanner) {
        // This method should prompt the user to enter the date, time, vendor, and amount of a deposit.
        // The user should enter the date and time in the following format: yyyy-MM-dd HH:mm:ss
        // The amount should be a positive number.
        // After validating the input, a new `Deposit` object should be created with the entered values.
        // The new deposit should be added to the `transactions` ArrayList.

        System.out.println("Enter the date and time (yyyy-MM-dd HH:mm:ss): ");
        String dateAndTime = scanner.nextLine();
        LocalDateTime dateTime = null;//initially nothing is here

        try {
            dateTime = LocalDateTime.parse(dateAndTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));//try/catch human error
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date and time format. Please use this format (yyyy-MM-dd HH:mm:ss)");
            return;
        }
        System.out.println("Enter the vendor: ");
        String vendor = scanner.nextLine();

        System.out.println("Enter the description of this deposit");
        String type = scanner.nextLine();

        System.out.println("Enter the amount of deposit: ");
        double amount = Double.parseDouble(scanner.nextLine());


        if (amount <= 0) {
            System.out.println("Deposit must be a positive number ");
            return;

        }
        Deposit deposit = new Deposit(dateTime, vendor, type, amount);
        transactions.add(deposit);

        try {
            FileWriter fileWriter = new FileWriter(FILE_NAME, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(dateTime.toLocalDate() + "|" + dateTime.toLocalTime() + "|" + vendor + "|" + type + "|" + amount + "\n");
            bufferedWriter.flush();
            bufferedWriter.close();

        } catch (IOException e) {
            System.out.println("IOException occured!" + e.getMessage());
        }

        System.out.println("The deposit was successful ");

    }


    private static void addPayment(Scanner scanner) {
        // This method should prompt the user to enter the date, time, vendor, and amount of a payment.
        // The user should enter the date and time in the following format: yyyy-MM-dd HH:mm:ss
        // The amount should be a positive number.
        // After validating the input, a new `Payment` object should be created with the entered values.
        // The new payment should be added to the `transactions` ArrayList.

        System.out.println("Enter the date and time (yyyy-MM-dd HH:mm:ss): ");
        String dateAndTime = scanner.nextLine().trim();
        LocalDateTime dateTime = null;

        try {
            dateTime = LocalDateTime.parse(dateAndTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date and time format. Please use this format (yyyy-MM-dd HH:mm:ss)");
            return;
        }
        System.out.println("Enter the vendor: ");
        String vendor = scanner.nextLine().trim();

        System.out.println("Enter the description of this payment");
        String type = scanner.nextLine();

        System.out.println("Enter the amount of payment: ");
        double cost = Double.parseDouble(scanner.nextLine());
        if (cost <= 0) {
            System.out.println("Amount should be a positive not a negative ");
            return;
        }
        Payment payment = new Payment(dateTime, vendor, type, cost);
        transactions.add(payment);

        try {
            FileWriter fileWriter = new FileWriter(FILE_NAME, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(dateTime.toLocalDate() + "|" + dateTime.toLocalTime() + "|" + vendor + "|" + type + "|" + -cost + "\n");
            bufferedWriter.flush();// flushes out anything thats not important then closes.
            bufferedWriter.close();

        } catch (IOException e) {

            System.out.println("IOException occured!" + e.getMessage());
        }


        System.out.println("Payment was added successfully!");
    }


    private static void ledgerMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Ledger");
            System.out.println("Choose an option:");
            System.out.println("A) A`ll");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "A":
                    displayLedger();
                    break;
                case "D":
                    displayDeposits();
                    break;
                case "P":
                    displayPayments();
                    break;
                case "R":
                    reportsMenu(scanner);
                    break;
                case "H":
                    running = false;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    private static void displayLedger() {
        // This method should display a table of all transactions in the `transactions` ArrayList.
        // The table should have columns for date, time, vendor, type, and amount.
        System.out.println("Ledger");
        System.out.println("=========================================================================================================");
        System.out.printf("%-15s %-10s %-20s %-20s %s\n", "Date", "Time", "Type", "Vendor", "Amount"); // align output to the left (the reason for negative number) (if positive number it would display to right ) and specify the width for every item
        System.out.println("=========================================================================================================");
        for (Transaction transaction : transactions) {
            System.out.printf("%-15s %-10s %-20s %-20s $%.2f\n", transaction.getDate(), transaction.getTime(), transaction.getType(), transaction.getVendor(), transaction.getPrice());// .2f - only want 2 digits after the decimal.
        }
    }

    private static void displayDeposits() {
        // This method should display a table of all deposits in the `transactions` ArrayList.
        // The table should have columns for date, time, vendor, and amount.

        System.out.println("Deposits");
        System.out.println("=========================================================================================================");
        System.out.printf("%-15s %-10s %-20s %-20s %s\n", "Date", "Time", "Type", "Vendor", "Amount"); // align output to the left and specify the width for every item
        System.out.println("=========================================================================================================");
        for (Transaction transaction : transactions) {
            if (transaction instanceof Deposit) {
                System.out.printf("%-15s %-10s %-20s %-20s $%.2f\n", transaction.getDate(), transaction.getTime(), transaction.getType(), transaction.getVendor(), transaction.getPrice());

            }
        }

    }

    private static void displayPayments() {
        // This method should display a table of all payments in the `transactions` ArrayList.
        // The table should have columns for date, time, vendor, and amount.
        System.out.println("Payments");
        System.out.println("=========================================================================================================");
        System.out.printf("%-15s %-10s %-20s %-20s %s\n", "Date", "Time", "Type", "Vendor", "Amount"); // align output to the left and specify the width for every item
        System.out.println("=========================================================================================================");
        for (Transaction transaction : transactions) {
            if (transaction instanceof Payment) {
                System.out.printf("%-15s %-10s %-20s %-20s $%.2f\n", transaction.getDate(), transaction.getTime(), transaction.getType(), transaction.getVendor(), transaction.getPrice());

            }
        }
    }

    private static void reportsMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Reports");
            System.out.println("Choose an option:");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back");

            String input = scanner.nextLine().trim();

        }
    }
}