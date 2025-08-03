package controller;

import model.Transaction;
import service.AccountService;
import service.AuthService;
import service.TransactionService;

import java.util.List;

public class BankController {
    AuthService auth = new AuthService();
    AccountService account = new AccountService();
    TransactionService transaction = new TransactionService();
    public void register(String name, String email, String password) {
        auth.registerUser(name, email, password);
    }

    public void login(String email, String password) {
        auth.loginUser(email, password);
    }

    public void createAccount(int userId, double initialBalance) {
        account.createAccount(userId,initialBalance);
    }

    public void deposit(int accountId,double amount) {
        account.deposit(accountId,amount);
    }

    public void withdraw(int accountId,double amount) {
        account.withdraw(accountId,amount);
    }

    public void transfer(int fromAccount,int toAccount,double amount, String type) {
        transaction.logTransaction(fromAccount,toAccount,amount,type);
    }

    public List<Transaction> getTransaction(int accountId) {
        return transaction.getTransactions(accountId);
    }
}
