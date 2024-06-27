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
        CustomerView.itemsName.setText(LoginView.inventory.get(CustomerView.itemSelected).itemName);
        CustomerView.itemsName.setVisible(true);
        CustomerView.itemsDescription.setText(LoginView.inventory.get(CustomerView.itemSelected).itemDescription);
        CustomerView.itemsDescription.setVisible(true);
        CustomerView.price.setText("Price: $" + LoginView.inventory.get(CustomerView.itemSelected).retailPrice);
        CustomerView.price.setVisible(true);
        CustomerView.quantity.setText("Quantity: " + LoginView.inventory.get(CustomerView.itemSelected).quantity);
        CustomerView.quantity.setVisible(true);
        if (LoginView.currentPerson != 0) {
            CustomerView.confirm.setVisible(true);
            CustomerView.cancel.setVisible(true);
            CustomerView.addToCart.setVisible(true);
            CustomerView.returnToSearch.setVisible(true);
            CustomerView.searchBarEntry.setVisible(false);
            CustomerView.searchBar.setVisible(false);
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
            CustomerView.searchButton.setVisible(false);
            CustomerView.igneousButton.setVisible(false);
            CustomerView.sedimentaryButton.setVisible(false);
            CustomerView.metamorphicButton.setVisible(false);
            CustomerView.checkout.setVisible(false);
            CustomerView.logOut.setVisible(false);
            CustomerView.confirm.setSelected(false);
            CustomerView.cancel.setSelected(true);
        }
    }

}
