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

    /**
    * The inventory method contains many variables, some of which only apply to
    * one of two types of rocks.
    * @param inventoryID References this item's unique inventoryID in the database
    * @param itemName Contains the item's name
    * @param itemDescription Contains the item's description
    * @param categoryID Contains the category id associated with the item. 1 is Igneous, 2 is Sedimentary, 3 is Metamorphic
    * @param stoneOrGemstone Determines whether the item is a stone or gemstone, 0 is stone, 1 is gemstone
    * @param grainSize Describes the grain size of a stone
    * @param grainShape Describes the shape of the stone's grains
    * @param heft Describes a gemstone's apparent weight
    * @param semiOrPrecious Describes a gemstone's relative value
    * @param hardness Describes the resistance to breakage
    * @param stoneSize Determines the size of the item, unit differing if stone or gemstone
    * @param stoneWeight Describes a rock or gemstone's actual weight, unit differing depending on type
    * @param retailPrice Describes the average value of the item
    * @param cost Describes the price it is being sold for, higher than retail to make a profit
    * @param quantity Describes how many of the item are currently in stock
    * @param restockThreshold The threshold at which managers are notified about a need to restock
    * @param itemImage The image of the associated item
    */
    public Inventory(int inventoryID, String itemName, String itemDescription, int categoryID, int stoneOrGemstone, String grainSize, String grainShape, String heft, String semiOrPrecious, double hardness, double stoneSize, double stoneWeight, double retailPrice, double cost, int quantity, int restockThreshold, byte[] itemImage) {
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
