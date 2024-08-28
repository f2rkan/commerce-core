package main.com.ecommerce.utils;

import main.com.ecommerce.models.Admin;
import main.com.ecommerce.models.User;
import main.com.ecommerce.services.UserService;
import main.com.ecommerce.services.AdminService;

public class DataInitializer {
    public static void initialize(UserService userService, AdminService adminService) {
        // default user
        User defaultUser = new User();
        defaultUser.setId(1); // default ID
        defaultUser.setUsername("user");
        defaultUser.setPassword("password");
        defaultUser.setEmail("user@example.com");
        defaultUser.setRole("USER");
        userService.createUser(defaultUser);

        // default admin
        Admin defaultAdmin = new Admin();
        defaultAdmin.setId(2); // default ID
        defaultAdmin.setUsername("admin");
        defaultAdmin.setPassword("adminpassword");
        defaultAdmin.setEmail("admin@example.com");
        defaultAdmin.setAdminLevel("ADMIN");
        adminService.addAdmin(defaultAdmin);
    }
}
