package geogeniuses;

/**
* The cart class contains all data found in each customer's cart.
* @author David Bowen
*/
public class Cart {
    
    int inventoryID;
    String itemName;
    double itemCost;
    int quantity;
    
    /**
    * A method that allows for the creation of the 'Cart' ArrayList.
    * @param inventoryID The id of the specific item in the cart, associated with the database's exact id
    * @param itemName The name of the specific item in the cart
    * @param itemCost The cost of the specific item in the cart
    * @param quantity The amount of the item in the cart. If you try to add an item multiple times, this goes up
    */
    public Cart(int inventoryID, String itemName, double itemCost, int quantity) {
        this.inventoryID = inventoryID;
        this.itemName = itemName;
        this.itemCost = itemCost;
        this.quantity = quantity;
    }
    
}
