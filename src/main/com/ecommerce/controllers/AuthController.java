package main.com.ecommerce.controllers;

import main.com.ecommerce.models.User;
import main.com.ecommerce.services.UserService;
import main.com.ecommerce.exceptions.UserNotFoundException;
import java.util.Scanner;

public class AuthController {
    private UserService userService;
    private Scanner scanner;

    public AuthController(UserService userService) {
        this.userService = userService;
        this.scanner = new Scanner(System.in);
    }

    public User login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try {
            User user = userService.getUserByUsername(username);
            if (user.getPassword().equals(password)) {
                return user;
            } else {
                System.out.println("Invalid password.");
                return null;
            }
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
