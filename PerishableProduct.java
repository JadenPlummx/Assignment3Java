/*
 * Name: Jaden Plummer
 * Date: 2026-04-04
 * Description: This class stores information for a perishable product
 *              and inherits from the Product class.
 */

import java.util.Date;

public class PerishableProduct extends Product {

    private Date expiryDate;

    // Default constructor
    public PerishableProduct() {
        super();
        this.expiryDate = new Date();
    }

    // Parameterized constructor
    public PerishableProduct(String sku, String productName, double unitCost, double salePrice,
                             int quantityOnHand, int quantityNeeded, String specialInstructions,
                             Date expiryDate) {
        super(sku, productName, unitCost, salePrice, quantityOnHand, quantityNeeded, specialInstructions);
        setExpiryDate(expiryDate);
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        if (expiryDate == null) {
            throw new IllegalArgumentException("Expiry date cannot be null.");
        }
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return super.toString()
                + "\nExpiry Date: " + expiryDate;
    }
}