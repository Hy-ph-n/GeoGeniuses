package geogeniuses;

import java.util.Date;

public class Orders {
    
    int orderID;
    int discountID;
    int personID;
    int managerID;
    String orderDate;
    long ccNumber;
    String expDate;
    int ccv;
    
    Orders(int orderID, int discountID, int personID, int managerID, String orderDate, long ccNumber, String expDate, int ccv) {
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
