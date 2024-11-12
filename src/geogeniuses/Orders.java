package geogeniuses;

/**
* The orders class contains all the information found in an order. The constructor
* entrusts order with the specified parameters in a specific order.
* @author David Bowen
*/
public class Orders {
    
    int orderID;
    int discountID;
    int personID;
    int managerID;
    String orderDate;
    long ccNumber;
    String expDate;
    int ccv;
    
    /**
    * A method that allows for the creation of the 'Orders' ArrayList.
    * @param orderID References this order's unique id in the database
    * @param discountID References the discount code used with this order, if any, in the database
    * @param personID References the person who made the order in the database
    * @param managerID References the manager who assisted with this order, if any, in the database
    * @param orderDate The order date is when the order took place
    * @param ccNumber The credit card number associated with the purchase
    * @param expDate The expiration date for the credit card
    * @param ccv The security code for the credit card
    */
    public Orders(int orderID, int discountID, int personID, int managerID, String orderDate, long ccNumber, String expDate, int ccv) {
        this.orderID = orderID;
        this.discountID = discountID;
        this.personID = personID;
        this.managerID = managerID;
        this.orderDate = orderDate;
        this.ccNumber = ccNumber;
        this.expDate = expDate;
        this.ccv = ccv;
    }
}
