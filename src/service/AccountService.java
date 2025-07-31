package service;

import model.Account;
import repository.AccountRepository;
import repository.UserRepository;

public class AccountService {
    UserRepository userRepository = new UserRepository();
    AccountRepository accountRepository = new AccountRepository();
    public void createAccount(int userId,int initialBalance){
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
}
