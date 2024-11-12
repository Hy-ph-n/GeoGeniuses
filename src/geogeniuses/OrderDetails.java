package geogeniuses;

/**
* The order details class contains all variables associated with the details of
* an individual order.
* @author David Bowen
*/
public class OrderDetails {
    
    int orderID;
    int inventoryID;
    int discountID;
    int quantity;
    
    /**
    * The OrderDetails constructor takes integer variables correlating to order information
    * @param orderID References the id of the order
    * @param inventoryID References the id of the individual item this instance of orderdetails covers
    * @param discountID References the discount applied to the item
    * @param quantity References the amount of the item being purchased
    */
    public OrderDetails(int orderID, int inventoryID, int discountID, int quantity) {
        this.orderID = orderID;
        this.inventoryID = inventoryID;
        this.discountID = discountID;
        this.quantity = quantity;
    }
}
