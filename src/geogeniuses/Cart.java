package geogeniuses;

public class Cart {
    
    int inventoryID;
    String itemName;
    double itemCost;
    int quantity;
    
    Cart(int inventoryID, String itemName, double itemCost, int quantity) {
        this.inventoryID = inventoryID;
        this.itemName = itemName;
        this.itemCost = itemCost;
        this.quantity = quantity;
    }
    
}
