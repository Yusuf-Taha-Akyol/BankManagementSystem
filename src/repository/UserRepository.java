package repository;

import config.DatabaseConnection;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    Connection conn = DatabaseConnection.getConnection();
    public void create(User user) {
        String sql = "INSERT INTO users(name, email, password) VALUES (?, ?, ?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());

            stmt.executeUpdate();
            stmt.close();
            System.out.println("User created successfully");
            conn.close();
        } catch (Exception e){
            System.out.println("Error in creating user : " + e.getMessage());
        }

    }

    public User findByUserId(int userId) {
        String sql = "SELECT * FROM users WHERE id = ? and is_deleted = FALSE";

        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1 , userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");

                User user = new User(id,name,email,password);

                stmt.close();
                rs.close();
                System.out.println("User found successfully");
                return user;
            }
        } catch (Exception e) {
            System.out.println("Error in Find user by ID : " + e.getMessage());
        }
        return null;
    }

    public User findByEmail(String userEmail) {
        String sql = "SELECT * FROM users WHERE email = ? AND is_deleted = FALSE";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, userEmail);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");

                User user = new User(id,name,email,password);

                stmt.close();
                rs.close();
                System.out.println("User found successfully");

                return user;
            }
        }catch (Exception e){
            System.out.println("Error in Find user by email : " + e.getMessage());
        }

        return null;
    }

    public List<User> findAll() {
        String sql = "SELECT * FROM users WHERE is_deleted = FALSE ORDER BY id ASC";

        List<User> users = new ArrayList<>();

        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");

                User user = new User(id,name,email,password);
                users.add(user);
            }
            rs.close();
            stmt.close();
            System.out.println("All users were found successfully");
            return users;
        }catch (Exception e){
            System.out.println("Error in find all users : " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void update(User user, int userId) {
        String sql = "UPDATE users SET name=?, email=?, password=? WHERE id=? and is_deleted = FALSE";

        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setInt(4, userId);

            stmt.executeUpdate();
            stmt.close();
            System.out.println("User updated successfully");
            conn.close();
        }catch (Exception e){
            System.out.println("Error in update user : " + e.getMessage());
        }
    }

    public void safeDelete(int userId) {
        String sql = "UPDATE users SET is_deleted=TRUE WHERE id=?";

        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);

            stmt.executeUpdate();
            stmt.close();
            System.out.println("User deleted successfully");
        }catch (Exception e){
            System.out.println("Error in safe delete user : " + e.getMessage());
        }
    }

    public void hardDelete(int userId) {
        String sql = "DELETE FROM users WHERE id = ?";

        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);

            stmt.executeUpdate();
            stmt.close();
            System.out.println("User deleted successfully");
        }catch (Exception e){
            System.out.println("Error in hard delete user : " + e.getMessage());
        }
    }
}
