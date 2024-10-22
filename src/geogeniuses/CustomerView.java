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
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.regex.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;

//The customer view is entered by either a customer account or a guest account.
public class CustomerView extends State {

    ArrayList<JButton> items = new ArrayList();

    static ArrayList<ArrayList<Cart>> cartList = new ArrayList<>();

    //The cart arraylist will track items waiting to be purchased.
    static ArrayList<Cart> cart = new ArrayList();

    //The personDetails integer will contain the current user's arraylist index value
    static int personDetails = 0;

    //The decimal format is now listed properly.
    static DecimalFormat f = new DecimalFormat("#,###.00");

    static JPanel panel = new JPanel(null);
    static JPanel itemDisplay = new JPanel(null);

    static JPanel cartPanel = new JPanel(null);
    static JTable cartjt;

    static String[] cartCol;
    static Object[][] cartData;

    static boolean cartVisible = false;

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
    static int itemSelected = 0;

    static JLabel searchBarEntry;
    static JTextField searchBar;

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

    static int sortedIgn = 0;
    static int sortedSed = 0;
    static int sortedMet = 0;
    static int sortedSto = 0;
    static int sortedGem = 0;

    static JButton refreshButton;
    static JButton igneousButton;
    static JButton sedimentaryButton;
    static JButton metamorphicButton;
    static JButton stoneButton;
    static JButton gemstoneButton;
    static JButton addToCart;
    static JButton returnToSearch;
    static JButton checkout;
    static JButton clearCart;
    static JButton ordersButton;
    static JButton logOut;

    static JSlider minPriceSlider;
    static JSlider maxPriceSlider;

    static ScrollPane inventory;
    static ScrollPane cartjs;

    static JLabel orderError;

    static double minPrice;
    static double maxPrice;

