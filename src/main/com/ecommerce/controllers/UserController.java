package main.com.ecommerce.controllers;

import main.com.ecommerce.models.User;
import main.com.ecommerce.services.UserService;
import main.com.ecommerce.exceptions.UserNotFoundException;
import java.util.List;
import java.util.Scanner;

public class UserController {
    private UserService userService;
    private Scanner scanner;

    public UserController(UserService userService) {
        this.userService = userService;
        this.scanner = new Scanner(System.in);
    }

    public void addUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter role (e.g., USER, ADMIN): ");
        String role = scanner.nextLine();

        int id = userService.generateUserId();

        User newUser = new User(id, username, password, email, role);
        userService.createUser(newUser);

        System.out.println("User created successfully!");
    }

    public void listUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            for (User user : users) {
                System.out.println("ID: " + user.getId() + ", Username: " + user.getUsername() + ", Email: " + user.getEmail() + ", Role: " + user.getRole());
            }
        }
    }

    public void updateUser() {
        System.out.print("Enter the user ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            User existingUser = userService.getUserById(id);

            System.out.print("Enter new username (current: " + existingUser.getUsername() + "): ");
            String newUsername = scanner.nextLine();

            System.out.print("Enter new password (current: " + existingUser.getPassword() + "): ");
            String newPassword = scanner.nextLine();

            System.out.print("Enter new email (current: " + existingUser.getEmail() + "): ");
            String newEmail = scanner.nextLine();

            System.out.print("Enter new role (current: " + existingUser.getRole() + "): ");
            String newRole = scanner.nextLine();

            User updatedUser = new User(id, newUsername, newPassword, newEmail, newRole);
            userService.updateUser(id, updatedUser);

            System.out.println("User updated successfully!");
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteUser() {
        System.out.print("Enter the user ID to delete: ");
        int id = scanner.nextInt();

        try {
            userService.deleteUser(id);
            System.out.println("User deleted successfully!");
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }


    public void authenticateUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = userService.authenticate(username, password);
        if (user != null) {
            System.out.println("Authentication successful: " + user.getUsername() + " (" + user.getRole() + ")");
        } else {
            System.out.println("Authentication failed. Please check your username and/or password.");
        }
    }

//  public void findUserByEmail() {
//      System.out.print("Enter email to find user: ");
//      String email = scanner.nextLine();
//
//      try {
//          User user = userService.getUserByEmail(email);
//          System.out.println("User found: ID: " + user.getId() + ", Username: " + user.getUsername() + ", Email: " + user.getEmail() + ", Role: " + user.getRole());
//      } catch (UserNotFoundException e) {
//          System.out.println(e.getMessage());
//      }
//  }

//  public void findUserByUsername() {
//      System.out.print("Enter username to find user: ");
//      String username = scanner.nextLine();
//
//      try {
//          User user = userService.getUserByUsername(username);
//          System.out.println("User found: ID: " + user.getId() + ", Username: " + user.getUsername() + ", Email: " + user.getEmail() + ", Role: " + user.getRole());
//      } catch (UserNotFoundException e) {
//          System.out.println(e.getMessage());
//      }
//  }
}
