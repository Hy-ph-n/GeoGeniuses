package geogeniuses;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import java.sql.*;

public class ItemSelection extends State implements ActionListener {

    private ArrayList<JButton> items;

    public ItemSelection(ArrayList<JButton> items) {

        this.items = items;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String itemName = event.getActionCommand();
        try {
            String query = "SELECT InventoryID FROM Inventory WHERE ItemName = '" + itemName + "';";
            PreparedStatement ps = GeoGeniuses.con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            rs.next();
            CustomerView.itemSelected = Integer.parseInt(rs.getString(1)) - 1;
        } catch (SQLException e) {
            System.out.println(e);
        }
        Inventory inventory = LoginView.inventory.get(CustomerView.itemSelected);
        CustomerView.itemsName.setText(inventory.itemName);
        CustomerView.itemsName.setVisible(true);
        CustomerView.itemsDescription.setText(inventory.itemDescription);
        CustomerView.itemsDescription.setVisible(true);
        CustomerView.itemGrainSize.setVisible(false);
        CustomerView.itemGrainShape.setVisible(false);
        CustomerView.itemHeft.setVisible(false);
        int yValue = 300;
        if (inventory.stoneOrGemstone == 0) {
            CustomerView.itemRockOrGem.setLocation(CustomerView.itemRockOrGem.getX(), yValue += 15);
            if (inventory.categoryID == 1) {
                CustomerView.itemRockOrGem.setText("Igneous Rock");
            } else if (inventory.categoryID == 2) {
                CustomerView.itemRockOrGem.setText("Sedimentary Rock");
            } else if (inventory.categoryID == 3) {
                CustomerView.itemRockOrGem.setText("Metamorphic Rock");
            }
            if (inventory.grainSize != null) {
                CustomerView.itemGrainSize.setLocation(CustomerView.itemGrainSize.getX(), yValue += 15);
                if (Integer.parseInt(inventory.grainSize) == 0) {
                    CustomerView.itemGrainSize.setText("Rough Grained Rock");
                } else if (Integer.parseInt(inventory.grainSize) == 0) {
                    CustomerView.itemGrainSize.setText("Fine Grained Rock");
                }
                CustomerView.itemGrainSize.setVisible(true);
            }
            if (inventory.grainShape != null) {
                CustomerView.itemGrainShape.setLocation(CustomerView.itemGrainShape.getX(), yValue += 15);
                CustomerView.itemGrainShape.setText("Grain Shape: " + inventory.grainShape);
                CustomerView.itemGrainShape.setVisible(true);
            }
        } else if (inventory.stoneOrGemstone == 1) {
            CustomerView.itemRockOrGem.setLocation(CustomerView.itemRockOrGem.getX(), yValue += 15);
            if (Integer.parseInt(inventory.semiOrPrecious) == 0) {
                CustomerView.itemRockOrGem.setText("Semi-Precious Gemstone");
            } else if (Integer.parseInt(inventory.semiOrPrecious) == 1) {
                CustomerView.itemRockOrGem.setText("Precious Gemstone");
            }
            CustomerView.itemRockOrGem.setVisible(true);
        }
        CustomerView.itemHardness.setLocation(CustomerView.itemHardness.getX(), yValue += 15);
        CustomerView.itemHardness.setText("Hardness: " + inventory.hardness);
        CustomerView.itemHardness.setVisible(true);
        CustomerView.price.setLocation(CustomerView.price.getX(), yValue += 15);
        CustomerView.price.setText("Price: $" + inventory.retailPrice);
        CustomerView.price.setVisible(true);
        CustomerView.quantity.setLocation(CustomerView.quantity.getX(), yValue += 15);
        if (CustomerView.cart.size() == 0) {
            CustomerView.quantity.setText("Quantity: " + inventory.quantity);
        } else {
            boolean itemFound = false;
            for (int i = 0; i < CustomerView.cart.size(); i++) {
                if (CustomerView.cart.get(i).inventoryID == inventory.inventoryID) {
                    CustomerView.quantity.setText("Quantity: " + (inventory.quantity - CustomerView.cart.get(i).quantity));
                    itemFound = true;
                }
            }
            if (!itemFound) {
                CustomerView.quantity.setText("Quantity: " + inventory.quantity);
            }
        }
        CustomerView.quantity.setVisible(true);
        if (LoginView.currentPerson != 0) {
            CustomerView.addToCart.setVisible(true);
            CustomerView.addToCart.setEnabled(true);
            CustomerView.returnToSearch.setVisible(true);
            CustomerView.searchError.setText("");
            CustomerView.cardNumberEntry.setVisible(false);
            CustomerView.cardNumber.setVisible(false);
            CustomerView.cvvEntry.setVisible(false);
            CustomerView.cardCVV.setVisible(false);
            CustomerView.cardExpireYear.setVisible(false);
            CustomerView.cardExpirationYear.setVisible(false);
            CustomerView.cardExpireMonth.setVisible(false);
            CustomerView.cardExpirationMonth.setVisible(false);
            CustomerView.cardError.setText("");
            CustomerView.cvvError.setText("");
            CustomerView.expirationError.setText("");
            CustomerView.discountEntry.setVisible(false);
            CustomerView.discountCode.setVisible(false);
            CustomerView.discountError.setText("");
            CustomerView.checkout.setVisible(false);
            CustomerView.clearCart.setVisible(false);
            CustomerView.logOut.setVisible(false);
        }
    }

}
