package main.com.ecommerce.services;

import main.com.ecommerce.models.Product;
import main.com.ecommerce.utils.FileHandler;
import main.com.ecommerce.exceptions.ProductNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductService {
    private Map<Integer, Product> productMap = new HashMap<>();

    public ProductService() {
        FileHandler.loadProductsFromFile(this); // Ürünleri dosyadan yükle
    }

    private int getMaxProductIdFromFile() {
        List<Product> products = FileHandler.loadAllProductsFromFile();
        return products.stream()
                       .mapToInt(Product::getId)
                       .max()
                       .orElse(0);
    }

    public void addProduct(Product product) {
        int newId = getMaxProductIdFromFile() + 1;
        
        product.setId(newId);

        productMap.put(newId, product);
        FileHandler.saveProductToFile(product);
    }

    public Product getProduct(int id) throws ProductNotFoundException {
        Product product = productMap.get(id);
        if (product == null) {
            throw new ProductNotFoundException("Product not found with ID: " + id);
        }
        return product;
    }
    
    public Map<Integer, Product> getProductMap() {
        return productMap;
    }

    public Product getProductById(int id) throws ProductNotFoundException {
        return getProduct(id);
    }

    public List<Product> getAllProducts() {
        return productMap.values().stream().collect(Collectors.toList());
    }

    public void updateProduct(Product product) throws ProductNotFoundException {
        if (productMap.containsKey(product.getId())) {
            productMap.put(product.getId(), product);
            FileHandler.saveProductToFile(product);
        } else {
            throw new ProductNotFoundException("Product not found with ID: " + product.getId());
        }
    }

    public void deleteProduct(int id) throws ProductNotFoundException {
        if (productMap.remove(id) == null) {
            throw new ProductNotFoundException("Product not found with ID: " + id);
        }
        FileHandler.deleteProductFromFile(id);
    }
}
