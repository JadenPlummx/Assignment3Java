/*
 * Name: Jaden Plummer
 * Date: 2026-04-04
 * Description: Stores product information and validates inputs.
 */

public class Product {

    // product details
    private String sku;
    private String productName;
    private double unitCost;
    private double salePrice;
    private int quantityOnHand;
    private int quantityNeeded;
    private String specialInstructions;

    // default values if nothing is provided
    public Product() {
        this.sku = "00000000";
        this.productName = "Unknown Product";
        this.unitCost = 0.0;
        this.salePrice = 0.0;
        this.quantityOnHand = 0;
        this.quantityNeeded = 0;
        this.specialInstructions = "None";
    }

    // constructor with values
    public Product(String sku, String productName, double unitCost, double salePrice,
                   int quantityOnHand, int quantityNeeded, String specialInstructions) {

        // use setters so validation still happens
        setSku(sku);
        setProductName(productName);
        setUnitCost(unitCost);
        setSalePrice(salePrice);
        setQuantityOnHand(quantityOnHand);
        setQuantityNeeded(quantityNeeded);
        setSpecialInstructions(specialInstructions);
    }

    // SKU must be 8+ digits
    public final String getSku() {
        return sku;
    }

    public final void setSku(String sku) {
        if (sku == null || !sku.matches("\\d{8,}")) {
            throw new IllegalArgumentException("SKU must contain 8 or more digits.");
        }
        this.sku = sku;
    }

    // name can't be empty
    public final String getProductName() {
        return productName;
    }

    public final void setProductName(String productName) {
        if (productName == null || productName.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be blank.");
        }
        this.productName = productName.trim();
    }

    // cost must be 0 or more
    public final double getUnitCost() {
        return unitCost;
    }

    public final void setUnitCost(double unitCost) {
        if (unitCost < 0) {
            throw new IllegalArgumentException("Unit cost must be 0 or greater.");
        }
        this.unitCost = unitCost;
    }

    // price must be 0 or more
    public final double getSalePrice() {
        return salePrice;
    }

    public final void setSalePrice(double salePrice) {
        if (salePrice < 0) {
            throw new IllegalArgumentException("Sale price must be 0 or greater.");
        }
        this.salePrice = salePrice;
    }

    // quantities must be positive
    public final int getQuantityOnHand() {
        return quantityOnHand;
    }

    public final void setQuantityOnHand(int quantityOnHand) {
        if (quantityOnHand < 0) {
            throw new IllegalArgumentException("Quantity must be 0 or greater.");
        }
        this.quantityOnHand = quantityOnHand;
    }

    public final int getQuantityNeeded() {
        return quantityNeeded;
    }

    public final void setQuantityNeeded(int quantityNeeded) {
        if (quantityNeeded < 0) {
            throw new IllegalArgumentException("Quantity must be 0 or greater.");
        }
        this.quantityNeeded = quantityNeeded;
    }

    // no validation needed
    public final String getSpecialInstructions() {
        return specialInstructions;
    }

    public final void setSpecialInstructions(String specialInstructions) {
        if (specialInstructions == null) {
            this.specialInstructions = "";
        } else {
            this.specialInstructions = specialInstructions;
        }
    }

    // display product info
    @Override
    public String toString() {
        return "SKU: " + sku
                + "\nProduct Name: " + productName
                + "\nUnit Cost: $" + String.format("%.2f", unitCost)
                + "\nSale Price: $" + String.format("%.2f", salePrice)
                + "\nQuantity on hand: " + quantityOnHand
                + "\nQuantity Needed: " + quantityNeeded
                + "\nSpecial Instructions: " + specialInstructions;
    }
}
