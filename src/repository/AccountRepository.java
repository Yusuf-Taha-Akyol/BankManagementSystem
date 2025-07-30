package repository;

import config.DatabaseConnection;
import model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AccountRepository {
    Connection conn = DatabaseConnection.getConnection();
    public void create(Account account) {
        String sql = "INSERT INTO accounts(userId,balance,createdAt) VALUES (?,?,?)";

        try{
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, account.getUserId());
            stmt.setBigDecimal(2, account.getBalance());

            java.sql.Timestamp timestamp = new java.sql.Timestamp(account.getCreatedAt().getDayOfYear());
            System.out.println(timestamp);
        } catch (Exception e) {
            System.out.println("Error in creating account : " + e.getMessage());
        }
    }
}
