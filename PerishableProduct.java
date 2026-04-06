/*
 * Name: Jaden Plummer
 * Date: 2026-04-04
 * Description: Extends Product and adds an expiry date.
 */

import java.util.Date;

public class PerishableProduct extends Product {

    private Date expiryDate;

    // default constructor
    public PerishableProduct() {
        super();
        this.expiryDate = new Date();
    }

    // constructor with expiry date
    public PerishableProduct(String sku, String productName, double unitCost, double salePrice,
                             int quantityOnHand, int quantityNeeded, String specialInstructions,
                             Date expiryDate) {

        super(sku, productName, unitCost, salePrice, quantityOnHand, quantityNeeded, specialInstructions);
        setExpiryDate(expiryDate);
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    // expiry date cannot be null
    public void setExpiryDate(Date expiryDate) {
        if (expiryDate == null) {
            throw new IllegalArgumentException("Expiry date cannot be null.");
        }
        this.expiryDate = expiryDate;
    }

    // include expiry date in display
    @Override
    public String toString() {
        return super.toString()
                + "\nExpiry Date: " + expiryDate;
    }
}
