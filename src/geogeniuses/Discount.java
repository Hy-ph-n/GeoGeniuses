package geogeniuses;

/**
 * The discount class contains the variables used for examples of discount
 * @author David Bowen
 */
public class Discount {
    
    int discountID;
    String discountCode;
    String discountDescription;
    int discountLevel;
    int inventoryID;
    int discountType;
    double discountPercentage;
    double discountDollarAmount;
    String startDate;
    String expirationDate;
    
    Discount(int discountID, String discountCode, String discountDescription, int discountLevel, int inventoryID, int discountType, double discountPercentage, double discountDollarAmount, String startDate, String expirationDate) {
        this.discountID = discountID;
        this.discountCode = discountCode;
        this.discountDescription = discountDescription;
        this.discountLevel = discountLevel;
        this.inventoryID = inventoryID;
        this.discountType = discountType;
        this.discountPercentage = discountPercentage;
        this.discountDollarAmount = discountDollarAmount;
        this.startDate = startDate;
        this.expirationDate = expirationDate;
    }
    
}
