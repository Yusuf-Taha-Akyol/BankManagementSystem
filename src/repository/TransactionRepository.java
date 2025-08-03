package repository;

import config.DatabaseConnection;
import model.Transaction;
import service.AccountService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {
    AccountService accountService = new AccountService();
    Connection conn = DatabaseConnection.getConnection();
    public void create(Transaction transaction) {
        String sql = "INSERT INTO transactions (from_account,to_account,amount,type) VALUES (?,?,?,?)";

        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, transaction.getFromAccountId());
            stmt.setInt(2, transaction.getToAccountId());
            stmt.setDouble(3, transaction.getAmount());
            stmt.setString(4, transaction.getType());

            stmt.executeUpdate();
            stmt.close();
            System.out.println("Transaction created successfully");
        }catch (Exception e){
            System.out.println("Error in creating transaction : " + e.getMessage());
        }
    }

    public List<Transaction> findByAccountId(int accountId){
        String sql = "SELECT * FROM transactions WHERE from_account = ? OR to_account = ?";
        List<Transaction> transactions = new ArrayList<>();
        if(accountService.getAccountById(accountId) == null){
            System.out.println("Account couldn't found");
            return new ArrayList<>();
        }else{
            try{
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, accountId);
                stmt.setInt(2, accountId);
                ResultSet rs = stmt.executeQuery();

                while(rs.next()){
                    int id =  rs.getInt("id");
                    int fromAccountId = rs.getInt("from_account");
                    int toAccountId = rs.getInt("to_account");
                    double amount = rs.getDouble("amount");
                    String type = rs.getString("type");
                    LocalDateTime transactionDate = rs.getTimestamp("timestamp").toLocalDateTime();

                    Transaction transaction = new Transaction(fromAccountId, toAccountId, amount, type,transactionDate);
                    transactions.add(transaction);
                }
                rs.close();
                stmt.close();
                System.out.println("Transactions found successfully");
                return transactions;
            }catch (Exception e){
                System.out.println("Error in finding transactions by accountId : " + e.getMessage());
                return new ArrayList<>();
            }
        }
    }
}
