package main.com.ecommerce.controllers;

import main.com.ecommerce.models.Product;
import main.com.ecommerce.services.ProductService;
import main.com.ecommerce.exceptions.ProductNotFoundException;
import main.com.ecommerce.utils.InputValidator;

public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    public void handleProductActions() {
        while (true) {
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. Delete Product");
            System.out.println("4. List Products");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            
            int choice = InputValidator.getValidInt("Choose an option: ");

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    updateProduct();
                    break;
                case 3:
                    deleteProduct();
                    break;
                case 4:
                    listProducts();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public void addProduct() {
        System.out.print("Enter product name: ");
        String name = InputValidator.validateStringInput();

        System.out.print("Enter product description: ");
        String description = InputValidator.validateStringInput();

        System.out.print("Enter product price: ");
        double price = InputValidator.validateDoubleInput();

        System.out.print("Enter product stock: ");
        int stock = InputValidator.validateIntInput();

        Product product = new Product(0, name, description, price, stock);
        productService.addProduct(product);
        System.out.println("Product added successfully!");
    }


    public void updateProduct() {
        int id = InputValidator.getValidInt("Enter Product ID to Update: ");
        
        try {
            Product product = productService.getProduct(id);
            String name = InputValidator.getValidString("Enter new Product Name: ");
            String description = InputValidator.getValidString("Enter new Product Description: ");
            double price = InputValidator.getValidDouble("Enter new Product Price: ");
            int stock = InputValidator.getValidInt("Enter new Product Stock: ");

            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setStock(stock);

            productService.updateProduct(product);
            System.out.println("Product updated successfully.");
        } catch (ProductNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteProduct() {
        int id = InputValidator.getValidInt("Enter Product ID to Delete: ");

        try {
            productService.deleteProduct(id);
            System.out.println("Product deleted successfully.");
        } catch (ProductNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void listProducts() {
        System.out.println("Product List:");
        productService.getAllProducts().forEach(product ->
            System.out.println("ID: " + product.getId() + ", Name: " + product.getName() +
                    ", Description: " + product.getDescription() + ", Price: " + product.getPrice() +
                    ", Stock: " + product.getStock()));
    }
}
