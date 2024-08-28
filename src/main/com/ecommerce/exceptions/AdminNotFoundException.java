package main.com.ecommerce.exceptions;

public class AdminNotFoundException extends Exception {
    public AdminNotFoundException(String message) {
        super(message);
    }
}
