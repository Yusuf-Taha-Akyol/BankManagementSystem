package service;

import model.User;
import repository.UserRepository;

public class AuthService {
    public void registerUser(String name, String email,String password) {
        //Empty value check
        if(name == null || email == null || password == null) {
            System.out.println("You can't register with null or empty values");
        } else if (password.length() < 8) { //Password strength check
            System.out.println("Your password must contain at least 15 characters.");
        } else if (!password.contains(".*[A-Z].*") //At least one capital letter
        || !password.contains(".*[a-z].*") //At least one lowercase letter
        || !password.contains(".*\\d.*") // At least one digit
        || !password.contains(".*[@#$%^&+=!].*")) { //At least one special character

            System.out.println("Your password is too weak");

        } else{
            UserRepository userRepository = new UserRepository();
            //Uniqe Email check
            if(userRepository.findByEmail(email) != null) {
                System.out.println("Email already exists");
            }else{
                User user = new User(0,name,email,password);
                userRepository.create(user);
            }
        }
    }

    public void loginUser(String email, String password) {
        //Empty value check
        if(email == null || password == null) {
            System.out.println("You can't login with null or empty values");
        }else{
            UserRepository userRepository = new UserRepository();

            User user = userRepository.findByEmail(email);
            if (user == null) {
                System.out.println("User not found");
            } else if (user.getPassword().equals(password)) {
                System.out.println("User logged in");
            }else  {
                System.out.println("Wrong password");
            }
        }
    }
}
