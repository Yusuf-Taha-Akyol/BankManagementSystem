package repository;

import config.DatabaseConnection;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    Connection conn = DatabaseConnection.getConnection();
    public void create(User user) {
        //Database düzenlendikden sonra eklenecek
        String sql = "INSERT INTO users(name, email, password) VALUES (?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());

            ps.executeUpdate();

            System.out.println("User created successfully");
        } catch (Exception e){
            System.out.println("Error in creating user : " + e.getMessage());
        }
    }

    public User findByUserId(int userId) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1 , userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                User user = new User(0,name,email,password);
                System.out.println("User finded successfully");
                return user;
            }
        } catch (Exception e) {
            System.out.println("Error in Find user by ID : " + e.getMessage());
        }
        return null;
    }

    public List<User> findAll() {
        String sql = "SELECT * FROM users";

        List<User> users = new ArrayList<>();

        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                users.add(new User(rs.getInt("id"),rs.getString("name"),rs.getString("email"),rs.getString("password")));
            }

            return users;
        }catch (Exception e){
            System.out.println("Error in find all users : " + e.getMessage());
        }

        return null;
    }

    public void Update(User user){
        String sql = "UPDATE users SET name=?, email=?, password=? WHERE id=?";

        try{
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());

            stmt.executeUpdate();
        }catch (Exception e){
            System.out.println("Error in update user : " + e.getMessage());
        }
    }
}
