package geogeniuses;

public class Inventory {

    int inventoryID;
    String itemName;
    String itemDescription;
    int categoryID;
    int stoneOrGemstone;
    String grainSize;
    String grainShape;
    String heft;
    String semiOrPrecious;
    double hardness;
    double retailPrice;
    double cost;
    int quantity;
    int restockThreshold;
    int discontinued;

    Inventory(int inventoryID, String itemName, String itemDescription, int categoryID, int stoneOrGemstone, String grainSize, String grainShape, String heft, String semiOrPrecious, double hardness, double retailPrice, double cost, int quantity, int restockThreshold, int discontinued) {
        this.inventoryID = inventoryID;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.categoryID = categoryID;
        this.stoneOrGemstone = stoneOrGemstone;
        this.grainSize = grainSize;
        this.grainShape = grainShape;
        this.heft = heft;
        this.semiOrPrecious = semiOrPrecious;
        this.hardness = hardness;
        this.retailPrice = retailPrice;
        this.cost = cost;
        this.quantity = quantity;
        this.restockThreshold = restockThreshold;
        this.discontinued = discontinued;
    }

}
