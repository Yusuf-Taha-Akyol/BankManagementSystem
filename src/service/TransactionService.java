package service;

import model.Account;
import model.Transaction;
import repository.TransactionRepository;

import java.util.List;

public class TransactionService {
    AccountService accountService = new AccountService();
    TransactionRepository transactionRepository = new TransactionRepository();
    public void logTransaction(int fromAccountId,int toAccountId,double amount,String type) {
        Account fromAccount = accountService.getAccountById(fromAccountId);
        Account toAccount = accountService.getAccountById(toAccountId);
        if (fromAccountId == toAccountId || fromAccountId < 1 || toAccountId < 1) {
            System.out.println("Invalid account id input");
        } else if (amount < 0) {
            System.out.println("Invalid amount");
        } else if (type == null) {
            System.out.println("Transaction type cannot be null");
        } else if (fromAccount == null || toAccount == null) {
            System.out.println("Account not found");
        }else {
            boolean isApproved = accountService.transfer(fromAccountId,toAccountId,amount,type);
            if(isApproved){
                System.out.println("Transfer successful");
                Transaction transaction = new Transaction(fromAccountId,toAccountId,amount,type);
                transactionRepository.create(transaction);
            }
        }
    }

    public List<Transaction> getTransactions(int accountId){
        Account account = accountService.getAccountById(accountId);
        if (accountId < 1){
            System.out.println("Invalid account id");
        }else if(account == null){
            System.out.println("Account not found");
        }else {
            return transactionRepository.findByAccountId(accountId);
        }
        return null;
    }
}
