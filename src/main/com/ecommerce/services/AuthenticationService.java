package main.com.ecommerce.services;

import main.com.ecommerce.models.User;
import main.com.ecommerce.models.Admin;

import java.util.Optional;

public class AuthenticationService {
    private UserService userService;
    private AdminService adminService;

    public AuthenticationService(UserService userService, AdminService adminService) {
        this.userService = userService;
        this.adminService = adminService;
    }

    public Optional<Object> authenticate(String username, String password) {
        Optional<Admin> admin = adminService.getAllAdmins().stream()
                .filter(a -> a.getUsername().equals(username) && a.getPassword().equals(password))
                .findFirst();
        if (admin.isPresent()) {
            return Optional.of(admin.get());
        }

        Optional<User> user = userService.getAllUsers().stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst();
        return Optional.ofNullable(user.orElse(null));
    }
}