    CustomerView() {

        Color lightCyan = Color.decode("#DFFDFF");
        Color thistle = Color.decode("#D5CBE2");
        Color royalPurple = Color.decode("#8062A7");

        jp.setLayout(new BoxLayout(jp, BoxLayout.X_AXIS));

        panel.setBackground(lightCyan);
        panel.setBounds(0, 0, 273, 628);
        panel.setPreferredSize(new Dimension(120, 0));
        jp.add(panel);

        orderError = new JLabel("");
        orderError.setBounds(100, 15, 200, 15);
        orderError.setForeground(Color.red);
        panel.add(orderError);

        itemsName = new JLabel("");
        itemsName.setBounds(panel.getWidth() / 8, 260, 200, 15);
        itemsName.setHorizontalAlignment(SwingConstants.CENTER);
        itemsName.setVisible(false);
        panel.add(itemsName);

        itemsDescription = new JTextArea("");
        itemsDescription.setBounds(11, 285, 250, 105);
        itemsDescription.setEnabled(false);
        itemsDescription.setDisabledTextColor(Color.black);
        itemsDescription.setLineWrap(true);
        itemsDescription.setWrapStyleWord(true);
        itemsDescription.setBackground(lightCyan);
        itemsDescription.setVisible(false);
        panel.add(itemsDescription);

        itemRockOrGem = new JLabel("");
        itemRockOrGem.setBounds(panel.getWidth() / 8, 375, 200, 15);
        itemRockOrGem.setHorizontalAlignment(SwingConstants.CENTER);
        itemRockOrGem.setVisible(false);
        panel.add(itemRockOrGem);

        itemGrainSize = new JLabel("");
        itemGrainSize.setBounds(panel.getWidth() / 8, 390, 200, 15);
        itemGrainSize.setHorizontalAlignment(SwingConstants.CENTER);
        itemGrainSize.setVisible(false);
        panel.add(itemGrainSize);

        itemGrainShape = new JLabel("");
        itemGrainShape.setBounds(panel.getWidth() / 8, 405, 200, 15);
        itemGrainShape.setHorizontalAlignment(SwingConstants.CENTER);
        itemGrainShape.setVisible(false);
        panel.add(itemGrainShape);

        itemHeft = new JLabel("");
        itemHeft.setBounds(panel.getWidth() / 8, 390, 200, 15);
        itemHeft.setHorizontalAlignment(SwingConstants.CENTER);
        itemHeft.setVisible(false);
        panel.add(itemHeft);

        itemHardness = new JLabel("");
        itemHardness.setBounds(panel.getWidth() / 8, 405, 200, 15);
        itemHardness.setHorizontalAlignment(SwingConstants.CENTER);
        itemHardness.setVisible(false);
        panel.add(itemHardness);

        stoneSize = new JLabel("");
        stoneSize.setBounds(panel.getWidth() / 8, 420, 200, 15);
        stoneSize.setHorizontalAlignment(SwingConstants.CENTER);
        stoneSize.setVisible(false);
        panel.add(stoneSize);

        stoneWeight = new JLabel("");
        stoneWeight.setBounds(panel.getWidth() / 8, 435, 200, 15);
        stoneWeight.setHorizontalAlignment(SwingConstants.CENTER);
        stoneWeight.setVisible(false);
        panel.add(stoneWeight);

        price = new JLabel("");
        price.setBounds(panel.getWidth() / 8, 350, 200, 15);
        price.setHorizontalAlignment(SwingConstants.CENTER);
        price.setVisible(false);
        panel.add(price);

        quantity = new JLabel("");
        quantity.setBounds(panel.getWidth() / 8, 465, 200, 15);
        quantity.setHorizontalAlignment(SwingConstants.CENTER);
        quantity.setVisible(false);
        panel.add(quantity);

        addToCart = new JButton("Add to Cart");
        addToCart.setBounds(140, 515, 100, 50);
        addToCart.setVisible(false);
        addToCart.setBackground(thistle);
        addToCart.addActionListener((e) -> {
            if (LoginView.inventory.get(itemSelected).quantity > 0) {
                boolean itemNotInCart = true;
                boolean cartChange = false;
                if (!cartVisible) {
                    if (ManagerView.managerAsCustomer) {
                        jf.setBounds(jf.getX(), jf.getY(), 1730, 610);
                    } else {
                        inventory.setPreferredSize(new Dimension(360, 0));
                    }
                    jp.add(cartPanel);
                    cartVisible = true;
                    jp.validate();
                }
                itemsSelected++;
                for (int i = 0; i < cartList.get(LoginView.personPosition).size(); i++) {
                    Cart cartItem = cartList.get(LoginView.personPosition).get(i);
                    if (LoginView.inventory.get(itemSelected).inventoryID == cartItem.inventoryID) {
                        itemNotInCart = false;
                        if ((LoginView.inventory.get(itemSelected).quantity - cartItem.quantity) > 0) {
                            cartItem.quantity++;
                            // Changes the data in the current cart index, the only difference being a change in quantity
                            cartList.get(LoginView.personPosition).set(i, cartItem);
                            quantity.setText("Quantity: " + (LoginView.inventory.get(itemSelected).quantity - cartItem.quantity));
                            cartChange = true;
                            break;
                        } else {
                            addToCart.setEnabled(false);
                        }
                    }
                } // If the selected item is not already in the cart, it gets added
                if (itemNotInCart) {
                    cartList.get(LoginView.personPosition).add(new Cart(LoginView.inventory.get(itemSelected).inventoryID, LoginView.inventory.get(itemSelected).itemName, LoginView.inventory.get(itemSelected).retailPrice, 1));
                    quantity.setText("Quantity: " + (LoginView.inventory.get(itemSelected).quantity - 1));
                    cartChange = true;
                }
                if (cartChange) {
                    cartData = getCartData();
                    DefaultTableModel carttable = (DefaultTableModel) cartjt.getModel();
                    carttable.setDataVector(cartData, cartCol);
                    jp.validate();
                }
            } else {
                addToCart.setEnabled(false);
            }
        });
        panel.add(addToCart);

        returnToSearch = new JButton("Return");
        returnToSearch.setBounds(25, 515, 100, 50);
        returnToSearch.setVisible(false);
        returnToSearch.setBackground(thistle);
        returnToSearch.addActionListener((e) -> {
            itemSelected = -1;

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
            stoneButton.setVisible(true);
            gemstoneButton.setVisible(true);
            checkout.setVisible(true);
            clearCart.setVisible(true);
            if (!ManagerView.managerAsCustomer) {
                ordersButton.setVisible(true);
                logOut.setVisible(true);
            }
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
        cartPanel.setBounds(0, 0, 267, 590);

        cartjt = new JTable();
        cartCol = new String[]{"Item Name", "Item Qty", "Item Cost"};
        cartjt.setModel(new DefaultTableModel(cartData, cartCol));
        cartjt.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        cartjt.setShowGrid(false);
        cartjt.setDefaultEditor(Object.class, null);
        cartjs = new ScrollPane(cartjt);
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

                int minPrice = minPriceSlider.getValue();
                int maxPrice = maxPriceSlider.getValue();
                int maxPriceMaximum = maxPriceSlider.getMaximum();

                ArrayList<Inventory> sortedInventory = new ArrayList<>(LoginView.inventory);

                if (maxPrice == maxPriceMaximum) {
                    sortedInventory.removeIf(item -> item.cost < minPrice);
                } else {
                    sortedInventory.removeIf(item -> item.cost < minPrice || item.cost > maxPrice);
                }

                Collections.sort(sortedInventory, new Comparator<Inventory>() {
                    @Override
                    public int compare(Inventory item1, Inventory item2) {
                        return Double.compare(item1.cost, item2.cost);
                    }
                });

                for (int i = 0; i < sortedInventory.size(); i++) {
                    String compareName = sortedInventory.get(i).itemName.toLowerCase();
                    if (compareName.contains(searchText)) {
                        String itemName = sortedInventory.get(i).itemName;

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

                        if (sortedInventory.get(i).itemImage == null) {
                            JButton item = new JButton(itemName);
                            item.setBackground(thistle);
                            item.addActionListener(select);
                            items.add(item);
                            itemDisplay.add(item, imageLayout);
                        } else {
                            try {
                                byte[] b = sortedInventory.get(i).itemImage;
                                ItemIcon itemIcon = new ItemIcon(b);
                                Image itemImage = itemIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
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

        JLabel minimumPrice = new JLabel("Min");
        minimumPrice.setBounds(2, 161, 110, 25);
        panel.add(minimumPrice);

        JLabel maximumPrice = new JLabel("Max");
        maximumPrice.setBounds(2, 196, 110, 25);
        panel.add(maximumPrice);

        refreshButton = new JButton("Refresh");
        refreshButton.setBounds(20, 60, 110, 30);
        refreshButton.setBackground(thistle);
        refreshButton.addActionListener((e) -> {
            sortedIgn = 0;
            sortedSed = 0;
            sortedMet = 0;
            sortedSto = 0;
            sortedGem = 0;

            searchBar.setText("");
            orderError.setText("");
            panel.repaint();
            setMin();
            setMax();
            updateData();
        });
        panel.add(refreshButton);

        igneousButton = new JButton("Igneous");
        igneousButton.setBounds(135, 60, 110, 30);
        igneousButton.setBackground(thistle);
        igneousButton.addActionListener((e) -> {
            igneousSort();
        });
        panel.add(igneousButton);

        sedimentaryButton = new JButton("Sedimentary");
        sedimentaryButton.setBounds(20, 95, 110, 30);
        sedimentaryButton.setBackground(thistle);
        sedimentaryButton.addActionListener((e) -> {
            sedimentarySort();
        });
        panel.add(sedimentaryButton);

        metamorphicButton = new JButton("Metamorphic");
        metamorphicButton.setBounds(135, 95, 110, 30);
        metamorphicButton.setBackground(thistle);
        metamorphicButton.addActionListener((e) -> {
            metamorphicSort();
        });
        panel.add(metamorphicButton);

        stoneButton = new JButton("Stone");
        stoneButton.setBounds(20, 130, 110, 30);
        stoneButton.setBackground(thistle);
        stoneButton.addActionListener((e) -> {
            stoneSort();
        });
        panel.add(stoneButton);

        gemstoneButton = new JButton("Gemstone");
        gemstoneButton.setBounds(135, 130, 110, 30);
        gemstoneButton.setBackground(thistle);
        gemstoneButton.addActionListener((e) -> {
            gemstoneSort();
        });
        panel.add(gemstoneButton);

        minPriceSlider = new JSlider(0, 100, 0);
        minPriceSlider.setMajorTickSpacing(10);
        minPriceSlider.setMinorTickSpacing(1);
        minPriceSlider.setPaintTicks(false);
        minPriceSlider.setPaintLabels(true);
        minPriceSlider.setBackground(lightCyan);
        minPriceSlider.setBounds(10, 165, 250, 25);
        minPriceSlider.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                newMinForMax();

                if (sortedIgn == 1) {
                    igneousSort();
                } else if (sortedSed == 1) {
                    sedimentarySort();
                } else if (sortedMet == 1) {
                    metamorphicSort();
                } else if (sortedSto == 1) {
                    stoneSort();
                } else if (sortedGem == 1) {
                    gemstoneSort();
                } else {

                    GridBagConstraints imageLayout = new GridBagConstraints();
                    imageLayout.insets = new Insets(2, 2, 2, 2);
                    int x = 0;
                    int y = 0;
                    itemDisplay.removeAll();
                    items.clear();
                    itemDisplay.repaint();
                    ItemSelection select = new ItemSelection(items);

                    int minPrice = minPriceSlider.getValue();
                    int maxPrice = maxPriceSlider.getValue();
                    int maxPriceMaximum = maxPriceSlider.getMaximum();

                    ArrayList<Inventory> sortedInventory = new ArrayList<>(LoginView.inventory);

                    if (maxPrice == maxPriceMaximum) {
                        sortedInventory.removeIf(item -> item.cost < minPrice);
                    } else {
                        sortedInventory.removeIf(item -> item.cost < minPrice || item.cost > maxPrice);
                    }

                    Collections.sort(sortedInventory, new Comparator<Inventory>() {
                        @Override
                        public int compare(Inventory item1, Inventory item2) {
                            return Double.compare(item1.cost, item2.cost);
                        }
                    });

                    for (int i = 0; i < sortedInventory.size(); i++) {
                        String itemName = sortedInventory.get(i).itemName;

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

                        if (sortedInventory.get(i).itemImage == null) {
                            JButton item = new JButton(itemName);
                            item.setBackground(thistle);
                            item.addActionListener(select);
                            items.add(item);
                            itemDisplay.add(item, imageLayout);
                        } else {
                            try {
                                byte[] b = sortedInventory.get(i).itemImage;
                                ItemIcon itemIcon = new ItemIcon(b);
                                Image itemImage = itemIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
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
                    panel.repaint();
                }
            }
        });
        panel.add(minPriceSlider);

        maxPriceSlider = new JSlider(0, 100, 0);
        maxPriceSlider.setMajorTickSpacing(10);
        maxPriceSlider.setMinorTickSpacing(1);
        maxPriceSlider.setPaintTicks(false);
        maxPriceSlider.setPaintLabels(true);
        maxPriceSlider.setBackground(lightCyan);
        maxPriceSlider.setBounds(10, 200, 250, 25);
        maxPriceSlider.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if (sortedIgn == 1) {
                    igneousSort();
                } else if (sortedSed == 1) {
                    sedimentarySort();
                } else if (sortedMet == 1) {
                    metamorphicSort();
                } else if (sortedSto == 1) {
                    stoneSort();
                } else if (sortedGem == 1) {
                    gemstoneSort();
                } else {
                    GridBagConstraints imageLayout = new GridBagConstraints();
                    imageLayout.insets = new Insets(2, 2, 2, 2);
                    int x = 0;
                    int y = 0;
                    itemDisplay.removeAll();
                    items.clear();
                    itemDisplay.repaint();
                    ItemSelection select = new ItemSelection(items);

                    int minPrice = minPriceSlider.getValue();
                    int maxPrice = maxPriceSlider.getValue();
                    int maxPriceMaximum = maxPriceSlider.getMaximum();

                    ArrayList<Inventory> sortedInventory = new ArrayList<>(LoginView.inventory);

                    if (maxPrice == maxPriceMaximum) {
                        sortedInventory.removeIf(item -> item.cost < minPrice);
                    } else {
                        sortedInventory.removeIf(item -> item.cost < minPrice || item.cost > maxPrice);
                    }

                    Collections.sort(sortedInventory, new Comparator<Inventory>() {
                        @Override
                        public int compare(Inventory item1, Inventory item2) {
                            return Double.compare(item1.cost, item2.cost);
                        }
                    });

                    for (int i = 0; i < sortedInventory.size(); i++) {
                        String itemName = sortedInventory.get(i).itemName;

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

                        if (sortedInventory.get(i).itemImage == null) {
                            JButton item = new JButton(itemName);
                            item.setBackground(thistle);
                            item.addActionListener(select);
                            items.add(item);
                            itemDisplay.add(item, imageLayout);
                        } else {
                            try {
                                byte[] b = sortedInventory.get(i).itemImage;
                                ItemIcon itemIcon = new ItemIcon(b);
                                Image itemImage = itemIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
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
                    panel.repaint();
                }
            }
        });
        panel.add(maxPriceSlider);

        ordersButton = new JButton("Orders");
        ordersButton.setBounds(136, 515, 100, 50);
        ordersButton.setBackground(thistle);
        ordersButton.addActionListener((e) -> {
            customerSalesDetails();
        });
        panel.add(ordersButton);

        cardNumberEntry = new JLabel("Card Number");
        cardNumberEntry.setBounds(32, 245, 200, 15);
        panel.add(cardNumberEntry);

        cardNumber = new JTextField("");
        cardNumber.setBounds(32, 262, 200, 20);
        panel.add(cardNumber);

        cardNumber.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                validateCard();
            }
        });

        securityCodeEntry = new JLabel("Security Code");
        securityCodeEntry.setBounds(32, 295, 200, 15);
        panel.add(securityCodeEntry);

        cardSecurityCode = new JTextField("");
        cardSecurityCode.setBounds(32, 312, 200, 20);
        panel.add(cardSecurityCode);

        cardSecurityCode.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                validateCard();
            }
        });

        cardExpireYear = new JLabel("Expire Year");
        cardExpireYear.setBounds(50, 345, 100, 15);
        panel.add(cardExpireYear);

        int yearsForList = currentDate.getYear();

        String[] expireYear = {"" + (yearsForList - 7), "" + (yearsForList - 6), "" + (yearsForList - 5), "" + (yearsForList - 4), "" + (yearsForList - 3), "" + (yearsForList - 2), "" + (yearsForList - 1), "" + yearsForList, "" + (yearsForList + 1), "" + (yearsForList + 2), "" + (yearsForList + 3), "" + (yearsForList + 4), "" + (yearsForList + 5), "" + (yearsForList + 6), "" + (yearsForList + 7)};
        cardExpirationYear = new JComboBox(expireYear);
        cardExpirationYear.setBounds(55, 360, 55, 20);
        cardExpirationYear.setSelectedIndex(7);
        panel.add(cardExpirationYear);

        cardExpirationYear.addActionListener((e) -> {
            validateCard();
        });

        cardExpireMonth = new JLabel("Expire Month");
        cardExpireMonth.setBounds(140, 345, 110, 15);
        panel.add(cardExpireMonth);

        String[] expireMonth = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        cardExpirationMonth = new JComboBox(expireMonth);
        cardExpirationMonth.setBounds(150, 360, 55, 20);
        cardExpirationMonth.setSelectedIndex(currentDate.getMonthValue() - 1);
        panel.add(cardExpirationMonth);

        cardError = new JLabel("");
        cardError.setBounds(110, 245, 200, 15);
        cardError.setForeground(Color.red);
        panel.add(cardError);

        cardSecurityError = new JLabel("");
        cardSecurityError.setBounds(120, 295, 200, 15);
        cardSecurityError.setForeground(Color.red);
        panel.add(cardSecurityError);

        expirationError = new JLabel("");
        expirationError.setBounds(CustomerView.panel.getWidth() / 8, 380, 200, 15);
        expirationError.setHorizontalAlignment(SwingConstants.CENTER);
        expirationError.setForeground(Color.red);
        panel.add(expirationError);

        discountEntry = new JLabel("Discount Code");
        discountEntry.setBounds(32, 395, 200, 15);
        panel.add(discountEntry);

        discountCode = new JTextField("");
        discountCode.setBounds(32, 412, 200, 20);
        panel.add(discountCode);

        discountCode.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                validateDiscount();
            }
        });

        discountError = new JLabel("");
        discountError.setBounds(120, 395, 200, 15);
        discountError.setForeground(Color.red);
        panel.add(discountError);

        checkout = new JButton("Checkout");
        checkout.setBounds(27, 450, 100, 50);
        checkout.setBackground(thistle);
        checkout.addActionListener((e) -> {
            if (itemsSelected > 0) {
                validateCard();
                if (!discountCode.getText().equals("")) {
                    validateDiscount();
                    if (cardValid) {
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
        clearCart.setBounds(136, 450, 100, 50);
        clearCart.setBackground(thistle);
        clearCart.addActionListener((e) -> {
            if (ManagerView.managerAsCustomer) {
                jf.setBounds(jf.getX(), jf.getY(), 1466, 610);
            }
            inventory.setPreferredSize(new Dimension(625, 0));
            jp.remove(cartPanel);
            cartVisible = false;
            cartList.get(LoginView.personPosition).clear();
            jp.validate();

            itemsSelected = 0;
        });
        panel.add(clearCart);

        //This button logs the user out, returning them to the login menu.
        logOut = new JButton("Log Out");
        logOut.setBounds(27, 515, 100, 50);
        logOut.setBackground(thistle);
        logOut.addActionListener((e) -> {
            sortedIgn = 0;
            sortedSed = 0;
            sortedMet = 0;
            sortedSto = 0;
            sortedGem = 0;

            inventory.setPreferredSize(new Dimension(625, 0));
            jp.remove(cartPanel);
            cartVisible = false;
            jp.validate();

            itemSelected = -1;

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
            orderError.setText("");
            cardNumberEntry.setVisible(true);
            cardNumber.setText("");
            cardNumber.setVisible(true);
            securityCodeEntry.setVisible(true);
            cardSecurityCode.setText("");
            cardSecurityCode.setVisible(true);
            cardExpireYear.setVisible(true);
            cardExpirationYear.setSelectedIndex(7);
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
            stoneButton.setVisible(true);
            gemstoneButton.setVisible(true);
            ordersButton.setVisible(true);
            checkout.setVisible(true);
            clearCart.setVisible(true);
            logOut.setBounds(27, 515, 100, 50);
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
            for (int i = 0; i < LoginView.discount.size(); i++) {
                if (discountCode.getText().equals(LoginView.discount.get(i).discountCode)) {
                    discountID = LoginView.discount.get(i).discountID;
                    if (LoginView.discount.get(i).startDate != null) {
                        LocalDate localStartDate = LocalDate.parse(LoginView.discount.get(i).startDate);
                        startDate = Date.valueOf(localStartDate);
                    }
                    LocalDate localExpireDate = LocalDate.parse(LoginView.discount.get(i).expirationDate);
                    expireDate = Date.valueOf(localExpireDate);
                    discountLevel = LoginView.discount.get(i).discountLevel;
                    if (discountLevel == 1) {
                        discountInventoryID = LoginView.discount.get(i).inventoryID;
                    }
                    discountType = LoginView.discount.get(i).discountType;
                    discountPercent = LoginView.discount.get(i).discountPercentage;
                    discountAmount = LoginView.discount.get(i).discountDollarAmount;
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
                for (int i = 0; i < cartList.get(LoginView.personPosition).size(); i++) {
                    if (discountInventoryID == cartList.get(LoginView.personPosition).get(i).inventoryID) {
                        discountAppliable = true;
                        break;
                    }
                }
                if (!discountAppliable) {
                    discountValid = false;
                    discountError.setText("Not Applicable");
                }
            }
        }
    }

    void igneousSort() {
        Color thistle = Color.decode("#D5CBE2");

        sortedIgn = 1;
        sortedSed = 0;
        sortedMet = 0;
        sortedSto = 0;
        sortedGem = 0;

        GridBagConstraints imageLayout = new GridBagConstraints();
        imageLayout.insets = new Insets(2, 2, 2, 2);
        int x = 0;
        int y = 0;
        itemDisplay.removeAll();
        items.clear();
        itemDisplay.repaint();
        ItemSelection select = new ItemSelection(items);

        int minPrice = minPriceSlider.getValue();
        int maxPrice = maxPriceSlider.getValue();
        int maxPriceMaximum = maxPriceSlider.getMaximum();

        ArrayList<Inventory> sortedInventory = new ArrayList<>(LoginView.inventory);

        if (maxPrice == maxPriceMaximum) {
            sortedInventory.removeIf(item -> item.cost < minPrice);
        } else {
            sortedInventory.removeIf(item -> item.cost < minPrice || item.cost > maxPrice);
        }

        Collections.sort(sortedInventory, new Comparator<Inventory>() {
            @Override
            public int compare(Inventory item1, Inventory item2) {
                return Double.compare(item1.cost, item2.cost);
            }
        });

        for (int i = 0; i < sortedInventory.size(); i++) {
            if (sortedInventory.get(i).categoryID == 1) {
                String itemName = sortedInventory.get(i).itemName;

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

                if (sortedInventory.get(i).itemImage == null) {
                    JButton item = new JButton(itemName);
                    item.setBackground(thistle);
                    item.addActionListener(select);
                    items.add(item);
                    itemDisplay.add(item, imageLayout);
                } else {
                    try {
                        byte[] b = sortedInventory.get(i).itemImage;
                        ItemIcon itemIcon = new ItemIcon(b);
                        Image itemImage = itemIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
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
        panel.repaint();
    }

    void sedimentarySort() {
        Color thistle = Color.decode("#D5CBE2");

        sortedIgn = 0;
        sortedSed = 1;
        sortedMet = 0;
        sortedSto = 0;
        sortedGem = 0;

        GridBagConstraints imageLayout = new GridBagConstraints();
        imageLayout.insets = new Insets(2, 2, 2, 2);
        int x = 0;
        int y = 0;
        itemDisplay.removeAll();
        items.clear();
        itemDisplay.repaint();
        ItemSelection select = new ItemSelection(items);

        int minPrice = minPriceSlider.getValue();
        int maxPrice = maxPriceSlider.getValue();
        int maxPriceMaximum = maxPriceSlider.getMaximum();

        ArrayList<Inventory> sortedInventory = new ArrayList<>(LoginView.inventory);

        if (maxPrice == maxPriceMaximum) {
            sortedInventory.removeIf(item -> item.cost < minPrice);
        } else {
            sortedInventory.removeIf(item -> item.cost < minPrice || item.cost > maxPrice);
        }

        Collections.sort(sortedInventory, new Comparator<Inventory>() {
            @Override
            public int compare(Inventory item1, Inventory item2) {
                return Double.compare(item1.cost, item2.cost);
            }
        });

        for (int i = 0; i < sortedInventory.size(); i++) {
            if (sortedInventory.get(i).categoryID == 2) {
                String itemName = sortedInventory.get(i).itemName;

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

                if (sortedInventory.get(i).itemImage == null) {
                    JButton item = new JButton(itemName);
                    item.setBackground(thistle);
                    item.addActionListener(select);
                    items.add(item);
                    itemDisplay.add(item, imageLayout);
                } else {
                    try {
                        byte[] b = sortedInventory.get(i).itemImage;
                        ItemIcon itemIcon = new ItemIcon(b);
                        Image itemImage = itemIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
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
        panel.repaint();
    }

    void metamorphicSort() {
        Color thistle = Color.decode("#D5CBE2");

        sortedIgn = 0;
        sortedSed = 0;
        sortedMet = 1;
        sortedSto = 0;
        sortedGem = 0;

        GridBagConstraints imageLayout = new GridBagConstraints();
        imageLayout.insets = new Insets(2, 2, 2, 2);
        int x = 0;
        int y = 0;
        itemDisplay.removeAll();
        items.clear();
        itemDisplay.repaint();
        ItemSelection select = new ItemSelection(items);

        int minPrice = minPriceSlider.getValue();
        int maxPrice = maxPriceSlider.getValue();
        int maxPriceMaximum = maxPriceSlider.getMaximum();

        ArrayList<Inventory> sortedInventory = new ArrayList<>(LoginView.inventory);

        if (maxPrice == maxPriceMaximum) {
            sortedInventory.removeIf(item -> item.cost < minPrice);
        } else {
            sortedInventory.removeIf(item -> item.cost < minPrice || item.cost > maxPrice);
        }

        Collections.sort(sortedInventory, new Comparator<Inventory>() {
            @Override
            public int compare(Inventory item1, Inventory item2) {
                return Double.compare(item1.cost, item2.cost);
            }
        });

        for (int i = 0; i < sortedInventory.size(); i++) {
            if (sortedInventory.get(i).categoryID == 3) {
                String itemName = sortedInventory.get(i).itemName;

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

                if (sortedInventory.get(i).itemImage == null) {
                    JButton item = new JButton(itemName);
                    item.setBackground(thistle);
                    item.addActionListener(select);
                    items.add(item);
                    itemDisplay.add(item, imageLayout);
                } else {
                    try {
                        byte[] b = sortedInventory.get(i).itemImage;
                        ItemIcon itemIcon = new ItemIcon(b);
                        Image itemImage = itemIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
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
        panel.repaint();
    }

    void stoneSort() {
        Color thistle = Color.decode("#D5CBE2");

        sortedIgn = 0;
        sortedSed = 0;
        sortedMet = 0;
        sortedSto = 1;
        sortedGem = 0;

        GridBagConstraints imageLayout = new GridBagConstraints();
        imageLayout.insets = new Insets(2, 2, 2, 2);
        int x = 0;
        int y = 0;
        itemDisplay.removeAll();
        items.clear();
        itemDisplay.repaint();
        ItemSelection select = new ItemSelection(items);

        int minPrice = minPriceSlider.getValue();
        int maxPrice = maxPriceSlider.getValue();
        int maxPriceMaximum = maxPriceSlider.getMaximum();

        ArrayList<Inventory> sortedInventory = new ArrayList<>(LoginView.inventory);

        if (maxPrice == maxPriceMaximum) {
            sortedInventory.removeIf(item -> item.cost < minPrice);
        } else {
            sortedInventory.removeIf(item -> item.cost < minPrice || item.cost > maxPrice);
        }

        Collections.sort(sortedInventory, new Comparator<Inventory>() {
            @Override
            public int compare(Inventory item1, Inventory item2) {
                return Double.compare(item1.cost, item2.cost);
            }
        });

        for (int i = 0; i < sortedInventory.size(); i++) {
            if (sortedInventory.get(i).stoneOrGemstone == 0) {
                String itemName = sortedInventory.get(i).itemName;

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

                if (sortedInventory.get(i).itemImage == null) {
                    JButton item = new JButton(itemName);
                    item.setBackground(thistle);
                    item.addActionListener(select);
                    items.add(item);
                    itemDisplay.add(item, imageLayout);
                } else {
                    try {
                        byte[] b = sortedInventory.get(i).itemImage;
                        ItemIcon itemIcon = new ItemIcon(b);
                        Image itemImage = itemIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
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
        panel.repaint();
    }

    void gemstoneSort() {
        Color thistle = Color.decode("#D5CBE2");

        sortedIgn = 0;
        sortedSed = 0;
        sortedMet = 0;
        sortedSto = 0;
        sortedGem = 1;

        GridBagConstraints imageLayout = new GridBagConstraints();
        imageLayout.insets = new Insets(2, 2, 2, 2);
        int x = 0;
        int y = 0;
        itemDisplay.removeAll();
        items.clear();
        itemDisplay.repaint();
        ItemSelection select = new ItemSelection(items);

        int minPrice = minPriceSlider.getValue();
        int maxPrice = maxPriceSlider.getValue();
        int maxPriceMaximum = maxPriceSlider.getMaximum();

        ArrayList<Inventory> sortedInventory = new ArrayList<>(LoginView.inventory);

        if (maxPrice == maxPriceMaximum) {
            sortedInventory.removeIf(item -> item.cost < minPrice);
        } else {
            sortedInventory.removeIf(item -> item.cost < minPrice || item.cost > maxPrice);
        }

        Collections.sort(sortedInventory, new Comparator<Inventory>() {
            @Override
            public int compare(Inventory item1, Inventory item2) {
                return Double.compare(item1.cost, item2.cost);
            }
        });

        for (int i = 0; i < sortedInventory.size(); i++) {
            if (sortedInventory.get(i).stoneOrGemstone == 1) {
                String itemName = sortedInventory.get(i).itemName;

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

                if (sortedInventory.get(i).itemImage == null) {
                    JButton item = new JButton(itemName);
                    item.setBackground(thistle);
                    item.addActionListener(select);
                    items.add(item);
                    itemDisplay.add(item, imageLayout);
                } else {
                    try {
                        byte[] b = sortedInventory.get(i).itemImage;
                        ItemIcon itemIcon = new ItemIcon(b);
                        Image itemImage = itemIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
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
        panel.repaint();
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
                    Image itemImage = itemIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
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

    static Object[][] getCartData() {
        try {
            Object[][] cartData = new Object[cartList.get(LoginView.personPosition).size()][3];
            for (int i = 0; i < cartList.get(LoginView.personPosition).size(); i++) {
                cartData[i] = new String[]{cartList.get(LoginView.personPosition).get(i).itemName, cartList.get(LoginView.personPosition).get(i).quantity + "", "$" + f.format(cartList.get(LoginView.personPosition).get(i).itemCost * cartList.get(LoginView.personPosition).get(i).quantity)};
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
            sb.append("<style>")
                    .append("body {background-color: #DFFDFF;}")
                    .append("h1 {text-align: center; font-size: 2.5em; font-weight: bold; color: #005f73; margin: 20px 0; padding: 10px; text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.2); letter-spacing: 1px;}")
                    .append("h2 {text-align: center; font-size: 2.5em; font-weight: bold; color: #005f73;}")
                    .append("table {width: 100%; border-collapse: collapse; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);}")
                    .append("th, td {padding: 10px; text-align: center; border: 1px solid #ddd;}")
                    .append("td {background-color: white !important;}")
                    .append("th {background-color: #f2f2f2;}")
                    .append("tr:hover {background-color: #f1f1f1;}")
                    .append("</style>");
            sb.append("<head></head>");
            sb.append("<body>");

            if (ManagerView.managerAsCustomer) {
                sb.append("<h2 style='text-align: center;'>Assisted Checkout</h2>");
                sb.append("<h2 style='text-align: center;'>Manager Name: ").append(LoginView.person.get(ManagerView.managerDetails).nameFirst + " " + LoginView.person.get(ManagerView.managerDetails).nameLast.charAt(0)).append(".</h2>");
            }
            sb.append("<h2 style='text-align: center;'>Customer Name: ").append(LoginView.person.get(personDetails).nameFirst + " " + LoginView.person.get(personDetails).nameLast.charAt(0)).append(".</h2>");
            sb.append("<h2 style='text-align: center;'>Phone Number:  ").append(LoginView.person.get(personDetails).phonePrimary).append("</h2>");
            sb.append("<h2 style='text-align: center;'>Card Expiration Date:  ").append(cardExpirationMonth.getSelectedItem() + "/" + cardExpirationYear.getSelectedItem()).append("</h2>");

            sb.append("<table>");

            sb.append("<tr>");
            sb.append("<th style=\"background: #f2f2f2; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\">Item Name</th>");
            sb.append("<th style=\"background: #f2f2f2; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\">Cost</th>");
            sb.append("<th style=\"background: #f2f2f2; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\">Quantity</th>");
            sb.append("<th style=\"background: #f2f2f2; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\">Total Cost</th>");
            sb.append("</tr>");

            for (int i = 0; i < cartList.get(LoginView.personPosition).size(); i++) {
                itemSubtotal += cartList.get(LoginView.personPosition).get(i).itemCost * cartList.get(LoginView.personPosition).get(i).quantity;
                if (discountValid) {
                    if (discountLevel == 1 && discountInventoryID == cartList.get(LoginView.personPosition).get(i).inventoryID) {
                        if (discountType == 0) {
                            discount = (cartList.get(LoginView.personPosition).get(i).itemCost * cartList.get(LoginView.personPosition).get(i).quantity) * discountPercent;
                        } else if (discountType == 1) {
                            discount = discountAmount;
                        }
                    }
                }
            }

            for (Cart item : cartList.get(LoginView.personPosition)) {
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

                if (discountAmount > itemSubtotal) {
                    discount = 0;
                } else {
                    discount = itemSubtotal - discount;
                }

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
                ps = con.prepareStatement(statement);
                if (discountID == 0) {
                    ps.setNull(1, java.sql.Types.INTEGER);
                } else {
                    ps.setInt(1, discountID);
                }
                ps.setInt(2, LoginView.currentPerson);
                if (ManagerView.managerAsCustomer) {
                    ps.setInt(3, LoginView.person.get(ManagerView.managerDetails).personID);
                } else {
                    ps.setNull(3, java.sql.Types.INTEGER);
                }
                ps.setString(4, dateNow.toString());
                ps.setString(5, cardNumber.getText());
                ps.setString(6, cardExpirationMonth.getSelectedItem() + "/" + cardExpirationYear.getSelectedItem());
                ps.setString(7, cardSecurityCode.getText() + "");
                ps.execute();

                String query = "SELECT OrderID FROM Orders ORDER BY OrderID DESC LIMIT 1";
                ps = con.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                rs.next();
                int orderID = rs.getInt("OrderID");

                for (int i = 0; i < cartList.get(LoginView.personPosition).size(); i++) {
                    query = "SELECT Quantity FROM Inventory WHERE InventoryID = " + cartList.get(LoginView.personPosition).get(i).inventoryID + ";";
                    ps = con.prepareStatement(query);
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        int quantity = rs.getInt("Quantity");

                        statement = "UPDATE Inventory SET Quantity = " + (quantity - cartList.get(LoginView.personPosition).get(i).quantity) + " WHERE InventoryID = " + cartList.get(LoginView.personPosition).get(i).inventoryID + ";";
                        ps = con.prepareStatement(statement);
                        ps.execute();

                        statement = "INSERT INTO OrderDetails (OrderID, InventoryID, DiscountID, Quantity)"
                                + "VALUES (?, ?, ?, ?)";
                        ps = con.prepareStatement(statement);
                        ps.setInt(1, orderID);
                        ps.setInt(2, cartList.get(LoginView.personPosition).get(i).inventoryID);
                        if (discountValid) {
                            if (discountLevel == 0) {
                                ps.setInt(3, discountID);
                            } else if (discountLevel == 1) {
                                if (discountInventoryID == cartList.get(LoginView.personPosition).get(i).inventoryID) {
                                    ps.setInt(3, discountID);
                                } else {
                                    ps.setNull(3, java.sql.Types.INTEGER);
                                }
                            } else {
                                ps.setNull(3, java.sql.Types.INTEGER);
                            }
                        } else {
                            ps.setNull(3, java.sql.Types.INTEGER);
                        }
                        ps.setInt(4, cartList.get(LoginView.personPosition).get(i).quantity);
                        ps.execute();
                    }
                }
                if (ManagerView.managerAsCustomer) {
                    jf.setBounds(jf.getX(), jf.getY(), 1466, 610);
                }
                inventory.setPreferredSize(new Dimension(625, 0));
                jp.remove(cartPanel);
                cartVisible = false;
                cartList.get(LoginView.personPosition).clear();
                jp.validate();
                validateDiscount();

                itemsSelected = 0;
                Thread inventoryData = new Thread(LoginView.inventoryInfo);
                inventoryData.start();
                Thread orderData = new Thread(LoginView.orderInfo);
                orderData.start();
                Thread orderDetailsData = new Thread(LoginView.orderDetailsInfo);
                orderDetailsData.start();

                cardNumber.setText("");
                cardSecurityCode.setText("");
                discountCode.setText("");
                cardError.setText("");
                cardSecurityError.setText("");
                cardExpirationYear.setSelectedIndex(7);
                cardExpirationMonth.setSelectedIndex(currentDate.getMonthValue() - 1);

                JOptionPane.showMessageDialog(null, "Order details saved in documents and displayed in browser", "Purchase Successful!!!", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                System.out.println(e);
            }
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
    };

    void helpSystem() {
        Document document = new Document();
        Page page = document.getPages().add();
        page.getParagraphs().add(new TextFragment("Help Guide for Customers"));
        page.getParagraphs().add(new TextFragment("==================================="));
        page.getParagraphs().add(new TextFragment("----Guest Accessible Features"));
        page.getParagraphs().add(new TextFragment("Refresh: Instantly removes all filters by refreshing the items on display."));
        page.getParagraphs().add(new TextFragment("Igneous Button: Finds all rocks classified as igneous."));
        page.getParagraphs().add(new TextFragment("Sedimentary Button: Finds all rocks classified as sedimentary."));
        page.getParagraphs().add(new TextFragment("Metamorphic Button: Finds all rocks classified as metamorphic."));
        page.getParagraphs().add(new TextFragment("Any Button on Right Side: Showcases relevant data about the stone including the name, description, and quantity."));
        page.getParagraphs().add(new TextFragment("Logout Button: Returns the user to the login menu."));
        page.getParagraphs().add(new TextFragment("----Additional Features for Customers Only"));
        page.getParagraphs().add(new TextFragment("Clear Cart Button: Removes all items from the cart and hides the table."));
        page.getParagraphs().add(new TextFragment("Add to Cart: Allows the user to add an item to their cart (confirmation required)."));
        page.getParagraphs().add(new TextFragment("Return Button: Returns the user to search/purchase options."));
        page.getParagraphs().add(new TextFragment("Card Data: Enter a valid card number (Visa/Mastercard) and CVV; the card cannot be expired by more than five years."));
        page.getParagraphs().add(new TextFragment("Discount Code (NOT REQUIRED): Enter a valid discount code to save money."));
        if (ManagerView.managerAsCustomer) {
            page.getParagraphs().add(new TextFragment("----Manager Features"));
            page.getParagraphs().add(new TextFragment("As a manager using the customer view, you have access to all standard features and can manage customer transactions effectively."));
            page.getParagraphs().add(new TextFragment("Use the Clear Cart and Add to Cart functions as described above."));
        }
        document.save("customerhelp.pdf");
        try {
            File pdfFile = new File("customerhelp.pdf");
            Desktop.getDesktop().open(pdfFile);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    void customerSalesDetails() {
        int orderNumber = 0;
        boolean ordersFound = false;
        double totalOfAllOrders = 0;
        orderError.setText("");
        final double TAX = 0.0825;
        StringBuilder orderReport = new StringBuilder();
        for (int p = 0; p < LoginView.person.size(); p++) {
            if (LoginView.currentPerson == LoginView.person.get(p).personID) {
                for (int or = 0; or < LoginView.orders.size(); or++) {
                    Orders order = LoginView.orders.get(or);
                    double orderTotalCost = 0;
                    orderNumber++;
                    if (LoginView.currentPerson == order.personID) {
                        if (!ordersFound) {
                            orderReport.append("<html><body><h1>Purchase Report</h1>");
                            orderReport.append("<style>")
                                    .append("body {background-color: #DFFDFF;}")
                                    .append("h1 {text-align: center; font-size: 2.5em; font-weight: bold; color: #005f73; margin: 20px 0; padding: 10px; text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.2); letter-spacing: 1px;}")
                                    .append("h2 {text-align: center; font-size: 2.5em; font-weight: bold; color: #005f73;}")
                                    .append("table {width: 100%; border-collapse: collapse; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);}")
                                    .append("th, td {padding: 10px; text-align: center; border: 1px solid #ddd;}")
                                    .append("td {background-color: white !important;}")
                                    .append("th {background-color: #f2f2f2;}")
                                    .append("tr:hover {background-color: #f1f1f1;}")
                                    .append("</style>");
                            orderReport.append("<table border='1'><tr></th><th>Order Date</th><th>Customer</th><th>Card Number</th><th>Card Expiration</th><th>Security Code</th><th>Discount Code</th><th>Order Total</th><th>Manager</th></tr>");
                            ordersFound = true;
                        }
                        orderReport.append("<tr>");
                        orderReport.append("<td>").append(order.orderDate).append("</td>");
                        orderReport.append("<td>").append(LoginView.person.get(p).nameFirst + " " + LoginView.person.get(p).nameLast.charAt(0)).append(".</td>");
                        orderReport.append("<td>").append(order.ccNumber).append("</td>");
                        orderReport.append("<td>").append(order.expDate).append("</td>");
                        orderReport.append("<td>").append(order.ccv).append("</td>");
                        for (int od = 0; od < LoginView.orderDetails.size(); od++) {
                            OrderDetails orderDetail = LoginView.orderDetails.get(od);
                            boolean amountUsedUp = false;
                            if (LoginView.orders.get(or).orderID == LoginView.orderDetails.get(od).orderID) {
                                double itemTotalCost = LoginView.inventory.get(orderDetail.inventoryID).cost * orderDetail.quantity;
                                if (orderDetail.discountID != 0) {
                                    for (int i = 0; i < LoginView.discount.size(); i++) {
                                        if (LoginView.discount.get(i).discountID == orderDetail.discountID) {
                                            if (LoginView.discount.get(i).discountLevel == 0) {
                                                if (LoginView.discount.get(i).discountType == 0) {
                                                    itemTotalCost = itemTotalCost - (itemTotalCost * LoginView.discount.get(i).discountPercentage);
                                                }
                                                if (LoginView.discount.get(i).discountType == 1 && !amountUsedUp) {
                                                    amountUsedUp = true;
                                                    itemTotalCost = itemTotalCost - LoginView.discount.get(i).discountDollarAmount;
                                                }
                                            } else if (LoginView.discount.get(i).discountLevel == 1) {
                                                if (LoginView.discount.get(i).inventoryID == LoginView.orderDetails.get(i).inventoryID) {
                                                    if (LoginView.discount.get(i).discountType == 0) {
                                                        itemTotalCost = itemTotalCost - (itemTotalCost * LoginView.discount.get(i).discountPercentage);
                                                    }
                                                    if (LoginView.discount.get(i).discountType == 1 && !amountUsedUp) {
                                                        amountUsedUp = true;
                                                        itemTotalCost = itemTotalCost - LoginView.discount.get(i).discountDollarAmount;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                orderTotalCost += itemTotalCost;
                            }
                        }
                        boolean discountPresent = false;
                        for (int i = 0; i < LoginView.discount.size(); i++) {
                            if (LoginView.discount.get(i).discountID == order.discountID) {
                                orderReport.append("<td>").append(LoginView.discount.get(i).discountCode).append("</td>");
                                discountPresent = true;
                                break;
                            }
                        }
                        if (!discountPresent) {
                            orderReport.append("<td>").append("None").append("</td>");
                        }
                        double afterTax = orderTotalCost * TAX;
                        orderTotalCost = orderTotalCost + afterTax;
                        orderReport.append("<td>").append("$" + f.format(orderTotalCost)).append("</td>");
                        boolean managerFound = false;
                        for (int i = 0; i < LoginView.person.size(); i++) {
                            if (LoginView.person.get(i).personID == order.managerID) {
                                orderReport.append("<td>").append(LoginView.person.get(i).nameFirst + " " + LoginView.person.get(i).nameLast.charAt(0)).append("</td>");
                                managerFound = true;
                                break;
                            }
                        }
                        if (!managerFound) {
                            orderReport.append("<td>").append("None").append("</td>");
                        }
                        totalOfAllOrders += orderTotalCost;
                    }
                }
                if (ordersFound) {
                    orderReport.append("</table>");
                    orderReport.append("<h2 style='text-align: center;'>Cost of all orders: $" + f.format(totalOfAllOrders) + "<h2>");
                    orderReport.append("</body></html>");
                } else {
                    orderReport.append("<p>No orders</p>");
                    orderReport.append("</body></html>");
                }
                break;
            }
        }
        if (ordersFound) {
            try {
                String documentsDirectory = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
                File directory = new File(documentsDirectory);
                if (!directory.exists()) {
                    boolean created = directory.mkdirs();
                    if (!created) {
                        System.out.println("Directory Not Established: " + documentsDirectory);
                        return;
                    }
                }

                String filePath = documentsDirectory + File.separator + "customerOrders.html";
                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
                writer.write(orderReport.toString());
                writer.close();
                File file = new File(filePath);
                URI uri = file.toURI();
                Desktop.getDesktop().browse(uri);
            } catch (IOException e) {
                System.out.println(e);
            }
        } else {
            orderError.setText("No orders found");
        }
    }

    static void setMin() {
        for (int i = 0; i < LoginView.inventory.size(); i++) {
            if (LoginView.inventory.get(i).cost > minPrice) {
                minPrice = LoginView.inventory.get(i).cost;
            }
        }

        minPriceSlider.setMinimum(0);
        minPriceSlider.setMaximum((int) minPrice);
        minPriceSlider.setValue(0);

        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(0, createLabel("$0"));
        labelTable.put((int) minPrice / 2, createLabel("$" + f.format(((minPrice / 2)))));
        labelTable.put((int) minPrice, createLabel("$" + f.format(minPrice)));

        minPriceSlider.setLabelTable(labelTable);
        minPriceSlider.setValue(minPriceSlider.getMinimum());
        minPriceSlider.repaint();
        minPriceSlider.setLabelTable(labelTable);
    }

    static void setMax() {
        for (int i = 0; i < LoginView.inventory.size(); i++) {
            if (LoginView.inventory.get(i).cost > maxPrice) {
                maxPrice = LoginView.inventory.get(i).cost;
            }
        }

        maxPriceSlider.setMinimum((int) minPriceSlider.getValue());
        maxPriceSlider.setMaximum((int) maxPrice);
        maxPriceSlider.setValue(0);

        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        if (minPriceSlider.getValue() > 0) {
            labelTable.put(0, createLabel("$" + f.format(minPriceSlider.getValue())));
        } else {
            labelTable.put(0, createLabel("$" + minPriceSlider.getValue()));
        }
        double mediumPrice = (maxPrice + minPriceSlider.getValue()) / 2;
        labelTable.put((int) maxPrice / 2, createLabel("$" + f.format(mediumPrice)));
        labelTable.put((int) maxPrice, createLabel("$" + f.format(maxPrice)));

        maxPriceSlider.setLabelTable(labelTable);
        maxPriceSlider.setValue(maxPriceSlider.getMaximum());
        maxPriceSlider.repaint();
        maxPriceSlider.setLabelTable(labelTable);
    }

    static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 10));
        return label;
    }

    void newMinForMax() {
        maxPriceSlider.setMinimum(minPriceSlider.getValue());

        if (maxPriceSlider.getValue() < maxPriceSlider.getMinimum()) {
            maxPriceSlider.setValue(maxPriceSlider.getMinimum());
        }

        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        if (minPriceSlider.getValue() > 0) {
            labelTable.put(maxPriceSlider.getMinimum(), createLabel("$" + f.format(maxPriceSlider.getMinimum())));
        } else {
            labelTable.put(maxPriceSlider.getMinimum(), createLabel("$" + maxPriceSlider.getMinimum()));
        }
        labelTable.put(maxPriceSlider.getMaximum(), createLabel("$" + f.format(maxPriceSlider.getMaximum())));

        double mediumPrice = (maxPriceSlider.getMaximum() + maxPriceSlider.getMinimum()) / 2;
        labelTable.put((int) mediumPrice, createLabel("$" + f.format(mediumPrice)));

        maxPriceSlider.setLabelTable(labelTable);
        maxPriceSlider.repaint();
    }
}
