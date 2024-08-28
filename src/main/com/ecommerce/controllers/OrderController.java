package main.com.ecommerce.controllers;

import main.com.ecommerce.models.Order;
import main.com.ecommerce.models.Product;
import main.com.ecommerce.services.OrderService;
import main.com.ecommerce.services.ProductService;
import main.com.ecommerce.utils.InputValidator;
import main.com.ecommerce.exceptions.OrderNotFoundException;
import main.com.ecommerce.exceptions.ProductNotFoundException;

import java.util.Map;

public class OrderController {
    private OrderService orderService;
    private ProductService productService;

    public OrderController(OrderService orderService, ProductService productService) {
        this.orderService = orderService;
        this.productService = productService;
    }

    public void addOrder() {
        listProducts();
        int productId = InputValidator.getValidInt("Enter Product ID: ");
        int quantity = InputValidator.getValidInt("Enter quantity: ");
        String status = InputValidator.getValidString("Enter order status: ");

        try {
            Product product = productService.getProduct(productId);
            if (product == null) {
                System.out.println("Product not found with ID: " + productId);
                return;
            }
            
            Order newOrder = new Order(0, product, quantity, status);
            orderService.addOrder(newOrder);
            System.out.println("Order added successfully with ID: " + newOrder.getId());
        } catch (ProductNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private void listProducts() {
        Map<Integer, Product> products = productService.getProductMap();
        System.out.println("Available Products:");
        for (Product product : products.values()) {
            System.out.println("Product ID: " + product.getId() +
                               ", Name: " + product.getName() +
                               ", Description: " + product.getDescription() +
                               ", Price: " + product.getPrice() +
                               ", Stock: " + product.getStock());
        }
    }

    public void getOrder() {
        int id = InputValidator.getValidInt("Enter order ID to retrieve: ");
        try {
            Order order = orderService.getOrder(id);
            System.out.println(order);
        } catch (OrderNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
