package geogeniuses;

import com.aspose.pdf.Document;
import com.aspose.pdf.Page;
import com.aspose.pdf.TextFragment;
import java.sql.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

//The customer view is entered by either a customer account or a guest account.
public class CustomerView extends State {

    ArrayList<JButton> items = new ArrayList();

    //The order arraylist will track orders.
    static ArrayList<Cart> cart = new ArrayList();

    //The decimal format is now listed properly.
    DecimalFormat f = new DecimalFormat("#,###.00");

    static JPanel panel = new JPanel(null);
    static JPanel itemDisplay = new JPanel(null);

    static JPanel cartPanel = new JPanel(null);
    JTable cartjt;

    String[] cartCol;
    Object[][] cartData;

    boolean cartVisible = false;

    static JLabel itemsName;
    static JTextArea itemsDescription;
    static JLabel itemRockOrGem;
    static JLabel itemGrainSize;
    static JLabel itemGrainShape;
    static JLabel itemHeft;
    static JLabel itemHardness;
    static JLabel stoneSize;
    static JLabel stoneWeight;
    static JLabel price;
    static JLabel quantity;

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
    static JLabel securityCodeEntry;
    static JTextField cardSecurityCode;
    static JLabel cardSecurityError;
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

    static JButton refreshButton;
    static JButton igneousButton;
    static JButton sedimentaryButton;
    static JButton metamorphicButton;
    static JButton addToCart;
    static JButton returnToSearch;
    static JButton checkout;
    static JButton clearCart;
    static JButton logOut;

    ScrollPane inventory;

