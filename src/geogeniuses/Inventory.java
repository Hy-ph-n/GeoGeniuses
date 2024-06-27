package geogeniuses;

public class Inventory {

    int inventoryID;
    String itemName;
    String itemDescription;
    int categoryID;
    double retailPrice;
    double cost;
    int quantity;
    int restockThreshold;
    byte[] itemImage;
    int discontinued;

    Inventory(int inventoryID, String itemName, String itemDescription, int categoryID, double retailPrice, double cost, int quantity, int restockThreshold, byte[] itemImage, int discontinued) {
        this.inventoryID = inventoryID;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.categoryID = categoryID;
        this.retailPrice = retailPrice;
        this.cost = cost;
        this.quantity = quantity;
        this.restockThreshold = restockThreshold;
        this.itemImage = itemImage;
        this.discontinued = discontinued;
    }

}
