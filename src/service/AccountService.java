package service;

import model.Account;
import model.Transaction;
import repository.AccountRepository;
import repository.UserRepository;

import java.util.*;

public class AccountService {
    UserRepository userRepository = new UserRepository();
    AccountRepository accountRepository = new AccountRepository();
    public void createAccount(int userId,double initialBalance){
        if(userRepository.findByUserId(userId)==null){
            System.out.println("There is no account with userId " + userId);
        }else if (initialBalance < 0){
            System.out.println("Insufficient balance");
        }else {
            Account account = new Account(userId,initialBalance);
            accountRepository.create(account);
        }
    }

    public void deposit (int accountId, double amount){
        Account account = accountRepository.findByAccountId(accountId);

        if(amount <= 0){
            System.out.println("Insufficient amount");
        }else if(account == null)  {
                System.out.println("There is no account with userId " + accountId);
        }else{
            double accountAmount =account.getBalance();
            double newBalance = accountAmount + amount;
            accountRepository.updateBalance(account,newBalance);
            System.out.println("Deposited " + amount + " from user " + account.getUserId() + " to this account " + accountId);
        }
    }

    public void withdraw (int accountId, double amount){
        Account account = accountRepository.findByAccountId(accountId);

        if(amount <= 0){
            System.out.println("Insufficient amount");
        }else if(account == null)  {
            System.out.println("There is no account with userId " + accountId);
        } else if (account.getBalance() < amount) {
            System.out.println("insufficient funds");
        } else {
            double accountAmount =account.getBalance();
            double newBalance = accountAmount - amount;
            accountRepository.updateBalance(account,newBalance);
            System.out.println(amount + " was withdraw from this account " + accountId);
        }
    }

    public boolean transfer (int fromAccountId, int toAccountId, double amount,String type) {
        Account fromAccount = accountRepository.findByAccountId(fromAccountId);
        Account toAccount = accountRepository.findByAccountId(toAccountId);

        if(amount <= 0){
            System.out.println("Insufficient amount");
            return false;
        }else if(fromAccount.getBalance() < amount){
            System.out.println("Insufficient funds");
            return false;
        }else{
            accountRepository.updateBalance(fromAccount,fromAccount.getBalance()-amount);
            accountRepository.updateBalance(toAccount,toAccount.getBalance() + amount);
            return true;
        }


    }

    public Account getAccountById(int accountId){
        Account account = accountRepository.findByAccountId(accountId);

        if(account == null){
            System.out.println("There is no account with userId " + accountId);
        }

        return account;
    }

    public List<Account> getAllAccounts(){
        List<Account> accounts = accountRepository.findAll();

        if(accounts.isEmpty()){
            System.out.println("Account list is empty");
        }else {
            System.out.println("Account list contains " + accounts.size());
        }
        return accounts;
    }

    public void safeDelete(int accountId){
        Account account = accountRepository.findByAccountId(accountId);

        if(account == null){
            System.out.println("There is no account with userId " + accountId);
        }else {
            accountRepository.safeDelete(accountId);
        }
    }
}
