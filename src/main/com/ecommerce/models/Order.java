package main.com.ecommerce.models;

public class Order {
    private int id;
    private Product product;
    private int quantity;
    private double totalPrice;
    private String status;

    public Order(int id, Product product, int quantity, String status) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = calculateTotalPrice();
        this.status = status;
    }
    
    private double calculateTotalPrice() {
        return this.product.getPrice() * this.quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        this.totalPrice = calculateTotalPrice();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.totalPrice = calculateTotalPrice();
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getProductId() {
        return product != null ? product.getId() : -1;
    }
    @Override
    public String toString() {
        return "Order ID: " + id + 
               ", Product: " + product.getName() + 
               ", Quantity: " + quantity + 
               ", Total Price: " + totalPrice + 
               ", Status: " + status;
    }

}
