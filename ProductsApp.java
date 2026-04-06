/*
 * Name: Jaden Plummer
 * Date: 2026-04-04
 * Description: Menu program to create, edit, delete, and display products.
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class ProductsApp {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        ArrayList<Product> products = new ArrayList<>();

        int choice;

        do {
            displayMenu();
            choice = readInt(input, "Enter your choice: ");

            switch (choice) {
                case 1:
                    createProduct(input, products);
                    break;
                case 2:
                    createPerishableProduct(input, products);
                    break;
                case 3:
                    editProductBySku(input, products);
                    break;
                case 4:
                    deleteProductBySku(input, products);
                    break;
                case 5:
                    displayProductBySku(input, products);
                    break;
                case 6:
                    displayAllProducts(products);
                    break;
                case 7:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option.");
            }

            System.out.println();

        } while (choice != 7);

        input.close();
    }

    // menu display
    public static void displayMenu() {
        System.out.println("===== Product Menu =====");
        System.out.println("1) Create Product");
        System.out.println("2) Create Perishable Product");
        System.out.println("3) Edit Product by SKU");
        System.out.println("4) Delete Product by SKU");
        System.out.println("5) Display Product by SKU");
        System.out.println("6) Display all Products");
        System.out.println("7) Exit");
    }

    // create normal product
    public static void createProduct(Scanner input, ArrayList<Product> products) {
        try {
            String sku = readSku(input, products, null);
            String name = readNonBlankString(input, "Enter product name: ");
            double cost = readDouble(input, "Enter unit cost: ");
            double price = readDouble(input, "Enter sale price: ");
            int onHand = readInt(input, "Enter quantity on hand: ");
            int needed = readInt(input, "Enter quantity needed: ");

            System.out.print("Enter special instructions: ");
            String instructions = input.nextLine();

            products.add(new Product(sku, name, cost, price, onHand, needed, instructions));
            System.out.println("Product created.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // create perishable product
    public static void createPerishableProduct(Scanner input, ArrayList<Product> products) {
        try {
            String sku = readSku(input, products, null);
            String name = readNonBlankString(input, "Enter product name: ");
            double cost = readDouble(input, "Enter unit cost: ");
            double price = readDouble(input, "Enter sale price: ");
            int onHand = readInt(input, "Enter quantity on hand: ");
            int needed = readInt(input, "Enter quantity needed: ");

            System.out.print("Enter special instructions: ");
            String instructions = input.nextLine();

            Date expiry = readDate(input, "Enter expiry date (yyyy-MM-dd): ");

            products.add(new PerishableProduct(sku, name, cost, price, onHand, needed, instructions, expiry));
            System.out.println("Perishable product created.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // edit product
    public static void editProductBySku(Scanner input, ArrayList<Product> products) {
        System.out.print("Enter SKU: ");
        String sku = input.nextLine();

        Product p = findProduct(products, sku);

        if (p == null) {
            System.out.println("Not found.");
            return;
        }

        try {
            p.setProductName(readNonBlankString(input, "New name: "));
            p.setUnitCost(readDouble(input, "New cost: "));
            p.setSalePrice(readDouble(input, "New price: "));
            p.setQuantityOnHand(readInt(input, "New quantity on hand: "));
            p.setQuantityNeeded(readInt(input, "New quantity needed: "));

            System.out.print("New instructions: ");
            p.setSpecialInstructions(input.nextLine());

            if (p instanceof PerishableProduct) {
                ((PerishableProduct) p).setExpiryDate(readDate(input, "New expiry date: "));
            }

            System.out.println("Updated.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // delete product
    public static void deleteProductBySku(Scanner input, ArrayList<Product> products) {
        System.out.print("Enter SKU: ");
        String sku = input.nextLine();

        Product p = findProduct(products, sku);

        if (p != null) {
            products.remove(p);
            System.out.println("Deleted.");
        } else {
            System.out.println("Not found.");
        }
    }

    // display one product
    public static void displayProductBySku(Scanner input, ArrayList<Product> products) {
        System.out.print("Enter SKU: ");
        String sku = input.nextLine();

        Product p = findProduct(products, sku);

        if (p != null) {
            System.out.println(p);
        } else {
            System.out.println("Not found.");
        }
    }

    // display all products
    public static void displayAllProducts(ArrayList<Product> products) {
        if (products.isEmpty()) {
            System.out.println("No products.");
            return;
        }

        for (Product p : products) {
            System.out.println("------------------");
            System.out.println(p);
        }
    }

    // find product by sku
    public static Product findProduct(ArrayList<Product> products, String sku) {
        for (Product p : products) {
            if (p.getSku().equals(sku)) {
                return p;
            }
        }
        return null;
    }

    // input helpers
    public static int readInt(Scanner input, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(input.nextLine());
                if (value >= 0) return value;
            } catch (Exception e) {}
            System.out.println("Invalid number.");
        }
    }

    public static double readDouble(Scanner input, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = Double.parseDouble(input.nextLine());
                if (value >= 0) return value;
            } catch (Exception e) {}
            System.out.println("Invalid number.");
        }
    }

    public static String readNonBlankString(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = input.nextLine();
            if (!value.trim().isEmpty()) return value;
            System.out.println("Cannot be blank.");
        }
    }

    public static String readSku(Scanner input, ArrayList<Product> products, Product current) {
        while (true) {
            System.out.print("Enter SKU: ");
            String sku = input.nextLine();

            if (!sku.matches("\\d{8,}")) {
                System.out.println("Must be 8+ digits.");
                continue;
            }

            Product existing = findProduct(products, sku);

            if (existing != null && existing != current) {
                System.out.println("SKU already exists.");
                continue;
            }

            return sku;
        }
    }

    public static Date readDate(Scanner input, String prompt) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setLenient(false);

        while (true) {
            try {
                System.out.print(prompt);
                return format.parse(input.nextLine());
            } catch (ParseException e) {
                System.out.println("Invalid date format.");
            }
        }
    }
}
