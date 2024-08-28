package main.com.ecommerce.controllers;

import main.com.ecommerce.exceptions.AdminNotFoundException;
import main.com.ecommerce.exceptions.OrderNotFoundException;
import main.com.ecommerce.exceptions.ProductNotFoundException;
import main.com.ecommerce.models.Admin;
import main.com.ecommerce.models.Order;
import main.com.ecommerce.models.Product;
import main.com.ecommerce.services.AdminService;
import main.com.ecommerce.services.OrderService;
import main.com.ecommerce.services.ProductService;
import main.com.ecommerce.utils.InputValidator;

import java.util.Scanner;

public class AdminController {
    private AdminService adminService;
    private OrderService orderService;
    private ProductService productService;
    private Scanner scanner;

    public AdminController(AdminService adminService, OrderService orderService, ProductService productService, Scanner scanner) {
        this.adminService = adminService;
        this.orderService = orderService;
        this.productService = productService;
        this.scanner = scanner;
    }

    public void addAdmin() {
        int id = InputValidator.getValidInt("Enter admin ID: ");
        String username = InputValidator.getValidString("Enter username: ");
        String password = InputValidator.getValidString("Enter password: ");
        String email = InputValidator.getValidString("Enter email: ");
        String adminLevel = InputValidator.getValidString("Enter admin level: ");

        Admin newAdmin = new Admin(id, username, password, email, adminLevel);
        adminService.addAdmin(newAdmin);
        System.out.println("Admin added successfully!");
    }

    public void getAdmin() {
        int id = InputValidator.getValidInt("Enter admin ID to retrieve: ");
        try {
            Admin admin = adminService.getAdmin(id);
            System.out.println(admin);
        } catch (AdminNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewAllOrders() {
        for (Order order : orderService.getAllOrders()) {
            System.out.println(order);
        }
    }

    public void updateOrderStatus() {
        int id = InputValidator.getValidInt("Enter order ID to update: ");
        String newStatus = InputValidator.getValidString("Enter new status: ");
        try {
            Order order = orderService.getOrder(id);
            order.setStatus(newStatus);
            orderService.updateOrder(order);
            System.out.println("Order status updated successfully!");
        } catch (OrderNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (ProductNotFoundException e) {
            System.out.println("Error updating order status: " + e.getMessage());
        }
    }


    public void cancelOrder() {
        int id = InputValidator.getValidInt("Enter order ID to cancel: ");
        try {
            orderService.deleteOrder(id);
            System.out.println("Order canceled successfully!");
        } catch (OrderNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    // Delegating product management to ProductService
    public void addProduct() {
        int id = InputValidator.getValidInt("Enter product ID: ");
        String name = InputValidator.getValidString("Enter product name: ");
        String description = InputValidator.getValidString("Enter product description: ");
        double price = InputValidator.getValidDouble("Enter product price: ");
        int stock = InputValidator.getValidInt("Enter product stock: ");

        Product product = new Product(id, name, description, price, stock);
        productService.addProduct(product);
        System.out.println("Product added successfully!");
    }

    public void updateProduct() {
        int id = InputValidator.getValidInt("Enter product ID to update: ");
        try {
            Product product = productService.getProduct(id);
            String name = InputValidator.getValidString("Enter new product name (leave empty to keep current): ");
            String description = InputValidator.getValidString("Enter new product description (leave empty to keep current): ");
            Double price = InputValidator.getValidDouble("Enter new product price (leave empty to keep current): ");
            Integer stock = InputValidator.getValidInt("Enter new product stock (leave empty to keep current): ");

            if (!name.isEmpty()) product.setName(name);
            if (!description.isEmpty()) product.setDescription(description);
            if (price != null) product.setPrice(price);
            if (stock != null) product.setStock(stock);

            productService.updateProduct(product);
            System.out.println("Product updated successfully!");
        } catch (ProductNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteProduct() {
        int id = InputValidator.getValidInt("Enter product ID to delete: ");
        try {
            productService.deleteProduct(id);
            System.out.println("Product deleted successfully!");
        } catch (ProductNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void listProducts() {
        for (Product product : productService.getAllProducts()) {
            System.out.println(product);
        }
    }

    public void getProduct() {
        int id = InputValidator.getValidInt("Enter product ID to retrieve: ");
        try {
            Product product = productService.getProduct(id);
            System.out.println(product);
        } catch (ProductNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
