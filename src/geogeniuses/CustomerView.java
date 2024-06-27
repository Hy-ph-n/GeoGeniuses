package geogeniuses;

import com.aspose.pdf.Document;
import com.aspose.pdf.Page;
import com.aspose.pdf.TextFragment;
import java.sql.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.*;

//The customer view is entered by either a customer account or a guest account.
public class CustomerView extends State {

    //
    ArrayList<JButton> items = new ArrayList();

    //The order arraylist will track orders.
    static ArrayList<Cart> cart = new ArrayList();

    //The decimal format is now listed properly.
    DecimalFormat f = new DecimalFormat("#,###.00");

    static JPanel panel = new JPanel(null);
    static JPanel itemDisplay = new JPanel(null);

    static JLabel itemsName;
    static JTextArea itemsDescription;
    static JLabel price;
    static JLabel quantity;

    static JRadioButton confirm;
    static JRadioButton cancel;

    //Items selected is just to ensure the user does not make an order without having bought anything
    static int itemsSelected = 0;
    //Item selected is used for data retrieval
    static int itemSelected = -1;

    static JLabel searchBarEntry;
    static JTextField searchBar;
    static JLabel searchError;

    static JLabel cardNumberEntry;
    static JTextField cardNumber;
    static JLabel cardError;
    static JLabel cvvEntry;
    static JTextField cardCVV;
    static JLabel cvvError;
    static JLabel cardExpireYear;
    static JComboBox cardExpirationYear;
    static JLabel cardExpireMonth;
    static JComboBox cardExpirationMonth;
    static JLabel expirationError;
    boolean cardValid = false;

    static JLabel discountEntry;
    static JTextField discountCode;
    static JLabel discountError;
    boolean discountValid = false;

    int discountID = 0;
    Date startDate = null;
    Date expireDate = null;
    int discountLevel = -1;
    int discountInventoryID = 0;
    int discountType = -1;
    double discountPercent = 0;
    double discountAmount = 0;

    // The current date placed into a variable
    LocalDate currentDate = LocalDate.now();

    static JButton searchButton;
    static JButton igneousButton;
    static JButton sedimentaryButton;
    static JButton metamorphicButton;
    static JButton addToCart;
    static JButton returnToSearch;
    static JButton checkout;
    static JButton logOut;

    ScrollPane inventory;

