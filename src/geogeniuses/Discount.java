package geogeniuses;

/**
* The discount class contains the variables used for examples of discount as described
* in {@link #Discount(int, String, String, int, int, int, double, double, String, String)}.
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
    
    /**
    * A method that allows for the creation of the 'Discount' ArrayList.
    * @param discountID References the discount's unique id in the database
    * @param discountCode The code the user must enter to access the discount
    * @param discountDescription A description about the discount
    * @param discountLevel The level of the discount, the levels are cart (total purchase) or item
    * @param inventoryID The inventoryID references the associated item if it is an item level purchase
    * @param discountType The discount type determines if it reduces the price through a division and subtraction process or just a subtraction process
    * @param discountPercentage If the discount type is percentage, the price is reduced by the percentage
    * @param discountDollarAmount If the discount type is amount, the amount is subtracted from the price
    * @param startDate The start date determines when the discount starts being valid. It is not required.
    * @param expirationDate The expiration date determines when the discount stops being valid. It is required.
    */
    public Discount(int discountID, String discountCode, String discountDescription, int discountLevel, int inventoryID, int discountType, double discountPercentage, double discountDollarAmount, String startDate, String expirationDate) {
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
