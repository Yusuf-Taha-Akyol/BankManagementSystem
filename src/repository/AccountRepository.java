package repository;

import config.DatabaseConnection;
import model.Account;
import model.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository {
    Connection conn = DatabaseConnection.getConnection();
    public void create(Account account) {
        String sql = "INSERT INTO accounts(user_id,balance,created_at) VALUES (?,?,?)";

        try{
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, account.getUserId());
            stmt.setDouble(2, account.getBalance());
            stmt.setTimestamp(3, Timestamp.valueOf(account.getCreatedAt()));

            stmt.executeUpdate();
            stmt.close();
            System.out.println("Account created successfully");
        } catch (Exception e) {
            System.out.println("Error in creating account : " + e.getMessage());
        }
    }

    public Account findByAccountId(int accountId) {
        String sql = "SELECT * FROM accounts WHERE id = ? AND is_deleted = FALSE";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, accountId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int userId = rs.getInt("user_id");
                double balance = rs.getDouble("balance");
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();

                Account account = new Account(id,userId, balance,createdAt);

                stmt.close();
                rs.close();
                System.out.println("Account found successfully");
                return account;
            }
        } catch (SQLException e) {
            System.out.println("Error in finding account by id : " + e.getMessage());
        }
        return null;
    }

    public void updateBalance(Account account, double balance) {
        String sql = "UPDATE accounts SET balance = ? WHERE id = ? AND is_deleted = FALSE";

        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, balance);
            stmt.setInt(2, account.getId());

            stmt.executeUpdate();
            stmt.close();

            System.out.println("Account updated successfully");
        } catch (Exception e) {
            System.out.println("Error in updating account balance : " + e.getMessage());
        }
    }

    public List<Account> findAll() {
        String sql = "SELECT * FROM accounts WHERE is_deleted = FALSE ORDER BY id ASC";

        List<Account> accounts = new ArrayList<>();
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int userId = rs.getInt("user_id");
                double balance = rs.getDouble("balance");
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();

                Account account = new Account(id,userId, balance,createdAt);
                accounts.add(account);
            }
            rs.close();
            stmt.close();
            System.out.println("All accounts found successfully");
            return accounts;
        } catch (Exception e) {
            System.out.println("Error in finding all accounts : " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<Account> findByUserId(int userId) {
        String sql = "SELECT * FROM accounts WHERE user_id = ?";
        List<Account> accounts = new ArrayList<>();

        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int accountUserId = rs.getInt("user_id");
                double balance = rs.getDouble("balance");
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();

                Account account = new Account(id,accountUserId,balance,createdAt);
                accounts.add(account);
            }

            stmt.close();
            rs.close();
            System.out.println("All accounts found successfully");
            return accounts;
        }catch(Exception e){
            System.out.println("Error in finding account by id : " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void safeDelete(int accountId) {
        String sql = "DELETE FROM accounts SET is_deleted = true WHERE id = ? ";

        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, accountId);

            stmt.executeUpdate();
            stmt.close();
            System.out.println("Account deleted successfully");
        }catch(Exception e){
            System.out.println("Error in safe deleting account : " + e.getMessage());
        }
    }

    public void hardDelete(int accountId) {
        String sql = "DELETE FROM accounts WHERE id = ? ";

        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, accountId);

            stmt.executeUpdate();
            stmt.close();
            System.out.println("Account deleted successfully");
        }catch(Exception e){
            System.out.println("Error in safe deleting account : " + e.getMessage());
        }
    }

}