    CustomerView() {

        Color lightCyan = Color.decode("#DFFDFF");

        jp.setLayout(new BoxLayout(jp, BoxLayout.X_AXIS));

        panel.setBackground(lightCyan);
        panel.setBounds(0, 0, 273, 523);
        jp.add(panel);

        itemsName = new JLabel("");
        itemsName.setBounds(panel.getWidth() / 8, 60, 200, 15);
        itemsName.setHorizontalAlignment(SwingConstants.CENTER);
        itemsName.setVisible(false);
        panel.add(itemsName);

        itemsDescription = new JTextArea("");
        itemsDescription.setBounds(11, 80, 250, 60);
        itemsDescription.setEnabled(false);
        itemsDescription.setDisabledTextColor(Color.black);
        itemsDescription.setLineWrap(true);
        itemsDescription.setWrapStyleWord(true);
        itemsDescription.setBackground(lightCyan);
        itemsDescription.setVisible(false);
        panel.add(itemsDescription);

        price = new JLabel("");
        price.setBounds(panel.getWidth() / 8, 140, 200, 15);
        price.setHorizontalAlignment(SwingConstants.CENTER);
        price.setVisible(false);
        panel.add(price);

        quantity = new JLabel("");
        quantity.setBounds(panel.getWidth() / 8, 165, 200, 15);
        quantity.setHorizontalAlignment(SwingConstants.CENTER);
        quantity.setVisible(false);
        panel.add(quantity);

        //This radio button will check the operation of a user. If they decide to add an order to the cart, they will have to use this button.
        confirm = new JRadioButton("Add to Cart");
        confirm.setBounds(160, 200, 90, 25);
        confirm.setBackground(null);
        confirm.setVisible(false);
        panel.add(confirm);
        //This radio button will check the operation of a user. If they decide not to add an order to the cart, they will have to use this button.
        cancel = new JRadioButton("Don't Add To Cart");
        cancel.setBounds(25, 200, 130, 25);
        cancel.setBackground(null);
        cancel.setVisible(false);
        panel.add(cancel);

        confirm.addActionListener((e) -> {
            if (cancel.isSelected()) {
                cancel.setSelected(false);
            }
        });

        cancel.addActionListener((e) -> {
            if (confirm.isSelected()) {
                confirm.setSelected(false);
            }
        });

        addToCart = new JButton("Add to Cart");
        addToCart.setBounds(155, 400, 100, 50);
        addToCart.setVisible(false);
        addToCart.addActionListener((e) -> {
            if (confirm.isSelected()) {
                boolean itemNotInCart = true;
                itemsSelected++;
                for (int i = 0; i < cart.size(); i++) {
                    Cart cartItem = cart.get(i);
                    if (LoginView.inventory.get(itemSelected).inventoryID == cartItem.inventoryID) {
                        itemNotInCart = false;
                        cartItem.quantity++;
                        // Changes the data in the current cart index, the only difference being a change in quantity
                        cart.set(i, cartItem);
                        break;
                    }
                } // If the selected item is not already in the cart, it gets added
                if (itemNotInCart) {
                    cart.add(new Cart(LoginView.inventory.get(itemSelected).inventoryID, LoginView.inventory.get(itemSelected).itemName, LoginView.inventory.get(itemSelected).retailPrice, 1));
                }
            }
        });
        panel.add(addToCart);

        returnToSearch = new JButton("Return");
        returnToSearch.setBounds(30, 400, 100, 50);
        returnToSearch.setVisible(false);
        returnToSearch.addActionListener((e) -> {
            itemSelected = 0;

            itemsName.setVisible(false);
            itemsDescription.setVisible(false);
            price.setVisible(false);
            quantity.setVisible(false);
            confirm.setVisible(false);
            cancel.setVisible(false);
            addToCart.setVisible(false);
            returnToSearch.setVisible(false);
            searchBarEntry.setVisible(true);
            searchBar.setVisible(true);
            searchError.setText("");
            cardNumberEntry.setVisible(true);
            cardNumber.setVisible(true);
            cvvEntry.setVisible(true);
            cardCVV.setVisible(true);
            cardExpireYear.setVisible(true);
            cardExpirationYear.setVisible(true);
            cardExpireMonth.setVisible(true);
            cardExpirationMonth.setVisible(true);
            cardError.setText("");
            cvvError.setText("");
            expirationError.setText("");
            discountEntry.setVisible(true);
            discountCode.setVisible(true);
            discountError.setText("");
            searchButton.setVisible(true);
            igneousButton.setVisible(true);
            sedimentaryButton.setVisible(true);
            metamorphicButton.setVisible(true);
            checkout.setVisible(true);
            logOut.setVisible(true);
        });
        panel.add(returnToSearch);

        itemDisplay = new JPanel(new GridBagLayout());
        itemDisplay.setBounds(panel.getWidth(), 50, 200, 200);
        itemDisplay.setBackground(lightCyan);
        inventory = new ScrollPane(itemDisplay);
        inventory.setBounds(0, 0, 210, 485);
        inventory.setVisible(true);
        jp.add(inventory);

        JLabel help = new JLabel("Help");
        help.setBounds(13, 0, 50, 20);
        help.setForeground(Color.blue);
        panel.add(help);

        help.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                helpSystem();
            }
        });

        searchBarEntry = new JLabel("Search");
        searchBarEntry.setBounds(37, 15, 200, 15);
        panel.add(searchBarEntry);

        searchBar = new JTextField("");
        searchBar.setBounds(37, 32, 200, 20);
        panel.add(searchBar);

        searchError = new JLabel("");
        searchError.setBounds(130, 10, 200, 15);
        searchError.setForeground(Color.red);
        panel.add(searchError);

        searchButton = new JButton("Search");
        searchButton.setBounds(25, 60, 110, 30);
        searchButton.addActionListener((e) -> {
            GridBagConstraints imageLayout = new GridBagConstraints();
            imageLayout.insets = new Insets(2, 2, 2, 2);
            int x = 0;
            int y = 0;
            String n[];
            try {
                if (!con.isClosed()) {
                    //The boolean searchValid prevents the display from being cleared until the result set retrieves at least one value
                    boolean searchValid = false;
                    int l = 0;
                    ItemSelection select = new ItemSelection(items);
                    //Using 'like' means that as long as the character is present, the character being anything from 'Obsidian' to 'a'
                    //then the search will select it.
                    String query = "SELECT ItemName, ItemImage FROM Inventory WHERE ItemName LIKE '%" + searchBar.getText() + "%';";
                    ps = con.prepareStatement(query);
                    ResultSet rs = ps.executeQuery();
                    ResultSetMetaData md = rs.getMetaData();
                    while (rs.next()) {
                        n = new String[md.getColumnCount() + 1];
                        for (int i = 1; i < md.getColumnCount() + 1; i++) {
                            n[i - 1] = rs.getString(i);
                        }

                        //At this point, the search is valid, so we can clear the display once and then leave it alone so it does not constantly clear
                        if (!searchValid) {
                            searchValid = true;
                            itemDisplay.removeAll();
                            items.clear();
                            itemDisplay.repaint();
                        }

                        String itemName = n[0];

                        imageLayout.weightx = 0.5;
                        imageLayout.fill = GridBagConstraints.HORIZONTAL;
                        imageLayout.gridx = x;
                        imageLayout.gridy = y;
                        if (x == 4) {
                            x = 0;
                            y++;
                        } else {
                            x++;
                        }

                        if (n[1] == null) {
                            JButton item = new JButton(itemName);
                            items.add(item);
                            items.get(l).addActionListener(select);
                            itemDisplay.add(items.get(l), imageLayout);
                        } else {
                            try {
                                Blob itemBlob = null;
                                itemBlob = rs.getBlob("ItemImage");
                                byte[] b = itemBlob.getBinaryStream(1, itemBlob.length()).readAllBytes();
                                ItemIcon itemImage = new ItemIcon(b);
                                JButton item = new JButton(itemName, itemImage);
                                items.add(item);
                                items.get(l).addActionListener(select);
                                itemDisplay.add(items.get(l), imageLayout);
                            } catch (IOException ex) {
                                JButton item = new JButton(itemName);
                                items.add(item);
                                items.get(l).addActionListener(select);
                                itemDisplay.add(items.get(l), imageLayout);
                            }
                        }
                        l++;
                    }
                    itemDisplay.validate();
                }
            } catch (SQLException ex) {
                searchError.setText("Invalid Search");
            }
        });
        panel.add(searchButton);

        igneousButton = new JButton("Igneous");
        igneousButton.setBounds(140, 60, 110, 30);
        igneousButton.addActionListener((e) -> {
            GridBagConstraints imageLayout = new GridBagConstraints();
            imageLayout.insets = new Insets(2, 2, 2, 2);
            int x = 0;
            int y = 0;
            String n[];
            try {
                if (!con.isClosed()) {
                    itemDisplay.removeAll();
                    items.clear();
                    itemDisplay.repaint();
                    int l = 0;
                    ItemSelection select = new ItemSelection(items);
                    String query = "SELECT ItemName, ItemImage FROM Inventory WHERE CategoryID = 1;";
                    ps = con.prepareStatement(query);
                    ResultSet rs = ps.executeQuery();
                    ResultSetMetaData md = rs.getMetaData();
                    while (rs.next()) {
                        n = new String[md.getColumnCount() + 1];
                        for (int i = 1; i < md.getColumnCount() + 1; i++) {
                            n[i - 1] = rs.getString(i);
                        }

                        String itemName = n[0];

                        imageLayout.weightx = 0.5;
                        imageLayout.fill = GridBagConstraints.HORIZONTAL;
                        imageLayout.gridx = x;
                        imageLayout.gridy = y;
                        if (x == 4) {
                            x = 0;
                            y++;
                        } else {
                            x++;
                        }

                        if (n[1] == null) {
                            JButton item = new JButton(itemName);
                            items.add(item);
                            items.get(l).addActionListener(select);
                            itemDisplay.add(items.get(l), imageLayout);
                        } else {
                            try {
                                Blob itemBlob = null;
                                itemBlob = rs.getBlob("ItemImage");
                                byte[] b = itemBlob.getBinaryStream(1, itemBlob.length()).readAllBytes();
                                ItemIcon itemImage = new ItemIcon(b);
                                JButton item = new JButton(itemName, itemImage);
                                items.add(item);
                                items.get(l).addActionListener(select);
                                itemDisplay.add(items.get(l), imageLayout);
                            } catch (IOException ex) {
                                JButton item = new JButton(itemName);
                                items.add(item);
                                items.get(l).addActionListener(select);
                                itemDisplay.add(items.get(l), imageLayout);
                            }
                        }
                        l++;
                    }
                    itemDisplay.validate();
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        });
        panel.add(igneousButton);

        sedimentaryButton = new JButton("Sedimentary");
        sedimentaryButton.setBounds(25, 95, 110, 30);
        sedimentaryButton.addActionListener((e) -> {
            GridBagConstraints imageLayout = new GridBagConstraints();
            imageLayout.insets = new Insets(2, 2, 2, 2);
            int x = 0;
            int y = 0;
            String n[];
            try {
                if (!con.isClosed()) {
                    itemDisplay.removeAll();
                    items.clear();
                    itemDisplay.repaint();
                    int l = 0;
                    ItemSelection select = new ItemSelection(items);
                    String query = "SELECT ItemName, ItemImage FROM Inventory WHERE CategoryID = 2;";
                    ps = con.prepareStatement(query);
                    ResultSet rs = ps.executeQuery();
                    ResultSetMetaData md = rs.getMetaData();
                    while (rs.next()) {
                        n = new String[md.getColumnCount() + 1];
                        for (int i = 1; i < md.getColumnCount() + 1; i++) {
                            n[i - 1] = rs.getString(i);
                        }

                        String itemName = n[0];

                        imageLayout.weightx = 0.5;
                        imageLayout.fill = GridBagConstraints.HORIZONTAL;
                        imageLayout.gridx = x;
                        imageLayout.gridy = y;
                        if (x == 4) {
                            x = 0;
                            y++;
                        } else {
                            x++;
                        }

                        if (n[1] == null) {
                            JButton item = new JButton(itemName);
                            items.add(item);
                            items.get(l).addActionListener(select);
                            itemDisplay.add(items.get(l), imageLayout);
                        } else {
                            try {
                                Blob itemBlob = null;
                                itemBlob = rs.getBlob("ItemImage");
                                byte[] b = itemBlob.getBinaryStream(1, itemBlob.length()).readAllBytes();
                                ItemIcon itemImage = new ItemIcon(b);
                                JButton item = new JButton(itemName, itemImage);
                                items.add(item);
                                items.get(l).addActionListener(select);
                                itemDisplay.add(items.get(l), imageLayout);
                            } catch (IOException ex) {
                                JButton item = new JButton(itemName);
                                items.add(item);
                                items.get(l).addActionListener(select);
                                itemDisplay.add(items.get(l), imageLayout);
                            }
                        }
                        l++;
                    }
                    itemDisplay.validate();
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        });
        panel.add(sedimentaryButton);

        metamorphicButton = new JButton("Metamorphic");
        metamorphicButton.setBounds(140, 95, 110, 30);
        metamorphicButton.addActionListener((e) -> {
            GridBagConstraints imageLayout = new GridBagConstraints();
            imageLayout.insets = new Insets(2, 2, 2, 2);
            int x = 0;
            int y = 0;
            String n[];
            try {
                if (!con.isClosed()) {
                    itemDisplay.removeAll();
                    items.clear();
                    itemDisplay.repaint();
                    int l = 0;
                    ItemSelection select = new ItemSelection(items);
                    String query = "SELECT ItemName, ItemImage FROM Inventory WHERE CategoryID = 3;";
                    ps = con.prepareStatement(query);
                    ResultSet rs = ps.executeQuery();
                    ResultSetMetaData md = rs.getMetaData();
                    while (rs.next()) {
                        n = new String[md.getColumnCount() + 1];
                        for (int i = 1; i < md.getColumnCount() + 1; i++) {
                            n[i - 1] = rs.getString(i);
                        }

                        String itemName = n[0];

                        imageLayout.weightx = 0.5;
                        imageLayout.fill = GridBagConstraints.HORIZONTAL;
                        imageLayout.gridx = x;
                        imageLayout.gridy = y;
                        if (x == 4) {
                            x = 0;
                            y++;
                        } else {
                            x++;
                        }

                        if (n[1] == null) {
                            JButton item = new JButton(itemName);
                            items.add(item);
                            items.get(l).addActionListener(select);
                            itemDisplay.add(items.get(l), imageLayout);
                        } else {
                            try {
                                Blob itemBlob = null;
                                itemBlob = rs.getBlob("ItemImage");
                                byte[] b = itemBlob.getBinaryStream(1, itemBlob.length()).readAllBytes();
                                ItemIcon itemImage = new ItemIcon(b);
                                JButton item = new JButton(itemName, itemImage);
                                items.add(item);
                                items.get(l).addActionListener(select);
                                itemDisplay.add(items.get(l), imageLayout);
                            } catch (IOException ex) {
                                JButton item = new JButton(itemName);
                                items.add(item);
                                items.get(l).addActionListener(select);
                                itemDisplay.add(items.get(l), imageLayout);
                            }
                        }
                        l++;
                    }
                    itemDisplay.validate();
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        });
        panel.add(metamorphicButton);

        cardNumberEntry = new JLabel("Card Number");
        cardNumberEntry.setBounds(42, 140, 200, 15);
        panel.add(cardNumberEntry);

        cardNumber = new JTextField("");
        cardNumber.setBounds(42, 157, 200, 20);
        panel.add(cardNumber);

        cvvEntry = new JLabel("Card Verify Value");
        cvvEntry.setBounds(42, 190, 200, 15);
        panel.add(cvvEntry);

        cardCVV = new JTextField("");
        cardCVV.setBounds(42, 207, 200, 20);
        panel.add(cardCVV);

        cardExpireYear = new JLabel("Expire Year");
        cardExpireYear.setBounds(65, 240, 100, 15);
        panel.add(cardExpireYear);

        int yearsForList = currentDate.getYear();

        String[] expireYear = {"" + (yearsForList - 7), "" + (yearsForList - 6), "" + (yearsForList - 5), "" + (yearsForList - 4), "" + (yearsForList - 3), "" + (yearsForList - 2), "" + (yearsForList - 1), "" + yearsForList, "" + (yearsForList + 1), "" + (yearsForList + 2), "" + (yearsForList + 3), "" + (yearsForList + 4), "" + (yearsForList + 5), "" + (yearsForList + 6), "" + (yearsForList + 7), ""};
        cardExpirationYear = new JComboBox(expireYear);
        cardExpirationYear.setBounds(70, 255, 55, 20);
        cardExpirationYear.setSelectedIndex(7);
        panel.add(cardExpirationYear);

        cardExpireMonth = new JLabel("Expire Month");
        cardExpireMonth.setBounds(155, 240, 110, 15);
        panel.add(cardExpireMonth);

        String[] expireMonth = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        cardExpirationMonth = new JComboBox(expireMonth);
        cardExpirationMonth.setBounds(165, 255, 55, 20);
        cardExpirationMonth.setSelectedIndex(0);
        panel.add(cardExpirationMonth);

        cardError = new JLabel("");
        cardError.setBounds(130, 140, 200, 15);
        cardError.setForeground(Color.red);
        panel.add(cardError);

        cvvError = new JLabel("");
        cvvError.setBounds(145, 190, 200, 15);
        cvvError.setForeground(Color.red);
        panel.add(cvvError);

        expirationError = new JLabel("");
        expirationError.setBounds(CustomerView.panel.getWidth() / 8, 275, 200, 15);
        expirationError.setHorizontalAlignment(SwingConstants.CENTER);
        expirationError.setForeground(Color.red);
        panel.add(expirationError);

        discountEntry = new JLabel("Discount Code");
        discountEntry.setBounds(42, 290, 200, 15);
        panel.add(discountEntry);

        discountCode = new JTextField("");
        discountCode.setBounds(42, 307, 200, 20);
        panel.add(discountCode);

        discountError = new JLabel("");
        discountError.setBounds(130, 290, 200, 15);
        discountError.setForeground(Color.red);
        panel.add(discountError);

        checkout = new JButton("Checkout");
        checkout.setBounds(83, 345, 125, 50);
        checkout.addActionListener((e) -> {
            cardError.setText("");
            cvvError.setText("");
            expirationError.setText("");
            discountError.setText("");
            discountValid = false;
            if (itemsSelected > 0) {
                validateCard();
                if (!discountCode.getText().equals("")) {
                    validateDiscount();
                    if (cardValid && discountValid) {
                        Thread createCheckout = new Thread(customerCheckout);
                        createCheckout.start();
                    }
                } else {
                    if (cardValid) {
                        Thread createCheckout = new Thread(customerCheckout);
                        createCheckout.start();
                    }
                }
            }
        });
        panel.add(checkout);

        //This button logs the user out, returning them to the login menu.
        logOut = new JButton("Log Out");
        logOut.setBounds(95, 410, 100, 50);
        logOut.addActionListener((e) -> {
            itemsSelected = 0;
            itemSelected = 0;

            itemsName.setVisible(false);
            itemsName.setBounds(75, 60, 200, 15);
            itemsDescription.setVisible(false);
            itemsDescription.setBounds(11, 80, 250, 60);
            price.setVisible(false);
            price.setBounds(panel.getWidth() / 8, 150, 200, 15);
            quantity.setVisible(false);
            quantity.setBounds(panel.getWidth() / 8, 165, 200, 15);
            confirm.setVisible(false);
            cancel.setVisible(false);
            addToCart.setVisible(false);
            returnToSearch.setVisible(false);
            searchBarEntry.setVisible(true);
            searchBar.setVisible(true);
            searchError.setText("");
            cardNumberEntry.setVisible(true);
            cardNumber.setVisible(true);
            cvvEntry.setVisible(true);
            cardCVV.setVisible(true);
            cardExpireYear.setVisible(true);
            cardExpirationYear.setVisible(true);
            cardExpireMonth.setVisible(true);
            cardExpirationMonth.setVisible(true);
            cardError.setText("");
            cvvError.setText("");
            expirationError.setText("");
            discountEntry.setVisible(true);
            discountCode.setVisible(true);
            discountError.setText("");
            searchButton.setVisible(true);
            igneousButton.setVisible(true);
            sedimentaryButton.setVisible(true);
            metamorphicButton.setVisible(true);
            checkout.setVisible(true);
            logOut.setVisible(true);

            //A switch to the login view
            LoginView.currentPerson = 0;
            jf.setTitle("Login");
            jp.setVisible(false);
            jf.remove(jp);
            jf.add(loginView.jp);
            jf.setBounds(550, 200, 800, 500);
            loginView.jp.setVisible(true);
        });
        panel.add(logOut);
    }

    void validateCard() {
        boolean cardNum = false;
        boolean cvvValid = false;
        boolean cardNotExpired = false;
        int currentYear = currentDate.getYear();
        cardValid = false;

        String visa = "^4[0-9]{12}(?:[0-9]{3})?$";
        String americanExpress = "^3[47][0-9]{0,}$.";
        String masterCard = "^(5[1-5]|222[1-9]|22[3-9]|2[3-6]|27[01]|2720)[0-9]{0,}$.";
        String cvv = "^[0-9]{3,4}$";

        Pattern p = Pattern.compile(visa);
        Matcher m = p.matcher(cardNumber.getText());

        if (!m.matches()) {
            p = Pattern.compile(americanExpress);
            m = p.matcher(cardNumber.getText());
            if (!m.matches()) {
                p = Pattern.compile(masterCard);
                m = p.matcher(cardNumber.getText());
                if (!m.matches()) {
                    cardError.setText("Invalid Card Number");
                } else {
                    cardNum = true;
                }
            } else {
                cardNum = true;
            }
        } else {
            cardNum = true;
        }

        p = Pattern.compile(cvv);
        m = p.matcher(cardCVV.getText());
        if (m.matches()) {
            cvvValid = true;
        } else {
            cvvError.setText("Invalid CVV");
        }

        int year = Integer.valueOf((String) cardExpirationYear.getSelectedItem());

        if (currentYear == year || currentYear < year) {
            cardNotExpired = true;
        } else if (currentYear > year) {
            int yearDiff = currentYear - year;
            if (yearDiff < 5) {
                cardNotExpired = true;
            } else {
                expirationError.setText("Card Expired");
            }
        }

        if (cardNum && cvvValid && cardNotExpired) {
            cardValid = true;
        }
    }

    void validateDiscount() {
        String n[];
        discountID = 0;
        discountValid = true;
        startDate = null;
        expireDate = null;
        discountLevel = -1;
        discountInventoryID = 0;
        discountType = -1;
        discountPercent = 0;
        discountAmount = 0;
        try {
            String query = "SELECT DiscountID, DiscountLevel, InventoryID, DiscountType, DiscountPercentage, DiscountDollarAmount, StartDate, ExpirationDate FROM Discounts WHERE DiscountCode = '" + discountCode.getText() + "';";
            ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                discountID = rs.getInt("DiscountID");
                String start = rs.getString("StartDate");
                if (start != null) {
                    startDate = rs.getDate("StartDate");
                }
                expireDate = rs.getDate("ExpirationDate");
                if (expireDate == null) {
                    discountValid = false;
                    discountError.setText("Bad Expire Date");
                    break;
                }
                String id = rs.getString("InventoryID");
                String level = rs.getString("DiscountLevel");
                if (level != null) {
                    discountLevel = Integer.parseInt(level);
                    if (discountLevel == 1) {
                        if (id != null) {
                            discountInventoryID = Integer.parseInt(id);
                        } else {
                            discountValid = false;
                            discountError.setText("Invalid Discount");
                            break;
                        }
                    }
                } else {
                    discountValid = false;
                    discountError.setText("Invalid Discount");
                    break;
                }
                discountType = rs.getInt("DiscountType");
                String percentDiscounted = rs.getString("DiscountPercentage");
                String amountDiscounted = rs.getString("DiscountDollarAmount");
                if (percentDiscounted != null && discountType == 0) {
                    discountPercent = Double.parseDouble(percentDiscounted);
                }
                if (amountDiscounted != null && discountType == 1) {
                    discountAmount = Double.parseDouble(amountDiscounted);
                }
            }

            int currentYear = currentDate.getYear();
            int currentMonth = currentDate.getMonthValue();
            int currentDay = currentDate.getDayOfMonth();

            Date current = Date.valueOf(LocalDate.of(currentYear, currentMonth, currentDay));

            if (startDate != null) {
                boolean isBetween = current.after(startDate) && current.before(expireDate);
                if (!isBetween) {
                    discountValid = false;
                }
            }

            if (discountLevel != 0) {
                boolean discountAppliable = false;
                for (int i = 0; i < cart.size(); i++) {
                    if (discountInventoryID == cart.get(i).inventoryID) {
                        discountAppliable = true;
                        break;
                    }
                }
                if (!discountAppliable) {
                    discountValid = false;
                    discountError.setText("Not Applicable");
                }
            }
        } catch (SQLException e) {
            discountValid = false;
            discountError.setText("Invalid Code");
            e.printStackTrace();
        }
    }

    void updateData() {
        GridBagConstraints imageLayout = new GridBagConstraints();
        imageLayout.insets = new Insets(2, 2, 2, 2);
        int x = 0;
        int y = 0;
        String e[];
        try {
            if (!con.isClosed()) {
                itemDisplay.removeAll();
                items.clear();
                itemDisplay.repaint();
                int l = 0;
                ItemSelection select = new ItemSelection(items);
                String query = "SELECT ItemName, ItemImage FROM Inventory;";
                ps = con.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                ResultSetMetaData md = rs.getMetaData();
                while (rs.next()) {
                    e = new String[md.getColumnCount() + 1];
                    for (int i = 1; i < md.getColumnCount() + 1; i++) {
                        e[i - 1] = rs.getString(i);
                    }

                    String itemName = e[0];

                    imageLayout.weightx = 0.5;
                    imageLayout.fill = GridBagConstraints.HORIZONTAL;
                    imageLayout.gridx = x;
                    imageLayout.gridy = y;
                    if (x == 4) {
                        x = 0;
                        y++;
                    } else {
                        x++;
                    }

                    if (e[1] == null) {
                        JButton item = new JButton(itemName);
                        items.add(item);
                        items.get(l).addActionListener(select);
                        itemDisplay.add(items.get(l), imageLayout);
                    } else {
                        try {
                            Blob itemBlob = null;
                            itemBlob = rs.getBlob("ItemImage");
                            byte[] b = itemBlob.getBinaryStream(1, itemBlob.length()).readAllBytes();
                            ItemIcon itemImage = new ItemIcon(b);
                            JButton item = new JButton(itemName, itemImage);
                            items.add(item);
                            items.get(l).addActionListener(select);
                            itemDisplay.add(items.get(l), imageLayout);
                        } catch (IOException ex) {
                            JButton item = new JButton(itemName);
                            items.add(item);
                            items.get(l).addActionListener(select);
                            itemDisplay.add(items.get(l), imageLayout);
                        }
                    }
                    l++;
                }
                itemDisplay.validate();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    Runnable customerCheckout = () -> {
        try {
            double itemSubtotal = 0;
            double discount = 0;
            double costBeforeTax = 0;
            double taxValue = 0;
            double total = 0;
            final double TAX = 0.0825;

            StringBuilder sb = new StringBuilder();
            sb.append("<html>");
            sb.append("<head></head>");
            sb.append("<body>");

            sb.append("<h2>Customer Name: ").append(LoginView.person.get(LoginView.currentPerson).nameFirst + " " + LoginView.person.get(LoginView.currentPerson).nameLast.charAt(0)).append(".</h2>");
            sb.append("<h2>Phone Number:  ").append(LoginView.person.get(LoginView.currentPerson).phonePrimary).append("</h2>");

            sb.append("<table>");

            sb.append("<tr>");
            sb.append("<th style=\"background: #333; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\">Item Name</th>");
            sb.append("<th style=\"background: #333; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\">Cost</th>");
            sb.append("<th style=\"background: #333; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\">Quantity</th>");
            sb.append("<th style=\"background: #333; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\">Total Cost</th>");
            sb.append("</tr>");

            for (int i = 0; i < cart.size(); i++) {
                itemSubtotal += cart.get(i).itemCost * cart.get(i).quantity;
                if (discountValid) {
                    if (discountLevel == 1 && discountInventoryID == cart.get(i).inventoryID) {
                        if (discountType == 0) {
                            discount = (cart.get(i).itemCost * cart.get(i).quantity) * discountPercent;
                        } else if (discountType == 1) {
                            discount = discountAmount;
                        }
                    }
                }
            }

            for (Cart item : cart) {
                sb.append("<tr>");
                sb.append("<td style=\"padding: 6px; border: 1px solid #ccc; text-align: left;\">").append(item.itemName).append("</td>");
                sb.append("<td style=\"padding: 6px; border: 1px solid #ccc; text-align: left;\">$").append(item.itemCost).append("</td>");
                sb.append("<td style=\"padding: 6px; border: 1px solid #ccc; text-align: left;\">").append(item.quantity).append("</td>");
                sb.append("<td style=\"padding: 6px; border: 1px solid #ccc; text-align: left;\">$").append(item.itemCost * item.quantity).append("</td>");
                sb.append("</tr>");
            }

            sb.append("<tr>");
            sb.append("<td style=\"padding: 6px; border: 1px solid #ccc; text-align: left;\"></td>");
            sb.append("<td style=\"padding: 6px; border: 1px solid #ccc; text-align: left;\"></td>");
            sb.append("<td style=\"padding: 6px; border: 1px solid #ccc; text-align: left;\">Item Subtotal:</td>");
            sb.append("<td style=\"padding: 6px; border: 1px solid #ccc; text-align: left;\">$").append(f.format(itemSubtotal)).append("</td>");
            sb.append("</tr>");

            if (discountValid) {
                if (discountLevel == 0) {
                    if (discountType == 0) {
                        discount = itemSubtotal * discountPercent;
                    } else if (discountType == 1) {
                        discount = discountAmount;
                    }
                }

                discount = itemSubtotal - discount;

                sb.append("<tr>");
                sb.append("<td style=\"padding: 6px; border: 1px solid #ccc; text-align: left;\">Discount</td>");
                sb.append("<td style=\"padding: 6px; border: 1px solid #ccc; text-align: left;\"></td>");
                if (discountLevel == 0) {
                    sb.append("<td style=\"padding: 6px; border: 1px solid #ccc; text-align: left;\">").append(f.format(discountPercent * 100)).append("%</td>");
                    sb.append("<td style=\"padding: 6px; border: 1px solid #ccc; text-align: left;\">-$").append(f.format(itemSubtotal * discountPercent)).append("</td>");
                } else if (discountType == 1) {
                    sb.append("<td style=\"padding: 6px; border: 1px solid #ccc; text-align: left;\">$").append(f.format(discountAmount)).append("</td>");
                    sb.append("<td style=\"padding: 6px; border: 1px solid #ccc; text-align: left;\">-$").append(f.format(discountAmount)).append("</td>");
                }
                sb.append("</tr>");

                sb.append("<tr>");
                sb.append("<td style=\"padding: 6px; border: 1px solid #ccc; text-align: left;\"></td>");
                sb.append("<td style=\"padding: 6px; border: 1px solid #ccc; text-align: left;\"></td>");
                sb.append("<td style=\"padding: 6px; border: 1px solid #ccc; text-align: left;\">Discount Subtotal:</td>");
                sb.append("<td style=\"padding: 6px; border: 1px solid #ccc; text-align: left;\">$").append(f.format(discount)).append("</td>");
                sb.append("</tr>");

                costBeforeTax = discount;
            } else {
                costBeforeTax = itemSubtotal;
            }

            //Uses items discount to ensure all values are accurate
            taxValue = costBeforeTax * TAX;

            sb.append("<tr>");
            sb.append("<td style=\"padding: 6px; border: 1px solid #ccc; text-align: left;\"></td>");
            sb.append("<td style=\"padding: 6px; border: 1px solid #ccc; text-align: left;\"></td>");
            sb.append("<td style=\"padding: 6px; border: 1px solid #ccc; text-align: left;\">TAX (").append(TAX * 100).append("%)</td>");
            sb.append("<td style=\"padding: 6px; border: 1px solid #ccc; text-align: left;\">$").append(f.format(taxValue)).append("</td>");
            sb.append("</tr>");

            total = costBeforeTax + taxValue;

            sb.append("<tr>");
            sb.append("<td style=\"padding: 6px; border: 1px solid #ccc; text-align: left;\"></td>");
            sb.append("<td style=\"padding: 6px; border: 1px solid #ccc; text-align: left;\"></td>");
            sb.append("<td style=\"padding: 6px; border: 1px solid #ccc; text-align: left;\">Total:</td>");
            sb.append("<td style=\"padding: 6px; border: 1px solid #ccc; text-align: left;\">$").append(f.format(total)).append("</td>");
            sb.append("</tr>");

            sb.append("</table>");
            sb.append("</body>");
            sb.append("</html>");

            String html = sb.toString();

            String documentsDirectory = System.getProperty("user.home") + File.separator + "Documents";
            String targetDirectory = documentsDirectory + File.separator + "GeoGeniuses";
            File directory = new File(targetDirectory);
            if (!directory.exists()) {
                boolean created = directory.mkdirs();
                if (!created) {
                    System.out.println("Directory Not Established: " + targetDirectory);
                    return;
                }
            }

            String filePath = targetDirectory + File.separator + "purchase.html";

            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
                writer.write(html);
                writer.close();

                File file = new File(filePath);
                URI uri = file.toURI();
                Desktop.getDesktop().browse(uri);
                //Files.deleteIfExists(Paths.get(filePath));

                Date dateNow = Date.valueOf(currentDate);

                String statement = "INSERT INTO Orders (DiscountID, PersonID, EmployeeID, OrderDate, CC_Number, ExpDate, CCV)"
                        + "VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = con.prepareStatement(statement);
                if (discountID == 0) {
                    preparedStatement.setString(1, null);
                } else {
                    preparedStatement.setInt(1, discountID);
                }
                preparedStatement.setInt(2, LoginView.currentPerson);
                preparedStatement.setString(3, null);
                preparedStatement.setDate(4, dateNow);
                preparedStatement.setString(5, cardNumber.getText());
                preparedStatement.setString(6, cardExpirationMonth.getSelectedItem() + "/" + cardExpirationYear.getSelectedItem());
                preparedStatement.setString(7, cardCVV.getText() + "");
                preparedStatement.execute();

                String query = "SELECT OrderID FROM Orders ORDER BY OrderID DESC LIMIT 1";
                ps = con.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                rs.next();
                int orderID = rs.getInt("OrderID");

                for (int i = 0; i < cart.size(); i++) {
                    query = "SELECT Quantity FROM Inventory WHERE InventoryID = " + cart.get(i).inventoryID + ";";
                    ps = con.prepareStatement(query);
                    rs = ps.executeQuery();
                    rs.next();
                    int quantity = rs.getInt("Quantity");
                    
                    statement = "UPDATE Inventory SET Quantity = " + (quantity - cart.get(i).quantity) + " WHERE InventoryID = " + cart.get(i).inventoryID + ";";
                    preparedStatement = con.prepareStatement(statement);
                    preparedStatement.execute();

                    statement = "INSERT INTO OrderDetails (OrderID, InventoryID, DiscountID, Quantity)"
                            + "VALUES (?, ?, ?, ?)";
                    preparedStatement = con.prepareStatement(statement);
                    preparedStatement.setInt(1, orderID);
                    preparedStatement.setInt(2, cart.get(i).inventoryID);
                    if (discountValid) {
                        if (discountLevel == 0) {
                            preparedStatement.setInt(3, discountID);
                        } else if (discountLevel == 1) {
                            if (discountInventoryID == cart.get(i).inventoryID) {
                                preparedStatement.setInt(3, discountID);
                            } else {
                                preparedStatement.setString(3, null);
                            }
                        } else {
                            preparedStatement.setString(3, null);
                        }
                    } else {
                        preparedStatement.setString(3, null);
                    }
                    preparedStatement.setInt(4, cart.get(i).quantity);
                    preparedStatement.execute();
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    };

    void helpSystem() {
        Document document = new Document();

        Page page = document.getPages().add();

        page.getParagraphs().add(new TextFragment("Searching - Please enter a valid item descriptor and press 'search' to search"
                + "\n\nIgneous Button - The igneous button finds all rocks classified as igneous"
                + "\n\nSedimentary Button - The igneous button finds all rocks classified as sedimentary"
                + "\n\nMetamorphic Button - The igneous button finds all rocks classified as metamorphic"
                + "\n\nLogout Button - The log out button returns the user to the login menu"
                + "\n\nAny Button on Right Side - Showcases relevant data about the stone including the name, basic description, and current quantity"
                + "\n\nConfirm/Cancel - A preventative measure to keep the user from automatically buying $500 rubies when trying to observe them"
                + "\n\nAdd to Cart - A button allowing the user to add an item to their cart (confirm button required), can be clicked multiple times"
                + "\n\nReturn - A button that returns the user to search/purchase options"
                + "\n\nCard Data - You must enter a valid card number (Visa/Mastercard) and Card Verification Value (CVV), the card year cannot be five years expired"
                + "\n\nDiscount Code (NOT REQUIRED) - You must enter a valid discount code if you wish to save money"));

        document.save("help.pdf");

        try {
            File pdfFile = new File("help.pdf");
            Desktop.getDesktop().open(pdfFile);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