    CustomerView() {

        Color lightCyan = Color.decode("#DFFDFF");
        Color thistle = Color.decode("#D5CBE2");
        Color royalPurple = Color.decode("#8062A7");

        jp.setLayout(new BoxLayout(jp, BoxLayout.X_AXIS));

        panel.setBackground(lightCyan);
        panel.setBounds(0, 0, 273, 523);
        panel.setPreferredSize(new Dimension(120, 0));
        jp.add(panel);

        itemsName = new JLabel("");
        itemsName.setBounds(panel.getWidth() / 8, 150, 200, 15);
        itemsName.setHorizontalAlignment(SwingConstants.CENTER);
        itemsName.setVisible(false);
        panel.add(itemsName);

        itemsDescription = new JTextArea("");
        itemsDescription.setBounds(11, 175, 250, 105);
        itemsDescription.setEnabled(false);
        itemsDescription.setDisabledTextColor(Color.black);
        itemsDescription.setLineWrap(true);
        itemsDescription.setWrapStyleWord(true);
        itemsDescription.setBackground(lightCyan);
        itemsDescription.setVisible(false);
        panel.add(itemsDescription);

        itemRockOrGem = new JLabel("");
        itemRockOrGem.setBounds(panel.getWidth() / 8, 265, 200, 15);
        itemRockOrGem.setHorizontalAlignment(SwingConstants.CENTER);
        itemRockOrGem.setVisible(false);
        panel.add(itemRockOrGem);

        itemGrainSize = new JLabel("");
        itemGrainSize.setBounds(panel.getWidth() / 8, 280, 200, 15);
        itemGrainSize.setHorizontalAlignment(SwingConstants.CENTER);
        itemGrainSize.setVisible(false);
        panel.add(itemGrainSize);

        itemGrainShape = new JLabel("");
        itemGrainShape.setBounds(panel.getWidth() / 8, 295, 200, 15);
        itemGrainShape.setHorizontalAlignment(SwingConstants.CENTER);
        itemGrainShape.setVisible(false);
        panel.add(itemGrainShape);

        itemHeft = new JLabel("");
        itemHeft.setBounds(panel.getWidth() / 8, 280, 200, 15);
        itemHeft.setHorizontalAlignment(SwingConstants.CENTER);
        itemHeft.setVisible(false);
        panel.add(itemHeft);

        itemHardness = new JLabel("");
        itemHardness.setBounds(panel.getWidth() / 8, 295, 200, 15);
        itemHardness.setHorizontalAlignment(SwingConstants.CENTER);
        itemHardness.setVisible(false);
        panel.add(itemHardness);

        stoneSize = new JLabel("");
        stoneSize.setBounds(panel.getWidth() / 8, 310, 200, 15);
        stoneSize.setHorizontalAlignment(SwingConstants.CENTER);
        stoneSize.setVisible(false);
        panel.add(stoneSize);

        stoneWeight = new JLabel("");
        stoneWeight.setBounds(panel.getWidth() / 8, 325, 200, 15);
        stoneWeight.setHorizontalAlignment(SwingConstants.CENTER);
        stoneWeight.setVisible(false);
        panel.add(stoneWeight);

        price = new JLabel("");
        price.setBounds(panel.getWidth() / 8, 340, 200, 15);
        price.setHorizontalAlignment(SwingConstants.CENTER);
        price.setVisible(false);
        panel.add(price);

        quantity = new JLabel("");
        quantity.setBounds(panel.getWidth() / 8, 355, 200, 15);
        quantity.setHorizontalAlignment(SwingConstants.CENTER);
        quantity.setVisible(false);
        panel.add(quantity);

        addToCart = new JButton("Add to Cart");
        addToCart.setBounds(140, 410, 100, 50);
        addToCart.setVisible(false);
        addToCart.setBackground(thistle);
        addToCart.addActionListener((e) -> {
            boolean itemNotInCart = true;
            boolean cartChange = false;
            if (!cartVisible) {
                inventory.setPreferredSize(new Dimension(360, 0));
                jp.add(cartPanel);
                cartVisible = true;
                jp.validate();
            }
            itemsSelected++;
            for (int i = 0; i < cart.size(); i++) {
                Cart cartItem = cart.get(i);
                if (LoginView.inventory.get(itemSelected).inventoryID == cartItem.inventoryID) {
                    itemNotInCart = false;
                    if ((LoginView.inventory.get(itemSelected).quantity - cartItem.quantity) > 0) {
                        cartItem.quantity++;
                        // Changes the data in the current cart index, the only difference being a change in quantity
                        cart.set(i, cartItem);
                        quantity.setText("Quantity: " + (LoginView.inventory.get(itemSelected).quantity - cartItem.quantity));
                        cartChange = true;
                        break;
                    } else {
                        addToCart.setEnabled(false);
                    }
                }
            } // If the selected item is not already in the cart, it gets added
            if (itemNotInCart) {
                cart.add(new Cart(LoginView.inventory.get(itemSelected).inventoryID, LoginView.inventory.get(itemSelected).itemName, LoginView.inventory.get(itemSelected).retailPrice, 1));
                quantity.setText("Quantity: " + (LoginView.inventory.get(itemSelected).quantity - 1));
                cartChange = true;
            }
            if (cartChange) {
                cartData = getCartData();
                DefaultTableModel carttable = (DefaultTableModel) cartjt.getModel();
                carttable.setDataVector(cartData, cartCol);
                jp.validate();
            }
        });
        panel.add(addToCart);

        returnToSearch = new JButton("Return");
        returnToSearch.setBounds(25, 410, 100, 50);
        returnToSearch.setVisible(false);
        returnToSearch.setBackground(thistle);
        returnToSearch.addActionListener((e) -> {
            itemSelected = 0;

            itemsName.setVisible(false);
            itemsDescription.setVisible(false);
            itemRockOrGem.setVisible(false);
            itemGrainSize.setVisible(false);
            itemGrainShape.setVisible(false);
            itemHeft.setVisible(false);
            itemHardness.setVisible(false);
            stoneSize.setVisible(false);
            stoneWeight.setVisible(false);
            price.setVisible(false);
            quantity.setVisible(false);
            addToCart.setVisible(false);
            addToCart.setEnabled(true);
            returnToSearch.setVisible(false);
            searchBarEntry.setVisible(true);
            searchBar.setVisible(true);
            searchError.setText("");
            cardNumberEntry.setVisible(true);
            cardNumber.setVisible(true);
            securityCodeEntry.setVisible(true);
            cardSecurityCode.setVisible(true);
            cardExpireYear.setVisible(true);
            cardExpirationYear.setVisible(true);
            cardExpireMonth.setVisible(true);
            cardExpirationMonth.setVisible(true);
            cardError.setText("");
            cardSecurityError.setText("");
            expirationError.setText("");
            discountEntry.setVisible(true);
            discountCode.setVisible(true);
            discountError.setText("");
            refreshButton.setVisible(true);
            igneousButton.setVisible(true);
            sedimentaryButton.setVisible(true);
            metamorphicButton.setVisible(true);
            checkout.setVisible(true);
            clearCart.setVisible(true);
            logOut.setVisible(true);
        });
        panel.add(returnToSearch);

        itemDisplay = new JPanel(new GridBagLayout());
        itemDisplay.setBounds(panel.getWidth(), 50, 200, 200);
        itemDisplay.setBackground(lightCyan);
        inventory = new ScrollPane(itemDisplay);
        inventory.setBounds(0, 0, 210, 485);
        inventory.setVisible(true);
        inventory.setPreferredSize(new Dimension(625, 0));
        jp.add(inventory);

        cartPanel = new JPanel();
        cartPanel.setLayout(null);
        cartPanel.setBounds(0, 0, 267, 485);

        cartjt = new JTable();
        cartCol = new String[]{"Item Name", "Item Qty", "Item Cost"};
        cartData = getCartData();
        cartjt.setModel(new DefaultTableModel(cartData, cartCol));
        cartjt.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        cartjt.setShowGrid(false);
        cartjt.setDefaultEditor(Object.class, null);
        ScrollPane cartjs = new ScrollPane(cartjt);
        cartjs.setBounds(0, 0, cartPanel.getWidth(), cartPanel.getHeight());

        cartPanel.add(cartjs);
        cartPanel.setPreferredSize(new Dimension(120, 0));

        JLabel help = new JLabel("Help");
        help.setBounds(235, 0, 50, 20);
        help.setForeground(royalPurple);
        panel.add(help);

        help.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                helpSystem();
            }
        });

        searchBarEntry = new JLabel("Search");
        searchBarEntry.setBounds(32, 15, 200, 15);
        panel.add(searchBarEntry);

        searchBar = new JTextField("");
        searchBar.setBounds(32, 32, 200, 20);
        panel.add(searchBar);

        searchError = new JLabel("");
        searchError.setBounds(120, 10, 200, 15);
        searchError.setForeground(Color.red);
        panel.add(searchError);

        searchBar.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                GridBagConstraints imageLayout = new GridBagConstraints();
                imageLayout.insets = new Insets(2, 2, 2, 2);
                int x = 0;
                int y = 0;
                String n[];
                itemDisplay.removeAll();
                items.clear();
                itemDisplay.repaint();
                ItemSelection select = new ItemSelection(items);
                String searchText = searchBar.getText().trim().toLowerCase();
                for (int i = 0; i < LoginView.inventory.size(); i++) {
                    String compareName = LoginView.inventory.get(i).itemName.toLowerCase();
                    if (compareName.contains(searchText)) {
                        String itemName = LoginView.inventory.get(i).itemName;

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

                        if (LoginView.inventory.get(i).itemImage == null) {
                            JButton item = new JButton(itemName);
                            item.setBackground(thistle);
                            item.addActionListener(select);
                            items.add(item);
                            itemDisplay.add(item, imageLayout);
                        } else {
                            try {
                                byte[] b = LoginView.inventory.get(i).itemImage;
                                ItemIcon itemIcon = new ItemIcon(b);
                                Image itemImage = itemIcon.getImage().getScaledInstance(itemIcon.getIconWidth() / 17, itemIcon.getIconHeight() / 17, Image.SCALE_SMOOTH);
                                itemIcon = new ItemIcon(itemImage);
                                JButton item = new JButton(itemName, itemIcon);
                                item.setVerticalTextPosition(SwingConstants.BOTTOM);
                                item.setHorizontalTextPosition(SwingConstants.CENTER);
                                item.setBackground(thistle);
                                item.addActionListener(select);
                                items.add(item);
                                itemDisplay.add(item, imageLayout);
                            } catch (Exception ex) {
                                JButton item = new JButton(itemName);
                                item.setBackground(thistle);
                                item.addActionListener(select);
                                items.add(item);
                                itemDisplay.add(item, imageLayout);
                                System.out.println("Invalid Image");
                            }
                        }
                    }
                }
                itemDisplay.validate();
            }
        });

        refreshButton = new JButton("Refresh");
        refreshButton.setBounds(20, 60, 110, 30);
        refreshButton.setBackground(thistle);
        refreshButton.addActionListener((e) -> {
            searchBar.setText("");
            updateData();
        });
        panel.add(refreshButton);

        igneousButton = new JButton("Igneous");
        igneousButton.setBounds(135, 60, 110, 30);
        igneousButton.setBackground(thistle);
        igneousButton.addActionListener((e) -> {
            GridBagConstraints imageLayout = new GridBagConstraints();
            imageLayout.insets = new Insets(2, 2, 2, 2);
            int x = 0;
            int y = 0;
            itemDisplay.removeAll();
            items.clear();
            itemDisplay.repaint();
            ItemSelection select = new ItemSelection(items);
            for (int i = 0; i < LoginView.inventory.size(); i++) {
                if (LoginView.inventory.get(i).categoryID == 1) {
                    String itemName = LoginView.inventory.get(i).itemName;

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

                    if (LoginView.inventory.get(i).itemImage == null) {
                        JButton item = new JButton(itemName);
                        item.setBackground(thistle);
                        item.addActionListener(select);
                        items.add(item);
                        itemDisplay.add(item, imageLayout);
                    } else {
                        try {
                            byte[] b = LoginView.inventory.get(i).itemImage;
                            ItemIcon itemIcon = new ItemIcon(b);
                            Image itemImage = itemIcon.getImage().getScaledInstance(itemIcon.getIconWidth() / 17, itemIcon.getIconHeight() / 17, Image.SCALE_SMOOTH);
                            itemIcon = new ItemIcon(itemImage);
                            JButton item = new JButton(itemName, itemIcon);
                            item.setVerticalTextPosition(SwingConstants.BOTTOM);
                            item.setHorizontalTextPosition(SwingConstants.CENTER);
                            item.setBackground(thistle);
                            item.addActionListener(select);
                            items.add(item);
                            itemDisplay.add(item, imageLayout);
                        } catch (Exception ex) {
                            JButton item = new JButton(itemName);
                            item.setBackground(thistle);
                            item.addActionListener(select);
                            items.add(item);
                            itemDisplay.add(item, imageLayout);
                            System.out.println("Invalid Image");
                        }
                    }
                }
            }
            itemDisplay.validate();
        });
        panel.add(igneousButton);

        sedimentaryButton = new JButton("Sedimentary");
        sedimentaryButton.setBounds(20, 95, 110, 30);
        sedimentaryButton.setBackground(thistle);
        sedimentaryButton.addActionListener((e) -> {
            GridBagConstraints imageLayout = new GridBagConstraints();
            imageLayout.insets = new Insets(2, 2, 2, 2);
            int x = 0;
            int y = 0;
            itemDisplay.removeAll();
            items.clear();
            itemDisplay.repaint();
            ItemSelection select = new ItemSelection(items);
            for (int i = 0; i < LoginView.inventory.size(); i++) {
                if (LoginView.inventory.get(i).categoryID == 2) {
                    String itemName = LoginView.inventory.get(i).itemName;

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

                    if (LoginView.inventory.get(i).itemImage == null) {
                        JButton item = new JButton(itemName);
                        item.setBackground(thistle);
                        item.addActionListener(select);
                        items.add(item);
                        itemDisplay.add(item, imageLayout);
                    } else {
                        try {
                            byte[] b = LoginView.inventory.get(i).itemImage;
                            ItemIcon itemIcon = new ItemIcon(b);
                            Image itemImage = itemIcon.getImage().getScaledInstance(itemIcon.getIconWidth() / 17, itemIcon.getIconHeight() / 17, Image.SCALE_SMOOTH);
                            itemIcon = new ItemIcon(itemImage);
                            JButton item = new JButton(itemName, itemIcon);
                            item.setVerticalTextPosition(SwingConstants.BOTTOM);
                            item.setHorizontalTextPosition(SwingConstants.CENTER);
                            item.setBackground(thistle);
                            item.addActionListener(select);
                            items.add(item);
                            itemDisplay.add(item, imageLayout);
                        } catch (Exception ex) {
                            JButton item = new JButton(itemName);
                            item.setBackground(thistle);
                            item.addActionListener(select);
                            items.add(item);
                            itemDisplay.add(item, imageLayout);
                            System.out.println("Invalid Image");
                        }
                    }
                }
            }
            itemDisplay.validate();
        });
        panel.add(sedimentaryButton);

        metamorphicButton = new JButton("Metamorphic");
        metamorphicButton.setBounds(135, 95, 110, 30);
        metamorphicButton.setBackground(thistle);
        metamorphicButton.addActionListener((e) -> {
            GridBagConstraints imageLayout = new GridBagConstraints();
            imageLayout.insets = new Insets(2, 2, 2, 2);
            int x = 0;
            int y = 0;
            itemDisplay.removeAll();
            items.clear();
            itemDisplay.repaint();
            ItemSelection select = new ItemSelection(items);
            for (int i = 0; i < LoginView.inventory.size(); i++) {
                if (LoginView.inventory.get(i).categoryID == 3) {
                    String itemName = LoginView.inventory.get(i).itemName;

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

                    if (LoginView.inventory.get(i).itemImage == null) {
                        JButton item = new JButton(itemName);
                        item.setBackground(thistle);
                        item.addActionListener(select);
                        items.add(item);
                        itemDisplay.add(item, imageLayout);
                    } else {
                        try {
                            byte[] b = LoginView.inventory.get(i).itemImage;
                            ItemIcon itemIcon = new ItemIcon(b);
                            Image itemImage = itemIcon.getImage().getScaledInstance(itemIcon.getIconWidth() / 17, itemIcon.getIconHeight() / 17, Image.SCALE_SMOOTH);
                            itemIcon = new ItemIcon(itemImage);
                            JButton item = new JButton(itemName, itemIcon);
                            item.setVerticalTextPosition(SwingConstants.BOTTOM);
                            item.setHorizontalTextPosition(SwingConstants.CENTER);
                            item.setBackground(thistle);
                            item.addActionListener(select);
                            items.add(item);
                            itemDisplay.add(item, imageLayout);
                        } catch (Exception ex) {
                            JButton item = new JButton(itemName);
                            item.setBackground(thistle);
                            item.addActionListener(select);
                            items.add(item);
                            itemDisplay.add(item, imageLayout);
                            System.out.println("Invalid Image");
                        }
                    }
                }
            }
            itemDisplay.validate();
        });
        panel.add(metamorphicButton);

        cardNumberEntry = new JLabel("Card Number");
        cardNumberEntry.setBounds(32, 140, 200, 15);
        panel.add(cardNumberEntry);

        cardNumber = new JTextField("");
        cardNumber.setBounds(32, 157, 200, 20);
        panel.add(cardNumber);

        cardNumber.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                validateCard();
            }
        });

        securityCodeEntry = new JLabel("Security Code");
        securityCodeEntry.setBounds(32, 190, 200, 15);
        panel.add(securityCodeEntry);

        cardSecurityCode = new JTextField("");
        cardSecurityCode.setBounds(32, 207, 200, 20);
        panel.add(cardSecurityCode);

        cardSecurityCode.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                validateCard();
            }
        });

        cardExpireYear = new JLabel("Expire Year");
        cardExpireYear.setBounds(60, 240, 100, 15);
        panel.add(cardExpireYear);

        int yearsForList = currentDate.getYear();

        String[] expireYear = {"" + (yearsForList - 7), "" + (yearsForList - 6), "" + (yearsForList - 5), "" + (yearsForList - 4), "" + (yearsForList - 3), "" + (yearsForList - 2), "" + (yearsForList - 1), "" + yearsForList, "" + (yearsForList + 1), "" + (yearsForList + 2), "" + (yearsForList + 3), "" + (yearsForList + 4), "" + (yearsForList + 5), "" + (yearsForList + 6), "" + (yearsForList + 7)};
        cardExpirationYear = new JComboBox(expireYear);
        cardExpirationYear.setBounds(65, 255, 55, 20);
        cardExpirationYear.setSelectedIndex(7);
        panel.add(cardExpirationYear);

        cardExpirationYear.addActionListener((e) -> {
            validateCard();
        });

        cardExpireMonth = new JLabel("Expire Month");
        cardExpireMonth.setBounds(150, 240, 110, 15);
        panel.add(cardExpireMonth);

        String[] expireMonth = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        cardExpirationMonth = new JComboBox(expireMonth);
        cardExpirationMonth.setBounds(160, 255, 55, 20);
        cardExpirationMonth.setSelectedIndex(0);
        panel.add(cardExpirationMonth);

        cardError = new JLabel("");
        cardError.setBounds(120, 140, 200, 15);
        cardError.setForeground(Color.red);
        panel.add(cardError);

        cardSecurityError = new JLabel("");
        cardSecurityError.setBounds(120, 190, 200, 15);
        cardSecurityError.setForeground(Color.red);
        panel.add(cardSecurityError);

        expirationError = new JLabel("");
        expirationError.setBounds(CustomerView.panel.getWidth() / 8, 275, 200, 15);
        expirationError.setHorizontalAlignment(SwingConstants.CENTER);
        expirationError.setForeground(Color.red);
        panel.add(expirationError);

        discountEntry = new JLabel("Discount Code");
        discountEntry.setBounds(32, 290, 200, 15);
        panel.add(discountEntry);

        discountCode = new JTextField("");
        discountCode.setBounds(32, 307, 200, 20);
        panel.add(discountCode);

        discountCode.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                validateDiscount();
            }
        });

        discountError = new JLabel("");
        discountError.setBounds(120, 290, 200, 15);
        discountError.setForeground(Color.red);
        panel.add(discountError);

        checkout = new JButton("Checkout");
        checkout.setBounds(27, 345, 100, 50);
        checkout.setBackground(thistle);
        checkout.addActionListener((e) -> {
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

        clearCart = new JButton("Clear Cart");
        clearCart.setBounds(136, 345, 100, 50);
        clearCart.setBackground(thistle);
        clearCart.addActionListener((e) -> {
            inventory.setPreferredSize(new Dimension(625, 0));
            jp.remove(cartPanel);
            cartVisible = false;
            cart.clear();
            jp.validate();

            itemsSelected = 0;
        });
        panel.add(clearCart);

        //This button logs the user out, returning them to the login menu.
        logOut = new JButton("Log Out");
        logOut.setBounds(28, 410, 207, 50);
        logOut.setBackground(thistle);
        logOut.addActionListener((e) -> {
            inventory.setPreferredSize(new Dimension(625, 0));
            jp.remove(cartPanel);
            cartVisible = false;
            cart.clear();
            jp.validate();

            itemsSelected = 0;
            itemSelected = 0;

            itemsName.setVisible(false);
            itemsDescription.setVisible(false);
            itemRockOrGem.setVisible(false);
            itemGrainSize.setVisible(false);
            itemGrainShape.setVisible(false);
            itemHeft.setVisible(false);
            itemHardness.setVisible(false);
            stoneSize.setVisible(false);
            stoneWeight.setVisible(false);
            price.setVisible(false);
            quantity.setVisible(false);
            addToCart.setVisible(false);
            returnToSearch.setVisible(false);
            searchBarEntry.setVisible(true);
            searchBar.setVisible(true);
            searchBar.setText("");
            searchError.setText("");
            cardNumberEntry.setVisible(true);
            cardNumber.setVisible(true);
            securityCodeEntry.setVisible(true);
            cardSecurityCode.setVisible(true);
            cardExpireYear.setVisible(true);
            cardExpirationYear.setVisible(true);
            cardExpireMonth.setVisible(true);
            cardExpirationMonth.setVisible(true);
            cardError.setText("");
            cardSecurityError.setText("");
            expirationError.setText("");
            discountEntry.setVisible(true);
            discountCode.setVisible(true);
            discountError.setText("");
            refreshButton.setVisible(true);
            igneousButton.setVisible(true);
            sedimentaryButton.setVisible(true);
            metamorphicButton.setVisible(true);
            checkout.setVisible(true);
            clearCart.setVisible(true);
            logOut.setVisible(true);

            jp.remove(connectionStatus);
            connectionStatus = new JLabel("");
            connectionStatus.setBounds(5, 445, 200, 15);
            connectionStatus.setForeground(Color.red);
            loginView.jp.add(connectionStatus);

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
        cardError.setText("");
        cardSecurityError.setText("");
        expirationError.setText("");

        String visa = "^4[0-9]{12}(?:[0-9]{3})?$";
        String americanExpress = "^3[47][0-9]{13}$";
        String masterCard = "^(5[1-5][0-9]{14}|(222[1-9]|22[3-9]|2[3-6]|27[01]|2720)[0-9]{12})$";
        String securityCode = "^[0-9]{3}$";

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

        p = Pattern.compile(securityCode);
        m = p.matcher(cardSecurityCode.getText());
        if (m.matches()) {
            cvvValid = true;
        } else {
            cardSecurityError.setText("Invalid Security Code");
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
        discountError.setText("");
        if (!discountCode.getText().equals("")) {
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
            } catch (SQLException ex) {
                discountValid = false;
                discountError.setText("Invalid Code");
            }
        }
    }

    void updateData() {
        Color thistle = Color.decode("#D5CBE2");
        GridBagConstraints imageLayout = new GridBagConstraints();
        imageLayout.insets = new Insets(2, 2, 2, 2);
        int x = 0;
        int y = 0;
        itemDisplay.removeAll();
        items.clear();
        itemDisplay.repaint();
        ItemSelection select = new ItemSelection(items);
        for (int i = 0; i < LoginView.inventory.size(); i++) {
            String itemName = LoginView.inventory.get(i).itemName;

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

            if (LoginView.inventory.get(i).itemImage == null) {
                JButton item = new JButton(itemName);
                item.setBackground(thistle);
                item.addActionListener(select);
                items.add(item);
                itemDisplay.add(item, imageLayout);
            } else {
                try {
                    byte[] b = LoginView.inventory.get(i).itemImage;
                    ItemIcon itemIcon = new ItemIcon(b);
                    Image itemImage = itemIcon.getImage().getScaledInstance(itemIcon.getIconWidth() / 17, itemIcon.getIconHeight() / 17, Image.SCALE_SMOOTH);
                    itemIcon = new ItemIcon(itemImage);
                    JButton item = new JButton(itemName, itemIcon);
                    item.setVerticalTextPosition(SwingConstants.BOTTOM);
                    item.setHorizontalTextPosition(SwingConstants.CENTER);
                    item.setBackground(thistle);
                    item.addActionListener(select);
                    items.add(item);
                    itemDisplay.add(item, imageLayout);
                } catch (Exception ex) {
                    JButton item = new JButton(itemName);
                    item.setBackground(thistle);
                    item.addActionListener(select);
                    items.add(item);
                    itemDisplay.add(item, imageLayout);
                    System.out.println("Invalid Image");
                }
            }
        }
        itemDisplay.validate();
    }

    Object[][] getCartData() {
        try {
            Object[][] cartData = new Object[cart.size()][3];
            for (int i = 0; i < cart.size(); i++) {
                cartData[i] = new String[]{cart.get(i).itemName, cart.get(i).quantity + "", "$" + f.format(cart.get(i).itemCost * cart.get(i).quantity)};
            }
            return cartData;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
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
            sb.append("<h2>Card Expiration Date:  ").append(cardExpirationMonth.getSelectedItem() + "/" + cardExpirationYear.getSelectedItem()).append("</h2>");

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
                sb.append("<td style=\"padding: 6px; border: 1px solid #ccc; text-align: left;\">$").append(f.format(item.itemCost)).append("</td>");
                sb.append("<td style=\"padding: 6px; border: 1px solid #ccc; text-align: left;\">").append(item.quantity).append("</td>");
                sb.append("<td style=\"padding: 6px; border: 1px solid #ccc; text-align: left;\">$").append(f.format(item.itemCost * item.quantity)).append("</td>");
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

            String documentsDirectory = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
            File directory = new File(documentsDirectory);
            if (!directory.exists()) {
                boolean created = directory.mkdirs();
                if (!created) {
                    System.out.println("Directory Not Established: " + documentsDirectory);
                    return;
                }
            }

            String filePath = documentsDirectory + File.separator + "purchase.html";

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
                preparedStatement.setString(7, cardSecurityCode.getText() + "");
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
                inventory.setPreferredSize(new Dimension(625, 0));
                jp.remove(cartPanel);
                cartVisible = false;
                cart.clear();
                jp.validate();

                itemsSelected = 0;
                Thread inventoryData = new Thread(LoginView.inventoryInfo);
                inventoryData.start();
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

        if (LoginView.currentPerson == 0) {
            page.getParagraphs().add(new TextFragment("Refresh - Instantly removes all filters by refreshing the items on display"
                    + "\n\nIgneous Button - The igneous button finds all rocks classified as igneous"
                    + "\n\nSedimentary Button - The igneous button finds all rocks classified as sedimentary"
                    + "\n\nMetamorphic Button - The igneous button finds all rocks classified as metamorphic"
                    + "\n\nAny Button on Right Side - Showcases relevant data about the stone including the name, basic description, and current quantity"
                    + "\n\nLogout Button - The log out button returns the user to the login menu"));
        } else {
            page.getParagraphs().add(new TextFragment("Refresh - Instantly removes all filters by refreshing the items on display"
                    + "\n\nIgneous Button - The igneous button finds all rocks classified as igneous"
                    + "\n\nSedimentary Button - The igneous button finds all rocks classified as sedimentary"
                    + "\n\nMetamorphic Button - The igneous button finds all rocks classified as metamorphic"
                    + "\n\nClear Cart Button - This button removes all items from the cart and hides the table"
                    + "\n\nLogout Button - The log out button returns the user to the login menu"
                    + "\n\nAny Button on Right Side - Showcases relevant data about the stone including the name, basic description, and current quantity"
                    + "\n\nAdd to Cart - A button allowing the user to add an item to their cart (confirm button required), can be clicked multiple times"
                    + "\n\nReturn - A button that returns the user to search/purchase options"
                    + "\n\nCard Data - You must enter a valid card number (Visa/Mastercard) and Card Verification Value (CVV), the card year cannot be five years expired"
                    + "\n\nDiscount Code (NOT REQUIRED) - You must enter a valid discount code if you wish to save money"));
        }

        document.save("help.pdf");

        try {
            File pdfFile = new File("help.pdf");
            Desktop.getDesktop().open(pdfFile);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
