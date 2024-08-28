package main.com.ecommerce.utils;

import java.util.Scanner;

public class InputValidator {
    private static Scanner scanner = new Scanner(System.in);

    public static int getValidInt(String prompt) {
        int value;
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                value = Integer.parseInt(input);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
        return value;
    }

    public static String getValidString(String prompt) {
        String value;
        while (true) {
            System.out.print(prompt);
            value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                break;
            } else {
                System.out.println("Input cannot be empty. Please enter a valid string.");
            }
        }
        return value;
    }

    public static String getValidEmail(String prompt) {
        String email;
        while (true) {
            System.out.print(prompt);
            email = scanner.nextLine().trim();
            if (email.contains("@") && email.contains(".")) { 
                break;
            } else {
                System.out.println("Invalid email format. Please enter a valid email address.");
            }
        }
        return email;
    }

    public static String getValidAdminLevel(String prompt) {
        String level;
        while (true) {
            System.out.print(prompt);
            level = scanner.nextLine().trim();
            if (level.equalsIgnoreCase("ADMIN") || level.equalsIgnoreCase("SUPERADMIN")) {
                break;
            } else {
                System.out.println("Invalid admin level. Please enter 'ADMIN' or 'SUPERADMIN'.");
            }
        }
        return level;
    }

    public static double getValidDouble(String prompt) {
        double value;
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                value = Double.parseDouble(input);
                break; 
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid decimal number.");
            }
        }
        return value;
    }

    public static String validateStringInput() {
        return getValidString("");
    }

    public static int validateIntInput() {
        return getValidInt("");
    }

    public static double validateDoubleInput() {
        return getValidDouble("");
    }
}
