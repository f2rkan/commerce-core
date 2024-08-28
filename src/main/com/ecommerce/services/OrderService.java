package main.com.ecommerce.services;

import main.com.ecommerce.exceptions.OrderNotFoundException;
import main.com.ecommerce.exceptions.ProductNotFoundException;
import main.com.ecommerce.models.Order;
import main.com.ecommerce.models.Product;
import main.com.ecommerce.utils.FileHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderService {
    private Map<Integer, Order> orderMap = new HashMap<>();
    private ProductService productService;
    private int nextId;

    public OrderService(ProductService productService) {
        this.productService = productService;
        try {
            FileHandler.loadOrdersFromFile(this, productService);
            nextId = orderMap.keySet().stream().mapToInt(v -> v).max().orElse(0) + 1;
        } catch (ProductNotFoundException e) {
            System.out.println("Error loading orders: " + e.getMessage());
        }
    }

    public void addOrder(Order order) throws ProductNotFoundException {
        if (productService.getProduct(order.getProduct().getId()) == null) {
            throw new ProductNotFoundException("Product not found with ID: " + order.getProduct().getId());
        }
        if (!orderMap.containsKey(order.getId()) || order.getId() == 0) {
            order.setId(nextId++);
            orderMap.put(order.getId(), order);
            FileHandler.saveOrderToFile(order);
        } else {
            orderMap.put(order.getId(), order);
            FileHandler.saveOrderToFile(order);
        }
    }


    public Order getOrder(int id) throws OrderNotFoundException {
        Order order = orderMap.get(id);
        if (order == null) {
            throw new OrderNotFoundException("Order not found with ID: " + id);
        }
        return order;
    }

    public Map<Integer, Order> getOrderMap() {
        return orderMap;
    }

    public List<Order> getAllOrders() {
        return orderMap.values().stream().collect(Collectors.toList());
    }

    public void updateOrder(Order order) throws ProductNotFoundException {
        if (productService.getProduct(order.getProduct().getId()) == null) {
            throw new ProductNotFoundException("Product not found with ID: " + order.getProduct().getId());
        }
        if (orderMap.containsKey(order.getId())) {
            orderMap.put(order.getId(), order);
            FileHandler.saveOrderToFile(order);
        }
    }

    public void deleteOrder(int id) throws OrderNotFoundException {
        if (orderMap.remove(id) == null) {
            throw new OrderNotFoundException("Order not found with ID: " + id);
        }
    }
}
