package geogeniuses;

/**
 * The inventory class contains all data that matches with individual items
 * @author David Bowen
 */
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
    double stoneSize;
    double stoneWeight;
    double retailPrice;
    double cost;
    int quantity;
    int restockThreshold;
    byte[] itemImage;

    Inventory(int inventoryID, String itemName, String itemDescription, int categoryID, int stoneOrGemstone, String grainSize, String grainShape, String heft, String semiOrPrecious, double hardness, double stoneSize, double stoneWeight, double retailPrice, double cost, int quantity, int restockThreshold, byte[] itemImage) {
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
        this.stoneSize = stoneSize;
        this.stoneWeight = stoneWeight;
        this.retailPrice = retailPrice;
        this.cost = cost;
        this.quantity = quantity;
        this.restockThreshold = restockThreshold;
        this.itemImage = itemImage;
    }

}
