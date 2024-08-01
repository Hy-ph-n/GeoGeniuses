package geogeniuses;

public class OrderDetails {
    
    int orderID;
    int inventoryID;
    int discountID;
    int quantity;
    
    OrderDetails(int orderID, int inventoryID, int discountID, int quantity) {
        this.orderID = orderID;
        this.inventoryID = inventoryID;
        this.discountID = discountID;
        this.quantity = quantity;
    }
}
