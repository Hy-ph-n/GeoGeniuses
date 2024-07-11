package geogeniuses;

import java.util.Date;

public class Discount {
    
    int discountID;
    String discountCode;
    int discountLevel;
    int inventoryID;
    int discountType;
    double discountPercentage;
    double discountDollarAmount;
    Date startDate;
    Date expirationDate;
    
    Discount(int discountID, String discountCode, int discountLevel, int inventoryID, int discountType, double discountPercentage, double discountDollarAmount, Date startDate, Date expirationDate) {
        this.discountID = discountID;
        this.discountCode = discountCode;
        this.discountLevel = discountLevel;
        this.inventoryID = inventoryID;
        this.discountType = discountType;
        this.discountPercentage = discountPercentage;
        this.discountDollarAmount = discountDollarAmount;
        this.startDate = startDate;
        this.expirationDate = expirationDate;
    }
    
}
