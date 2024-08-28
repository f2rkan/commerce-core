package main.com.ecommerce.utils;

import main.com.ecommerce.exceptions.ProductNotFoundException;
import main.com.ecommerce.models.Order;
import main.com.ecommerce.models.Product;
import main.com.ecommerce.models.User;
import main.com.ecommerce.services.OrderService;
import main.com.ecommerce.services.ProductService;
import main.com.ecommerce.services.UserService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private static final String USER_FILE = "users.txt";
    private static final String ORDER_FILE = "orders.txt";
    private static final String PRODUCT_FILE = "products.txt";

    public static void loadUsersFromFile(UserService userService) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String username = parts[1];
                String password = parts[2];
                String email = parts[3];
                String role = parts[4];
                User user = new User(id, username, password, email, role);
                userService.createUser(user);
            }
        } catch (IOException e) {
            System.out.println("No previous users found.");
        }
    }

    public static void saveUserToFile(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE, true))) {
            writer.write(user.getId() + "," + user.getUsername() + "," + user.getPassword() + "," + user.getEmail() + "," + user.getRole());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing user to file.");
        }
    }

    public static void loadOrdersFromFile(OrderService orderService, ProductService productService) throws ProductNotFoundException {
        try (BufferedReader reader = new BufferedReader(new FileReader(ORDER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                int productId = Integer.parseInt(parts[1]);
                int quantity = Integer.parseInt(parts[2]);
                double totalPrice = Double.parseDouble(parts[3]);
                String status = parts[4];

                Product product = productService.getProduct(productId);
                if (product == null) {
                    throw new ProductNotFoundException("Product not found with ID: " + productId);
                }
                Order order = new Order(id, product, quantity, status);
                order.setTotalPrice(totalPrice);
                orderService.getOrderMap().put(id, order);
            }
        } catch (IOException e) {
            System.out.println("No previous orders found.");
        }
    }

    public static void saveOrderToFile(Order order) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ORDER_FILE, true))) {
            writer.write(order.getId() + "," + order.getProduct().getId() + "," + order.getQuantity() + "," + order.getTotalPrice() + "," + order.getStatus());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing order to file.");
        }
    }

    public static void loadProductsFromFile(ProductService productService) {
        try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                String description = parts[2];
                double price = Double.parseDouble(parts[3]);
                int stock = Integer.parseInt(parts[4]);

                Product product = new Product(id, name, description, price, stock);
                productService.getProductMap().put(id, product);
            }
        } catch (IOException e) {
            System.out.println("Error reading products file: " + e.getMessage());
        }
    }

    // Ürünü dosyaya kaydet
    public static void saveProductToFile(Product product) {
        List<Product> products = loadAllProductsFromFile();
        boolean exists = false;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PRODUCT_FILE))) {
            for (Product p : products) {
                if (p.getId() == product.getId()) {
                    writer.write(product.getId() + "," + product.getName() + "," + product.getDescription() + "," + product.getPrice() + "," + product.getStock());
                    writer.newLine();
                    exists = true;
                } else {
                    writer.write(p.getId() + "," + p.getName() + "," + p.getDescription() + "," + p.getPrice() + "," + p.getStock());
                    writer.newLine();
                }
            }
            if (!exists) {
                // Yeni ürün ekleme
                writer.write(product.getId() + "," + product.getName() + "," + product.getDescription() + "," + product.getPrice() + "," + product.getStock());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing product to file.");
        }
    }

    public static List<Product> loadAllProductsFromFile() {
        List<Product> products = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String description = parts[2];
                    double price = Double.parseDouble(parts[3]);
                    int stock = Integer.parseInt(parts[4]);
                    products.add(new Product(id, name, description, price, stock));
                }
            }
        } catch (IOException e) {
        }
        return products;
    }


    public static void deleteProductFromFile(int id) {
        File inputFile = new File(PRODUCT_FILE);
        File tempFile = new File("products_temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int productId = Integer.parseInt(parts[0]);

                if (productId != id) {
                    writer.write(line);
                    writer.newLine();
                }
            }

            if (!inputFile.delete()) {
                System.out.println("Could not delete file");
                return;
            }
            if (!tempFile.renameTo(inputFile)) {
                System.out.println("Could not rename file");
            }

        } catch (IOException e) {
            System.out.println("Error deleting product from file.");
        }
    }
}
