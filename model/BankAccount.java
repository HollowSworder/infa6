package model;

import exceptions.*;

public class BankAccount {
    private String accountNumber;
    private double balance;
    private String ownerName;
    
    public BankAccount(String accountNumber, double initialBalance, String ownerName) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.ownerName = ownerName;
    }
    
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new InvalidAmountException(amount);
        }
        balance += amount;
    }
    
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new InvalidAmountException(amount);
        }

        if (balance < amount) {
            throw new InsufficientFundsException(balance, amount);
        }
        balance -= amount;
    }
    
    public void transfer(BankAccount target, double amount) {
        if (target == null) {
            throw new InvalidAccountException("Целевой счет не существует");
        }

        this.withdraw(amount);
        target.deposit(amount);
    }
    
    public void applyInterest(double rate) {
        double interest = balance * rate;
        deposit(interest);
    }
    
    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    @Override
    public String toString() {
        return "Счет: " + accountNumber + "\n" + 
            "Владелец: " + ownerName + "\n" + 
            "Баланс: " + balance;
    }
}
