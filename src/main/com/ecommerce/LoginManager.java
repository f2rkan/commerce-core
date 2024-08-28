package main.com.ecommerce;

import main.com.ecommerce.models.User;
import main.com.ecommerce.models.Admin;
import main.com.ecommerce.services.AuthenticationService;

import java.util.Optional;
import java.util.Scanner;

public class LoginManager {
    private AuthenticationService authenticationService;
    private Object loggedInEntity;

    public LoginManager(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public boolean login(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        Optional<Object> loggedIn = authenticationService.authenticate(username, password);
        if (loggedIn.isPresent()) {
            loggedInEntity = loggedIn.get();
            if (loggedInEntity instanceof Admin) {
                System.out.println("Logged in as admin.");
            } else if (loggedInEntity instanceof User) {
                System.out.println("Logged in as user.");
            }
            return true;
        } else {
            System.out.println("Invalid username or password.");
            return false;
        }
    }

    public void logout() {
        loggedInEntity = null;
        System.out.println("Successfully logged out.");
    }

    public Object getLoggedInEntity() {
        return loggedInEntity;
    }
}
