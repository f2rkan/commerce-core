package main.com.ecommerce;

import main.com.ecommerce.services.OrderService;
import main.com.ecommerce.services.UserService;
import main.com.ecommerce.services.AdminService;
import main.com.ecommerce.services.ProductService;
import main.com.ecommerce.services.AuthenticationService;
import main.com.ecommerce.controllers.AdminController;
import main.com.ecommerce.controllers.OrderController;
import main.com.ecommerce.controllers.UserController;
import main.com.ecommerce.controllers.ProductController; // ProductController'ı ekleyin
import main.com.ecommerce.utils.DataInitializer;
import main.com.ecommerce.utils.FileHandler;
import main.com.ecommerce.models.User;
import main.com.ecommerce.models.Admin;
import main.com.ecommerce.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<User> users = new ArrayList<>();
        UserService userService = new UserService(users);
        ProductService productService = new ProductService();

        OrderService orderService = new OrderService(productService);
        AdminService adminService = new AdminService();
        Scanner scanner = new Scanner(System.in);

        UserController userController = new UserController(userService);
        OrderController orderController = new OrderController(orderService, productService); 
        AdminController adminController = new AdminController(adminService, orderService, productService, scanner);
        ProductController productController = new ProductController(productService);

        FileHandler.loadUsersFromFile(userService);
        
        DataInitializer.initialize(userService, adminService);

        AuthenticationService authenticationService = new AuthenticationService(userService, adminService);
        LoginManager loginManager = new LoginManager(authenticationService);

        boolean loggedIn = false;

        while (!loggedIn) {
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                loggedIn = loginManager.login(scanner);
            } else if (choice == 2) {
                registerUser(scanner, userService);
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }

        Object loggedInEntity = loginManager.getLoggedInEntity();

        while (true) {
            if (loggedInEntity instanceof Admin) {
                showAdminMenu(scanner, adminController, userController, productController);
            } else if (loggedInEntity instanceof User) {
                showUserMenu(scanner, userController, orderController);
            } else {
                System.out.println("Role not recognized.");
            }
        }
    }

    private static void registerUser(Scanner scanner, UserService userService) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        User user = new User(userService.generateUserId(), username, password, email, "user");
        userService.createUser(user);

        FileHandler.saveUserToFile(user);
        
        System.out.println("User registered successfully!");
    }

    private static void showAdminMenu(Scanner scanner, AdminController adminController, UserController userController, ProductController productController) {
        System.out.println("1. View All Orders");
        System.out.println("2. Update Order Status");
        System.out.println("3. Cancel Order");
        System.out.println("4. Manage Users");
        System.out.println("5. Manage Products");
        System.out.println("6. Logout");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                adminController.viewAllOrders();
                break;
            case 2:
                adminController.updateOrderStatus();
                break;
            case 3:
                adminController.cancelOrder();
                break;
            case 4:
                manageUsers(scanner, userController);
                break;
            case 5:
                manageProducts(scanner, productController);
                break;
            case 6:
                System.out.println("Logging out...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static void manageUsers(Scanner scanner, UserController userController) {
        while (true) {
            System.out.println("1. Add User");
            System.out.println("2. List Users");
            System.out.println("3. Update User");
            System.out.println("4. Delete User");
            System.out.println("5. Back to Admin Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    userController.addUser();
                    break;
                case 2:
                    userController.listUsers();
                    break;
                case 3:
                    userController.updateUser();
                    break;
                case 4:
                    userController.deleteUser();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void manageProducts(Scanner scanner, ProductController productController) {
        while (true) {
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. Delete Product");
            System.out.println("4. List Products");
            System.out.println("5. Back to Admin Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    productController.addProduct();
                    break;
                case 2:
                    productController.updateProduct();
                    break;
                case 3:
                    productController.deleteProduct();
                    break;
                case 4:
                    productController.listProducts();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void showUserMenu(Scanner scanner, UserController userController, OrderController orderController) {
        System.out.println("1. Manage Orders");
        System.out.println("2. Logout");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                manageOrders(scanner, orderController);
                break;
            case 2:
                System.out.println("Logging out...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static void manageOrders(Scanner scanner, OrderController orderController) {
        System.out.println("1. Add Order");
        System.out.println("2. Get Order");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                orderController.addOrder();
                break;
            case 2:
                orderController.getOrder();
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }
}
