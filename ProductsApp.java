/*
 * Name: Jaden Plummer
 * Date: 2026-04-04
 * Description: This program simulates a small retail store by allowing
 *              the user to create, edit, delete, and display products.
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
                    System.out.println("Exiting program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid menu option. Please try again.");
            }

            System.out.println();

        } while (choice != 7);

        input.close();
    }

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

    public static void createProduct(Scanner input, ArrayList<Product> products) {
        try {
            String sku = readSku(input, products, null);
            String productName = readNonBlankString(input, "Enter product name: ");
            double unitCost = readDouble(input, "Enter unit cost: ");
            double salePrice = readDouble(input, "Enter sale price: ");
            int quantityOnHand = readInt(input, "Enter quantity on hand: ");
            int quantityNeeded = readInt(input, "Enter quantity needed: ");

            System.out.print("Enter special instructions: ");
            String specialInstructions = input.nextLine();

            Product product = new Product(sku, productName, unitCost, salePrice,
                    quantityOnHand, quantityNeeded, specialInstructions);

            products.add(product);
            System.out.println("Product created successfully.");

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void createPerishableProduct(Scanner input, ArrayList<Product> products) {
        try {
            String sku = readSku(input, products, null);
            String productName = readNonBlankString(input, "Enter product name: ");
            double unitCost = readDouble(input, "Enter unit cost: ");
            double salePrice = readDouble(input, "Enter sale price: ");
            int quantityOnHand = readInt(input, "Enter quantity on hand: ");
            int quantityNeeded = readInt(input, "Enter quantity needed: ");

            System.out.print("Enter special instructions: ");
            String specialInstructions = input.nextLine();

            Date expiryDate = readDate(input, "Enter expiry date (yyyy-MM-dd): ");

            PerishableProduct product = new PerishableProduct(sku, productName, unitCost, salePrice,
                    quantityOnHand, quantityNeeded, specialInstructions, expiryDate);

            products.add(product);
            System.out.println("Perishable product created successfully.");

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void editProductBySku(Scanner input, ArrayList<Product> products) {
        System.out.print("Enter SKU to edit: ");
        String sku = input.nextLine();

        Product product = findProductBySku(products, sku);

        if (product == null) {
            System.out.println("No product found with that SKU.");
            return;
        }

        try {
            String newSku = readSku(input, products, product);
            String productName = readNonBlankString(input, "Enter new product name: ");
            double unitCost = readDouble(input, "Enter new unit cost: ");
            double salePrice = readDouble(input, "Enter new sale price: ");
            int quantityOnHand = readInt(input, "Enter new quantity on hand: ");
            int quantityNeeded = readInt(input, "Enter new quantity needed: ");

            System.out.print("Enter new special instructions: ");
            String specialInstructions = input.nextLine();

            product.setSku(newSku);
            product.setProductName(productName);
            product.setUnitCost(unitCost);
            product.setSalePrice(salePrice);
            product.setQuantityOnHand(quantityOnHand);
            product.setQuantityNeeded(quantityNeeded);
            product.setSpecialInstructions(specialInstructions);

            if (product instanceof PerishableProduct) {
                Date expiryDate = readDate(input, "Enter new expiry date (yyyy-MM-dd): ");
                ((PerishableProduct) product).setExpiryDate(expiryDate);
            }

            System.out.println("Product updated successfully.");

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void deleteProductBySku(Scanner input, ArrayList<Product> products) {
        System.out.print("Enter SKU to delete: ");
        String sku = input.nextLine();

        Product product = findProductBySku(products, sku);

        if (product == null) {
            System.out.println("No product found with that SKU.");
            return;
        }

        products.remove(product);
        System.out.println("Product deleted successfully.");
    }

    public static void displayProductBySku(Scanner input, ArrayList<Product> products) {
        System.out.print("Enter SKU to display: ");
        String sku = input.nextLine();

        Product product = findProductBySku(products, sku);

        if (product == null) {
            System.out.println("No product found with that SKU.");
        } else {
            System.out.println();
            System.out.println(product);
        }
    }

    public static void displayAllProducts(ArrayList<Product> products) {
        if (products.isEmpty()) {
            System.out.println("There are no products to display.");
            return;
        }

        for (int i = 0; i < products.size(); i++) {
            System.out.println("----- Product " + (i + 1) + " -----");
            System.out.println(products.get(i));
            System.out.println();
        }
    }

    public static Product findProductBySku(ArrayList<Product> products, String sku) {
        for (Product product : products) {
            if (product.getSku().equals(sku)) {
                return product;
            }
        }
        return null;
    }

    public static String readSku(Scanner input, ArrayList<Product> products, Product currentProduct) {
        while (true) {
            System.out.print("Enter SKU (8 or more digits): ");
            String sku = input.nextLine();

            if (!sku.matches("\\d{8,}")) {
                System.out.println("SKU must contain 8 or more digits.");
                continue;
            }

            Product existingProduct = findProductBySku(products, sku);

            if (existingProduct != null && existingProduct != currentProduct) {
                System.out.println("That SKU already exists. Enter a different SKU.");
                continue;
            }

            return sku;
        }
    }

    public static String readNonBlankString(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = input.nextLine();

            if (!value.trim().isEmpty()) {
                return value;
            }

            System.out.println("This field cannot be blank.");
        }
    }

    public static int readInt(Scanner input, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(input.nextLine());

                if (value < 0) {
                    System.out.println("Value must be 0 or greater.");
                } else {
                    return value;
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    public static double readDouble(Scanner input, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = Double.parseDouble(input.nextLine());

                if (value < 0) {
                    System.out.println("Value must be 0 or greater.");
                } else {
                    return value;
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid decimal number.");
            }
        }
    }

    public static Date readDate(Scanner input, String prompt) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);

        while (true) {
            try {
                System.out.print(prompt);
                String dateInput = input.nextLine();
                return dateFormat.parse(dateInput);

            } catch (ParseException e) {
                System.out.println("Please enter the date in yyyy-MM-dd format.");
            }
        }
    }
}