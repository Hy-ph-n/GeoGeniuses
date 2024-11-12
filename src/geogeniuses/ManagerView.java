package geogeniuses;

import com.aspose.pdf.Document;
import com.aspose.pdf.Page;
import com.aspose.pdf.FontRepository;
import com.aspose.pdf.TextFragment;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.*;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;

/**
* ManagerView is the largest and most complicated of the views, allowing valid users to
* add/edit/remove items, discounts, and people, as well as viewing orders, viewing users
* and making point of sales.
* @author David Bowen
*/
public class ManagerView extends State {

    static DecimalFormat f = new DecimalFormat("#,##0.00");
    static DecimalFormat ff = new DecimalFormat("###");
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    static ArrayList<JButton> items = new ArrayList();

    static JPanel panel = new JPanel(null);
    static JPanel viewPanel = new JPanel(null);
    static JPanel itemDisplay = new JPanel(null);
    ScrollPane inventory;

    /*
    A boolean to test of the manager is using customer view
    */
    static boolean managerAsCustomer = false;

    /*
    A boolean that will allow the inventory to be reloaded when necessary
    */
    static boolean updatedFromManager = false;
    static boolean updatedFromManagerPerson = false;
    static boolean updatedFromManagerLogon = false;

    /*
    Identifies the manager
    */
    static int managerDetails = 0;

    /*
    Customer selected is used for customer data retrieval
    */
    static int customerSelected = -1;

    /*
    User selected is used for user data retrieval
    */
    static int userSelected = -1;

    /*
    Item selected is used for item data retrieval
    */
    static int itemSelected = -1;

    /*
    Discount selected is used for item data retrieval
    */
    static int discountSelected = -1;

    /*
    The current date placed into a variable
    */
    static LocalDate currentDate = LocalDate.now();

    JComboBox minimumYear;

    JComboBox minimumMonth;

    static JComboBox minimumDay;

    /*
    These int variables will contain the discount's starting year, month, and day.
    */
    int orderStartYear = currentDate.getYear();
    int orderStartMonth = currentDate.getMonthValue();
    static int orderStartDay = currentDate.getDayOfMonth();

    JComboBox maximumYear;

    JComboBox maximumMonth;

    JComboBox maximumDay;

    /*
    These int variables will contain the order's maximum year, month, and day.
    */
    int orderEndingYear = currentDate.getYear();
    int orderEndingMonth = currentDate.getMonthValue();
    int orderEndingDay = currentDate.getDayOfMonth();

    /*
    These strings will be used to ensure the date is adapted properly.
    */
    String orderStartingMonth = "";
    String orderStartingDay = "";
    String orderEndMonth = "";
    String orderEndDay = "";
    String orderStartTime = "" + currentDate;
    String orderEndTime = "" + currentDate;

    static JRadioButton searchForCustomer;
    static JRadioButton searchForManager;
    JLabel orderDateError;

    /*
    The table, scrollpane, column, and data for customers
    */
    static JTable customerjt;
    ScrollPane customerjs;
    static String[] customerCol;
    static Object[][] customerData;

    /*
    The table, scrollpane, column, and data viewed for a manager's convenience
    */
    static JTable managerdiscountjt;
    ScrollPane managerdiscountjs;
    static String[] managerDiscountCol;
    static Object[][] managerDiscountData;

    /*
    The table, scrollpane, column, and data for users
    */
    static JTable personjt;
    ScrollPane personjs;
    static String[] personCol;
    static Object[][] personData;

    /*
    The table, scrollpane, column, and data for login
    */
    static JTable loginjt;
    ScrollPane loginjs;
    static String[] loginCol;
    static Object[][] loginData;

    /*
    Logon name error label
    */
    JLabel customersLogonNameError;

    /*
    The two text fields
    */
    static JTextField customersEmail;
    static JTextField customersPhoneNumber1;
    static JTextField customersPhoneNumber2;
    static JTextField customersPhoneNumber3;

    /*
    A series of booleans that will inform the program if data entered by the user is valid
    */
    boolean loginValid = true;
    boolean passwordValid = true;

    /*
    A boolean only used in the context of a delete operation
    */
    static int personDetailsSelected = -1;

    JTextField personsLoginName;
    JLabel personsLoginNameError;
    JPasswordField personsPassword;
    JLabel personsPasswordError;
    JLabel personsSpecialError;
    JComboBox personsPrefix;
    JTextField personsFirstName;
    JLabel personsFirstNameError;
    JTextField personsMiddleName;
    JTextField personsLastName;
    JLabel personsLastNameError;
    JComboBox personsSuffix;
    JTextField personsAddress1;
    JTextField personsAddress2;
    JTextField personsAddress3;
    JLabel addressError;
    JTextField personsCity;
    JLabel personsCityError;
    JTextField personsZip;
    JLabel personsZipError;
    JComboBox personsState;
    JLabel personsStateError;
    JTextField personsEmail;
    JLabel personsEmailError;
    JTextField personsPhonePrimary1;
    JTextField personsPhonePrimary2;
    JTextField personsPhonePrimary3;
    JTextField personsPhoneSecondary1;
    JTextField personsPhoneSecondary2;
    JTextField personsPhoneSecondary3;
    JLabel personsPhoneError1;
    JLabel personsPhoneError2;
    int questionID1;
    int questionID2;
    int questionID3;
    static JComboBox securityQuestion1;
    static JComboBox securityQuestion2;
    static JComboBox securityQuestion3;
    JTextField securityAnswer1;
    JTextField securityAnswer2;
    JTextField securityAnswer3;
    JLabel answerError;
    JRadioButton userIsCustomer;
    JRadioButton userIsManager;
    JLabel userSelectedWarning;

    /*
    A series of booleans that will inform the program if data entered by the user is valid
    */
    boolean nameValid = true;
    boolean descriptionValid = true;
    boolean heftValid = true;
    boolean hardnessValid = true;
    boolean sizeValid = true;
    boolean weightValid = true;
    boolean retailValid = true;
    boolean costValid = true;
    boolean quantityValid = true;
    boolean restockThresholdValid = true;

    /*
    The label only visible if at least one item's quantity is at or below the restock threshold
    */
    static JLabel needsRestock;

    /*
    The label that access a report consisting of all items
    */
    static JLabel allItems;

    /*
    Text fields, error labels, and radio buttons required to receive the user's input and warn them about invalid entries for items
    */
    static JTextField itemsName;
    static JLabel itemsNameError;
    static JTextArea itemsDescription;
    static JLabel itemsDescriptionError;
    static JRadioButton itemIsIgneous;
    static JRadioButton itemIsSedimentary;
    static JRadioButton itemIsMetamorphic;
    static JRadioButton itemIsStone;
    static JRadioButton itemIsGemstone;
    static JRadioButton itemIsPreciousGemstone;
    static JLabel grainSizeOfItem;
    static JRadioButton itemIsCoarseGrained;
    static JRadioButton itemIsFineGrained;
    static JLabel grainShapeOfItem;
    static JTextField itemsGrainShape;
    static JLabel heftOfItem;
    static JTextField itemsHeft;
    static JLabel itemsHeftError;
    JLabel hardnessOfItem;
    static JTextField itemsHardness;
    static JLabel itemsHardnessError;
    static JLabel sizeOfItem;
    static JTextField itemsSize;
    static JLabel itemsSizeError;
    static JLabel weightOfItem;
    static JTextField itemsWeight;
    static JLabel itemsWeightError;
    static JTextField itemsRetail;
    static JLabel itemsRetailError;
    static JTextField itemsCost;
    static JLabel itemsCostError;
    static JTextField itemsQuantity;
    static JLabel itemsQuantityError;
    static JTextField itemsRestockThreshold;
    static JLabel itemsRestockThresholdError;
    static JLabel itemSelectedWarning;

    int discountItemSelectedID = -1;

    boolean discountCodeValid = true;
    boolean discountDescriptionValid = true;
    boolean percentValid = true;
    boolean amountValid = true;

    JTextField discountsCode;
    JLabel discountsCodeError;
    JTextArea discountsDescription;
    JLabel discountsDescriptionError;
    JRadioButton discountIsCartLevel;
    JRadioButton discountIsItemLevel;
    JRadioButton discountIsPercentage;
    JRadioButton discountIsAmount;
    JLabel percentageOfDiscount;
    JTextField discountsPercentage;
    JLabel discountsPercentageError;
    JLabel amountOfDiscount;
    JLabel amountDollarSign;
    JTextField discountsAmount;
    JLabel discountsAmountError;
    JLabel discountsInventoryID;
    JRadioButton discountStartDate;
    JRadioButton discountStartDateNotPresent;
    JLabel discountSelectedWarning;

    JComboBox startYear;

    JComboBox startMonth;

    static JComboBox startDay;

    /*
    These int variables will contain the discount's starting year, month, and day.
    */
    int discountStartYear = currentDate.getYear();
    int discountStartMonth = currentDate.getMonthValue();
    static int discountStartDay = currentDate.getDayOfMonth();

    JComboBox expireYear;

    JComboBox expireMonth;

    JComboBox expireDay;

    /*
    These int variables will contain the discount's expiration year, month, and day.
    */
    int discountExpirationYear = currentDate.getYear();
    int discountExpirationMonth = currentDate.getMonthValue();
    int discountExpirationDay = currentDate.getDayOfMonth();

    /*
    These strings will be used to ensure the date is adapted properly.
    */
    String startingMonth = "";
    String startingDay = "";
    String expirationMonth = "";
    String expirationDay = "";
    String discountStartTime = "" + currentDate;
    String discountExpirationTime = "" + currentDate;

    JLabel discountDateError;

    /*
    The table, scrollpane, column, and data for discounts
    */
    static JTable discountjt;
    ScrollPane discountjs;
    static String[] discountCol;
    static Object[][] discountData;

    /*
    The table, scrollpane, column, and data for items
    */
    static JTable discountitemsjt;
    ScrollPane discountitemsjs;
    static String[] discountitemsCol;
    static Object[][] discountitemsData;

    /*
    Buttons for editing users
    */
    static JButton addUser;
    static JButton editUser;
    static JButton deleteUser;

    /*
    Buttons for editing items
    */
    static JButton addItem;
    static JButton addImage;
    static JButton editItem;
    static JButton deleteItem;

    /*
    Buttons for adding and editing discounts
    */
    static JButton addDiscount;
    static JButton editDiscount;

    /*
    Buttons present on the manager page
    */
    JButton editUsers;
    JButton editItems;
    JButton editDiscounts;
    JButton logOut;
    JButton returnToView;

    /**
    * The manager view constructor has several different views depending on the manager's
    * aims whether they are editing users, items, or discounts, shopping as a customer, or
    * viewing orders.
    */
    public ManagerView() {

        Color lightCyan = Color.decode("#DFFDFF");
        Color thistle = Color.decode("#D5CBE2");
        Color royalPurple = Color.decode("#8062A7");

        jp.setLayout(new BoxLayout(jp, BoxLayout.X_AXIS));

        panel.setBackground(lightCyan);
        panel.setBounds(0, 0, 450, 523);
        panel.setPreferredSize(new Dimension(375, 0));
        jp.add(panel);

        viewPanel = new JPanel(new BorderLayout());
        viewPanel.setBounds(200, 200, 500, 500);
        viewPanel.setPreferredSize(new Dimension(465, 0));

        JLabel help = new JLabel("Help");
        help.setBounds(340, 0, 50, 20);
        help.setForeground(royalPurple);
        panel.add(help);

        help.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Thread helpSys = new Thread(helpSystem);
                helpSys.start();
            }
        });

        customerCol = new String[]{"Login Name", "First Name", "Last Name", "Email", "Phone Primary", "Phone Secondary"};
        customerjt = new JTable();
        customerjt.setModel(new DefaultTableModel(customerData, customerCol));
        customerjt.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        customerjs = new ScrollPane(customerjt);
        customerjs.setBounds(0, 0, 1075, 572);
        customerjs.setPreferredSize(new Dimension(1075, 572));
        customerjt.setDefaultEditor(Object.class, null);
        customerjs.setVisible(false);
        viewPanel.add(customerjs, BorderLayout.CENTER);

        customerjt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = customerjt.getSelectedRow();
                if (row >= 0) {
                    DefaultTableModel model = (DefaultTableModel) customerjt.getModel();
                    String customerLogonName = (String) model.getValueAt(row, 0);
                    for (int i = 0; i < LoginView.logon.size(); i++) {
                        String logN = LoginView.logon.get(i).logonName;
                        if (customerLogonName.toUpperCase().equals(logN.toUpperCase())) {
                            if (LoginView.logon.get(i).positionTitle.equals("Customer")) {
                                customersLogonNameError.setText("Customer found!");
                                customersLogonNameError.setForeground(Color.GRAY);
                                LoginView.personPosition = i;

                                clearCustomerData();

                                customerjs.setVisible(false);
                                managerdiscountjs.setVisible(false);

                                customerView.jp.remove(CustomerView.cartPanel);
                                CustomerView.cartVisible = false;
                                CustomerView.cart.clear();
                                customerView.jp.validate();

                                CustomerView.itemsSelected = 0;
                                CustomerView.itemSelected = -1;

                                CustomerView.sortedIgn = 0;
                                CustomerView.sortedSed = 0;
                                CustomerView.sortedMet = 0;
                                CustomerView.sortedSto = 0;
                                CustomerView.sortedGem = 0;

                                CustomerView.itemsName.setVisible(false);
                                CustomerView.itemsDescription.setVisible(false);
                                CustomerView.itemRockOrGem.setVisible(false);
                                CustomerView.itemGrainSize.setVisible(false);
                                CustomerView.itemGrainShape.setVisible(false);
                                CustomerView.itemHeft.setVisible(false);
                                CustomerView.itemHardness.setVisible(false);
                                CustomerView.stoneSize.setVisible(false);
                                CustomerView.stoneWeight.setVisible(false);
                                CustomerView.price.setVisible(false);
                                CustomerView.quantity.setVisible(false);
                                CustomerView.addToCart.setVisible(false);
                                CustomerView.returnToSearch.setVisible(false);
                                CustomerView.searchBarEntry.setVisible(true);
                                CustomerView.searchBar.setVisible(true);
                                CustomerView.searchBar.setText("");
                                CustomerView.orderError.setText("");
                                CustomerView.cardNumberEntry.setVisible(true);
                                CustomerView.cardNumber.setVisible(true);
                                CustomerView.securityCodeEntry.setVisible(true);
                                CustomerView.cardSecurityCode.setVisible(true);
                                CustomerView.cardExpireYear.setVisible(true);
                                CustomerView.cardExpirationYear.setVisible(true);
                                CustomerView.cardExpireMonth.setVisible(true);
                                CustomerView.cardExpirationMonth.setVisible(true);
                                CustomerView.cardError.setText("");
                                CustomerView.cardSecurityError.setText("");
                                CustomerView.expirationError.setText("");
                                CustomerView.discountEntry.setVisible(true);
                                CustomerView.discountCode.setVisible(true);
                                CustomerView.discountError.setText("");
                                CustomerView.refreshButton.setVisible(true);
                                CustomerView.igneousButton.setVisible(true);
                                CustomerView.sedimentaryButton.setVisible(true);
                                CustomerView.metamorphicButton.setVisible(true);
                                CustomerView.stoneButton.setVisible(true);
                                CustomerView.gemstoneButton.setVisible(true);
                                CustomerView.checkout.setVisible(true);
                                CustomerView.clearCart.setVisible(true);
                                CustomerView.ordersButton.setVisible(true);

                                customerSelected = LoginView.person.get(i).personID;
                                LoginView.SetPersonID(customerLogonName);
                                for (int l = 0; l < LoginView.person.size(); l++) {
                                    if (LoginView.currentPerson == LoginView.person.get(l).personID) {
                                        CustomerView.personDetails = l;
                                    }
                                }

                                CustomerView.setMax();
                                CustomerView.setMin();

                                managerDiscountData = getManagerDiscountData();
                                DefaultTableModel mdtable = (DefaultTableModel) managerdiscountjt.getModel();
                                mdtable.setDataVector(managerDiscountData, managerDiscountCol);
                                managerdiscountjs.setVisible(true);

                                if (!CustomerView.cartList.get(LoginView.personPosition).isEmpty()) {
                                    customerView.jp.add(CustomerView.cartPanel);
                                    CustomerView.cartPanel.setVisible(true);
                                    CustomerView.cartData = CustomerView.getCartData();
                                    DefaultTableModel carttable = (DefaultTableModel) CustomerView.cartjt.getModel();
                                    carttable.setDataVector(CustomerView.cartData, CustomerView.cartCol);
                                    jp.validate();
                                    jf.setBounds(jf.getX(), jf.getY(), 1730, 610);
                                } else {
                                    jf.setBounds(jf.getX(), jf.getY(), 1466, 610);
                                }

                                /*
                                Updates the inventory for the customer
                                */
                                Thread updData = new Thread(CustomerView.updateData);
                                updData.start();
                                jf.setBounds(jf.getX(), jf.getY(), 1466, 610);
                                managerAsCustomer = true;
                                viewPanel.add(customerView.jp, BorderLayout.EAST);
                                jp.add(viewPanel);
                                customerView.jp.setVisible(true);
                                break;
                            }
                        }
                    }
                }
            }
        });

        managerDiscountCol = new String[]{"Discount Codes"};
        managerdiscountjt = new JTable();
        managerdiscountjt.setModel(new DefaultTableModel(managerDiscountData, managerDiscountCol));
        managerdiscountjt.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        managerdiscountjs = new ScrollPane(managerdiscountjt);
        managerdiscountjs.setBounds(0, 0, 185, 572);
        managerdiscountjs.setPreferredSize(new Dimension(185, 572));
        managerdiscountjt.setDefaultEditor(Object.class, null);
        managerdiscountjs.setVisible(false);
        viewPanel.add(managerdiscountjs, BorderLayout.WEST);

        JLabel typeOfAccount = new JLabel("Manager or Customer");
        typeOfAccount.setBounds(225, 320, 200, 15);
        panel.add(typeOfAccount);

        searchForCustomer = new JRadioButton("Customer");
        searchForCustomer.setBounds(225, 335, 110, 25);
        searchForCustomer.setBackground(null);
        panel.add(searchForCustomer);

        searchForManager = new JRadioButton("Manager");
        searchForManager.setBounds(225, 360, 100, 25);
        searchForManager.setBackground(null);
        panel.add(searchForManager);

        searchForCustomer.addActionListener((e) -> {
            searchForCustomer.setSelected(true);
            searchForManager.setSelected(false);
        }
        );

        searchForManager.addActionListener((e) -> {
            searchForCustomer.setSelected(false);
            searchForManager.setSelected(true);
        }
        );

        JLabel validOrderStartYear = new JLabel("Starting Year");
        validOrderStartYear.setBounds(18, 320, 100, 15);
        panel.add(validOrderStartYear);

        int yearsForList = currentDate.getYear();

        String[] orderStarterYear = {"" + (yearsForList - 1), "" + yearsForList, "" + (yearsForList + 1), "" + (yearsForList + 2), "" + (yearsForList + 3), "" + (yearsForList + 5), "" + (yearsForList + 6), "" + (yearsForList + 7)};
        minimumYear = new JComboBox(orderStarterYear);
        minimumYear.setBounds(27, 335, 55, 20);
        panel.add(minimumYear);

        JLabel validOrderStartMonth = new JLabel("Starting Month");
        validOrderStartMonth.setBounds(10, 365, 110, 15);
        panel.add(validOrderStartMonth);

        String[] orderMonths = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        minimumMonth = new JComboBox(orderMonths);
        minimumMonth.setBounds(27, 380, 55, 20);
        minimumMonth.setSelectedIndex(orderStartMonth - 1);
        panel.add(minimumMonth);

        Calendar orderStartCalendar = new GregorianCalendar(Integer.parseInt(minimumYear.getSelectedItem().toString()), Integer.parseInt(minimumMonth.getSelectedItem().toString()), 1);
        String[] orderStartDayInitial = new String[orderStartCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)];

        JLabel validOrderStartDay = new JLabel("Starting Day");
        validOrderStartDay.setBounds(19, 410, 100, 15);
        panel.add(validOrderStartDay);

        minimumDay = new JComboBox(orderStartDayInitial);
        minimumDay.setBounds(27, 425, 55, 20);
        panel.add(minimumDay);

        minimumDay.addActionListener((e) -> {
            if (minimumDay.getSelectedItem() != null) {
                orderStartDay = Integer.parseInt(minimumDay.getSelectedItem().toString());
            }
        });

        minimumMonth.addActionListener((e) -> {
            setOrderStartDay();
        });

        minimumYear.addActionListener((e) -> {
            orderStartYear = Integer.parseInt(minimumYear.getSelectedItem().toString());
            setOrderStartDay();
        });

        JLabel validOrderEndYear = new JLabel("Ending Year");
        validOrderEndYear.setBounds(120, 320, 100, 15);
        panel.add(validOrderEndYear);

        String[] orderEnderYear = {"" + yearsForList, "" + (yearsForList + 1), "" + (yearsForList + 2), "" + (yearsForList + 3), "" + (yearsForList + 5), "" + (yearsForList + 6), "" + (yearsForList + 7)};
        maximumYear = new JComboBox(orderEnderYear);
        maximumYear.setBounds(127, 335, 55, 20);
        panel.add(maximumYear);

        JLabel validOrderEndMonth = new JLabel("Ending Month");
        validOrderEndMonth.setBounds(117, 365, 105, 15);
        panel.add(validOrderEndMonth);

        maximumMonth = new JComboBox(orderMonths);
        maximumMonth.setBounds(127, 380, 55, 20);
        maximumMonth.setSelectedIndex(orderEndingMonth - 1);
        panel.add(maximumMonth);

        Calendar orderEndCalendar = new GregorianCalendar(Integer.parseInt(maximumYear.getSelectedItem().toString()), Integer.parseInt(maximumMonth.getSelectedItem().toString()), 1);
        String[] orderEndDayInitial = new String[orderEndCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)];

        JLabel validOrderEndDay = new JLabel("Ending Day");
        validOrderEndDay.setBounds(124, 410, 100, 15);
        panel.add(validOrderEndDay);

        maximumDay = new JComboBox(orderEndDayInitial);
        maximumDay.setBounds(127, 425, 55, 20);
        panel.add(maximumDay);

        maximumDay.addActionListener((e) -> {
            if (maximumDay.getSelectedItem() != null) {
                orderEndingDay = Integer.parseInt(maximumDay.getSelectedItem().toString());
            }
        }
        );

        maximumMonth.addActionListener((e) -> {
            setOrderEndDay();
        }
        );

        maximumYear.addActionListener((e) -> {
            orderEndingYear = Integer.parseInt(maximumYear.getSelectedItem().toString());
            setOrderEndDay();
        }
        );

        orderDateError = new JLabel("");
        orderDateError.setBounds(10, 285, 250, 50);
        orderDateError.setForeground(Color.red);
        panel.add(orderDateError);

        resetOrderDates();

        JLabel logonNameOfCustomer = new JLabel("Customer Logon Name");
        logonNameOfCustomer.setBounds(10, 20, 135, 15);
        panel.add(logonNameOfCustomer);

        JTextField customersLogonName = new JTextField("");
        customersLogonName.setBounds(10, 35, 210, 20);
        panel.add(customersLogonName);

        customersLogonNameError = new JLabel("");
        customersLogonNameError.setBounds(160, 20, 200, 15);
        customersLogonNameError.setForeground(Color.red);
        panel.add(customersLogonNameError);

        JLabel emailOfCustomer = new JLabel("Customer Email");
        emailOfCustomer.setBounds(10, 70, 135, 15);
        panel.add(emailOfCustomer);

        customersEmail = new JTextField("");
        customersEmail.setBounds(10, 85, 210, 20);
        panel.add(customersEmail);

        JLabel customersEmailError = new JLabel("");
        customersEmailError.setBounds(160, 70, 200, 15);
        customersEmailError.setForeground(Color.red);
        panel.add(customersEmailError);

        JLabel phoneNumberOfCustomer = new JLabel("Customer Phone #");
        phoneNumberOfCustomer.setBounds(10, 120, 135, 15);
        panel.add(phoneNumberOfCustomer);

        customersPhoneNumber1 = new JTextField("");
        customersPhoneNumber1.setBounds(10, 135, 67, 20);
        panel.add(customersPhoneNumber1);

        customersPhoneNumber2 = new JTextField("");
        customersPhoneNumber2.setBounds(77, 135, 66, 20);
        panel.add(customersPhoneNumber2);

        customersPhoneNumber3 = new JTextField("");
        customersPhoneNumber3.setBounds(143, 135, 67, 20);
        panel.add(customersPhoneNumber3);

        JLabel customersPhoneNumberError = new JLabel("");
        customersPhoneNumberError.setBounds(160, 120, 200, 15);
        customersPhoneNumberError.setForeground(Color.red);
        panel.add(customersPhoneNumberError);

        JButton logonCustomerSelect = new JButton("Select Customer");
        logonCustomerSelect.setBounds(225, 35, 135, 20);
        logonCustomerSelect.setBackground(thistle);
        logonCustomerSelect.addActionListener((e) -> {
            String customerLogonName = customersLogonName.getText();
            customersLogonNameError.setText("");
            customersLogonNameError.setForeground(Color.red);
            customersEmailError.setText("");
            customersPhoneNumberError.setText("");
            if (!customerLogonName.equals("")) {
                boolean customerFound = false;
                for (int i = 0; i < LoginView.logon.size(); i++) {
                    String logN = LoginView.logon.get(i).logonName;
                    if (customerLogonName.toUpperCase().equals(logN.toUpperCase())) {
                        if (LoginView.logon.get(i).positionTitle.equals("Customer")) {
                            customersLogonNameError.setText("Customer found!");
                            customersLogonNameError.setForeground(Color.GRAY);
                            LoginView.personPosition = i;

                            clearCustomerData();

                            customerjs.setVisible(false);
                            managerdiscountjs.setVisible(false);

                            customerView.jp.remove(CustomerView.cartPanel);
                            CustomerView.cartVisible = false;
                            CustomerView.cart.clear();
                            customerView.jp.validate();

                            CustomerView.itemsSelected = 0;
                            CustomerView.itemSelected = -1;

                            CustomerView.sortedIgn = 0;
                            CustomerView.sortedSed = 0;
                            CustomerView.sortedMet = 0;
                            CustomerView.sortedSto = 0;
                            CustomerView.sortedGem = 0;

                            CustomerView.itemsName.setVisible(false);
                            CustomerView.itemsDescription.setVisible(false);
                            CustomerView.itemRockOrGem.setVisible(false);
                            CustomerView.itemGrainSize.setVisible(false);
                            CustomerView.itemGrainShape.setVisible(false);
                            CustomerView.itemHeft.setVisible(false);
                            CustomerView.itemHardness.setVisible(false);
                            CustomerView.stoneSize.setVisible(false);
                            CustomerView.stoneWeight.setVisible(false);
                            CustomerView.price.setVisible(false);
                            CustomerView.quantity.setVisible(false);
                            CustomerView.addToCart.setVisible(false);
                            CustomerView.returnToSearch.setVisible(false);
                            CustomerView.searchBarEntry.setVisible(true);
                            CustomerView.searchBar.setVisible(true);
                            CustomerView.searchBar.setText("");
                            CustomerView.orderError.setText("");
                            CustomerView.cardNumberEntry.setVisible(true);
                            CustomerView.cardNumber.setVisible(true);
                            CustomerView.securityCodeEntry.setVisible(true);
                            CustomerView.cardSecurityCode.setVisible(true);
                            CustomerView.cardExpireYear.setVisible(true);
                            CustomerView.cardExpirationYear.setVisible(true);
                            CustomerView.cardExpireMonth.setVisible(true);
                            CustomerView.cardExpirationMonth.setVisible(true);
                            CustomerView.cardError.setText("");
                            CustomerView.cardSecurityError.setText("");
                            CustomerView.expirationError.setText("");
                            CustomerView.discountEntry.setVisible(true);
                            CustomerView.discountCode.setVisible(true);
                            CustomerView.discountError.setText("");
                            CustomerView.refreshButton.setVisible(true);
                            CustomerView.igneousButton.setVisible(true);
                            CustomerView.sedimentaryButton.setVisible(true);
                            CustomerView.metamorphicButton.setVisible(true);
                            CustomerView.stoneButton.setVisible(true);
                            CustomerView.gemstoneButton.setVisible(true);
                            CustomerView.checkout.setVisible(true);
                            CustomerView.clearCart.setVisible(true);

                            customerFound = true;
                            customerSelected = LoginView.person.get(i).personID;
                            LoginView.SetPersonID(customerLogonName);
                            for (int l = 0; l < LoginView.person.size(); l++) {
                                if (LoginView.currentPerson == LoginView.person.get(l).personID) {
                                    CustomerView.personDetails = l;
                                }
                            }

                            CustomerView.setMax();
                            CustomerView.setMin();

                            managerDiscountData = getManagerDiscountData();
                            DefaultTableModel mdtable = (DefaultTableModel) managerdiscountjt.getModel();
                            mdtable.setDataVector(managerDiscountData, managerDiscountCol);
                            managerdiscountjs.setVisible(true);

                            if (!CustomerView.cartList.get(LoginView.personPosition).isEmpty()) {
                                customerView.jp.add(CustomerView.cartPanel);
                                CustomerView.cartPanel.setVisible(true);
                                CustomerView.cartData = CustomerView.getCartData();
                                DefaultTableModel carttable = (DefaultTableModel) CustomerView.cartjt.getModel();
                                carttable.setDataVector(CustomerView.cartData, CustomerView.cartCol);
                                jp.validate();
                                jf.setBounds(jf.getX(), jf.getY(), 1730, 610);
                            } else {
                                jf.setBounds(jf.getX(), jf.getY(), 1466, 610);
                            }

                            /*
                            Updates the inventory for the customer
                            */
                            Thread updData = new Thread(CustomerView.updateData);
                            updData.start();
                            managerAsCustomer = true;
                            viewPanel.add(customerView.jp, BorderLayout.EAST);
                            jp.add(viewPanel);
                            customerView.jp.setVisible(true);
                            break;
                        }
                    }
                }
                if (!customerFound) {
                    customersLogonNameError.setText("Customer not found");
                }
            } else {
                customersLogonNameError.setText("Enter customer login name");
            }
        }
        );
        panel.add(logonCustomerSelect);

        JButton emailCustomerSelect = new JButton("Select by Email");
        emailCustomerSelect.setBounds(225, 85, 135, 20);
        emailCustomerSelect.setBackground(thistle);
        emailCustomerSelect.addActionListener((e) -> {
            customersLogonNameError.setText("");
            customersEmailError.setText("");
            customersEmailError.setForeground(Color.red);
            customersPhoneNumberError.setText("");
            if (!customersEmail.getText().isEmpty()) {
                int timesEmailFound = 0;
                int customerValue = customerSelected;
                String customerLogonName = "";
                for (int i = 0; i < LoginView.person.size(); i++) {
                    if (customersEmail.getText().equals(LoginView.person.get(i).email)) {
                        customerSelected = LoginView.person.get(i).personID;
                        timesEmailFound++;
                    }
                }
                if (timesEmailFound == 1) {
                    for (int i = 0; i < LoginView.logon.size(); i++) {
                        if (customerSelected == LoginView.logon.get(i).personID && LoginView.logon.get(i).positionTitle.equals("Customer")) {
                            customersEmailError.setText("Customer found!");
                            customersEmailError.setForeground(Color.GRAY);
                            LoginView.personPosition = i;

                            clearCustomerData();

                            customerjs.setVisible(false);
                            managerdiscountjs.setVisible(false);

                            customerView.jp.remove(CustomerView.cartPanel);
                            CustomerView.cartVisible = false;
                            CustomerView.cart.clear();
                            customerView.jp.validate();

                            CustomerView.itemsSelected = 0;
                            CustomerView.itemSelected = -1;

                            CustomerView.sortedIgn = 0;
                            CustomerView.sortedSed = 0;
                            CustomerView.sortedMet = 0;
                            CustomerView.sortedSto = 0;
                            CustomerView.sortedGem = 0;

                            CustomerView.itemsName.setVisible(false);
                            CustomerView.itemsDescription.setVisible(false);
                            CustomerView.itemRockOrGem.setVisible(false);
                            CustomerView.itemGrainSize.setVisible(false);
                            CustomerView.itemGrainShape.setVisible(false);
                            CustomerView.itemHeft.setVisible(false);
                            CustomerView.itemHardness.setVisible(false);
                            CustomerView.stoneSize.setVisible(false);
                            CustomerView.stoneWeight.setVisible(false);
                            CustomerView.price.setVisible(false);
                            CustomerView.quantity.setVisible(false);
                            CustomerView.addToCart.setVisible(false);
                            CustomerView.returnToSearch.setVisible(false);
                            CustomerView.searchBarEntry.setVisible(true);
                            CustomerView.searchBar.setVisible(true);
                            CustomerView.searchBar.setText("");
                            CustomerView.orderError.setText("");
                            CustomerView.cardNumberEntry.setVisible(true);
                            CustomerView.cardNumber.setVisible(true);
                            CustomerView.securityCodeEntry.setVisible(true);
                            CustomerView.cardSecurityCode.setVisible(true);
                            CustomerView.cardExpireYear.setVisible(true);
                            CustomerView.cardExpirationYear.setVisible(true);
                            CustomerView.cardExpireMonth.setVisible(true);
                            CustomerView.cardExpirationMonth.setVisible(true);
                            CustomerView.cardError.setText("");
                            CustomerView.cardSecurityError.setText("");
                            CustomerView.expirationError.setText("");
                            CustomerView.discountEntry.setVisible(true);
                            CustomerView.discountCode.setVisible(true);
                            CustomerView.discountError.setText("");
                            CustomerView.refreshButton.setVisible(true);
                            CustomerView.igneousButton.setVisible(true);
                            CustomerView.sedimentaryButton.setVisible(true);
                            CustomerView.metamorphicButton.setVisible(true);
                            CustomerView.stoneButton.setVisible(true);
                            CustomerView.gemstoneButton.setVisible(true);
                            CustomerView.checkout.setVisible(true);
                            CustomerView.clearCart.setVisible(true);

                            customerLogonName = LoginView.logon.get(i).logonName;
                            LoginView.SetPersonID(customerLogonName);
                            for (int l = 0; l < LoginView.person.size(); l++) {
                                if (LoginView.currentPerson == LoginView.person.get(l).personID) {
                                    CustomerView.personDetails = l;
                                }
                            }

                            CustomerView.setMax();
                            CustomerView.setMin();

                            managerDiscountData = getManagerDiscountData();
                            DefaultTableModel mdtable = (DefaultTableModel) managerdiscountjt.getModel();
                            mdtable.setDataVector(managerDiscountData, managerDiscountCol);
                            managerdiscountjs.setVisible(true);

                            if (!CustomerView.cartList.get(LoginView.personPosition).isEmpty()) {
                                customerView.jp.add(CustomerView.cartPanel);
                                CustomerView.cartPanel.setVisible(true);
                                CustomerView.cartData = CustomerView.getCartData();
                                DefaultTableModel carttable = (DefaultTableModel) CustomerView.cartjt.getModel();
                                carttable.setDataVector(CustomerView.cartData, CustomerView.cartCol);
                                jp.validate();
                                jf.setBounds(jf.getX(), jf.getY(), 1730, 610);
                            } else {
                                jf.setBounds(jf.getX(), jf.getY(), 1466, 610);
                            }

                            /*
                            Updates the inventory for the customer
                            */
                            Thread updData = new Thread(CustomerView.updateData);
                            updData.start();
                            managerAsCustomer = true;
                            viewPanel.add(customerView.jp, BorderLayout.EAST);
                            jp.add(viewPanel);
                            customerView.jp.setVisible(true);
                            break;
                        }
                    }
                } else if (timesEmailFound > 1) {
                    customerView.jp.setVisible(false);

                    customerSelected = customerValue;
                    customerData = getCustomerData(false);
                    DefaultTableModel ctable = (DefaultTableModel) customerjt.getModel();
                    ctable.setDataVector(customerData, customerCol);
                    customerjs.setVisible(true);
                    jp.add(viewPanel);
                    jf.setBounds(jf.getX(), jf.getY(), 1466, 610);
                } else if (timesEmailFound == 0) {
                    customersEmailError.setText("Email not found");
                }
            } else {
                customersEmailError.setText("Valid Email: FirstL@place.com");
            }
        }
        );
        panel.add(emailCustomerSelect);

        JButton phoneNumberCustomerSelect = new JButton("Select by Phone");
        phoneNumberCustomerSelect.setBounds(225, 135, 135, 20);
        phoneNumberCustomerSelect.setBackground(thistle);
        phoneNumberCustomerSelect.addActionListener((e) -> {
            customersLogonNameError.setText("");
            customersEmailError.setText("");
            customersPhoneNumberError.setText("");
            customersPhoneNumberError.setForeground(Color.red);
            if (!customersPhoneNumber1.getText().isEmpty() && !customersPhoneNumber2.getText().isEmpty() && !customersPhoneNumber3.getText().isEmpty()) {
                String customersPhoneNumber = "(" + customersPhoneNumber1.getText() + ")-" + customersPhoneNumber2.getText() + "-" + customersPhoneNumber3.getText();
                int timesPhoneNumberFound = 0;
                int customerValue = customerSelected;
                String customerLogonName = "";
                for (int i = 0; i < LoginView.person.size(); i++) {
                    if (customersPhoneNumber.equals(LoginView.person.get(i).phonePrimary) || customersPhoneNumber.equals(LoginView.person.get(i).phoneSecondary)) {
                        customerSelected = LoginView.person.get(i).personID;
                        timesPhoneNumberFound++;
                    }
                }
                if (timesPhoneNumberFound == 1) {
                    for (int i = 0; i < LoginView.logon.size(); i++) {
                        if (customerSelected == LoginView.logon.get(i).personID && LoginView.logon.get(i).positionTitle.equals("Customer")) {
                            customersPhoneNumberError.setText("Customer found!");
                            customersPhoneNumberError.setForeground(Color.GRAY);
                            LoginView.personPosition = i;

                            clearCustomerData();

                            customerjs.setVisible(false);
                            managerdiscountjs.setVisible(false);

                            customerView.jp.remove(CustomerView.cartPanel);
                            CustomerView.cartVisible = false;
                            CustomerView.cart.clear();
                            customerView.jp.validate();

                            CustomerView.itemsSelected = 0;
                            CustomerView.itemSelected = -1;

                            CustomerView.sortedIgn = 0;
                            CustomerView.sortedSed = 0;
                            CustomerView.sortedMet = 0;
                            CustomerView.sortedSto = 0;
                            CustomerView.sortedGem = 0;

                            CustomerView.itemsName.setVisible(false);
                            CustomerView.itemsDescription.setVisible(false);
                            CustomerView.itemRockOrGem.setVisible(false);
                            CustomerView.itemGrainSize.setVisible(false);
                            CustomerView.itemGrainShape.setVisible(false);
                            CustomerView.itemHeft.setVisible(false);
                            CustomerView.itemHardness.setVisible(false);
                            CustomerView.stoneSize.setVisible(false);
                            CustomerView.stoneWeight.setVisible(false);
                            CustomerView.price.setVisible(false);
                            CustomerView.quantity.setVisible(false);
                            CustomerView.addToCart.setVisible(false);
                            CustomerView.returnToSearch.setVisible(false);
                            CustomerView.searchBarEntry.setVisible(true);
                            CustomerView.searchBar.setVisible(true);
                            CustomerView.searchBar.setText("");
                            CustomerView.orderError.setText("");
                            CustomerView.orderError.setText("");
                            CustomerView.cardNumberEntry.setVisible(true);
                            CustomerView.cardNumber.setVisible(true);
                            CustomerView.securityCodeEntry.setVisible(true);
                            CustomerView.cardSecurityCode.setVisible(true);
                            CustomerView.cardExpireYear.setVisible(true);
                            CustomerView.cardExpirationYear.setVisible(true);
                            CustomerView.cardExpireMonth.setVisible(true);
                            CustomerView.cardExpirationMonth.setVisible(true);
                            CustomerView.cardError.setText("");
                            CustomerView.cardSecurityError.setText("");
                            CustomerView.expirationError.setText("");
                            CustomerView.discountEntry.setVisible(true);
                            CustomerView.discountCode.setVisible(true);
                            CustomerView.discountError.setText("");
                            CustomerView.refreshButton.setVisible(true);
                            CustomerView.igneousButton.setVisible(true);
                            CustomerView.sedimentaryButton.setVisible(true);
                            CustomerView.metamorphicButton.setVisible(true);
                            CustomerView.stoneButton.setVisible(true);
                            CustomerView.gemstoneButton.setVisible(true);
                            CustomerView.checkout.setVisible(true);
                            CustomerView.clearCart.setVisible(true);

                            customerLogonName = LoginView.logon.get(i).logonName;
                            LoginView.SetPersonID(customerLogonName);
                            for (int l = 0; l < LoginView.person.size(); l++) {
                                if (LoginView.currentPerson == LoginView.person.get(l).personID) {
                                    CustomerView.personDetails = l;
                                }
                            }

                            CustomerView.setMax();
                            CustomerView.setMin();

                            managerDiscountData = getManagerDiscountData();
                            DefaultTableModel mdtable = (DefaultTableModel) managerdiscountjt.getModel();
                            mdtable.setDataVector(managerDiscountData, managerDiscountCol);
                            managerdiscountjs.setVisible(true);

                            if (!CustomerView.cartList.get(LoginView.personPosition).isEmpty()) {
                                customerView.jp.add(CustomerView.cartPanel);
                                CustomerView.cartPanel.setVisible(true);
                                CustomerView.cartData = CustomerView.getCartData();
                                DefaultTableModel carttable = (DefaultTableModel) CustomerView.cartjt.getModel();
                                carttable.setDataVector(CustomerView.cartData, CustomerView.cartCol);
                                jp.validate();
                                jf.setBounds(jf.getX(), jf.getY(), 1730, 610);
                            } else {
                                jf.setBounds(jf.getX(), jf.getY(), 1466, 610);
                            }

                            /*
                            Updates the inventory for the customer
                            */
                            Thread updData = new Thread(CustomerView.updateData);
                            updData.start();
                            managerAsCustomer = true;
                            viewPanel.add(customerView.jp, BorderLayout.EAST);
                            jp.add(viewPanel);
                            customerView.jp.setVisible(true);
                            break;
                        }
                    }
                } else if (timesPhoneNumberFound > 1) {
                    customerView.jp.setVisible(false);

                    customerSelected = customerValue;
                    customerData = getCustomerData(true);
                    DefaultTableModel ctable = (DefaultTableModel) customerjt.getModel();
                    ctable.setDataVector(customerData, customerCol);
                    customerjs.setVisible(true);
                    jp.add(viewPanel);
                    jf.setBounds(jf.getX(), jf.getY(), 1466, 610);
                } else if (timesPhoneNumberFound == 0) {
                    customersPhoneNumberError.setText("Phone number not found");
                }
            } else {
                customersPhoneNumberError.setText("Valid phone number: ###-###-####");
            }
        }
        );
        panel.add(phoneNumberCustomerSelect);

        /*
        This button searches for orders between the currently selected dates
        */
        JButton salesDetails = new JButton("Sale Details");
        salesDetails.setBounds(8, 460, 115, 50);
        salesDetails.setBackground(thistle);
        salesDetails.addActionListener((e) -> {
            if (customerSelected == -1) {
                Thread genSales = new Thread(generalSalesDetails);
                genSales.start();
            } else {
                Thread customSales = new Thread(customerSalesDetails);
                customSales.start();
            }
        });
        panel.add(salesDetails);

        /*
        This button searches for all customers or all managers
        */
        JButton userDetails = new JButton("User Details");
        userDetails.setBounds(210, 393, 150, 50);
        userDetails.setBackground(thistle);
        userDetails.addActionListener((e) -> {
            if (searchForCustomer.isSelected()) {
                Thread customReport = new Thread(customerReport);
                customReport.start();
            } else {
                Thread manageReport = new Thread(managerReport);
                manageReport.start();
            }
        });
        panel.add(userDetails);

        JButton resetCustomer = new JButton("Reset");
        resetCustomer.setBounds(130, 517, 115, 50);
        resetCustomer.setBackground(thistle);
        resetCustomer.addActionListener((e) -> {
            customerSelected = -1;
            clearCustomerData();
            managerAsCustomer = false;
            customerView.jp.setVisible(false);

            resetOrderDates();

            orderDateError.setText("");
            customersLogonName.setText("");
            customersLogonNameError.setText("");
            customersEmail.setText("");
            customersEmailError.setText("");
            customersPhoneNumber1.setText("");
            customersPhoneNumber2.setText("");
            customersPhoneNumber3.setText("");
            customersPhoneNumberError.setText("");

            searchForCustomer.setSelected(true);
            searchForManager.setSelected(false);

            customerjs.setVisible(false);
            managerdiscountjs.setVisible(false);
            jp.remove(viewPanel);

            jf.setBounds(jf.getX(), jf.getY(), 390, 610);
        }
        );
        panel.add(resetCustomer);

        /*--------------------------------------------------USERS SECTION--------------------------------------------------*/
        personCol = new String[]{"First Name", "Middle Name", "Last Name"};
        personjt = new JTable();
        personjt.setModel(new DefaultTableModel(personData, personCol));
        personjt.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        personjs = new ScrollPane(personjt);
        personjs.setBounds(0, 0, 400, 572);
        personjs.setPreferredSize(new Dimension(400, 572));
        personjt.setDefaultEditor(Object.class, null);
        personjs.setVisible(false);
        viewPanel.add(personjs, BorderLayout.EAST);

        personjt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                personsLoginNameError.setText("");
                personsPasswordError.setText("");
                personsFirstNameError.setText("");
                personsLastNameError.setText("");
                personsCityError.setText("");
                personsZipError.setText("");
                personsStateError.setText("");
                personsEmailError.setText("");
                personsPhoneError1.setText("");
                personsPhoneError2.setText("");
                answerError.setText("");
                userSelected = personjt.getSelectedRow();
                for (int i = 0; i < LoginView.person.size(); i++) {
                    if (userSelected == i) {
                        personsLoginName.setText(LoginView.logon.get(i).logonName);
                        personsPassword.setText(LoginView.logon.get(i).password);
                        personsPrefix.setSelectedItem(LoginView.person.get(i).title);
                        personsFirstName.setText(LoginView.person.get(i).nameFirst);
                        personsMiddleName.setText(LoginView.person.get(i).nameMiddle);
                        personsLastName.setText(LoginView.person.get(i).nameLast);
                        personsSuffix.setSelectedItem(LoginView.person.get(i).suffix);
                        personsAddress1.setText(LoginView.person.get(i).address1);
                        personsAddress2.setText(LoginView.person.get(i).address2);
                        personsAddress3.setText(LoginView.person.get(i).address3);
                        personsCity.setText(LoginView.person.get(i).city);
                        personsZip.setText(LoginView.person.get(i).zipCode);
                        personsState.setSelectedItem(LoginView.person.get(i).state);
                        personsEmail.setText(LoginView.person.get(i).email);
                        if (LoginView.person.get(i).phonePrimary.length() == 14) {
                            personsPhonePrimary1.setText(LoginView.person.get(i).phonePrimary.substring(1, 4));
                            personsPhonePrimary2.setText(LoginView.person.get(i).phonePrimary.substring(6, 9));
                            personsPhonePrimary3.setText(LoginView.person.get(i).phonePrimary.substring(10, 14));
                        } else {
                            personsPhonePrimary1.setText("");
                            personsPhonePrimary2.setText("");
                            personsPhonePrimary3.setText("");
                        }
                        if (LoginView.person.get(i).phoneSecondary.length() == 14) {
                            personsPhoneSecondary1.setText(LoginView.person.get(i).phoneSecondary.substring(1, 4));
                            personsPhoneSecondary2.setText(LoginView.person.get(i).phoneSecondary.substring(6, 9));
                            personsPhoneSecondary3.setText(LoginView.person.get(i).phoneSecondary.substring(10, 14));
                        } else {
                            personsPhoneSecondary1.setText("");
                            personsPhoneSecondary2.setText("");
                            personsPhoneSecondary3.setText("");
                        }
                        securityAnswer1.setText(LoginView.logon.get(i).firstChallengeAnswer);
                        securityAnswer2.setText(LoginView.logon.get(i).secondChallengeAnswer);
                        securityAnswer3.setText(LoginView.logon.get(i).thirdChallengeAnswer);
                        if (LoginView.logon.get(i).positionTitle.equals("Customer")) {
                            userIsCustomer.setSelected(true);
                            userIsManager.setSelected(false);
                        } else if (LoginView.logon.get(i).positionTitle.equals("Manager")) {
                            userIsCustomer.setSelected(false);
                            userIsManager.setSelected(true);
                        }
                        userSelectedWarning.setText("User Selected: " + userSelected);
                        userSelectedWarning.setForeground(Color.GRAY);
                        break;
                    }
                }
            }
        }
        );

        loginCol = new String[]{"Login Name", "Password", "Position Title"};
        loginjt = new JTable();
        loginjt.setModel(new DefaultTableModel(loginData, loginCol));
        loginjt.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        loginjs = new ScrollPane(loginjt);
        loginjs.setBounds(0, 0, 402, 572);
        loginjs.setPreferredSize(new Dimension(400, 572));
        loginjt.setDefaultEditor(Object.class, null);
        loginjs.setVisible(false);
        viewPanel.add(loginjs, BorderLayout.WEST);

        loginjt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                personsLoginNameError.setText("");
                personsPasswordError.setText("");
                personsFirstNameError.setText("");
                personsLastNameError.setText("");
                personsCityError.setText("");
                personsZipError.setText("");
                personsStateError.setText("");
                personsEmailError.setText("");
                personsPhoneError1.setText("");
                personsPhoneError2.setText("");
                answerError.setText("");
                userSelected = loginjt.getSelectedRow();
                for (int i = 0; i < LoginView.logon.size(); i++) {
                    if (userSelected == i) {
                        personsLoginName.setText(LoginView.logon.get(i).logonName);
                        personsPassword.setText(LoginView.logon.get(i).password);
                        personsPrefix.setSelectedItem(LoginView.person.get(i).title);
                        personsFirstName.setText(LoginView.person.get(i).nameFirst);
                        personsMiddleName.setText(LoginView.person.get(i).nameMiddle);
                        personsLastName.setText(LoginView.person.get(i).nameLast);
                        personsSuffix.setSelectedItem(LoginView.person.get(i).suffix);
                        personsAddress1.setText(LoginView.person.get(i).address1);
                        personsAddress2.setText(LoginView.person.get(i).address2);
                        personsAddress3.setText(LoginView.person.get(i).address3);
                        personsCity.setText(LoginView.person.get(i).city);
                        personsZip.setText(LoginView.person.get(i).zipCode);
                        personsState.setSelectedItem(LoginView.person.get(i).state);
                        personsEmail.setText(LoginView.person.get(i).email);
                        if (LoginView.person.get(i).phonePrimary.length() == 14) {
                            personsPhonePrimary1.setText(LoginView.person.get(i).phonePrimary.substring(1, 4));
                            personsPhonePrimary2.setText(LoginView.person.get(i).phonePrimary.substring(6, 9));
                            personsPhonePrimary3.setText(LoginView.person.get(i).phonePrimary.substring(10, 14));
                        } else {
                            personsPhonePrimary1.setText("");
                            personsPhonePrimary2.setText("");
                            personsPhonePrimary3.setText("");
                        }
                        if (LoginView.person.get(i).phoneSecondary.length() == 14) {
                            personsPhoneSecondary1.setText(LoginView.person.get(i).phoneSecondary.substring(1, 4));
                            personsPhoneSecondary2.setText(LoginView.person.get(i).phoneSecondary.substring(6, 9));
                            personsPhoneSecondary3.setText(LoginView.person.get(i).phoneSecondary.substring(10, 14));
                        } else {
                            personsPhoneSecondary1.setText("");
                            personsPhoneSecondary2.setText("");
                            personsPhoneSecondary3.setText("");
                        }
                        securityAnswer1.setText(LoginView.logon.get(i).firstChallengeAnswer);
                        securityAnswer2.setText(LoginView.logon.get(i).secondChallengeAnswer);
                        securityAnswer3.setText(LoginView.logon.get(i).thirdChallengeAnswer);
                        if (LoginView.logon.get(i).positionTitle.equals("Customer")) {
                            userIsCustomer.setSelected(true);
                            userIsManager.setSelected(false);
                        } else if (LoginView.logon.get(i).positionTitle.equals("Manager")) {
                            userIsCustomer.setSelected(false);
                            userIsManager.setSelected(true);
                        }
                        userSelectedWarning.setText("User Selected: " + userSelected);
                        userSelectedWarning.setForeground(Color.GRAY);
                        break;
                    }
                }
            }
        }
        );

        JLabel loginNameOfPerson = new JLabel("Login Name");
        loginNameOfPerson.setBounds(75, 10, loginNameOfPerson.getPreferredSize().width, loginNameOfPerson.getPreferredSize().height);
        loginNameOfPerson.setVisible(false);
        panel.add(loginNameOfPerson);

        personsLoginName = new JTextField("");
        personsLoginName.setBounds(75, 25, 200, 20);
        personsLoginName.setVisible(false);
        panel.add(personsLoginName);

        personsLoginName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                loginValid = true;
                String logonName = personsLoginName.getText();
                personsLoginNameError.setText("");
                /*
                If statement ensuring the user entered a login name
                */
                if (!logonName.isEmpty()) {
                    /*
                    If statement ensuring the login name does not begin with a number
                    */
                    if (!logonName.matches("^[0-9].*")) {
                        /*
                        If statement ensuring the login name does not contain any special characters
                        */
                        boolean specialChar = false;
                        for (char c : logonName.toCharArray()) {
                            if (!Character.isLetterOrDigit(c) && !Character.isWhitespace(c)) {
                                specialChar = true;
                            }
                        }
                        if (!specialChar) {
                            boolean whitespace = false;
                            /*
                            Line of code ensuring the login name does not contain a space at the start or end
                            */
                            logonName = logonName.trim();
                            personsLoginName.setText(logonName);

                            /*
                            Ensures no whitespaces are present in the password
                            */
                            for (char c : logonName.toCharArray()) {
                                if (Character.isWhitespace(c)) {
                                    whitespace = true;
                                }
                            }

                            if (!whitespace) {
                                /*
                                If statement ensuring the login name is not less than 8 characters or more than 20
                                */
                                if (logonName.length() >= 8 && logonName.length() <= 20) {
                                } else if (logonName.length() < 8) {
                                    loginValid = false;
                                    personsLoginNameError.setText("Below 8 letters");
                                } else if (logonName.length() > 20) {
                                    personsLoginNameError.setText("Above 20 letters");
                                }
                            } else {
                                loginValid = false;
                                personsLoginNameError.setText("Whitespace present");
                            }
                        } else {
                            loginValid = false;
                            personsLoginNameError.setText("No special chars");
                        }
                    } else {
                        loginValid = false;
                        personsLoginNameError.setText("Starts with number");
                    }
                } else {
                    loginValid = false;
                    personsLoginNameError.setText("Username required");
                }
            }
        }
        );

        personsLoginNameError = new JLabel("");
        personsLoginNameError.setBounds(150, 10, 300, 15);
        personsLoginNameError.setForeground(Color.red);
        panel.add(personsLoginNameError);

        JLabel passwordOfPerson = new JLabel("Password");
        passwordOfPerson.setBounds(285, 10, passwordOfPerson.getPreferredSize().width, passwordOfPerson.getPreferredSize().height);
        passwordOfPerson.setVisible(false);
        panel.add(passwordOfPerson);

        personsPassword = new JPasswordField("");
        personsPassword.setBounds(285, 25, 200, 20);
        personsPassword.setEchoChar('*');
        personsPassword.setVisible(false);
        panel.add(personsPassword);

        personsPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                passwordValid = true;
                String loginPassword = new String(personsPassword.getPassword());
                personsPasswordError.setText("");
                personsPasswordError.setBounds(485, 27, 400, 15);
                personsSpecialError.setText("");

                /*
                If statement ensuring the user entered a password
                */
                if (!loginPassword.isEmpty()) {
                    boolean whitespace = false;
                    /*
                    Removes any whitespaces from the beginning or end of the password
                    */
                    loginPassword = loginPassword.trim();
                    personsPassword.setText(loginPassword);

                    /*
                    Ensures no whitespaces are present in the password
                    */
                    for (char c : loginPassword.toCharArray()) {
                        if (Character.isWhitespace(c)) {
                            whitespace = true;
                        }
                    }

                    if (!whitespace) {
                        /*
                        If statement ensuring the password is not less than 8 characters or more than 20
                        */
                        if (loginPassword.length() < 8) {
                            passwordValid = false;
                            personsPasswordError.setText("Password must be at least 7 characters long");
                        } else if (loginPassword.length() > 20) {
                            passwordValid = false;
                            personsPasswordError.setText("Password cannot be more than 20 characters long");
                        } else {
                            int requirementsMet = 0;
                            char c;
                            boolean uppercasePresent = false;
                            boolean lowercasePresent = false;
                            boolean numberPresent = false;
                            boolean specialPresent = false;
                            for (int i = 0; i < loginPassword.length(); i++) {
                                c = loginPassword.charAt(i);
                                if (Character.isUpperCase(c)) {
                                    uppercasePresent = true;
                                } else if (Character.isLowerCase(c)) {
                                    lowercasePresent = true;
                                } else if (Character.isDigit(c)) {
                                    numberPresent = true;
                                } else if (!Character.isAlphabetic(c) && !Character.isDigit(c)) {
                                    specialPresent = true;
                                }
                            }
                            /*
                            A series of if statements to test if the user meets at least 3 out of 4 requirements
                            */
                            if (uppercasePresent) {
                                requirementsMet++;
                            }
                            if (lowercasePresent) {
                                requirementsMet++;
                            }
                            if (numberPresent) {
                                requirementsMet++;
                            }
                            if (specialPresent) {
                                requirementsMet++;
                            }
                            if (requirementsMet < 3) {
                                passwordValid = false;
                                personsPasswordError.setBounds(485, 21, 400, 15);
                                personsPasswordError.setText("Must hold a upper and/or lowercase letter");
                                personsSpecialError.setText("Must hold a number and/or special letter");
                            }
                        }
                    } else {
                        passwordValid = false;
                        personsPasswordError.setText("No whitespace allowed");
                    }
                } else {
                    passwordValid = false;
                    personsPasswordError.setText("Must enter a password");
                }
            }
        }
        );

        personsPasswordError = new JLabel("");
        personsPasswordError.setBounds(485, 27, 400, 15);
        personsPasswordError.setForeground(Color.red);
        panel.add(personsPasswordError);

        personsSpecialError = new JLabel("");
        personsSpecialError.setBounds(485, 33, 400, 15);
        personsSpecialError.setForeground(Color.red);
        panel.add(personsSpecialError);

        JLabel prefixOfPerson = new JLabel("Prefix");
        prefixOfPerson.setBounds(10, 90, 300, 15);
        prefixOfPerson.setVisible(false);
        panel.add(prefixOfPerson);

        String[] preFix = {"", "Mr.", "Ms.", "Mrs.", "Dr."};
        personsPrefix = new JComboBox(preFix);
        personsPrefix.setBounds(10, 104, 55, 20);
        personsPrefix.setVisible(false);
        panel.add(personsPrefix);

        JLabel firstNameOfPerson = new JLabel("First Name");
        firstNameOfPerson.setBounds(75, 90, firstNameOfPerson.getPreferredSize().width, firstNameOfPerson.getPreferredSize().height);
        firstNameOfPerson.setVisible(false);
        panel.add(firstNameOfPerson);

        personsFirstName = new JTextField("");
        personsFirstName.setBounds(75, 105, 200, 20);
        personsFirstName.setVisible(false);
        panel.add(personsFirstName);

        personsFirstNameError = new JLabel("");
        personsFirstNameError.setBounds(150, 90, 300, 15);
        personsFirstNameError.setForeground(Color.red);
        panel.add(personsFirstNameError);

        JLabel middleNameOfPerson = new JLabel("Middle Name");
        middleNameOfPerson.setBounds(285, 90, middleNameOfPerson.getPreferredSize().width, middleNameOfPerson.getPreferredSize().height);
        middleNameOfPerson.setVisible(false);
        panel.add(middleNameOfPerson);

        personsMiddleName = new JTextField("");
        personsMiddleName.setBounds(285, 105, 200, 20);
        personsMiddleName.setVisible(false);
        panel.add(personsMiddleName);

        JLabel lastNameOfPerson = new JLabel("Last Name");
        lastNameOfPerson.setBounds(495, 90, lastNameOfPerson.getPreferredSize().width, lastNameOfPerson.getPreferredSize().height);
        lastNameOfPerson.setVisible(false);
        panel.add(lastNameOfPerson);

        personsLastName = new JTextField("");
        personsLastName.setBounds(495, 105, 200, 20);
        personsLastName.setVisible(false);
        panel.add(personsLastName);

        personsLastNameError = new JLabel("");
        personsLastNameError.setBounds(570, 90, 300, 15);
        personsLastNameError.setForeground(Color.red);
        panel.add(personsLastNameError);

        JLabel suffixOfPerson = new JLabel("Suffix");
        suffixOfPerson.setBounds(705, 90, 300, 15);
        suffixOfPerson.setVisible(false);
        panel.add(suffixOfPerson);

        String[] sufFix = {"", "Jr.", "Sr.", "I", "II", "III"};
        personsSuffix = new JComboBox(sufFix);
        personsSuffix.setBounds(705, 104, 55, 20);
        personsSuffix.setVisible(false);
        panel.add(personsSuffix);

        JLabel address1OfPerson = new JLabel("Address 1");
        address1OfPerson.setBounds(75, 170, 200, 20);
        address1OfPerson.setVisible(false);
        panel.add(address1OfPerson);

        personsAddress1 = new JTextField("");
        personsAddress1.setBounds(75, 187, 200, 20);
        personsAddress1.setVisible(false);
        panel.add(personsAddress1);

        JLabel address2OfPerson = new JLabel("Address 2");
        address2OfPerson.setBounds(285, 170, 200, 20);
        address2OfPerson.setVisible(false);
        panel.add(address2OfPerson);

        personsAddress2 = new JTextField("");
        personsAddress2.setBounds(285, 187, 200, 20);
        personsAddress2.setVisible(false);
        panel.add(personsAddress2);

        JLabel address3OfPerson = new JLabel("Address 3");
        address3OfPerson.setBounds(495, 170, 200, 20);
        address3OfPerson.setVisible(false);
        panel.add(address3OfPerson);

        personsAddress3 = new JTextField("");
        personsAddress3.setBounds(495, 187, 200, 20);
        personsAddress3.setVisible(false);
        panel.add(personsAddress3);

        addressError = new JLabel("");
        addressError.setBounds(150, 173, 300, 15);
        addressError.setForeground(Color.red);
        panel.add(addressError);

        JLabel cityOfPerson = new JLabel("City");
        cityOfPerson.setBounds(75, 255, 200, 20);
        cityOfPerson.setVisible(false);
        panel.add(cityOfPerson);

        personsCity = new JTextField("");
        personsCity.setBounds(75, 272, 200, 20);
        personsCity.setVisible(false);
        panel.add(personsCity);

        personsCityError = new JLabel("");
        personsCityError.setBounds(150, 255, 200, 20);
        personsCityError.setForeground(Color.red);
        panel.add(personsCityError);

        JLabel zipOfPerson = new JLabel("Zip Code");
        zipOfPerson.setBounds(285, 255, 200, 20);
        zipOfPerson.setVisible(false);
        panel.add(zipOfPerson);

        personsZip = new JTextField("");
        personsZip.setBounds(285, 272, 200, 20);
        personsZip.setVisible(false);
        panel.add(personsZip);

        personsZipError = new JLabel("");
        personsZipError.setBounds(360, 255, 200, 20);
        personsZipError.setForeground(Color.red);
        panel.add(personsZipError);

        JLabel stateOfPerson = new JLabel("States");
        stateOfPerson.setBounds(495, 255, 200, 20);
        stateOfPerson.setVisible(false);
        panel.add(stateOfPerson);

        String[] states = {"", "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"};
        personsState = new JComboBox(states);
        personsState.setBounds(495, 272, 125, 20);
        personsState.setVisible(false);
        panel.add(personsState);

        personsStateError = new JLabel("");
        personsStateError.setBounds(622, 272, 200, 20);
        personsStateError.setForeground(Color.red);
        panel.add(personsStateError);

        JLabel emailOfPerson = new JLabel("Email");
        emailOfPerson.setBounds(75, 340, 200, 20);
        emailOfPerson.setVisible(false);
        panel.add(emailOfPerson);

        personsEmail = new JTextField("");
        personsEmail.setBounds(75, 357, 200, 20);
        personsEmail.setVisible(false);
        panel.add(personsEmail);

        personsEmailError = new JLabel("");
        personsEmailError.setBounds(150, 342, 400, 15);
        personsEmailError.setForeground(Color.red);
        panel.add(personsEmailError);

        JLabel phoneOfPerson1 = new JLabel("Phone #1");
        phoneOfPerson1.setBounds(285, 340, 200, 20);
        phoneOfPerson1.setVisible(false);
        panel.add(phoneOfPerson1);

        personsPhonePrimary1 = new JTextField("");
        personsPhonePrimary1.setBounds(285, 357, 67, 20);
        personsPhonePrimary1.setVisible(false);
        panel.add(personsPhonePrimary1);

        personsPhonePrimary2 = new JTextField("");
        personsPhonePrimary2.setBounds(352, 357, 66, 20);
        personsPhonePrimary2.setVisible(false);
        panel.add(personsPhonePrimary2);

        personsPhonePrimary3 = new JTextField("");
        personsPhonePrimary3.setBounds(418, 357, 67, 20);
        personsPhonePrimary3.setVisible(false);
        panel.add(personsPhonePrimary3);

        personsPhoneError1 = new JLabel("");
        personsPhoneError1.setBounds(360, 342, 400, 15);
        personsPhoneError1.setForeground(Color.red);
        panel.add(personsPhoneError1);

        JLabel phoneOfPerson2 = new JLabel("Phone #2");
        phoneOfPerson2.setBounds(495, 340, 200, 20);
        phoneOfPerson2.setVisible(false);
        panel.add(phoneOfPerson2);

        personsPhoneSecondary1 = new JTextField("");
        personsPhoneSecondary1.setBounds(495, 357, 67, 20);
        personsPhoneSecondary1.setVisible(false);
        panel.add(personsPhoneSecondary1);

        personsPhoneSecondary2 = new JTextField("");
        personsPhoneSecondary2.setBounds(562, 357, 66, 20);
        personsPhoneSecondary2.setVisible(false);
        panel.add(personsPhoneSecondary2);

        personsPhoneSecondary3 = new JTextField("");
        personsPhoneSecondary3.setBounds(628, 357, 67, 20);
        personsPhoneSecondary3.setVisible(false);
        panel.add(personsPhoneSecondary3);

        personsPhoneError2 = new JLabel("");
        personsPhoneError2.setBounds(585, 342, 400, 15);
        personsPhoneError2.setForeground(Color.red);
        panel.add(personsPhoneError2);

        securityQuestion1 = new JComboBox();
        securityQuestion1.setBounds(75, 420, 200, 20);
        securityQuestion1.setVisible(false);
        panel.add(securityQuestion1);

        securityQuestion2 = new JComboBox();
        securityQuestion2.setBounds(285, 420, 200, 20);
        securityQuestion2.setVisible(false);
        panel.add(securityQuestion2);

        securityQuestion3 = new JComboBox();
        securityQuestion3.setBounds(495, 420, 200, 20);
        securityQuestion3.setVisible(false);
        panel.add(securityQuestion3);

        securityAnswer1 = new JTextField("");
        securityAnswer1.setBounds(75, 442, 200, 20);
        securityAnswer1.setVisible(false);
        panel.add(securityAnswer1);

        securityAnswer2 = new JTextField("");
        securityAnswer2.setBounds(285, 442, 200, 20);
        securityAnswer2.setVisible(false);
        panel.add(securityAnswer2);

        securityAnswer3 = new JTextField("");
        securityAnswer3.setBounds(495, 442, 200, 20);
        securityAnswer3.setVisible(false);
        panel.add(securityAnswer3);

        answerError = new JLabel("");
        answerError.setBounds(290, 459, 200, 20);
        answerError.setForeground(Color.red);
        panel.add(answerError);

        JLabel positionOfUser = new JLabel("User Position");
        positionOfUser.setBounds(75, 470, 200, 15);
        positionOfUser.setVisible(false);
        panel.add(positionOfUser);

        userIsCustomer = new JRadioButton("Customer");
        userIsCustomer.setBounds(75, 487, 100, 25);
        userIsCustomer.setBackground(null);
        userIsCustomer.setVisible(false);
        panel.add(userIsCustomer);

        userIsManager = new JRadioButton("Manager");
        userIsManager.setBounds(175, 487, 100, 25);
        userIsManager.setBackground(null);
        userIsManager.setVisible(false);
        panel.add(userIsManager);

        userIsCustomer.addActionListener((e) -> {
            userIsCustomer.setSelected(true);
            userIsManager.setSelected(false);
        }
        );

        userIsManager.addActionListener((e) -> {
            userIsCustomer.setSelected(false);
            userIsManager.setSelected(true);
        }
        );

        userSelectedWarning = new JLabel("");
        userSelectedWarning.setBounds(325, 480, 150, 15);
        userSelectedWarning.setForeground(Color.GRAY);
        panel.add(userSelectedWarning);

        JButton resetUser = new JButton("Reset");
        resetUser.setBounds(175, 517, 115, 50);
        resetUser.setBackground(thistle);
        resetUser.setVisible(false);
        resetUser.addActionListener((e) -> {
            userSelected = -1;
            userSelectedWarning.setText("");
            accountDataReset();
        }
        );
        panel.add(resetUser);

        addUser = new JButton("Add User");
        addUser.setBounds(625, 517, 115, 50);
        addUser.setBackground(thistle);
        addUser.setVisible(false);
        addUser.addActionListener((e) -> {
            newAccount();
        }
        );
        panel.add(addUser);

        editUser = new JButton("Edit User");
        editUser.setBounds(475, 517, 115, 50);
        editUser.setBackground(thistle);
        editUser.setVisible(false);
        editUser.addActionListener((e) -> {
            if (userSelected != -1) {
                editAccount();
            } else {
                userSelectedWarning.setText("No user selected");
                userSelectedWarning.setForeground(Color.red);
            }
        }
        );
        panel.add(editUser);

        deleteUser = new JButton("Delete User");
        deleteUser.setBounds(325, 517, 115, 50);
        deleteUser.setBackground(thistle);
        deleteUser.setVisible(false);
        deleteUser.addActionListener((e) -> {
            if (userSelected != -1) {
                deleteAccount();
            } else {
                userSelectedWarning.setText("No user selected");
                userSelectedWarning.setForeground(Color.red);
            }
        }
        );
        panel.add(deleteUser);

        editUsers = new JButton("Edit Users");
        editUsers.setBounds(252, 517, 115, 50);
        editUsers.setBackground(thistle);
        editUsers.addActionListener((e) -> {
            customerSelected = -1;
            clearCustomerData();
            managerAsCustomer = false;
            customerView.jp.setVisible(false);

            customerjs.setVisible(false);
            managerdiscountjs.setVisible(false);

            resetOrderDates();

            validOrderStartYear.setVisible(false);
            minimumYear.setVisible(false);
            validOrderStartMonth.setVisible(false);
            minimumMonth.setVisible(false);
            validOrderStartDay.setVisible(false);
            minimumDay.setVisible(false);
            validOrderEndYear.setVisible(false);
            maximumYear.setVisible(false);
            validOrderEndMonth.setVisible(false);
            maximumMonth.setVisible(false);
            validOrderEndDay.setVisible(false);
            maximumDay.setVisible(false);
            orderDateError.setText("");

            typeOfAccount.setVisible(false);
            searchForCustomer.setVisible(false);
            searchForManager.setVisible(false);

            viewPanel.remove(personjs);
            viewPanel.remove(loginjs);
            personjs.setBounds(0, 0, 400, 572);
            personjs.setPreferredSize(new Dimension(400, 572));
            loginjs.setBounds(0, 0, 402, 572);
            loginjs.setPreferredSize(new Dimension(400, 572));
            viewPanel.add(personjs, BorderLayout.EAST);
            viewPanel.add(loginjs, BorderLayout.WEST);

            personjs.setVisible(true);
            personData = getPersonData();
            DefaultTableModel ptable = (DefaultTableModel) personjt.getModel();
            ptable.setDataVector(personData, personCol);
            loginjs.setVisible(true);
            loginData = getLoginData();
            DefaultTableModel ltable = (DefaultTableModel) loginjt.getModel();
            ltable.setDataVector(loginData, loginCol);

            loginNameOfPerson.setVisible(true);
            personsLoginName.setVisible(true);
            passwordOfPerson.setVisible(true);
            personsPassword.setVisible(true);
            prefixOfPerson.setVisible(true);
            personsPrefix.setVisible(true);
            firstNameOfPerson.setVisible(true);
            personsFirstName.setVisible(true);
            middleNameOfPerson.setVisible(true);
            personsMiddleName.setVisible(true);
            lastNameOfPerson.setVisible(true);
            personsLastName.setVisible(true);
            suffixOfPerson.setVisible(true);
            personsSuffix.setVisible(true);
            address1OfPerson.setVisible(true);
            personsAddress1.setVisible(true);
            address2OfPerson.setVisible(true);
            personsAddress2.setVisible(true);
            address3OfPerson.setVisible(true);
            personsAddress3.setVisible(true);
            cityOfPerson.setVisible(true);
            personsCity.setVisible(true);
            zipOfPerson.setVisible(true);
            personsZip.setVisible(true);
            stateOfPerson.setVisible(true);
            personsState.setVisible(true);
            emailOfPerson.setVisible(true);
            personsEmail.setVisible(true);
            phoneOfPerson1.setVisible(true);
            phoneOfPerson2.setVisible(true);
            personsPhonePrimary1.setVisible(true);
            personsPhonePrimary2.setVisible(true);
            personsPhonePrimary3.setVisible(true);
            personsPhoneSecondary1.setVisible(true);
            personsPhoneSecondary2.setVisible(true);
            personsPhoneSecondary3.setVisible(true);
            securityQuestion1.setVisible(true);
            securityQuestion2.setVisible(true);
            securityQuestion3.setVisible(true);
            securityAnswer1.setVisible(true);
            securityAnswer2.setVisible(true);
            securityAnswer3.setVisible(true);
            positionOfUser.setVisible(true);
            userIsCustomer.setVisible(true);
            userIsCustomer.setSelected(true);
            userIsManager.setVisible(true);
            userIsManager.setSelected(false);

            resetUser.setVisible(true);
            addUser.setVisible(true);
            editUser.setVisible(true);
            deleteUser.setVisible(true);
            returnToView.setBounds(25, 517, 115, 50);
            returnToView.setVisible(true);

            logonNameOfCustomer.setVisible(false);
            customersLogonName.setVisible(false);
            customersLogonName.setText("");
            customersLogonNameError.setText("");
            logonCustomerSelect.setVisible(false);
            emailOfCustomer.setVisible(false);
            customersEmail.setVisible(false);
            customersEmail.setText("");
            customersEmailError.setText("");
            emailCustomerSelect.setVisible(false);
            phoneNumberOfCustomer.setVisible(false);
            customersPhoneNumber1.setVisible(false);
            customersPhoneNumber1.setText("");
            customersPhoneNumber2.setVisible(false);
            customersPhoneNumber2.setText("");
            customersPhoneNumber3.setVisible(false);
            customersPhoneNumber3.setText("");
            customersPhoneNumberError.setText("");
            phoneNumberCustomerSelect.setVisible(false);
            salesDetails.setVisible(false);
            userDetails.setVisible(false);
            resetCustomer.setVisible(false);
            editItems.setVisible(false);
            editUsers.setVisible(false);
            editDiscounts.setVisible(false);
            logOut.setVisible(false);

            help.setBounds(735, 0, 50, 20);
            panel.setPreferredSize(new Dimension(770, 0));
            viewPanel.setPreferredSize(new Dimension(365, 0));
            jf.setBounds(jf.getX(), jf.getY(), 1586, 610);
            jp.add(viewPanel);
            jp.repaint();
        }
        );
        panel.add(editUsers);

        /*--------------------------------------------------ITEMS SECTION--------------------------------------------------*/
        needsRestock = new JLabel("Items Need Restock");
        needsRestock.setBounds(120, 0, 125, 20);
        needsRestock.setForeground(Color.red);
        needsRestock.setVisible(false);
        panel.add(needsRestock);

        needsRestock.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                restockAlert();
            }
        });

        allItems = new JLabel("All Items");
        allItems.setBounds(265, 0, 125, 20);
        allItems.setForeground(royalPurple);
        allItems.setVisible(false);
        panel.add(allItems);

        allItems.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                itemsReport();
            }
        });

        itemDisplay = new JPanel(new GridBagLayout());
        itemDisplay.setBounds(panel.getWidth(), 50, 200, 200);
        itemDisplay.setBackground(lightCyan);
        inventory = new ScrollPane(itemDisplay);
        inventory.setBounds(0, 0, 825, 572);
        inventory.setVisible(false);
        viewPanel.add(inventory, BorderLayout.CENTER);

        JLabel searchBarEntry = new JLabel("Search");
        searchBarEntry.setBounds(25, 20, 200, 15);
        searchBarEntry.setVisible(false);
        panel.add(searchBarEntry);

        JTextField searchBar = new JTextField("");
        searchBar.setBounds(25, 35, 200, 20);
        searchBar.setVisible(false);
        panel.add(searchBar);

        searchBar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                GridBagConstraints imageLayout = new GridBagConstraints();
                imageLayout.insets = new Insets(2, 2, 2, 2);
                int x = 0;
                int y = 0;
                String n[];
                itemDisplay.removeAll();
                items.clear();
                itemDisplay.repaint();
                ManagerItemSelection select = new ManagerItemSelection(items);
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

        JLabel nameOfItem = new JLabel("Item Name");
        nameOfItem.setBounds(25, 68, 200, 15);
        nameOfItem.setVisible(false);
        panel.add(nameOfItem);

        itemsName = new JTextField("");
        itemsName.setBounds(25, 83, 200, 20);
        itemsName.setVisible(false);
        panel.add(itemsName);

        itemsName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (itemsName.getText().length() > 37) {
                    itemsName.setText(itemsName.getText().substring(0, itemsName.getText().length() - 1));
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                nameValid = true;
                itemsNameError.setText("");
                if (itemsName.getText().isEmpty()) {
                    itemsNameError.setText("Name required");
                    nameValid = false;
                }
            }
        }
        );

        itemsNameError = new JLabel("");
        itemsNameError.setBounds(100, 68, 200, 15);
        itemsNameError.setForeground(Color.red);
        panel.add(itemsNameError);

        JLabel descriptionOfItem = new JLabel("Item Description");
        descriptionOfItem.setBounds(25, 111, 200, 15);
        descriptionOfItem.setVisible(false);
        panel.add(descriptionOfItem);

        itemsDescription = new JTextArea("");
        itemsDescription.setBounds(25, 128, 320, 80);
        itemsDescription.setLineWrap(true);
        itemsDescription.setWrapStyleWord(true);
        itemsDescription.setVisible(false);
        panel.add(itemsDescription);

        itemsDescription.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (itemsDescription.getText().length() > 224) {
                    itemsDescription.setText(itemsDescription.getText().substring(0, itemsDescription.getText().length() - 1));
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                descriptionValid = true;
                itemsDescriptionError.setText("");
                if (itemsDescription.getText().isEmpty()) {
                    itemsDescriptionError.setText("Description required");
                    descriptionValid = false;
                }
            }
        }
        );

        itemsDescriptionError = new JLabel("");
        itemsDescriptionError.setBounds(150, 111, 200, 15);
        itemsDescriptionError.setForeground(Color.red);
        panel.add(itemsDescriptionError);

        JLabel categoryOfItem = new JLabel("Item Category");
        categoryOfItem.setBounds(25, 218, 200, 15);
        categoryOfItem.setVisible(false);
        panel.add(categoryOfItem);

        itemIsIgneous = new JRadioButton("Igneous");
        itemIsIgneous.setBounds(25, 233, 70, 25);
        itemIsIgneous.setBackground(null);
        itemIsIgneous.setVisible(false);
        panel.add(itemIsIgneous);

        itemIsSedimentary = new JRadioButton("Sedimentary");
        itemIsSedimentary.setBounds(110, 233, 100, 25);
        itemIsSedimentary.setBackground(null);
        itemIsSedimentary.setVisible(false);
        panel.add(itemIsSedimentary);

        itemIsMetamorphic = new JRadioButton("Metamorphic");
        itemIsMetamorphic.setBounds(210, 233, 150, 25);
        itemIsMetamorphic.setBackground(null);
        itemIsMetamorphic.setVisible(false);
        panel.add(itemIsMetamorphic);

        itemIsIgneous.addActionListener((e) -> {
            itemIsIgneous.setSelected(true);
            itemIsSedimentary.setSelected(false);
            itemIsMetamorphic.setSelected(false);
        }
        );

        itemIsSedimentary.addActionListener((e) -> {
            itemIsIgneous.setSelected(false);
            itemIsSedimentary.setSelected(true);
            itemIsMetamorphic.setSelected(false);
        }
        );

        itemIsMetamorphic.addActionListener((e) -> {
            itemIsIgneous.setSelected(false);
            itemIsSedimentary.setSelected(false);
            itemIsMetamorphic.setSelected(true);
        }
        );

        JLabel classificationOfItem = new JLabel("Stone or gemstone");
        classificationOfItem.setBounds(25, 268, 200, 15);
        classificationOfItem.setVisible(false);
        panel.add(classificationOfItem);

        itemIsStone = new JRadioButton("Stone");
        itemIsStone.setBounds(25, 283, 60, 25);
        itemIsStone.setBackground(null);
        itemIsStone.setVisible(false);
        panel.add(itemIsStone);

        itemIsGemstone = new JRadioButton("Gemstone");
        itemIsGemstone.setBounds(110, 283, 100, 25);
        itemIsGemstone.setBackground(null);
        itemIsGemstone.setVisible(false);
        panel.add(itemIsGemstone);

        itemIsPreciousGemstone = new JRadioButton("Precious Gemstone");
        itemIsPreciousGemstone.setBounds(210, 283, 150, 25);
        itemIsPreciousGemstone.setBackground(null);
        itemIsPreciousGemstone.setVisible(false);
        panel.add(itemIsPreciousGemstone);

        itemIsStone.addActionListener((e) -> {
            itemIsStone.setSelected(true);
            itemIsGemstone.setSelected(false);
            itemIsPreciousGemstone.setSelected(false);

            grainSizeOfItem.setVisible(true);
            itemIsCoarseGrained.setVisible(true);
            itemIsFineGrained.setVisible(true);
            grainShapeOfItem.setVisible(true);
            itemsGrainShape.setVisible(true);
            heftOfItem.setVisible(false);
            itemsHeft.setVisible(false);
            itemsHeftError.setVisible(false);
            sizeOfItem.setText("Size [Centimeters]");
            weightOfItem.setText("Weight [Grams]");
        }
        );

        itemIsGemstone.addActionListener((e) -> {
            itemIsStone.setSelected(false);
            itemIsGemstone.setSelected(true);
            itemIsPreciousGemstone.setSelected(false);

            grainSizeOfItem.setVisible(false);
            itemIsCoarseGrained.setVisible(false);
            itemIsFineGrained.setVisible(false);
            grainShapeOfItem.setVisible(false);
            itemsGrainShape.setVisible(false);
            heftOfItem.setVisible(true);
            itemsHeft.setVisible(true);
            itemsHeftError.setVisible(true);
            sizeOfItem.setText("Size [Millimeters]");
            weightOfItem.setText("Weight [Carats]");
        }
        );

        itemIsPreciousGemstone.addActionListener((e) -> {
            itemIsStone.setSelected(false);
            itemIsGemstone.setSelected(false);
            itemIsPreciousGemstone.setSelected(true);

            grainSizeOfItem.setVisible(false);
            itemIsCoarseGrained.setVisible(false);
            itemIsFineGrained.setVisible(false);
            grainShapeOfItem.setVisible(false);
            itemsGrainShape.setVisible(false);
            heftOfItem.setVisible(true);
            itemsHeft.setVisible(true);
            itemsHeftError.setVisible(true);
            sizeOfItem.setText("Size [Millimeters]");
            weightOfItem.setText("Weight [Carats]");
        }
        );

        grainSizeOfItem = new JLabel("Grain Size");
        grainSizeOfItem.setBounds(25, 318, 200, 15);
        grainSizeOfItem.setVisible(false);
        panel.add(grainSizeOfItem);

        itemIsCoarseGrained = new JRadioButton("Coarse");
        itemIsCoarseGrained.setBounds(25, 333, 70, 25);
        itemIsCoarseGrained.setBackground(null);
        itemIsCoarseGrained.setVisible(false);
        panel.add(itemIsCoarseGrained);

        itemIsFineGrained = new JRadioButton("Fine");
        itemIsFineGrained.setBounds(110, 333, 50, 25);
        itemIsFineGrained.setBackground(null);
        itemIsFineGrained.setVisible(false);
        panel.add(itemIsFineGrained);

        itemIsCoarseGrained.addActionListener((e) -> {
            itemIsFineGrained.setSelected(false);
        }
        );

        itemIsFineGrained.addActionListener((e) -> {
            itemIsCoarseGrained.setSelected(false);
        }
        );

        grainShapeOfItem = new JLabel("Grain Shape");
        grainShapeOfItem.setBounds(175, 318, 100, 15);
        grainShapeOfItem.setVisible(false);
        panel.add(grainShapeOfItem);

        itemsGrainShape = new JTextField("");
        itemsGrainShape.setBounds(175, 333, 175, 20);
        itemsGrainShape.setVisible(false);
        panel.add(itemsGrainShape);

        heftOfItem = new JLabel("Gem Heft");
        heftOfItem.setBounds(25, 318, 100, 15);
        heftOfItem.setVisible(false);
        panel.add(heftOfItem);

        itemsHeft = new JTextField("");
        itemsHeft.setBounds(25, 333, 70, 20);
        itemsHeft.setVisible(false);
        panel.add(itemsHeft);

        itemsHeft.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                heftValid = true;
                itemsHeftError.setText("");
                if (!itemsHeft.getText().isEmpty()) {
                    if (itemsHeft.getText().matches("^\\d+\\.\\d+")) {
                        if (itemsHeft.getText().length() > 10) {
                            for (int i = 0; i < itemsHeft.getText().length(); i++) {
                                itemsHeft.setText(itemsHeft.getText().substring(0, 10));
                            }
                        }
                    } else {
                        if (itemsHeft.getText().length() > 9) {
                            for (int i = 0; i < itemsHeft.getText().length(); i++) {
                                itemsHeft.setText(itemsHeft.getText().substring(0, 9));
                            }
                        }
                        char[] chars = itemsHeft.getText().toCharArray();
                        for (char c : chars) {
                            if (!Character.isDigit(c)) {
                                itemsHeftError.setText("Only numbers and decimal numbers allowed");
                                heftValid = false;
                                break;
                            }
                        }
                    }
                } else {
                    itemsHeftError.setText("All gemstones require heft");
                    heftValid = false;
                }
            }
        }
        );

        itemsHeftError = new JLabel("");
        itemsHeftError.setBounds(95, 335, 255, 15);
        itemsHeftError.setForeground(Color.red);
        panel.add(itemsHeftError);

        hardnessOfItem = new JLabel("Hardness");
        hardnessOfItem.setBounds(10, 363, 100, 15);
        hardnessOfItem.setVisible(false);
        panel.add(hardnessOfItem);

        itemsHardness = new JTextField("");
        itemsHardness.setBounds(10, 380, 35, 20);
        itemsHardness.setVisible(false);
        panel.add(itemsHardness);

        itemsHardness.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                hardnessValid = true;
                itemsHardnessError.setText("");

                String inputText = itemsHardness.getText();

                if (inputText.isEmpty()) {
                    itemsHardnessError.setText("Required");
                    hardnessValid = false;
                    return;
                }

                if (inputText.length() > 3) {
                    inputText = inputText.substring(0, 3);
                    itemsHardness.setText(inputText);
                }

                if (!inputText.matches("^\\d+(\\.\\d{1})?$")) {
                    itemsHardnessError.setText("Invalid format");
                    hardnessValid = false;
                    return;
                }

                try {
                    double hardness = Double.parseDouble(inputText);
                    if (hardness > 10 || hardness < 1) {
                        itemsHardnessError.setText("Out of range");
                        hardnessValid = false;
                    }
                } catch (NumberFormatException ex) {
                    itemsHardnessError.setText("Invalid number");
                    hardnessValid = false;
                }
            }
        });

        itemsHardnessError = new JLabel("");
        itemsHardnessError.setBounds(45, 382, 200, 15);
        itemsHardnessError.setForeground(Color.red);
        itemsHardnessError.setVisible(true);
        panel.add(itemsHardnessError);

        sizeOfItem = new JLabel("Size [Centimeters]");
        sizeOfItem.setBounds(100, 363, 120, 15);
        sizeOfItem.setVisible(false);
        panel.add(sizeOfItem);

        itemsSize = new JTextField("");
        itemsSize.setBounds(125, 380, 55, 20);
        itemsSize.setVisible(false);
        panel.add(itemsSize);

        itemsSize.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                sizeValid = true;
                itemsSizeError.setText("");
                if (!itemsSize.getText().isEmpty()) {
                    if (itemsSize.getText().matches("^\\d*\\.?\\d{1,2}$")) {
                        if (itemsSize.getText().length() > 7) {
                            for (int i = 0; i < itemsSize.getText().length(); i++) {
                                itemsSize.setText(itemsSize.getText().substring(0, 7));
                            }
                        }
                    } else {
                        if (itemsSize.getText().length() > 6) {
                            for (int i = 0; i < itemsSize.getText().length(); i++) {
                                itemsSize.setText(itemsSize.getText().substring(0, 6));
                            }
                        }
                        char[] chars = itemsSize.getText().toCharArray();
                        for (char c : chars) {
                            if (!Character.isDigit(c)) {
                                itemsSizeError.setText("Digits only");
                                sizeValid = false;
                                break;
                            }
                        }
                    }
                } else {
                    itemsSizeError.setText("Required");
                    sizeValid = false;
                }
            }
        }
        );

        itemsSizeError = new JLabel("");
        itemsSizeError.setBounds(180, 382, 200, 15);
        itemsSizeError.setForeground(Color.red);
        panel.add(itemsSizeError);

        weightOfItem = new JLabel("Weight [Grams]");
        weightOfItem.setBounds(230, 363, 100, 15);
        weightOfItem.setVisible(false);
        panel.add(weightOfItem);

        itemsWeight = new JTextField("");
        itemsWeight.setBounds(250, 380, 55, 20);
        itemsWeight.setVisible(false);
        panel.add(itemsWeight);

        itemsWeight.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                weightValid = true;
                itemsWeightError.setText("");
                if (!itemsWeight.getText().isEmpty()) {
                    if (itemsWeight.getText().matches("^\\d*\\.?\\d{1,2}$")) {
                        if (itemsWeight.getText().length() > 7) {
                            for (int i = 0; i < itemsWeight.getText().length(); i++) {
                                itemsWeight.setText(itemsWeight.getText().substring(0, 7));
                            }
                        }
                    } else {
                        if (itemsWeight.getText().length() > 6) {
                            for (int i = 0; i < itemsWeight.getText().length(); i++) {
                                itemsWeight.setText(itemsWeight.getText().substring(0, 6));
                            }
                        }
                        char[] chars = itemsWeight.getText().toCharArray();
                        for (char c : chars) {
                            if (!Character.isDigit(c)) {
                                itemsWeightError.setText("Digits only");
                                weightValid = false;
                                break;
                            }
                        }
                    }
                } else {
                    itemsWeightError.setText("Required");
                    weightValid = false;
                }
            }
        }
        );

        itemsWeightError = new JLabel("");
        itemsWeightError.setBounds(305, 382, 200, 15);
        itemsWeightError.setForeground(Color.red);
        panel.add(itemsWeightError);

        JLabel retailOfItem = new JLabel("Retail Price");
        retailOfItem.setBounds(10, 408, 100, 15);
        retailOfItem.setVisible(false);
        panel.add(retailOfItem);

        JLabel dollarSign1 = new JLabel("$");
        dollarSign1.setBounds(1, 427, 10, 15);
        dollarSign1.setVisible(false);
        panel.add(dollarSign1);

        itemsRetail = new JTextField("");
        itemsRetail.setBounds(10, 425, 75, 20);
        itemsRetail.setVisible(false);
        panel.add(itemsRetail);

        itemsRetail.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                retailValid = true;
                itemsRetailError.setText("");
                if (!itemsRetail.getText().isEmpty()) {
                    if (itemsRetail.getText().length() > 10) {
                        for (int i = 0; i < itemsRetail.getText().length(); i++) {
                            itemsRetail.setText(itemsRetail.getText().substring(0, 10));
                        }
                    }
                    if (!itemsRetail.getText().matches("^\\d+\\.\\d{2}$")) {
                        char[] chars = itemsRetail.getText().toCharArray();
                        for (char c : chars) {
                            if (!Character.isDigit(c)) {
                                itemsRetailError.setText("#Req.");
                                retailValid = false;
                                break;
                            }
                        }
                    }
                } else {
                    itemsRetailError.setText("Req.");
                    retailValid = false;
                }
            }
        }
        );

        itemsRetailError = new JLabel("");
        itemsRetailError.setBounds(78, 408, 200, 15);
        itemsRetailError.setForeground(Color.red);
        panel.add(itemsRetailError);

        JLabel costOfItem = new JLabel("Price");
        costOfItem.setBounds(109, 408, 100, 15);
        costOfItem.setVisible(false);
        panel.add(costOfItem);

        JLabel dollarSign2 = new JLabel("$");
        dollarSign2.setBounds(100, 427, 10, 15);
        dollarSign2.setVisible(false);
        panel.add(dollarSign2);

        itemsCost = new JTextField("");
        itemsCost.setBounds(109, 425, 75, 20);
        itemsCost.setVisible(false);
        panel.add(itemsCost);

        itemsCost.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                costValid = true;
                itemsCostError.setText("");
                if (!itemsCost.getText().isEmpty()) {
                    if (itemsCost.getText().length() > 10) {
                        for (int i = 0; i < itemsCost.getText().length(); i++) {
                            itemsCost.setText(itemsCost.getText().substring(0, 10));
                        }
                    }
                    if (!itemsCost.getText().matches("^\\d+\\.\\d{2}$")) {
                        char[] chars = itemsCost.getText().toCharArray();
                        for (char c : chars) {
                            if (!Character.isDigit(c)) {
                                itemsCostError.setText("#Req.");
                                costValid = false;
                                break;
                            }
                        }
                    }
                } else {
                    itemsCostError.setText("Req.");
                    costValid = false;
                }
            }
        }
        );

        itemsCostError = new JLabel("");
        itemsCostError.setBounds(150, 408, 200, 15);
        itemsCostError.setForeground(Color.red);
        panel.add(itemsCostError);

        JLabel quantityOfItem = new JLabel("Quantity");
        quantityOfItem.setBounds(207, 408, 100, 15);
        quantityOfItem.setVisible(false);
        panel.add(quantityOfItem);

        itemsQuantity = new JTextField("");
        itemsQuantity.setBounds(207, 425, 65, 20);
        itemsQuantity.setVisible(false);
        panel.add(itemsQuantity);

        itemsQuantity.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = itemsQuantity.getText();
                StringBuilder filteredText = new StringBuilder();

                for (char c : text.toCharArray()) {
                    if (Character.isDigit(c)) {
                        filteredText.append(c);
                    }
                }

                if (filteredText.length() > 0 && filteredText.charAt(0) == '0') {
                    filteredText.delete(0, 1);
                }

                if (filteredText.length() > 8) {
                    filteredText.setLength(8);
                }
                itemsQuantity.setText(filteredText.toString());

                if (filteredText.length() == 0) {
                    itemsQuantityError.setText("Req.");
                    quantityValid = false;
                } else if (filteredText.length() > 0) {
                    itemsQuantityError.setText("");
                    quantityValid = true;
                }
            }
        }
        );

        itemsQuantityError = new JLabel("");
        itemsQuantityError.setBounds(259, 408, 200, 15);
        itemsQuantityError.setForeground(Color.red);
        panel.add(itemsQuantityError);

        JLabel restockThresholdOfItem = new JLabel("Restock");
        restockThresholdOfItem.setBounds(292, 408, 100, 15);
        restockThresholdOfItem.setVisible(false);
        panel.add(restockThresholdOfItem);

        itemsRestockThreshold = new JTextField("");
        itemsRestockThreshold.setBounds(292, 425, 65, 20);
        itemsRestockThreshold.setVisible(false);
        panel.add(itemsRestockThreshold);

        itemsRestockThreshold.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = itemsRestockThreshold.getText();
                StringBuilder filteredText = new StringBuilder();

                for (char c : text.toCharArray()) {
                    if (Character.isDigit(c)) {
                        filteredText.append(c);
                    }
                }

                if (filteredText.length() > 0 && filteredText.charAt(0) == '0') {
                    filteredText.delete(0, 1);
                }

                if (filteredText.length() > 8) {
                    filteredText.setLength(8);
                }
                itemsRestockThreshold.setText(filteredText.toString());

                if (filteredText.length() == 0) {
                    itemsRestockThresholdError.setText("Req.");
                    restockThresholdValid = false;
                } else if (filteredText.length() > 0) {
                    itemsRestockThresholdError.setText("");
                    restockThresholdValid = true;
                }
            }
        }
        );

        itemsRestockThresholdError = new JLabel("");
        itemsRestockThresholdError.setBounds(344, 408, 200, 15);
        itemsRestockThresholdError.setForeground(Color.red);
        panel.add(itemsRestockThresholdError);

        itemSelectedWarning = new JLabel("");
        itemSelectedWarning.setBounds(268, 445, 150, 15);
        itemSelectedWarning.setForeground(Color.GRAY);
        panel.add(itemSelectedWarning);

        JButton resetItem = new JButton("Reset");
        resetItem.setBounds(130, 517, 115, 50);
        resetItem.setBackground(thistle);
        resetItem.setVisible(false);
        resetItem.addActionListener((e) -> {
            itemSelected = -1;
            itemSelectedWarning.setText("");

            nameValid = true;
            descriptionValid = true;
            heftValid = true;
            hardnessValid = true;
            sizeValid = true;
            weightValid = true;
            retailValid = true;
            costValid = true;
            quantityValid = true;
            restockThresholdValid = true;

            itemsName.setText("");
            itemsNameError.setText("");
            itemsDescription.setText("");
            itemsDescriptionError.setText("");
            itemIsIgneous.setSelected(true);
            itemIsSedimentary.setSelected(false);
            itemIsMetamorphic.setSelected(false);
            itemIsStone.setSelected(true);
            itemIsGemstone.setSelected(false);
            itemIsPreciousGemstone.setSelected(false);

            grainSizeOfItem.setVisible(true);
            itemIsCoarseGrained.setVisible(true);
            itemIsCoarseGrained.setSelected(false);
            itemIsFineGrained.setVisible(true);
            itemIsFineGrained.setSelected(false);
            grainShapeOfItem.setVisible(true);
            itemsGrainShape.setVisible(true);
            itemsGrainShape.setText("");
            heftOfItem.setVisible(false);
            itemsHeft.setVisible(false);
            itemsHeft.setText("");
            itemsHeftError.setText("");
            itemsHardness.setText("");
            itemsHardnessError.setText("");
            itemsSize.setText("");
            itemsSizeError.setText("");
            itemsWeight.setText("");
            itemsWeightError.setText("");
            itemsRetail.setText("");
            itemsRetailError.setText("");
            itemsCost.setText("");
            itemsCostError.setText("");
            itemsQuantity.setText("");
            itemsQuantityError.setText("");
            itemsRestockThreshold.setText("");
            itemsRestockThreshold.setText("");
            itemsRestockThresholdError.setText("");
        }
        );
        panel.add(resetItem);

        addItem = new JButton("Add Item");
        addItem.setBounds(8, 460, 115, 50);
        addItem.setBackground(thistle);
        addItem.setVisible(false);
        addItem.addActionListener((e) -> {
            newItem();
        }
        );
        panel.add(addItem);

        editItem = new JButton("Edit Item");
        editItem.setBounds(252, 460, 115, 50);
        editItem.setBackground(thistle);
        editItem.setVisible(false);
        editItem.addActionListener((e) -> {
            if (itemSelected != -1) {
                editItem();
            } else {
                itemSelectedWarning.setText("No item selected");
                itemSelectedWarning.setBounds(260, 445, 150, 15);
                itemSelectedWarning.setForeground(Color.red);
            }
        }
        );
        panel.add(editItem);

        deleteItem = new JButton("Delete Item");
        deleteItem.setBounds(252, 517, 115, 50);
        deleteItem.setBackground(thistle);
        deleteItem.setVisible(false);
        deleteItem.addActionListener((e) -> {
            if (itemSelected != -1) {
                deleteItem();
            } else {
                itemSelectedWarning.setText("No item selected");
                itemSelectedWarning.setBounds(260, 445, 150, 15);
                itemSelectedWarning.setForeground(Color.red);
            }
        }
        );
        panel.add(deleteItem);

        editItems = new JButton("Edit Items");
        editItems.setBounds(130, 460, 115, 50);
        editItems.setBackground(thistle);
        editItems.addActionListener((e) -> {
            customerSelected = -1;
            clearCustomerData();
            managerAsCustomer = false;
            customerView.jp.setVisible(false);

            resetOrderDates();

            validOrderStartYear.setVisible(false);
            minimumYear.setVisible(false);
            validOrderStartMonth.setVisible(false);
            minimumMonth.setVisible(false);
            validOrderStartDay.setVisible(false);
            minimumDay.setVisible(false);
            validOrderEndYear.setVisible(false);
            maximumYear.setVisible(false);
            validOrderEndMonth.setVisible(false);
            maximumMonth.setVisible(false);
            validOrderEndDay.setVisible(false);
            maximumDay.setVisible(false);
            orderDateError.setText("");

            typeOfAccount.setVisible(false);
            searchForCustomer.setVisible(false);
            searchForManager.setVisible(false);

            /*
            Updates the inventory for the manager
            */
            Thread updData = new Thread(updateData);
            updData.start();
            inventory.setVisible(true);
            allItems.setVisible(true);

            searchBarEntry.setVisible(true);
            searchBar.setVisible(true);
            nameOfItem.setVisible(true);
            itemsName.setVisible(true);
            descriptionOfItem.setVisible(true);
            itemsDescription.setVisible(true);
            categoryOfItem.setVisible(true);
            itemIsIgneous.setVisible(true);
            itemIsIgneous.setSelected(true);
            itemIsSedimentary.setVisible(true);
            itemIsSedimentary.setSelected(false);
            itemIsMetamorphic.setVisible(true);
            itemIsMetamorphic.setSelected(false);
            classificationOfItem.setVisible(true);
            itemIsStone.setVisible(true);
            itemIsStone.setSelected(true);
            itemIsGemstone.setVisible(true);
            itemIsGemstone.setSelected(false);
            itemIsPreciousGemstone.setVisible(true);
            itemIsPreciousGemstone.setSelected(false);
            grainSizeOfItem.setVisible(true);
            itemIsCoarseGrained.setVisible(true);
            itemIsCoarseGrained.setSelected(false);
            itemIsFineGrained.setVisible(true);
            itemIsFineGrained.setSelected(false);
            grainShapeOfItem.setVisible(true);
            itemsGrainShape.setVisible(true);
            hardnessOfItem.setVisible(true);
            itemsHardness.setVisible(true);
            sizeOfItem.setVisible(true);
            sizeOfItem.setText("Size [Centimeters]");
            itemsSize.setVisible(true);
            weightOfItem.setVisible(true);
            weightOfItem.setText("Weight [Grams]");
            itemsWeight.setVisible(true);
            retailOfItem.setVisible(true);
            dollarSign1.setVisible(true);
            itemsRetail.setVisible(true);
            costOfItem.setVisible(true);
            dollarSign2.setVisible(true);
            itemsCost.setVisible(true);
            quantityOfItem.setVisible(true);
            itemsQuantity.setVisible(true);
            restockThresholdOfItem.setVisible(true);
            itemsRestockThreshold.setVisible(true);

            customerjs.setVisible(false);
            managerdiscountjs.setVisible(false);
            resetItem.setVisible(true);
            addItem.setVisible(true);
            editItem.setVisible(true);
            deleteItem.setVisible(true);
            addImage.setVisible(true);
            returnToView.setVisible(true);

            logonNameOfCustomer.setVisible(false);
            customersLogonName.setVisible(false);
            customersLogonName.setText("");
            customersLogonNameError.setText("");
            logonCustomerSelect.setVisible(false);
            emailOfCustomer.setVisible(false);
            customersEmail.setVisible(false);
            customersEmail.setText("");
            customersEmailError.setText("");
            emailCustomerSelect.setVisible(false);
            phoneNumberOfCustomer.setVisible(false);
            customersPhoneNumber1.setVisible(false);
            customersPhoneNumber1.setText("");
            customersPhoneNumber2.setVisible(false);
            customersPhoneNumber2.setText("");
            customersPhoneNumber3.setVisible(false);
            customersPhoneNumber3.setText("");
            customersPhoneNumberError.setText("");
            phoneNumberCustomerSelect.setVisible(false);
            salesDetails.setVisible(false);
            userDetails.setVisible(false);
            resetCustomer.setVisible(false);
            editItems.setVisible(false);
            editUsers.setVisible(false);
            editDiscounts.setVisible(false);
            logOut.setVisible(false);

            jf.setBounds(jf.getX(), jf.getY(), 1216, 610);
            jp.add(viewPanel);
            jp.repaint();
        }
        );
        panel.add(editItems);

        /*--------------------------------------------------DISCOUNTS SECTION--------------------------------------------------*/
        discountCol = new String[]{"Discount Code", "Level", "Inventory ID", "Type", "Percentage", "Dollar Amount", "Start Date", "Expiration Date"};
        discountjt = new JTable();
        discountjt.setModel(new DefaultTableModel(discountData, discountCol));
        discountjt.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        discountjs = new ScrollPane(discountjt);
        discountjs.setBounds(0, 0, 1000, 572);
        discountjs.setPreferredSize(new Dimension(800, 572));
        discountjt.setDefaultEditor(Object.class, null);
        discountjs.setVisible(false);
        viewPanel.add(discountjs, BorderLayout.CENTER);

        discountjt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                discountsCodeError.setText("");
                discountsDescriptionError.setText("");
                discountsInventoryID.setText("");
                discountsPercentageError.setText("");
                discountsAmountError.setText("");
                discountDateError.setText("");
                discountSelected = discountjt.getSelectedRow();
                for (int i = 0; i < LoginView.discount.size(); i++) {
                    if (discountSelected == i) {
                        discountSelectedWarning.setText("Discount Selected: " + LoginView.discount.get(i).discountID);
                        discountSelectedWarning.setForeground(Color.GRAY);
                        discountsCode.setText(LoginView.discount.get(i).discountCode);
                        discountsDescription.setText(LoginView.discount.get(i).discountDescription);
                        if (LoginView.discount.get(i).discountLevel == 0) {
                            discountIsCartLevel.setSelected(true);
                            discountIsItemLevel.setSelected(false);

                            discountItemSelectedID = -1;

                            viewPanel.remove(discountjs);
                            viewPanel.add(discountjs, BorderLayout.CENTER);
                            discountitemsjs.setVisible(false);
                            discountsInventoryID.setVisible(false);
                            discountsInventoryID.setText("");
                        } else if (LoginView.discount.get(i).discountLevel == 1) {
                            discountIsCartLevel.setSelected(false);
                            discountIsItemLevel.setSelected(true);

                            viewPanel.remove(discountjs);
                            viewPanel.add(discountjs, BorderLayout.EAST);
                            discountitemsjs.setVisible(true);
                            discountsInventoryID.setVisible(true);
                            discountItemSelectedID = LoginView.discount.get(i).inventoryID;
                            discountsInventoryID.setText("ID Selected: " + discountItemSelectedID);
                            discountsInventoryID.setForeground(Color.GRAY);
                        }
                        if (LoginView.discount.get(i).discountType == 0) {
                            discountIsPercentage.setSelected(true);
                            discountIsAmount.setSelected(false);

                            percentageOfDiscount.setVisible(true);
                            discountsPercentage.setVisible(true);
                            discountsPercentage.setText(LoginView.discount.get(i).discountPercentage + "");
                            discountsPercentageError.setVisible(true);
                            amountOfDiscount.setVisible(false);
                            amountDollarSign.setVisible(false);
                            discountsAmount.setVisible(false);
                            discountsAmountError.setVisible(false);
                        } else if (LoginView.discount.get(i).discountType == 1) {
                            discountIsPercentage.setSelected(false);
                            discountIsAmount.setSelected(true);

                            percentageOfDiscount.setVisible(false);
                            discountsPercentage.setVisible(false);
                            discountsPercentageError.setVisible(false);
                            amountOfDiscount.setVisible(true);
                            amountDollarSign.setVisible(true);
                            discountsAmount.setVisible(true);
                            discountsAmount.setText(LoginView.discount.get(i).discountDollarAmount + "");
                            discountsAmountError.setVisible(true);
                        }
                        if (LoginView.discount.get(i).startDate != null) {
                            discountStartDate.setSelected(true);
                            discountStartDateNotPresent.setSelected(false);
                            String date = LoginView.discount.get(i).startDate + "";
                            String year = date.substring(0, 4);
                            String month = date.substring(5, 7);
                            String day = date.substring(8, 10);
                            int yearUpdate = Integer.parseInt(year);
                            int monthUpdate = Integer.parseInt(month);
                            int dayUpdate = Integer.parseInt(day);
                            startYear.setSelectedItem(year);
                            startMonth.setSelectedIndex(monthUpdate - 1);
                            startDay.setSelectedIndex(dayUpdate - 1);
                        } else {
                            discountStartDate.setSelected(false);
                            discountStartDateNotPresent.setSelected(true);
                            Calendar today = Calendar.getInstance();
                            int currentYear = today.get(Calendar.YEAR);
                            int currentMonth = today.get(Calendar.MONTH) + 1;
                            int currentDay = today.get(Calendar.DAY_OF_MONTH);

                            startYear.setSelectedIndex(0);
                            startMonth.setSelectedIndex(currentMonth - 1);
                            startDay.setSelectedIndex(currentDay - 1);
                        }
                        String date = LoginView.discount.get(i).expirationDate + "";
                        String year = date.substring(0, 4);
                        String month = date.substring(5, 7);
                        String day = date.substring(8, 10);
                        int yearUpdate = Integer.parseInt(year);
                        int monthUpdate = Integer.parseInt(month);
                        int dayUpdate = Integer.parseInt(day);
                        expireYear.setSelectedItem(year);
                        expireMonth.setSelectedIndex(monthUpdate - 1);
                        expireDay.setSelectedIndex(dayUpdate - 1);
                        break;
                    }
                }
            }
        }
        );

        discountitemsCol = new String[]{"Item Name"};
        discountitemsjt = new JTable();
        discountitemsjt.setModel(new DefaultTableModel(discountitemsData, discountitemsCol));
        discountitemsjt.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        discountitemsjs = new ScrollPane(discountitemsjt);
        discountitemsjs.setBounds(0, 0, 200, 572);
        discountitemsjs.setPreferredSize(new Dimension(200, 572));
        discountitemsjt.setDefaultEditor(Object.class, null);
        discountitemsjs.setVisible(false);
        viewPanel.add(discountitemsjs, BorderLayout.WEST);

        discountitemsjt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int discountItemSelected = discountitemsjt.getSelectedRow();
                discountItemSelectedID = LoginView.inventory.get(discountItemSelected).inventoryID;

                discountsInventoryID.setText("ID Selected: " + discountItemSelectedID);
                discountsInventoryID.setForeground(Color.GRAY);
            }
        }
        );

        JLabel codeOfDiscount = new JLabel("Discount Code");
        codeOfDiscount.setBounds(25, 20, 200, 15);
        codeOfDiscount.setVisible(false);
        panel.add(codeOfDiscount);

        discountsCode = new JTextField("");
        discountsCode.setBounds(25, 35, 150, 20);
        discountsCode.setVisible(false);
        panel.add(discountsCode);

        discountsCode.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (discountsCode.getText().length() > 20) {
                    discountsCode.setText(discountsCode.getText().substring(0, discountsCode.getText().length() - 1));
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                discountCodeValid = true;
                discountsCodeError.setText("");
                if (discountsCode.getText().isEmpty()) {
                    discountsCodeError.setText("Code required");
                    discountCodeValid = false;
                }
            }
        });

        discountsCodeError = new JLabel("");
        discountsCodeError.setBounds(175, 37, 200, 15);
        discountsCodeError.setForeground(Color.red);
        panel.add(discountsCodeError);

        JLabel descriptionOfDiscount = new JLabel("Discount Description");
        descriptionOfDiscount.setBounds(25, 55, 200, 50);
        descriptionOfDiscount.setVisible(false);
        panel.add(descriptionOfDiscount);

        discountsDescription = new JTextArea("");
        discountsDescription.setBounds(25, 90, 220, 34);
        discountsDescription.setLineWrap(true);
        discountsDescription.setWrapStyleWord(true);
        discountsDescription.setVisible(false);
        panel.add(discountsDescription);

        discountsDescription.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (discountsDescription.getText().length() > 50) {
                    discountsDescription.setText(discountsDescription.getText().substring(0, discountsDescription.getText().length() - 1));
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                discountDescriptionValid = true;
                discountsDescriptionError.setText("");
                if (discountsDescription.getText().isEmpty()) {
                    discountsDescriptionError.setText("Description required");
                    discountDescriptionValid = false;
                }
            }
        }
        );

        discountsDescriptionError = new JLabel("");
        discountsDescriptionError.setBounds(150, 72, 150, 15);
        discountsDescriptionError.setForeground(Color.red);
        panel.add(discountsDescriptionError);

        JLabel levelOfDiscount = new JLabel("Discount Level");
        levelOfDiscount.setBounds(25, 142, 200, 15);
        levelOfDiscount.setVisible(false);
        panel.add(levelOfDiscount);

        discountIsCartLevel = new JRadioButton("Cart");
        discountIsCartLevel.setBounds(25, 159, 50, 25);
        discountIsCartLevel.setBackground(null);
        discountIsCartLevel.setVisible(false);
        panel.add(discountIsCartLevel);

        discountIsItemLevel = new JRadioButton("Item");
        discountIsItemLevel.setBounds(110, 159, 50, 25);
        discountIsItemLevel.setBackground(null);
        discountIsItemLevel.setVisible(false);
        panel.add(discountIsItemLevel);

        discountIsCartLevel.addActionListener((e) -> {
            discountIsCartLevel.setSelected(true);
            discountIsItemLevel.setSelected(false);

            discountItemSelectedID = -1;

            viewPanel.remove(discountjs);
            viewPanel.add(discountjs, BorderLayout.CENTER);
            discountitemsjs.setVisible(false);
            discountsInventoryID.setVisible(false);
            discountsInventoryID.setText("");
        }
        );

        discountIsItemLevel.addActionListener((e) -> {
            discountIsCartLevel.setSelected(false);
            discountIsItemLevel.setSelected(true);

            viewPanel.remove(discountjs);
            viewPanel.add(discountjs, BorderLayout.EAST);
            discountitemsjs.setVisible(true);
            discountsInventoryID.setVisible(true);
        }
        );

        JLabel typeOfDiscount = new JLabel("Discount Type");
        typeOfDiscount.setBounds(25, 220, 200, 15);
        typeOfDiscount.setVisible(false);
        panel.add(typeOfDiscount);

        discountIsPercentage = new JRadioButton("Percent");
        discountIsPercentage.setBounds(25, 237, 75, 25);
        discountIsPercentage.setBackground(null);
        discountIsPercentage.setVisible(false);
        panel.add(discountIsPercentage);

        discountIsAmount = new JRadioButton("Amount");
        discountIsAmount.setBounds(110, 237, 75, 25);
        discountIsAmount.setBackground(null);
        discountIsAmount.setVisible(false);
        panel.add(discountIsAmount);

        discountIsPercentage.addActionListener((e) -> {
            discountIsPercentage.setSelected(true);
            discountIsAmount.setSelected(false);

            percentageOfDiscount.setVisible(true);
            discountsPercentage.setVisible(true);
            discountsPercentageError.setVisible(true);
            amountOfDiscount.setVisible(false);
            amountDollarSign.setVisible(false);
            discountsAmount.setVisible(false);
            discountsAmountError.setVisible(false);
        }
        );

        discountIsAmount.addActionListener((e) -> {
            discountIsPercentage.setSelected(false);
            discountIsAmount.setSelected(true);

            percentageOfDiscount.setVisible(false);
            discountsPercentage.setVisible(false);
            discountsPercentageError.setVisible(false);
            amountOfDiscount.setVisible(true);
            amountDollarSign.setVisible(true);
            discountsAmount.setVisible(true);
            discountsAmountError.setVisible(true);
        }
        );

        percentageOfDiscount = new JLabel("Discount Percent");
        percentageOfDiscount.setBounds(220, 220, 150, 15);
        percentageOfDiscount.setVisible(false);
        panel.add(percentageOfDiscount);

        discountsPercentage = new JTextField("");
        discountsPercentage.setBounds(255, 235, 23, 20);
        discountsPercentage.setVisible(false);
        panel.add(discountsPercentage);

        discountsPercentage.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                percentValid = true;
                discountsPercentageError.setText("");
                if (!discountsPercentage.getText().isEmpty()) {
                    if (discountsPercentage.getText().length() > 3) {
                        for (int i = 0; i < discountsPercentage.getText().length(); i++) {
                            discountsPercentage.setText(discountsPercentage.getText().substring(0, 3));
                        }
                    }
                    if (!discountsPercentage.getText().matches("^\\d+\\.\\d{1}$")) {
                        discountsPercentageError.setText("Single decimal");
                        percentValid = false;
                    } else {
                        double percent = Double.parseDouble(discountsPercentage.getText());
                        if (percent > 1.0) {
                            discountsPercentageError.setText("Too large");
                        }
                    }
                } else {
                    discountsPercentageError.setText("Percent req.");
                    percentValid = false;
                }
            }
        }
        );

        discountsPercentageError = new JLabel("");
        discountsPercentageError.setBounds(278, 237, 150, 15);
        discountsPercentageError.setForeground(Color.red);
        panel.add(discountsPercentageError);

        amountOfDiscount = new JLabel("Discount Amount");
        amountOfDiscount.setBounds(198, 220, 150, 15);
        amountOfDiscount.setVisible(false);
        panel.add(amountOfDiscount);

        amountDollarSign = new JLabel("$");
        amountDollarSign.setBounds(198, 237, 150, 15);
        amountDollarSign.setVisible(false);
        panel.add(amountDollarSign);

        discountsAmount = new JTextField("");
        discountsAmount.setBounds(205, 235, 82, 20);
        discountsAmount.setVisible(false);
        panel.add(discountsAmount);

        discountsAmount.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                amountValid = true;
                discountsAmountError.setText("");
                if (!discountsAmount.getText().isEmpty()) {
                    if (discountsAmount.getText().length() > 11) {
                        for (int i = 0; i < discountsAmount.getText().length(); i++) {
                            discountsAmount.setText(discountsAmount.getText().substring(0, 11));
                        }
                    }
                    if (!discountsAmount.getText().matches("^\\d+\\.\\d{2}$")) {
                        char[] chars = discountsAmount.getText().toCharArray();
                        for (char c : chars) {
                            if (!Character.isDigit(c)) {
                                discountsAmountError.setText("Digits only");
                                amountValid = false;
                                break;
                            }
                        }
                    }
                } else {
                    discountsAmountError.setText("Amount req.");
                    amountValid = false;
                }
            }
        }
        );

        discountsAmountError = new JLabel("");
        discountsAmountError.setBounds(288, 237, 150, 15);
        discountsAmountError.setForeground(Color.red);
        panel.add(discountsAmountError);

        discountsInventoryID = new JLabel("");
        discountsInventoryID.setBounds(225, 153, 125, 20);
        discountsInventoryID.setVisible(false);
        panel.add(discountsInventoryID);

        JLabel startDateOfDiscount = new JLabel("Start Date");
        startDateOfDiscount.setBounds(225, 320, 200, 15);
        startDateOfDiscount.setVisible(false);
        panel.add(startDateOfDiscount);

        discountStartDate = new JRadioButton("Has Start Date");
        discountStartDate.setBounds(225, 335, 110, 25);
        discountStartDate.setBackground(null);
        discountStartDate.setVisible(false);
        panel.add(discountStartDate);

        discountStartDateNotPresent = new JRadioButton("No Start Date");
        discountStartDateNotPresent.setBounds(225, 360, 100, 25);
        discountStartDateNotPresent.setBackground(null);
        discountStartDateNotPresent.setVisible(false);
        panel.add(discountStartDateNotPresent);

        discountStartDate.addActionListener((e) -> {
            discountStartDate.setSelected(true);
            discountStartDateNotPresent.setSelected(false);
        }
        );

        discountStartDateNotPresent.addActionListener((e) -> {
            discountStartDate.setSelected(false);
            discountStartDateNotPresent.setSelected(true);
        }
        );

        JLabel validDiscountStartYear = new JLabel("Starting Year");
        validDiscountStartYear.setBounds(18, 320, 100, 15);
        validDiscountStartYear.setVisible(false);
        panel.add(validDiscountStartYear);

        int yearsForDiscountList = currentDate.getYear();

        String[] discountStarterYear = {"" + yearsForDiscountList, "" + (yearsForDiscountList + 1), "" + (yearsForDiscountList + 2), "" + (yearsForDiscountList + 3), "" + (yearsForDiscountList + 5), "" + (yearsForDiscountList + 6), "" + (yearsForDiscountList + 7)};
        startYear = new JComboBox(discountStarterYear);
        startYear.setBounds(27, 335, 55, 20);
        startYear.setVisible(false);
        panel.add(startYear);

        JLabel validDiscountStartMonth = new JLabel("Starting Month");
        validDiscountStartMonth.setBounds(10, 365, 110, 15);
        validDiscountStartMonth.setVisible(false);
        panel.add(validDiscountStartMonth);

        String[] discountMonths = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        startMonth = new JComboBox(discountMonths);
        startMonth.setBounds(27, 380, 55, 20);
        startMonth.setSelectedIndex(discountStartMonth - 1);
        startMonth.setVisible(false);
        panel.add(startMonth);

        Calendar discountStartCalendar = new GregorianCalendar(Integer.parseInt(startYear.getSelectedItem().toString()), Integer.parseInt(startMonth.getSelectedItem().toString()), 1);
        String[] discountStartDayInitial = new String[discountStartCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)];

        JLabel validDiscountStartDay = new JLabel("Starting Day");
        validDiscountStartDay.setBounds(19, 410, 100, 15);
        validDiscountStartDay.setVisible(false);
        panel.add(validDiscountStartDay);

        startDay = new JComboBox(discountStartDayInitial);
        startDay.setBounds(27, 425, 55, 20);
        startDay.setVisible(false);
        panel.add(startDay);

        startDay.addActionListener((e) -> {
            if (startDay.getSelectedItem() != null) {
                discountStartDay = Integer.parseInt(startDay.getSelectedItem().toString());
            }
        });

        startMonth.addActionListener((e) -> {
            setDiscountStartDay();
        });

        startYear.addActionListener((e) -> {
            discountStartYear = Integer.parseInt(startYear.getSelectedItem().toString());
            setDiscountStartDay();
        });

        JLabel validDiscountExpireYear = new JLabel("Expire Year");
        validDiscountExpireYear.setBounds(120, 320, 100, 15);
        validDiscountExpireYear.setVisible(false);
        panel.add(validDiscountExpireYear);

        String[] discountEnderYear = {"" + yearsForList, "" + (yearsForList + 1), "" + (yearsForList + 2), "" + (yearsForList + 3), "" + (yearsForList + 5), "" + (yearsForList + 6), "" + (yearsForList + 7)};
        expireYear = new JComboBox(discountEnderYear);
        expireYear.setBounds(127, 335, 55, 20);
        expireYear.setVisible(false);
        panel.add(expireYear);

        JLabel validDiscountExpireMonth = new JLabel("Expire Month");
        validDiscountExpireMonth.setBounds(117, 365, 105, 15);
        validDiscountExpireMonth.setVisible(false);
        panel.add(validDiscountExpireMonth);

        expireMonth = new JComboBox(discountMonths);
        expireMonth.setBounds(127, 380, 55, 20);
        expireMonth.setSelectedIndex(discountExpirationMonth - 1);
        expireMonth.setVisible(false);
        panel.add(expireMonth);

        Calendar discountEndCalendar = new GregorianCalendar(Integer.parseInt(expireYear.getSelectedItem().toString()), Integer.parseInt(expireMonth.getSelectedItem().toString()), 1);
        String[] discountExpireDayInitial = new String[discountEndCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)];

        JLabel validDiscountExpireDay = new JLabel("Expire Day");
        validDiscountExpireDay.setBounds(124, 410, 100, 15);
        validDiscountExpireDay.setVisible(false);
        panel.add(validDiscountExpireDay);

        expireDay = new JComboBox(discountExpireDayInitial);
        expireDay.setBounds(127, 425, 55, 20);
        expireDay.setVisible(false);
        panel.add(expireDay);

        expireDay.addActionListener((e) -> {
            if (expireDay.getSelectedItem() != null) {
                discountExpirationDay = Integer.parseInt(expireDay.getSelectedItem().toString());
            }
        }
        );

        expireMonth.addActionListener((e) -> {
            setDiscountEndDay();
        }
        );

        expireYear.addActionListener((e) -> {
            discountExpirationYear = Integer.parseInt(expireYear.getSelectedItem().toString());
            setDiscountEndDay();
        }
        );

        resetDiscountDates();

        discountDateError = new JLabel("");
        discountDateError.setBounds(80, 285, 250, 50);
        discountDateError.setForeground(Color.red);
        panel.add(discountDateError);

        discountSelectedWarning = new JLabel("");
        discountSelectedWarning.setBounds(215, 445, 200, 15);
        discountSelectedWarning.setForeground(Color.GRAY);
        panel.add(discountSelectedWarning);

        JButton resetDiscount = new JButton("Reset");
        resetDiscount.setBounds(193, 517, 172, 50);
        resetDiscount.setBackground(thistle);
        resetDiscount.setVisible(false);
        resetDiscount.addActionListener((e) -> {
            resetDiscountDates();
            discountItemSelectedID = -1;

            discountCodeValid = true;
            discountDescriptionValid = true;
            percentValid = true;
            amountValid = true;

            discountsCode.setText("");
            discountsCodeError.setText("");
            discountsDescription.setText("");
            discountsDescriptionError.setText("");
            discountIsCartLevel.setSelected(true);
            discountIsItemLevel.setSelected(false);
            typeOfDiscount.setVisible(false);
            discountIsPercentage.setSelected(true);
            discountIsAmount.setSelected(false);
            percentageOfDiscount.setVisible(true);
            discountsPercentage.setVisible(true);
            discountsPercentage.setText("");
            discountsPercentageError.setText("");
            amountOfDiscount.setVisible(false);
            amountDollarSign.setVisible(false);
            discountsAmount.setVisible(false);
            discountsAmount.setText("");
            discountsAmountError.setText("");
            discountsInventoryID.setText("");
            discountStartDate.setSelected(true);
            discountStartDateNotPresent.setSelected(false);
            discountDateError.setText("");
            discountSelectedWarning.setText("");
        }
        );
        panel.add(resetDiscount);

        addDiscount = new JButton("Add Discount");
        addDiscount.setBounds(10, 460, 172, 50);
        addDiscount.setBackground(thistle);
        addDiscount.setVisible(false);
        addDiscount.addActionListener((e) -> {
            newDiscount();
        }
        );
        panel.add(addDiscount);

        editDiscount = new JButton("Edit Discount");
        editDiscount.setBounds(193, 460, 172, 50);
        editDiscount.setBackground(thistle);
        editDiscount.setVisible(false);
        editDiscount.addActionListener((e) -> {
            discountSelectedWarning.setText("");
            if (discountSelected != -1) {
                editDiscount();
            } else {
                discountSelectedWarning.setText("No discount selected");
                discountSelectedWarning.setForeground(Color.red);
            }
        }
        );
        panel.add(editDiscount);

        editDiscounts = new JButton("Edit Discounts");
        editDiscounts.setBounds(252, 460, 115, 50);
        editDiscounts.setBackground(thistle);
        editDiscounts.addActionListener((e) -> {
            customerSelected = -1;
            clearCustomerData();
            managerAsCustomer = false;
            customerView.jp.setVisible(false);

            resetOrderDates();

            validOrderStartYear.setVisible(false);
            minimumYear.setVisible(false);
            validOrderStartMonth.setVisible(false);
            minimumMonth.setVisible(false);
            validOrderStartDay.setVisible(false);
            minimumDay.setVisible(false);
            validOrderEndYear.setVisible(false);
            maximumYear.setVisible(false);
            validOrderEndMonth.setVisible(false);
            maximumMonth.setVisible(false);
            validOrderEndDay.setVisible(false);
            maximumDay.setVisible(false);
            orderDateError.setText("");

            typeOfAccount.setVisible(false);
            searchForCustomer.setVisible(false);
            searchForManager.setVisible(false);

            /*
            Updates the discount table for manager
            */
            ManagerView.discountData = ManagerView.getDiscountData();
            DefaultTableModel dstable = (DefaultTableModel) ManagerView.discountjt.getModel();
            dstable.setDataVector(ManagerView.discountData, ManagerView.discountCol);
            /*
            Updates the discount items table for manager
            */
            ManagerView.discountitemsData = ManagerView.getDiscountItemsData();
            DefaultTableModel distable = (DefaultTableModel) ManagerView.discountitemsjt.getModel();
            distable.setDataVector(ManagerView.discountitemsData, ManagerView.discountitemsCol);

            codeOfDiscount.setVisible(true);
            discountsCode.setVisible(true);
            descriptionOfDiscount.setVisible(true);
            discountsDescription.setVisible(true);
            levelOfDiscount.setVisible(true);
            discountIsCartLevel.setVisible(true);
            discountIsCartLevel.setSelected(true);
            discountIsItemLevel.setVisible(true);
            discountIsItemLevel.setSelected(false);
            typeOfDiscount.setVisible(true);
            discountIsPercentage.setVisible(true);
            discountIsPercentage.setSelected(true);
            discountIsAmount.setVisible(true);
            discountIsAmount.setSelected(false);
            percentageOfDiscount.setVisible(true);
            discountsPercentage.setVisible(true);
            discountsPercentageError.setVisible(true);
            amountOfDiscount.setVisible(false);
            amountDollarSign.setVisible(false);
            discountsAmount.setVisible(false);
            discountsAmountError.setVisible(false);
            discountsInventoryID.setVisible(false);
            startDateOfDiscount.setVisible(true);
            discountStartDate.setVisible(true);
            discountStartDate.setSelected(true);
            discountStartDateNotPresent.setVisible(true);
            discountStartDateNotPresent.setSelected(false);
            validDiscountStartYear.setVisible(true);
            startYear.setVisible(true);
            validDiscountStartMonth.setVisible(true);
            startMonth.setVisible(true);
            validDiscountStartDay.setVisible(true);
            startDay.setVisible(true);
            validDiscountExpireYear.setVisible(true);
            expireYear.setVisible(true);
            validDiscountExpireMonth.setVisible(true);
            expireMonth.setVisible(true);
            validDiscountExpireDay.setVisible(true);
            expireDay.setVisible(true);

            resetDiscountDates();

            customerjs.setVisible(false);
            managerdiscountjs.setVisible(false);
            discountjs.setVisible(true);
            resetDiscount.setVisible(true);
            addDiscount.setVisible(true);
            editDiscount.setVisible(true);
            returnToView.setVisible(true);
            returnToView.setBounds(10, 517, 172, 50);

            logonNameOfCustomer.setVisible(false);
            customersLogonName.setVisible(false);
            customersLogonName.setText("");
            customersLogonNameError.setText("");
            logonCustomerSelect.setVisible(false);
            emailOfCustomer.setVisible(false);
            customersEmail.setVisible(false);
            customersEmail.setText("");
            customersEmailError.setText("");
            emailCustomerSelect.setVisible(false);
            phoneNumberOfCustomer.setVisible(false);
            customersPhoneNumber1.setVisible(false);
            customersPhoneNumber1.setText("");
            customersPhoneNumber2.setVisible(false);
            customersPhoneNumber2.setText("");
            customersPhoneNumber3.setVisible(false);
            customersPhoneNumber3.setText("");
            customersPhoneNumberError.setText("");
            phoneNumberCustomerSelect.setVisible(false);
            salesDetails.setVisible(false);
            userDetails.setVisible(false);
            resetCustomer.setVisible(false);
            editItems.setVisible(false);
            editUsers.setVisible(false);
            editDiscounts.setVisible(false);
            logOut.setVisible(false);

            jf.setBounds(jf.getX(), jf.getY(), 1390, 610);
            jp.add(viewPanel);
            jp.repaint();
        }
        );
        panel.add(editDiscounts);

        returnToView = new JButton("Return");
        returnToView.setBounds(8, 517, 115, 50);
        returnToView.setBackground(thistle);
        returnToView.setVisible(false);
        returnToView.addActionListener((e) -> {
            userSelected = -1;
            userSelectedWarning.setText("");
            itemSelected = -1;
            itemSelectedWarning.setText("");
            discountSelected = -1;
            discountSelectedWarning.setText("");
            needsRestock.setVisible(false);
            allItems.setVisible(false);

            validOrderStartYear.setVisible(true);
            minimumYear.setVisible(true);
            validOrderStartMonth.setVisible(true);
            minimumMonth.setVisible(true);
            validOrderStartDay.setVisible(true);
            minimumDay.setVisible(true);
            validOrderEndYear.setVisible(true);
            maximumYear.setVisible(true);
            validOrderEndMonth.setVisible(true);
            maximumMonth.setVisible(true);
            validOrderEndDay.setVisible(true);
            maximumDay.setVisible(true);

            typeOfAccount.setVisible(true);
            searchForCustomer.setVisible(true);
            searchForCustomer.setSelected(true);
            searchForManager.setVisible(true);
            searchForManager.setSelected(false);

            personjs.setVisible(false);
            loginjs.setVisible(false);
            inventory.setVisible(false);
            discountjs.setVisible(false);
            discountitemsjs.setVisible(false);

            loginValid = true;
            passwordValid = true;

            loginNameOfPerson.setVisible(false);
            personsLoginName.setVisible(false);
            personsLoginNameError.setText("");
            passwordOfPerson.setVisible(false);
            personsPassword.setVisible(false);
            personsPassword.setText("");
            personsPasswordError.setText("");
            personsSpecialError.setText("");
            prefixOfPerson.setVisible(false);
            personsPrefix.setVisible(false);
            personsPrefix.setSelectedIndex(0);
            firstNameOfPerson.setVisible(false);
            personsFirstName.setVisible(false);
            personsFirstName.setText("");
            personsFirstNameError.setText("");
            middleNameOfPerson.setVisible(false);
            personsMiddleName.setVisible(false);
            personsMiddleName.setText("");
            lastNameOfPerson.setVisible(false);
            personsLastName.setVisible(false);
            personsLastName.setText("");
            personsLastNameError.setText("");
            suffixOfPerson.setVisible(false);
            personsSuffix.setVisible(false);
            personsSuffix.setSelectedIndex(0);
            address1OfPerson.setVisible(false);
            personsAddress1.setVisible(false);
            personsAddress1.setText("");
            address2OfPerson.setVisible(false);
            personsAddress2.setVisible(false);
            personsAddress2.setText("");
            address3OfPerson.setVisible(false);
            personsAddress3.setVisible(false);
            personsAddress3.setText("");
            addressError.setText("");
            cityOfPerson.setVisible(false);
            personsCity.setVisible(false);
            personsCity.setText("");
            personsCityError.setText("");
            zipOfPerson.setVisible(false);
            personsZip.setVisible(false);
            personsZip.setText("");
            personsZipError.setText("");
            stateOfPerson.setVisible(false);
            personsState.setVisible(false);
            personsState.setSelectedIndex(0);
            personsStateError.setText("");
            emailOfPerson.setVisible(false);
            personsEmail.setVisible(false);
            personsEmail.setText("");
            personsEmailError.setText("");
            phoneOfPerson1.setVisible(false);
            phoneOfPerson2.setVisible(false);
            personsPhonePrimary1.setVisible(false);
            personsPhonePrimary2.setVisible(false);
            personsPhonePrimary3.setVisible(false);
            personsPhoneSecondary1.setVisible(false);
            personsPhoneSecondary2.setVisible(false);
            personsPhoneSecondary3.setVisible(false);
            personsPhoneError1.setText("");
            personsPhoneError2.setText("");
            securityQuestion1.setVisible(false);
            securityQuestion1.setSelectedIndex(0);
            securityQuestion2.setVisible(false);
            securityQuestion2.setSelectedIndex(0);
            securityQuestion3.setVisible(false);
            securityQuestion3.setSelectedIndex(0);
            securityAnswer1.setVisible(false);
            securityAnswer1.setText("");
            securityAnswer2.setVisible(false);
            securityAnswer2.setText("");
            securityAnswer3.setVisible(false);
            securityAnswer3.setText("");
            answerError.setText("");
            positionOfUser.setVisible(false);
            userIsCustomer.setVisible(false);
            userIsManager.setVisible(false);

            nameValid = true;
            descriptionValid = true;
            heftValid = true;
            hardnessValid = true;
            sizeValid = true;
            weightValid = true;
            retailValid = true;
            costValid = true;
            quantityValid = true;
            restockThresholdValid = true;

            searchBarEntry.setVisible(false);
            searchBar.setVisible(false);
            nameOfItem.setVisible(false);
            itemsName.setVisible(false);
            itemsName.setText("");
            itemsNameError.setText("");
            descriptionOfItem.setVisible(false);
            itemsDescription.setVisible(false);
            itemsDescription.setText("");
            itemsDescriptionError.setText("");
            categoryOfItem.setVisible(false);
            itemIsIgneous.setVisible(false);
            itemIsSedimentary.setVisible(false);
            itemIsMetamorphic.setVisible(false);
            classificationOfItem.setVisible(false);
            itemIsStone.setVisible(false);
            itemIsGemstone.setVisible(false);
            itemIsPreciousGemstone.setVisible(false);
            grainSizeOfItem.setVisible(false);
            itemIsCoarseGrained.setVisible(false);
            itemIsFineGrained.setVisible(false);
            grainShapeOfItem.setVisible(false);
            itemsGrainShape.setVisible(false);
            itemsGrainShape.setText("");
            heftOfItem.setVisible(false);
            itemsHeft.setVisible(false);
            itemsHeft.setText("");
            itemsHeftError.setText("");
            hardnessOfItem.setVisible(false);
            itemsHardness.setVisible(false);
            itemsHardness.setText("");
            itemsHardnessError.setText("");
            sizeOfItem.setVisible(false);
            itemsSize.setVisible(false);
            itemsSizeError.setText("");
            itemsSize.setText("");
            weightOfItem.setVisible(false);
            itemsWeight.setVisible(false);
            itemsWeight.setText("");
            itemsWeightError.setText("");
            retailOfItem.setVisible(false);
            dollarSign1.setVisible(false);
            itemsRetail.setVisible(false);
            itemsRetail.setText("");
            itemsRetailError.setText("");
            costOfItem.setVisible(false);
            dollarSign2.setVisible(false);
            itemsCost.setVisible(false);
            itemsCost.setText("");
            itemsCostError.setText("");
            quantityOfItem.setVisible(false);
            itemsQuantity.setVisible(false);
            itemsQuantity.setText("");
            itemsQuantityError.setText("");
            restockThresholdOfItem.setVisible(false);
            itemsRestockThreshold.setVisible(false);
            itemsRestockThreshold.setText("");
            itemsRestockThresholdError.setText("");

            discountItemSelectedID = -1;

            discountCodeValid = true;
            discountDescriptionValid = true;
            percentValid = true;
            amountValid = true;

            codeOfDiscount.setVisible(false);
            discountsCode.setVisible(false);
            discountsCode.setText("");
            discountsCodeError.setText("");
            descriptionOfDiscount.setVisible(false);
            discountsDescription.setVisible(false);
            discountsDescription.setText("");
            discountsDescriptionError.setText("");
            levelOfDiscount.setVisible(false);
            discountIsCartLevel.setVisible(false);
            discountIsItemLevel.setVisible(false);
            typeOfDiscount.setVisible(false);
            discountIsPercentage.setVisible(false);
            discountIsAmount.setVisible(false);
            percentageOfDiscount.setVisible(false);
            discountsPercentage.setVisible(false);
            discountsPercentage.setText("");
            discountsPercentageError.setText("");
            amountOfDiscount.setVisible(false);
            amountDollarSign.setVisible(false);
            discountsAmount.setVisible(false);
            discountsAmount.setText("");
            discountsAmountError.setText("");
            discountsInventoryID.setVisible(false);
            discountsInventoryID.setText("");
            startDateOfDiscount.setVisible(false);
            discountStartDate.setVisible(false);
            discountStartDateNotPresent.setVisible(false);
            validDiscountStartYear.setVisible(false);
            startYear.setVisible(false);
            validDiscountStartMonth.setVisible(false);
            startMonth.setVisible(false);
            validDiscountStartDay.setVisible(false);
            startDay.setVisible(false);
            validDiscountExpireYear.setVisible(false);
            expireYear.setVisible(false);
            validDiscountExpireMonth.setVisible(false);
            expireMonth.setVisible(false);
            validDiscountExpireDay.setVisible(false);
            expireDay.setVisible(false);
            discountDateError.setText("");
            discountSelectedWarning.setText("");

            resetUser.setVisible(false);
            addUser.setVisible(false);
            editUser.setVisible(false);
            deleteUser.setVisible(false);
            resetItem.setVisible(false);
            addItem.setVisible(false);
            editItem.setVisible(false);
            deleteItem.setVisible(false);
            resetDiscount.setVisible(false);
            addDiscount.setVisible(false);
            editDiscount.setVisible(false);

            addImage.setVisible(false);
            returnToView.setVisible(false);
            returnToView.setBounds(8, 517, 115, 50);
            logonNameOfCustomer.setVisible(true);
            customersLogonName.setVisible(true);
            logonCustomerSelect.setVisible(true);
            emailOfCustomer.setVisible(true);
            customersEmail.setVisible(true);
            emailCustomerSelect.setVisible(true);
            phoneNumberOfCustomer.setVisible(true);
            customersPhoneNumber1.setVisible(true);
            customersPhoneNumber2.setVisible(true);
            customersPhoneNumber3.setVisible(true);
            phoneNumberCustomerSelect.setVisible(true);
            salesDetails.setVisible(true);
            userDetails.setVisible(true);
            resetCustomer.setVisible(true);
            editItems.setVisible(true);
            editUsers.setVisible(true);
            editDiscounts.setVisible(true);
            logOut.setVisible(true);

            help.setBounds(340, 0, 50, 20);
            viewPanel.setBounds(200, 200, 500, 500);
            viewPanel.setPreferredSize(new Dimension(465, 0));
            panel.setPreferredSize(new Dimension(375, 0));
            jf.setBounds(jf.getX(), jf.getY(), 390, 610);
            jp.remove(viewPanel);
            jp.repaint();
        }
        );
        panel.add(returnToView);

        /*
        This button logs the user out, returning them to the login menu.
        */
        logOut = new JButton("Log Out");
        logOut.setBounds(8, 517, 115, 50);
        logOut.setBackground(thistle);
        logOut.addActionListener((e) -> {
            customerSelected = -1;
            customersLogonName.setText("");
            customersLogonNameError.setText("");

            CustomerView.panel.setBounds(0, 0, 273, 628);
            CustomerView.panel.setPreferredSize(new Dimension(120, 0));
            CustomerView.cartPanel.setBounds(0, 0, 267, 590);
            CustomerView.cartPanel.setPreferredSize(new Dimension(120, 0));
            CustomerView.cartjs.setBounds(0, 0, CustomerView.cartPanel.getWidth(), CustomerView.cartPanel.getHeight());

            resetOrderDates();

            clearCustomerData();
            managerAsCustomer = false;
            viewPanel.add(customerView.jp, BorderLayout.WEST);
            CustomerView.ordersButton.setVisible(true);
            CustomerView.logOut.setVisible(true);
            customerView.jp.setVisible(false);
            customerjs.setVisible(false);
            managerdiscountjs.setVisible(false);

            CustomerView.cardNumberEntry.setBounds(32, 245, 200, 15);
            CustomerView.cardNumber.setBounds(32, 262, 200, 20);
            CustomerView.securityCodeEntry.setBounds(32, 295, 200, 15);
            CustomerView.cardSecurityCode.setBounds(32, 312, 200, 20);
            CustomerView.cardExpireYear.setBounds(50, 345, 100, 15);
            CustomerView.cardExpirationYear.setBounds(55, 360, 55, 20);
            CustomerView.cardExpireMonth.setBounds(140, 345, 110, 15);
            CustomerView.cardExpirationMonth.setBounds(150, 360, 55, 20);
            CustomerView.cardError.setBounds(110, 245, 200, 15);
            CustomerView.cardSecurityError.setBounds(120, 295, 200, 15);
            CustomerView.expirationError.setBounds(CustomerView.panel.getWidth() / 8, 380, 200, 15);
            CustomerView.discountEntry.setBounds(32, 395, 200, 15);
            CustomerView.discountCode.setBounds(32, 412, 200, 20);
            CustomerView.discountError.setBounds(120, 395, 200, 15);
            CustomerView.checkout.setBounds(27, 450, 100, 50);
            CustomerView.clearCart.setBounds(136, 450, 100, 50);
            CustomerView.logOut.setBounds(27, 515, 100, 50);

            CustomerView.sortedIgn = 0;
            CustomerView.sortedSed = 0;
            CustomerView.sortedMet = 0;
            CustomerView.sortedSto = 0;
            CustomerView.sortedGem = 0;

            CustomerView.itemsName.setBounds(CustomerView.panel.getWidth() / 8, 260, 200, 15);
            CustomerView.itemsName.setHorizontalAlignment(SwingConstants.CENTER);
            CustomerView.itemsDescription.setBounds(11, 285, 250, 105);
            CustomerView.itemRockOrGem.setBounds(CustomerView.panel.getWidth() / 8, 375, 200, 15);
            CustomerView.itemRockOrGem.setHorizontalAlignment(SwingConstants.CENTER);
            CustomerView.itemGrainSize.setBounds(CustomerView.panel.getWidth() / 8, 390, 200, 15);
            CustomerView.itemGrainSize.setHorizontalAlignment(SwingConstants.CENTER);
            CustomerView.itemGrainShape.setBounds(CustomerView.panel.getWidth() / 8, 405, 200, 15);
            CustomerView.itemGrainShape.setHorizontalAlignment(SwingConstants.CENTER);
            CustomerView.itemHeft.setBounds(CustomerView.panel.getWidth() / 8, 390, 200, 15);
            CustomerView.itemHeft.setHorizontalAlignment(SwingConstants.CENTER);
            CustomerView.itemHardness.setBounds(CustomerView.panel.getWidth() / 8, 405, 200, 15);
            CustomerView.itemHardness.setHorizontalAlignment(SwingConstants.CENTER);
            CustomerView.stoneSize.setBounds(CustomerView.panel.getWidth() / 8, 420, 200, 15);
            CustomerView.stoneSize.setHorizontalAlignment(SwingConstants.CENTER);
            CustomerView.stoneWeight.setBounds(CustomerView.panel.getWidth() / 8, 435, 200, 15);
            CustomerView.stoneWeight.setHorizontalAlignment(SwingConstants.CENTER);
            CustomerView.price.setBounds(CustomerView.panel.getWidth() / 8, 350, 200, 15);
            CustomerView.price.setHorizontalAlignment(SwingConstants.CENTER);
            CustomerView.quantity.setBounds(CustomerView.panel.getWidth() / 8, 465, 200, 15);
            CustomerView.quantity.setHorizontalAlignment(SwingConstants.CENTER);

            jp.remove(connectionStatus);
            connectionStatus = new JLabel("");
            connectionStatus.setBounds(5, 445, 200, 15);
            connectionStatus.setForeground(Color.red);
            loginView.jp.add(connectionStatus);

            jp.remove(viewPanel);

            /*
            A switch to the login view
            */
            LoginView.currentPerson = 0;
            jf.setTitle("Login");
            jp.setVisible(false);
            jf.remove(jp);
            jf.add(loginView.jp);
            jf.setBounds(550, 200, 800, 500);
            loginView.jp.setVisible(true);
        });
        panel.add(logOut);

        JFileChooser imageChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG & PNG Images", "jpg", "jpeg", "png");
        imageChooser.setFileFilter(filter);
        imageChooser.setMultiSelectionEnabled(true);
        imageChooser.setCurrentDirectory(new java.io.File("Pictures"));
        imageChooser.setDialogTitle("Test");

        addImage = new JButton("Add Image");
        addImage.setBounds(130, 460, 115, 50);
        addImage.setBackground(thistle);
        addImage.setVisible(false);
        addImage.addActionListener((e) -> {
            if (imageChooser.showOpenDialog(addImage) == JFileChooser.APPROVE_OPTION) {
                File imageFile = imageChooser.getSelectedFile();
                UploadImage uploadImage = new UploadImage(imageFile);
                uploadImage.start();
            }
        }
        );
        panel.add(addImage);
    }

    static Object[][] getCustomerData(boolean isPhoneOrEmail) {
        java.util.List<Object[]> matchedData = new ArrayList<>();
        try {
            int c = 0;
            for (int i = 0; i < LoginView.person.size(); i++) {
                boolean match = false;

                if (!isPhoneOrEmail) {
                    String customerEmail = customersEmail.getText();
                    if (customerEmail.equals(LoginView.person.get(i).email)) {
                        match = true;
                    }
                } else if (isPhoneOrEmail) {
                    String customersPhoneNumber = "(" + customersPhoneNumber1.getText() + ")-" + customersPhoneNumber2.getText() + "-" + customersPhoneNumber3.getText();
                    if (customersPhoneNumber.equals(LoginView.person.get(i).phonePrimary)
                            || customersPhoneNumber.equals(LoginView.person.get(i).phoneSecondary)) {
                        match = true;
                    }
                }

                if (match) {
                    String loginName = "";
                    for (int l = 0; l < LoginView.logon.size(); l++) {
                        if (LoginView.person.get(i).personID == LoginView.logon.get(l).personID) {
                            loginName = LoginView.logon.get(l).logonName;
                            break;
                        }
                    }
                    String firstName = LoginView.person.get(i).nameFirst;
                    String lastName = LoginView.person.get(i).nameLast;
                    String email = LoginView.person.get(i).email;
                    String phonePrimary = LoginView.person.get(i).phonePrimary;
                    String phoneSecondary = LoginView.person.get(i).phoneSecondary.isEmpty() ? null : LoginView.person.get(i).phoneSecondary;

                    matchedData.add(new String[]{loginName, firstName, lastName, email, phonePrimary, phoneSecondary});
                }
            }
            return matchedData.toArray(new Object[0][]);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Object[][] getManagerDiscountData() {
        try {
            Object[][] isPersonData = new Object[LoginView.discount.size()][1];

            int d = 0;
            for (int i = 0; i < LoginView.discount.size(); i++) {
                String discountYearSubstring = LoginView.discount.get(i).expirationDate;
                String discountMonthSubstring = LoginView.discount.get(i).expirationDate;
                String discountDaySubstring = LoginView.discount.get(i).expirationDate;
                discountYearSubstring = discountYearSubstring.substring(0, 4);
                discountMonthSubstring = discountMonthSubstring.substring(5, 7);
                discountDaySubstring = discountDaySubstring.substring(8, 10);

                int managerDiscountYear = Integer.parseInt(discountYearSubstring);
                int managerDiscountMonth = Integer.parseInt(discountMonthSubstring);
                int managerDiscountDay = Integer.parseInt(discountDaySubstring);
                LocalDate expirationOfDiscount = LocalDate.of(managerDiscountYear, managerDiscountMonth, managerDiscountDay);
                long compareDateToDiscount = ChronoUnit.DAYS.between(currentDate, expirationOfDiscount);
                if (compareDateToDiscount >= 0) {
                    String discountCode = LoginView.discount.get(i).discountCode;
                    isPersonData[d] = new String[]{discountCode};
                    d++;
                }
            }
            return isPersonData;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    void setOrderStartDay() {
        orderStartMonth = Integer.parseInt(minimumMonth.getSelectedItem().toString());
        minimumDay.removeAllItems();
        orderStartDay = 1;

        /*
        The second discount start calender. This one is often re-used. It will apply values to the start-day combobox as the months change.
        */
        Calendar stayStartCalendar2 = new GregorianCalendar(orderStartYear, orderStartMonth - 1, 1);
        /*
        The proper amount of days, depending on the month, is added to the list. This even updates for leap years (febuary 29th).
        */
        for (int i = 0; i < stayStartCalendar2.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            minimumDay.addItem(i + 1);
        }
    }

    void setOrderEndDay() {
        orderEndingMonth = Integer.parseInt(maximumMonth.getSelectedItem().toString());
        maximumDay.removeAllItems();
        orderEndingDay = 1;

        /*
        The second stay end calender. This one is often re-used. It will apply values to the end-day combobox as the months change.
        */
        Calendar stayEndCalendar2 = new GregorianCalendar(orderEndingYear, orderEndingMonth - 1, 1);
        /*
        The proper amount of days, depending on the month, is added to the list. This even updates for leap years (febuary 29th).
        */
        for (int i = 0; i < stayEndCalendar2.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            maximumDay.addItem(i + 1);
        }
    }

    /**
    * Adapts the dates for the search of orders between two dates into usable form
    */
    public void adaptOrderDates() {
        if (orderStartMonth < 10) {
            orderStartingMonth = "0" + orderStartMonth;
        } else {
            orderStartingMonth = orderStartMonth + "";
        }
        if (orderStartDay < 10) {
            orderStartingDay = "0" + orderStartDay;
        } else {
            orderStartingDay = orderStartDay + "";
        }
        if (orderEndingMonth < 10) {
            orderEndMonth = "0" + orderEndingMonth;
        } else {
            orderEndMonth = orderEndingMonth + "";
        }
        if (orderEndingDay < 10) {
            orderEndDay = "0" + orderEndingDay;
        } else {
            orderEndDay = orderEndingDay + "";
        }
        orderStartTime = orderStartYear + "-" + orderStartingMonth + "-" + orderStartingDay;
        orderEndTime = orderEndingYear + "-" + orderEndMonth + "-" + orderEndDay;
    }

    /**
    * Resets order year, month, and date back to their original values for the search of orders between two dates
    */
    public void resetOrderDates() {
        Calendar today = Calendar.getInstance();
        int currentYear = today.get(Calendar.YEAR);
        int currentMonth = today.get(Calendar.MONTH) + 1;
        int currentDay = today.get(Calendar.DAY_OF_MONTH);

        minimumYear.setSelectedIndex(0);
        minimumMonth.setSelectedIndex(currentMonth - 1);
        minimumDay.setSelectedIndex(currentDay - 1);

        maximumYear.setSelectedIndex(0);
        maximumMonth.setSelectedIndex(currentMonth - 1);
        maximumDay.setSelectedIndex(currentDay - 1);
    }

    Runnable generalSalesDetails = () -> {
        int orderNumber = 0;
        boolean ordersFound = false;
        double totalOfAllOrders = 0;
        orderDateError.setText("");
        LocalDate endRangeOfOrder = LocalDate.of(orderEndingYear, orderEndingMonth, orderEndingDay);
        try {
            adaptOrderDates();
            LocalDate startRangeOfOrder = LocalDate.of(orderStartYear, orderStartMonth, orderStartDay);
            long diff = ChronoUnit.DAYS.between(startRangeOfOrder, endRangeOfOrder);
            if (diff > 0) {
                final double TAX = 0.0825;
                java.util.Date orderStarts = df.parse(orderStartTime);
                java.sql.Date orderStart = new Date(orderStarts.getTime());
                java.util.Date orderEnds = df.parse(orderEndTime);
                java.sql.Date orderEnd = new Date(orderEnds.getTime());
                StringBuilder orderReport = new StringBuilder();
                orderReport.append("<html>");
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
                orderReport.append("<head></head>");
                orderReport.append("<body><h1 style='text-align: center;'>Sales Report</h1>");
                orderReport.append("<table border='1'><tr></th><th>Order Date</th><th>Customer</th><th>Card Number</th><th>Card Expiration</th><th>Security Code</th><th>Discount Code</th><th>Order Total</th><th>Manager</th></tr>");
                for (int or = 0; or < LoginView.orders.size(); or++) {
                    Orders order = LoginView.orders.get(or);
                    Date orderDate = Date.valueOf(LoginView.orders.get(or).orderDate);
                    if (orderDate.compareTo(orderStart) >= 0 && orderDate.compareTo(orderEnd) <= 0) {
                        ordersFound = true;
                        double orderTotalCost = 0;
                        orderNumber++;
                        orderReport.append("<tr>");
                        orderReport.append("<td>").append(order.orderDate.toString()).append("</td>");
                        boolean customerFound = false;
                        for (int i = 0; i < LoginView.person.size(); i++) {
                            if (LoginView.person.get(i).personID == order.personID) {
                                orderReport.append("<td>").append(LoginView.person.get(i).nameFirst + " " + LoginView.person.get(i).nameLast.charAt(0)).append(".</td>");
                                customerFound = true;
                                break;
                            }
                        }
                        if (!customerFound) {
                            orderReport.append("<td>").append("Account Inactive - Customer Disabled").append("</td>");
                        }
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
                orderReport.append("</table>");
                orderReport.append("<h2 style='text-align: center;'>Cost of all orders: $" + f.format(totalOfAllOrders) + "<h2>");
                orderReport.append("</body></html>");
                if (ordersFound) {
                    try {
                        Path documentsDirectory = FileSystems.getDefault().getPath(System.getProperty("user.home"), "Documents");
                        if (!documentsDirectory.toFile().exists()) {
                            boolean created = documentsDirectory.toFile().mkdirs();
                            if (!created) {
                                System.out.println("Directory Not Established: " + documentsDirectory);
                                return;
                            }
                        }

                        String filePath = documentsDirectory.toString() + File.separator + "orders.html";
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
                    orderDateError.setText("No orders found");
                }
            } else if (diff == 0) {
                orderDateError.setText("Must widen range");
            } else if (diff < 0) {
                orderDateError.setText("Must set start before end");
            }
        } catch (ParseException e) {
            System.out.println(e);
        }
    };

    Runnable customerSalesDetails = () -> {
        int orderNumber = 0;
        boolean ordersFound = false;
        double totalOfAllOrders = 0;
        orderDateError.setText("");
        LocalDate endRangeOfOrder = LocalDate.of(orderEndingYear, orderEndingMonth, orderEndingDay);
        try {
            adaptOrderDates();
            LocalDate startRangeOfOrder = LocalDate.of(orderStartYear, orderStartMonth, orderStartDay);
            long diff = ChronoUnit.DAYS.between(startRangeOfOrder, endRangeOfOrder);
            if (diff > 0) {
                final double TAX = 0.0825;
                java.util.Date orderStarts = df.parse(orderStartTime);
                java.sql.Date orderStart = new Date(orderStarts.getTime());
                java.util.Date orderEnds = df.parse(orderEndTime);
                java.sql.Date orderEnd = new Date(orderEnds.getTime());
                StringBuilder orderReport = new StringBuilder();
                orderReport.append("<html>");
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
                orderReport.append("<head></head>");
                orderReport.append("<body><h1 style='text-align: center;'>Sales Report</h1>");
                orderReport.append("<table border='1'><tr></th><th>Order Date</th><th>Customer</th><th>Card Number</th><th>Card Expiration</th><th>Security Code</th><th>Discount Code</th><th>Order Total</th><th>Manager</th></tr>");
                for (int p = 0; p < LoginView.person.size(); p++) {
                    if (customerSelected == LoginView.person.get(p).personID) {
                        for (int or = 0; or < LoginView.orders.size(); or++) {
                            Orders order = LoginView.orders.get(or);
                            Date orderDate = Date.valueOf(LoginView.orders.get(or).orderDate);
                            if (orderDate.compareTo(orderStart) >= 0 && orderDate.compareTo(orderEnd) <= 0) {
                                ordersFound = true;
                                double orderTotalCost = 0;
                                orderNumber++;
                                orderReport.append("<tr>");
                                orderReport.append("<td>").append(order.orderDate).append("</td>");
                                boolean customerFound = false;
                                for (int i = 0; i < LoginView.person.size(); i++) {
                                    if (LoginView.person.get(i).personID == order.personID) {
                                        orderReport.append("<td>").append(LoginView.person.get(i).nameFirst + " " + LoginView.person.get(i).nameLast.charAt(0)).append(".</td>");
                                        customerFound = true;
                                        break;
                                    }
                                }
                                if (!customerFound) {
                                    orderReport.append("<td>").append("Account Inactive - Customer Disabled").append("</td>");
                                }
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
                        orderReport.append("</table>");
                        orderReport.append("<h2 style='text-align: center;'>Cost of all orders: $" + f.format(totalOfAllOrders) + "<h2>");
                        orderReport.append("</body></html>");
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
                    orderDateError.setText("No orders found");
                }
            } else if (diff == 0) {
                orderDateError.setText("Must widen range");
            } else if (diff < 0) {
                orderDateError.setText("Must set start before end");
            }
        } catch (ParseException e) {
            System.out.println(e);
        }
    };

    Runnable customerReport = () -> {
        StringBuilder userReport = new StringBuilder();
        userReport.append("<html>");
        userReport.append("<style>")
                .append("body {background-color: #DFFDFF;}")
                .append("h1 {text-align: center; font-size: 2.5em; font-weight: bold; color: #005f73; margin: 20px 0; padding: 10px; text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.2); letter-spacing: 1px;}")
                .append("h2 {text-align: center; font-size: 2.5em; font-weight: bold; color: #005f73;}")
                .append("table {width: 100%; border-collapse: collapse; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);}")
                .append("th, td {padding: 10px; text-align: center; border: 1px solid #ddd;}")
                .append("td {background-color: white !important;}")
                .append("th {background-color: #f2f2f2;}")
                .append("tr:hover {background-color: #f1f1f1;}")
                .append("</style>");
        userReport.append("<head></head>");
        userReport.append("<body><h1 style='text-align: center;'>All Customers</h1>");
        userReport.append("<table border='1'><tr></th><th>Logon Name</th><th>Password</th><th>Title</th><th>First Name</th><th>Middle Name</th><th>Last Name</th><th>Suffix</th><th>Address 1</th><th>Address 2</th><th>Address 3</th><th>City</th><th>Zipcode</th><th>State</th><th>Email</th><th>Phone #1</th><th>Phone #2</th></tr>");
        for (int i = 0; i < LoginView.logon.size(); i++) {
            Logon customer = LoginView.logon.get(i);
            Person person = LoginView.person.get(i);
            if (customer.positionTitle.equals("Customer")) {
                userReport.append("<tr>");
                userReport.append("<td>").append(customer.logonName).append("</td>");
                userReport.append("<td>").append(customer.password).append("</td>");
                userReport.append("<td>").append(person.title).append("</td>");
                userReport.append("<td>").append(person.nameFirst).append("</td>");
                userReport.append("<td>").append(person.nameMiddle).append("</td>");
                userReport.append("<td>").append(person.nameLast).append("</td>");
                userReport.append("<td>").append(person.suffix).append("</td>");
                userReport.append("<td>").append(person.address1).append("</td>");
                userReport.append("<td>").append(person.address2).append("</td>");
                userReport.append("<td>").append(person.address3).append("</td>");
                userReport.append("<td>").append(person.city).append("</td>");
                userReport.append("<td>").append(person.zipCode).append("</td>");
                userReport.append("<td>").append(person.state).append("</td>");
                userReport.append("<td>").append(person.email).append("</td>");
                userReport.append("<td>").append(person.phonePrimary).append("</td>");
                userReport.append("<td>").append(person.phoneSecondary).append("</td>");
                userReport.append("<tr>");
            }
        }
        userReport.append("</table></body></html>");
        try {
            Path documentsDirectory = FileSystems.getDefault().getPath(System.getProperty("user.home"), "Documents");
            if (!documentsDirectory.toFile().exists()) {
                boolean created = documentsDirectory.toFile().mkdirs();
                if (!created) {
                    System.out.println("Directory Not Established: " + documentsDirectory);
                    return;
                }
            }

            String filePath = documentsDirectory.toString() + File.separator + "customers.html";
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(userReport.toString());
            writer.close();

            File file = new File(filePath);
            URI uri = file.toURI();
            Desktop.getDesktop().browse(uri);
        } catch (IOException e) {
            System.out.println(e);
        }
    };

    Runnable managerReport = () -> {
        StringBuilder userReport = new StringBuilder();
        userReport.append("<html>");
        userReport.append("<style>")
                .append("body {background-color: #DFFDFF;}")
                .append("h1 {text-align: center; font-size: 2.5em; font-weight: bold; color: #005f73; margin: 20px 0; padding: 10px; text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.2); letter-spacing: 1px;}")
                .append("h2 {text-align: center; font-size: 2.5em; font-weight: bold; color: #005f73;}")
                .append("table {width: 100%; border-collapse: collapse; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);}")
                .append("th, td {padding: 10px; text-align: center; border: 1px solid #ddd;}")
                .append("td {background-color: white !important;}")
                .append("th {background-color: #f2f2f2;}")
                .append("tr:hover {background-color: #f1f1f1;}")
                .append("</style>");
        userReport.append("<head></head>");
        userReport.append("<body><h1 style='text-align: center;'>All Managers</h1>");
        userReport.append("<table border='1'><tr></th><th>Logon Name</th><th>Password</th><th>Title</th><th>First Name</th><th>Middle Name</th><th>Last Name</th><th>Suffix</th><th>Address 1</th><th>Address 2</th><th>Address 3</th><th>City</th><th>Zipcode</th><th>State</th><th>Email</th><th>Phone #1</th><th>Phone #2</th></tr>");
        for (int i = 0; i < LoginView.logon.size(); i++) {
            Logon manager = LoginView.logon.get(i);
            Person person = LoginView.person.get(i);
            if (manager.positionTitle.equals("Manager")) {
                userReport.append("<tr>");
                userReport.append("<td>").append(manager.logonName).append("</td>");
                userReport.append("<td>").append(manager.password).append("</td>");
                userReport.append("<td>").append(person.title).append("</td>");
                userReport.append("<td>").append(person.nameFirst).append("</td>");
                userReport.append("<td>").append(person.nameMiddle).append("</td>");
                userReport.append("<td>").append(person.nameLast).append("</td>");
                userReport.append("<td>").append(person.suffix).append("</td>");
                userReport.append("<td>").append(person.address1).append("</td>");
                userReport.append("<td>").append(person.address2).append("</td>");
                userReport.append("<td>").append(person.address3).append("</td>");
                userReport.append("<td>").append(person.city).append("</td>");
                userReport.append("<td>").append(person.zipCode).append("</td>");
                userReport.append("<td>").append(person.state).append("</td>");
                userReport.append("<td>").append(person.email).append("</td>");
                userReport.append("<td>").append(person.phonePrimary).append("</td>");
                userReport.append("<td>").append(person.phoneSecondary).append("</td>");
                userReport.append("<tr>");
            }
        }
        userReport.append("</table></body></html>");
        try {
            Path documentsDirectory = FileSystems.getDefault().getPath(System.getProperty("user.home"), "Documents");
            if (!documentsDirectory.toFile().exists()) {
                boolean created = documentsDirectory.toFile().mkdirs();
                if (!created) {
                    System.out.println("Directory Not Established: " + documentsDirectory);
                    return;
                }
            }

            String filePath = documentsDirectory.toString() + File.separator + "managers.html";
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(userReport.toString());
            writer.close();

            File file = new File(filePath);
            URI uri = file.toURI();
            Desktop.getDesktop().browse(uri);
        } catch (IOException e) {
            System.out.println(e);
        }
    };

    /**
    * Retrieves the security questions stored in the database
    */
    public static void includeQuestions() {
        boolean question1Update = true;
        boolean question2Update = true;
        boolean question3Update = true;

        for (int i = 0; i < LoginView.securityQuestions.size(); i++) {
            if (question1Update) {
                /*
                Sets up the security question
                */
                securityQuestion1.addItem(LoginView.securityQuestions.get(i).questionPrompt);
                question1Update = false;
            } else if (question2Update) {
                /*
                Sets up the security question
                */
                securityQuestion2.addItem(LoginView.securityQuestions.get(i).questionPrompt);
                question2Update = false;
            } else if (question3Update) {
                /*
                Sets up the security question
                */
                securityQuestion3.addItem(LoginView.securityQuestions.get(i).questionPrompt);
                question1Update = true;
                question2Update = true;
            }
        }
    }

    /**
    * Clears all fields in edit users and removes the selected user
    */
    public void accountDataReset() {
        personsLoginName.setText("");
        personsLoginNameError.setText("");
        personsPassword.setText("");
        personsPasswordError.setText("");
        personsPrefix.setSelectedItem("");
        personsFirstName.setText("");
        personsFirstNameError.setText("");
        personsLastNameError.setText("");
        personsMiddleName.setText("");
        personsLastName.setText("");
        personsSuffix.setSelectedItem("");
        personsAddress1.setText("");
        personsAddress2.setText("");
        personsAddress3.setText("");
        addressError.setText("");
        personsCity.setText("");
        personsCityError.setText("");
        personsZip.setText("");
        personsZipError.setText("");
        personsState.setSelectedItem("");
        personsStateError.setText("");
        personsEmail.setText("");
        personsEmailError.setText("");
        personsPhonePrimary1.setText("");
        personsPhonePrimary2.setText("");
        personsPhonePrimary3.setText("");
        personsPhoneSecondary1.setText("");
        personsPhoneSecondary2.setText("");
        personsPhoneSecondary3.setText("");
        personsPhoneError1.setText("");
        personsPhoneError2.setText("");
        questionID1 = 0;
        questionID2 = 0;
        questionID3 = 0;
        securityQuestion1.setSelectedItem(0);
        securityQuestion2.setSelectedItem(0);
        securityQuestion3.setSelectedItem(0);
        securityAnswer1.setText("");
        securityAnswer2.setText("");
        securityAnswer3.setText("");
        answerError.setText("");
        userIsCustomer.setSelected(true);
        userIsManager.setSelected(false);
    }

    /**
    * Creates a new user using entered values
    */
    public void newAccount() {
        /*
        A variable testing if a new account can be created. This value
        only needs to get switched once to prevent account creation.
        */
        boolean valid = true;

        personsLoginNameError.setText("");
        personsPasswordError.setBounds(500, 27, 400, 15);
        personsPasswordError.setText("");
        personsSpecialError.setText("");
        personsFirstNameError.setText("");
        personsLastNameError.setText("");
        addressError.setText("");
        personsCityError.setText("");
        personsZipError.setText("");
        personsStateError.setText("");
        personsEmailError.setText("");
        personsPhoneError1.setText("");
        personsPhoneError2.setText("");
        answerError.setText("");

        String logonName = personsLoginName.getText();
        String firstName = personsFirstName.getText();
        String middleName = personsMiddleName.getText();
        String lastName = personsLastName.getText();
        String password = new String(personsPassword.getPassword());
        String answer1 = securityAnswer1.getText();
        String answer2 = securityAnswer2.getText();
        String answer3 = securityAnswer3.getText();
        String address1 = personsAddress1.getText();
        String city = personsCity.getText();
        String zipCode = personsZip.getText();
        String state = personsState.getSelectedItem().toString();
        String email = personsEmail.getText();
        String phonePrimary = "";
        String primary1 = personsPhonePrimary1.getText();
        String primary2 = personsPhonePrimary2.getText();
        String primary3 = personsPhonePrimary3.getText();
        String phoneSecondary = "";
        String secondary1 = personsPhoneSecondary1.getText();
        String secondary2 = personsPhoneSecondary2.getText();
        String secondary3 = personsPhoneSecondary3.getText();
        String positionTitle = "Customer";

        /*
        If statement ensuring the user entered a login name
        */
        if (!loginValid) {
            valid = false;
        } else {
            String logName = personsLoginName.getText();
            personsLoginNameError.setText("");
            /*
            If statement ensuring the user entered a login name
            */
            if (!logName.isEmpty()) {
                /*
                If statement ensuring the login name does not begin with a number
                */
                if (!logName.matches("^[0-9].*")) {
                    /*
                    If statement ensuring the login name does not contain any special characters
                    */
                    boolean specialChar = false;
                    for (char c : logName.toCharArray()) {
                        if (!Character.isLetterOrDigit(c) && !Character.isWhitespace(c)) {
                            specialChar = true;
                        }
                    }
                    if (!specialChar) {
                        boolean whitespace = false;
                        /*
                        Line of code ensuring the login name does not contain a space at the start or end
                        */
                        logName = logName.trim();
                        personsLoginName.setText(logName);

                        /*
                        Ensures no whitespaces are present in the password
                        */
                        for (char c : logName.toCharArray()) {
                            if (Character.isWhitespace(c)) {
                                whitespace = true;
                            }
                        }

                        if (!whitespace) {
                            /*
                            If statement ensuring the login name is not less than 8 characters or more than 20
                            */
                            if (logName.length() >= 8 && logName.length() <= 20) {
                                /*
                                This try statement contains a query that will test if the login name already exists.
                                */
                                for (int i = 0; i < LoginView.logon.size(); i++) {
                                    String logN = LoginView.logon.get(i).logonName;
                                    if (logName.toUpperCase().equals(logN.toUpperCase())) {
                                        personsLoginNameError.setText("Already in use");
                                        valid = false;
                                    }
                                }
                            } else if (logName.length() < 8) {
                                valid = false;
                                personsLoginNameError.setText("Below 8 letters");
                            } else if (logName.length() > 20) {
                                personsLoginNameError.setText("Above 20 letters");
                            }
                        } else {
                            valid = false;
                            personsLoginNameError.setText("Whitespace present");
                        }
                    } else {
                        valid = false;
                        personsLoginNameError.setText("No special chars");
                    }
                } else {
                    valid = false;
                    personsLoginNameError.setText("Starts with number");
                }
            } else {
                valid = false;
                personsLoginNameError.setText("Username required");
            }
        }

        /*
        If statement ensuring the user has entered a valid password
        */
        if (!passwordValid) {
            valid = false;
        } else {
            String loginPassword = new String(personsPassword.getPassword());
            personsPasswordError.setText("");
            personsPasswordError.setBounds(485, 27, 400, 15);
            personsSpecialError.setText("");

            /*
            If statement ensuring the user entered a password
            */
            if (!loginPassword.isEmpty()) {
                boolean whitespace = false;
                /*
                Removes any whitespaces from the beginning or end of the password
                */
                loginPassword = loginPassword.trim();
                personsPassword.setText(loginPassword);

                /*
                Ensures no whitespaces are present in the password
                */
                for (char c : loginPassword.toCharArray()) {
                    if (Character.isWhitespace(c)) {
                        whitespace = true;
                    }
                }

                if (!whitespace) {
                    /*
                    If statement ensuring the password is not less than 8 characters or more than 20
                    */
                    if (loginPassword.length() < 8) {
                        valid = false;
                        personsPasswordError.setText("Password must be at least 7 characters long");
                    } else if (loginPassword.length() > 20) {
                        valid = false;
                        personsPasswordError.setText("Password cannot be more than 20 characters long");
                    } else {
                        int requirementsMet = 0;
                        char c;
                        boolean uppercasePresent = false;
                        boolean lowercasePresent = false;
                        boolean numberPresent = false;
                        boolean specialPresent = false;
                        for (int i = 0; i < loginPassword.length(); i++) {
                            c = loginPassword.charAt(i);
                            if (Character.isUpperCase(c)) {
                                uppercasePresent = true;
                            } else if (Character.isLowerCase(c)) {
                                lowercasePresent = true;
                            } else if (Character.isDigit(c)) {
                                numberPresent = true;
                            } else if (!Character.isAlphabetic(c) && !Character.isDigit(c)) {
                                specialPresent = true;
                            }
                        }
                        /*
                        A series of if statements to test if the user meets at least 3 out of 4 requirements
                        */
                        if (uppercasePresent) {
                            requirementsMet++;
                        }
                        if (lowercasePresent) {
                            requirementsMet++;
                        }
                        if (numberPresent) {
                            requirementsMet++;
                        }
                        if (specialPresent) {
                            requirementsMet++;
                        }
                        if (requirementsMet < 3) {
                            valid = false;
                            personsPasswordError.setBounds(485, 21, 400, 15);
                            personsPasswordError.setText("Must hold a upper and/or lowercase letter");
                            personsSpecialError.setText("Must hold a number and/or special letter");
                        }
                    }
                } else {
                    valid = false;
                    personsPasswordError.setText("No whitespace allowed");
                }
            } else {
                valid = false;
                personsPasswordError.setBounds(485, 27, 400, 15);
                personsPasswordError.setText("Must enter a password");
                personsSpecialError.setText("");
            }
        }

        /*
        If statement ensuring the user has entered their first name
        */
        if (firstName.isEmpty()) {
            valid = false;
            personsFirstNameError.setText("First name required");
        }

        /*
        If statement ensuring the user has entered their last name
        */
        if (lastName.isEmpty()) {
            valid = false;
            personsLastNameError.setText("Last name required");
        }

        /*
        If statement ensuring the user has entered something for the address
        */
        if (address1.isEmpty()) {
            valid = false;
            addressError.setText("Address required");
        }

        /*
        If statement ensuring the user has entered something for the city
        */
        if (city.isEmpty()) {
            valid = false;
            personsCityError.setText("City required");
        }

        /*
        If statement ensuring the user has entered something valid for the zip
        */
        if (zipCode.isEmpty()) {
            valid = false;
            personsZipError.setText("Zip required");
        } else if (zipCode.length() > 10) {
            valid = false;
            personsZipError.setText("Zip too long");
        } else {
            int nonDigitCharacters = 0;
            String isHyphen = "";
            int hyphenChar = 0;
            for (int i = 1; i < zipCode.length(); i++) {
                if (!Character.isDigit(zipCode.charAt(i))) {
                    nonDigitCharacters++;
                    isHyphen = Character.toString(zipCode.charAt(i));
                    hyphenChar = i;
                }
            }
            if (nonDigitCharacters < 2) {
                if (isHyphen.equals("")) {
                    /*
                    Keeps zip code length at 5 if a hyphen is not present
                    */
                    if (zipCode.length() < 5) {
                        valid = false;
                        personsZipError.setText("Zip too short");
                    } else if (zipCode.length() > 5) {
                        valid = false;
                        personsZipError.setText("Zip too long");
                    }
                } else if (isHyphen.equals("-")) {
                    /*
                    Makes sure the zip code length is long enough to be standard
                    */
                    if (hyphenChar == 5) {
                        if (zipCode.length() < 10) {
                            valid = false;
                            personsZipError.setText("Zip too short");
                        } else if (zipCode.length() > 10) {
                            valid = false;
                            personsZipError.setText("Zip too long");
                        }
                    } else {
                        valid = false;
                        personsZipError.setText("Zip invalid");
                    }
                } else {
                    valid = false;
                    personsZipError.setText("Zip invalid");
                }
            } else {
                valid = false;
                personsZipError.setText("Zip invalid");
            }
        }

        /*
        If statement ensuring the user has entered a state
        */
        if (state.equals("")) {
            valid = false;
            personsStateError.setText("Invalid state");
        }

        /*
        If statement ensuring the user has entered a valid email
        */
        if (!email.equals("")) {
            if (email.length() <= 40) {
                if (email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {

                } else {
                    valid = false;
                    personsEmailError.setText("Email invalid");
                }
            } else {
                valid = false;
                personsEmailError.setText("Above 40 characters");
            }
        } else {
            valid = false;
            personsEmailError.setText("Email required");
        }

        /*
        If statements for the primary phone number (optional) will give warnings if an entry is made but not prevent new account creation
        */
        if (!primary1.isEmpty() || !primary2.isEmpty() || !primary3.isEmpty()) {
            boolean phoneValid = true;
            if (primary1.length() != 3) {
                phoneValid = false;
            } else {
                for (int i = 1; i < primary1.length(); i++) {
                    if (!Character.isDigit(primary1.charAt(i))) {
                        phoneValid = false;
                    }
                }
                if (phoneValid) {
                    primary1 = "(" + primary1 + ")";
                }
            }
            if (primary2.length() != 3) {
                phoneValid = false;
            } else {
                for (int i = 1; i < primary2.length(); i++) {
                    if (!Character.isDigit(primary2.charAt(i))) {
                        phoneValid = false;
                    }
                }
                if (phoneValid) {
                    primary2 = "-" + primary2 + "-";
                }
            }
            if (primary3.length() != 4) {
                phoneValid = false;
            } else {
                for (int i = 1; i < primary3.length(); i++) {
                    if (!Character.isDigit(primary3.charAt(i))) {
                        phoneValid = false;
                    }
                }
            }

            if (phoneValid) {
                phonePrimary = primary1 + primary2 + primary3;
            } else {
                valid = false;
                personsPhoneError1.setText("Enter phone number");
            }
        } else {
            valid = false;
            personsPhoneError1.setText("Enter phone number");
        }

        /*
        If statements for the secondary phone number (optional) will give warnings if an entry is made but not prevent new account creation
        */
        if (!secondary1.isEmpty() || !secondary2.isEmpty() || !secondary3.isEmpty()) {
            boolean phoneValid = true;
            if (secondary1.length() != 3) {
                phoneValid = false;
            } else {
                for (int i = 1; i < secondary1.length(); i++) {
                    if (!Character.isDigit(secondary1.charAt(i))) {
                        phoneValid = false;
                    }
                }
                if (phoneValid) {
                    secondary1 = "(" + secondary1 + ")";
                }
            }
            if (secondary2.length() != 3) {
                phoneValid = false;
            } else {
                for (int i = 1; i < secondary2.length(); i++) {
                    if (!Character.isDigit(secondary2.charAt(i))) {
                        phoneValid = false;
                    }
                }
                if (phoneValid) {
                    secondary2 = "-" + secondary2 + "-";
                }
            }
            if (secondary3.length() != 4) {
                phoneValid = false;
            } else {
                for (int i = 1; i < secondary3.length(); i++) {
                    if (!Character.isDigit(secondary3.charAt(i))) {
                        phoneValid = false;
                    }
                }
            }

            if (phoneValid) {
                phoneSecondary = secondary1 + secondary2 + secondary3;
            } else {
                personsPhoneError2.setText("Enter phone number");
            }
        }

        /*
        If statement ensuring the user has entered something for all three security questions
        */
        if (answer1.isEmpty() || answer2.isEmpty() || answer3.isEmpty()) {
            valid = false;
            answerError.setText("Answer ALL security questions");
        } else {
            try {
                String query = "SELECT QuestionID FROM SecurityQuestions WHERE QuestionPrompt = ?;";
                ps = con.prepareStatement(query);
                ps.setString(1, securityQuestion1.getSelectedItem().toString());
                ResultSet rs = ps.executeQuery();
                rs.next();
                questionID1 = rs.getInt("QuestionID");

                query = "SELECT QuestionID FROM SecurityQuestions WHERE QuestionPrompt = ?;";
                ps = con.prepareStatement(query);
                ps.setString(1, securityQuestion2.getSelectedItem().toString());
                rs = ps.executeQuery();
                rs.next();
                questionID2 = rs.getInt("QuestionID");

                query = "SELECT QuestionID FROM SecurityQuestions WHERE QuestionPrompt = ?;";
                ps = con.prepareStatement(query);
                ps.setString(1, securityQuestion3.getSelectedItem().toString());
                rs = ps.executeQuery();
                rs.next();
                questionID3 = rs.getInt("QuestionID");
            } catch (SQLException ex) {
                valid = false;
                System.out.println(ex);
            }
        }

        if (userIsCustomer.isSelected()) {
            positionTitle = "Customer";
        } else if (userIsManager.isSelected()) {
            positionTitle = "Manager";
        }

        if (valid) {
            int personID;
            String title = personsPrefix.getSelectedItem().toString();
            String suffix = personsSuffix.getSelectedItem().toString();
            String address2 = personsAddress2.getText();
            String address3 = personsAddress3.getText();

            try {
                String newPerson = "INSERT INTO Person (Title, NameFirst, NameMiddle, NameLast, Suffix, Address1, Address2, Address3, City, Zipcode, State, Email, PhonePrimary, PhoneSecondary, Image, PersonDeleted) "
                        + "VALUES ('" + title + "', '" + firstName + "', '" + middleName + "', '" + lastName + "', '" + suffix + "', '" + address1 + "', '" + address2 + "', '" + address3 + "', '" + city + "', '" + zipCode + "', '" + state + "', '" + email + "', '" + phonePrimary + "', '" + phoneSecondary + "', '" + null + "', " + 0 + ");";
                ps = con.prepareStatement(newPerson);
                ps.execute();

                String query = "SELECT MAX(PersonID) FROM Person;";
                ps = con.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                rs.next();
                personID = Integer.parseInt(rs.getString(1));

                String newLogon = "INSERT INTO Logon "
                        + "(PersonID, LogonName, Password, FirstChallengeQuestion, FirstChallengeAnswer, SecondChallengeQuestion, SecondChallengeAnswer, ThirdChallengeQuestion, ThirdChallengeAnswer, PositionTitle, AccountDisabled, AccountDeleted)"
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0, 0)";
                ps = con.prepareStatement(newLogon);
                ps.setInt(1, personID);
                ps.setString(2, logonName);
                ps.setString(3, password);
                ps.setInt(4, questionID1);
                ps.setString(5, answer1.replaceAll("'", "''"));
                ps.setInt(6, questionID2);
                ps.setString(7, answer2.replaceAll("'", "''"));
                ps.setInt(8, questionID3);
                ps.setString(9, answer3.replaceAll("'", "''"));
                ps.setString(10, positionTitle);
                ps.execute();
            } catch (SQLException ex) {
                System.out.println(ex);
            }

            updatedFromManagerPerson = true;
            Thread personsData = new Thread(LoginView.personInfo);
            personsData.start();

            updatedFromManagerLogon = true;
            Thread logonData = new Thread(LoginView.logonInfo);
            logonData.start();
        }
    }

    /**
    * Edits the selected user using entered values
    */
    public void editAccount() {
        /*
        A variable testing if a new account can be created. This value
        only needs to get switched once to prevent account editing.
        */
        boolean valid = true;

        personsLoginNameError.setText("");
        personsPasswordError.setBounds(500, 27, 400, 15);
        personsPasswordError.setText("");
        personsSpecialError.setText("");
        personsFirstNameError.setText("");
        personsLastNameError.setText("");
        addressError.setText("");
        personsCityError.setText("");
        personsZipError.setText("");
        personsStateError.setText("");
        personsEmailError.setText("");
        personsPhoneError1.setText("");
        personsPhoneError2.setText("");
        answerError.setText("");

        String logonName = personsLoginName.getText();
        String firstName = personsFirstName.getText();
        String middleName = personsMiddleName.getText();
        String lastName = personsLastName.getText();
        String password = new String(personsPassword.getPassword());
        String answer1 = securityAnswer1.getText();
        String answer2 = securityAnswer2.getText();
        String answer3 = securityAnswer3.getText();
        String address1 = personsAddress1.getText();
        String city = personsCity.getText();
        String zipCode = personsZip.getText();
        String state = personsState.getSelectedItem().toString();
        String email = personsEmail.getText();
        String phonePrimary = "";
        String primary1 = personsPhonePrimary1.getText();
        String primary2 = personsPhonePrimary2.getText();
        String primary3 = personsPhonePrimary3.getText();
        String phoneSecondary = "";
        String secondary1 = personsPhoneSecondary1.getText();
        String secondary2 = personsPhoneSecondary2.getText();
        String secondary3 = personsPhoneSecondary3.getText();
        String positionTitle = "Customer";

        /*
        If statement ensuring the user entered a login name
        */
        if (!loginValid) {
            valid = false;
        } else {
            String logName = personsLoginName.getText();
            personsLoginNameError.setText("");
            /*
            If statement ensuring the user entered a login name
            */
            if (!logName.isEmpty()) {
                /*
                If statement ensuring the login name does not begin with a number
                */
                if (!logName.matches("^[0-9].*")) {
                    /*
                    If statement ensuring the login name does not contain any special characters
                    */
                    boolean specialChar = false;
                    for (char c : logName.toCharArray()) {
                        if (!Character.isLetterOrDigit(c) && !Character.isWhitespace(c)) {
                            specialChar = true;
                        }
                    }
                    if (!specialChar) {
                        boolean whitespace = false;
                        /*
                        Line of code ensuring the login name does not contain a space at the start or end
                        */
                        logName = logName.trim();
                        personsLoginName.setText(logName);

                        /*
                        Ensures no whitespaces are present in the password
                        */
                        for (char c : logName.toCharArray()) {
                            if (Character.isWhitespace(c)) {
                                whitespace = true;
                            }
                        }

                        if (!whitespace) {
                            /*
                            If statement ensuring the login name is not less than 8 characters or more than 20
                            */
                            if (logName.length() >= 8 && logName.length() <= 20) {
                            } else if (logName.length() < 8) {
                                valid = false;
                                personsLoginNameError.setText("Below 8 letters");
                            } else if (logName.length() > 20) {
                                personsLoginNameError.setText("Above 20 letters");
                            }
                        } else {
                            valid = false;
                            personsLoginNameError.setText("Whitespace present");
                        }
                    } else {
                        valid = false;
                        personsLoginNameError.setText("No special chars");
                    }
                } else {
                    valid = false;
                    personsLoginNameError.setText("Starts with number");
                }
            } else {
                valid = false;
                personsLoginNameError.setText("Username required");
            }
        }

        /*
        If statement ensuring the user has entered a valid password
        */
        if (!passwordValid) {
            valid = false;
        } else {
            String loginPassword = new String(personsPassword.getPassword());
            personsPasswordError.setText("");
            personsPasswordError.setBounds(485, 27, 400, 15);
            personsSpecialError.setText("");

            /*
            If statement ensuring the user entered a password
            */
            if (!loginPassword.isEmpty()) {
                boolean whitespace = false;
                /*
                Removes any whitespaces from the beginning or end of the password
                */
                loginPassword = loginPassword.trim();
                personsPassword.setText(loginPassword);

                /*
                Ensures no whitespaces are present in the password
                */
                for (char c : loginPassword.toCharArray()) {
                    if (Character.isWhitespace(c)) {
                        whitespace = true;
                    }
                }

                if (!whitespace) {
                    /*
                    If statement ensuring the password is not less than 8 characters or more than 20
                    */
                    if (loginPassword.length() < 8) {
                        valid = false;
                        personsPasswordError.setText("Password must be at least 7 characters long");
                    } else if (loginPassword.length() > 20) {
                        valid = false;
                        personsPasswordError.setText("Password cannot be more than 20 characters long");
                    } else {
                        int requirementsMet = 0;
                        char c;
                        boolean uppercasePresent = false;
                        boolean lowercasePresent = false;
                        boolean numberPresent = false;
                        boolean specialPresent = false;
                        for (int i = 0; i < loginPassword.length(); i++) {
                            c = loginPassword.charAt(i);
                            if (Character.isUpperCase(c)) {
                                uppercasePresent = true;
                            } else if (Character.isLowerCase(c)) {
                                lowercasePresent = true;
                            } else if (Character.isDigit(c)) {
                                numberPresent = true;
                            } else if (!Character.isAlphabetic(c) && !Character.isDigit(c)) {
                                specialPresent = true;
                            }
                        }
                        /*
                        A series of if statements to test if the user meets at least 3 out of 4 requirements
                        */
                        if (uppercasePresent) {
                            requirementsMet++;
                        }
                        if (lowercasePresent) {
                            requirementsMet++;
                        }
                        if (numberPresent) {
                            requirementsMet++;
                        }
                        if (specialPresent) {
                            requirementsMet++;
                        }
                        if (requirementsMet < 3) {
                            valid = false;
                            personsPasswordError.setBounds(485, 21, 400, 15);
                            personsPasswordError.setText("Must hold a upper and/or lowercase letter");
                            personsSpecialError.setText("Must hold a number and/or special letter");
                        }
                    }
                } else {
                    valid = false;
                    personsPasswordError.setText("No whitespace allowed");
                }
            } else {
                valid = false;
                personsPasswordError.setBounds(485, 27, 400, 15);
                personsPasswordError.setText("Must enter a password");
                personsSpecialError.setText("");
            }
        }

        /*
        If statement ensuring the user has entered their first name
        */
        if (firstName.isEmpty()) {
            valid = false;
            personsFirstNameError.setText("First name required");
        }

        /*
        If statement ensuring the user has entered their last name
        */
        if (lastName.isEmpty()) {
            valid = false;
            personsLastNameError.setText("Last name required");
        }

        /*
        If statement ensuring the user has entered something for the address
        */
        if (address1.isEmpty()) {
            valid = false;
            addressError.setText("Address required");
        }

        /*
        If statement ensuring the user has entered something for the city
        */
        if (city.isEmpty()) {
            valid = false;
            personsCityError.setText("City required");
        }

        /*
        If statement ensuring the user has entered something valid for the zip
        */
        if (zipCode.isEmpty()) {
            valid = false;
            personsZipError.setText("Zip required");
        } else if (zipCode.length() > 10) {
            valid = false;
            personsZipError.setText("Zip too long");
        } else {
            int nonDigitCharacters = 0;
            String isHyphen = "";
            int hyphenChar = 0;
            for (int i = 1; i < zipCode.length(); i++) {
                if (!Character.isDigit(zipCode.charAt(i))) {
                    nonDigitCharacters++;
                    isHyphen = Character.toString(zipCode.charAt(i));
                    hyphenChar = i;
                }
            }
            if (nonDigitCharacters < 2) {
                if (isHyphen.equals("")) {
                    /*
                    Keeps zip code length at 5 if a hyphen is not present
                    */
                    if (zipCode.length() < 5) {
                        valid = false;
                        personsZipError.setText("Zip too short");
                    } else if (zipCode.length() > 5) {
                        valid = false;
                        personsZipError.setText("Zip too long");
                    }
                } else if (isHyphen.equals("-")) {
                    /*
                    Makes sure the zip code length is long enough to be standard
                    */
                    if (hyphenChar == 5) {
                        if (zipCode.length() < 10) {
                            valid = false;
                            personsZipError.setText("Zip too short");
                        } else if (zipCode.length() > 10) {
                            valid = false;
                            personsZipError.setText("Zip too long");
                        }
                    } else {
                        valid = false;
                        personsZipError.setText("Zip invalid");
                    }
                } else {
                    valid = false;
                    personsZipError.setText("Zip invalid");
                }
            } else {
                valid = false;
                personsZipError.setText("Zip invalid");
            }
        }

        /*
        If statement ensuring the user has entered a state
        */
        if (state.equals("")) {
            valid = false;
            personsStateError.setText("Invalid state");
        }

        /*
        If statement ensuring the user has entered a valid email
        */
        if (!email.equals("")) {
            if (email.length() <= 40) {
                if (email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {

                } else {
                    valid = false;
                    personsEmailError.setText("Email invalid");
                }
            } else {
                valid = false;
                personsEmailError.setText("Above 40 characters");
            }
        } else {
            valid = false;
            personsEmailError.setText("Email required");
        }

        /*
        If statements for the primary phone number (optional) will give warnings if an entry is made but not prevent new account creation
        */
        if (!primary1.isEmpty() || !primary2.isEmpty() || !primary3.isEmpty()) {
            boolean phoneValid = true;
            if (primary1.length() != 3) {
                phoneValid = false;
            } else {
                for (int i = 1; i < primary1.length(); i++) {
                    if (!Character.isDigit(primary1.charAt(i))) {
                        phoneValid = false;
                    }
                }
                if (phoneValid) {
                    primary1 = "(" + primary1 + ")";
                }
            }
            if (primary2.length() != 3) {
                phoneValid = false;
            } else {
                for (int i = 1; i < primary2.length(); i++) {
                    if (!Character.isDigit(primary2.charAt(i))) {
                        phoneValid = false;
                    }
                }
                if (phoneValid) {
                    primary2 = "-" + primary2 + "-";
                }
            }
            if (primary3.length() != 4) {
                phoneValid = false;
            } else {
                for (int i = 1; i < primary3.length(); i++) {
                    if (!Character.isDigit(primary3.charAt(i))) {
                        phoneValid = false;
                    }
                }
            }

            if (phoneValid) {
                phonePrimary = primary1 + primary2 + primary3;
            } else {
                valid = false;
                personsPhoneError1.setText("Enter phone number");
            }
        } else {
            valid = false;
            personsPhoneError1.setText("Enter phone number");
        }

        /*
        If statements for the secondary phone number (optional) will give warnings if an entry is made but not prevent new account creation
        */
        if (!secondary1.isEmpty() || !secondary2.isEmpty() || !secondary3.isEmpty()) {
            boolean phoneValid = true;
            if (secondary1.length() != 3) {
                phoneValid = false;
            } else {
                for (int i = 1; i < secondary1.length(); i++) {
                    if (!Character.isDigit(secondary1.charAt(i))) {
                        phoneValid = false;
                    }
                }
                if (phoneValid) {
                    secondary1 = "(" + secondary1 + ")";
                }
            }
            if (secondary2.length() != 3) {
                phoneValid = false;
            } else {
                for (int i = 1; i < secondary2.length(); i++) {
                    if (!Character.isDigit(secondary2.charAt(i))) {
                        phoneValid = false;
                    }
                }
                if (phoneValid) {
                    secondary2 = "-" + secondary2 + "-";
                }
            }
            if (secondary3.length() != 4) {
                phoneValid = false;
            } else {
                for (int i = 1; i < secondary3.length(); i++) {
                    if (!Character.isDigit(secondary3.charAt(i))) {
                        phoneValid = false;
                    }
                }
            }

            if (phoneValid) {
                phoneSecondary = secondary1 + secondary2 + secondary3;
            } else {
                personsPhoneError2.setText("Enter phone number");
            }
        }

        /*
        If statement ensuring the user has entered something for all three security questions
        */
        if (answer1.isEmpty() || answer2.isEmpty() || answer3.isEmpty()) {
            valid = false;
            answerError.setText("Answer ALL security questions");
        } else {
            try {
                String query = "SELECT QuestionID FROM SecurityQuestions WHERE QuestionPrompt = ?;";
                ps = con.prepareStatement(query);
                ps.setString(1, securityQuestion1.getSelectedItem().toString());
                ResultSet rs = ps.executeQuery();
                rs.next();
                questionID1 = rs.getInt("QuestionID");

                query = "SELECT QuestionID FROM SecurityQuestions WHERE QuestionPrompt = ?;";
                ps = con.prepareStatement(query);
                ps.setString(1, securityQuestion2.getSelectedItem().toString());
                rs = ps.executeQuery();
                rs.next();
                questionID2 = rs.getInt("QuestionID");

                query = "SELECT QuestionID FROM SecurityQuestions WHERE QuestionPrompt = ?;";
                ps = con.prepareStatement(query);
                ps.setString(1, securityQuestion3.getSelectedItem().toString());
                rs = ps.executeQuery();
                rs.next();
                questionID3 = rs.getInt("QuestionID");
            } catch (SQLException ex) {
                valid = false;
                System.out.println(ex);
            }
        }

        if (userIsCustomer.isSelected()) {
            positionTitle = "Customer";
        } else if (userIsManager.isSelected()) {
            positionTitle = "Manager";
        }

        if (valid) {
            String title = personsPrefix.getSelectedItem().toString();
            String suffix = personsSuffix.getSelectedItem().toString();
            String address2 = personsAddress2.getText();
            String address3 = personsAddress3.getText();

            try {
                String editPerson = "UPDATE Person SET Title = ?, NameFirst = ?, NameMiddle = ?, NameLast = ?, Suffix = ?, Address1 = ?, Address2 = ?, Address3 = ?, City = ?, Zipcode = ?, State = ?, Email = ?, PhonePrimary = ?, PhoneSecondary = ?, Image = ?, PersonDeleted = ? WHERE PersonID = ?;";
                ps = con.prepareStatement(editPerson);
                ps.setString(1, title);
                ps.setString(2, firstName);
                ps.setString(3, middleName);
                ps.setString(4, lastName);
                ps.setString(5, suffix);
                ps.setString(6, address1);
                ps.setString(7, address2);
                ps.setString(8, address3);
                ps.setString(9, city);
                ps.setString(10, zipCode);
                ps.setString(11, state);
                ps.setString(12, email);
                ps.setString(13, phonePrimary);
                ps.setString(14, phoneSecondary);
                ps.setNull(15, java.sql.Types.VARCHAR);
                ps.setInt(16, 0);
                ps.setInt(17, LoginView.person.get(userSelected).personID);
                ps.executeUpdate();

                String editLogon = "UPDATE Logon SET LogonName = ?, Password = ?, FirstChallengeQuestion = ?, FirstChallengeAnswer = ?, SecondChallengeQuestion = ?, SecondChallengeAnswer = ?, ThirdChallengeQuestion = ?, ThirdChallengeAnswer = ?, PositionTitle = ?, AccountDisabled = ?, AccountDeleted = ? WHERE PersonID = ?;";
                ps = con.prepareStatement(editLogon);
                ps.setString(1, logonName);
                ps.setString(2, password);
                ps.setInt(3, questionID1);
                ps.setString(4, answer1.replaceAll("'", "''"));
                ps.setInt(5, questionID2);
                ps.setString(6, answer2.replaceAll("'", "''"));
                ps.setInt(7, questionID3);
                ps.setString(8, answer3.replaceAll("'", "''"));
                ps.setString(9, positionTitle);
                ps.setInt(10, 0);
                ps.setInt(11, 0);
                ps.setInt(12, LoginView.person.get(userSelected).personID);
                ps.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            updatedFromManagerPerson = true;
            Thread personsData = new Thread(LoginView.personInfo);
            personsData.start();

            updatedFromManagerLogon = true;
            Thread logonData = new Thread(LoginView.logonInfo);
            logonData.start();
        }
    }

    /**
    * Deletes the selected user
    */
    public void deleteAccount() {
        try {
            int personID = LoginView.person.get(userSelected).personID;
            personDetailsSelected = userSelected;

            String statement = "UPDATE Logon SET AccountDisabled = 1 WHERE PersonID = " + personID + ";";
            ps = con.prepareStatement(statement);
            ps.execute();

            statement = "UPDATE Person SET PersonDeleted = 1 WHERE PersonID = " + personID + ";";
            ps = con.prepareStatement(statement);
            ps.execute();

            userSelected = -1;
            userSelectedWarning.setText("");

            updatedFromManagerPerson = true;
            Thread personsData = new Thread(LoginView.personInfo);
            personsData.start();

            updatedFromManagerLogon = true;
            Thread logonData = new Thread(LoginView.logonInfo);
            logonData.start();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static Object[][] getPersonData() {
        try {
            Object[][] isPersonData = new Object[LoginView.person.size()][3];

            for (int i = 0; i < LoginView.person.size(); i++) {
                String firstName = LoginView.person.get(i).nameFirst;
                String middleName = null;
                String lastName = LoginView.person.get(i).nameLast;

                isPersonData[i] = new String[]{firstName, middleName, lastName};
            }
            return isPersonData;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    static Object[][] getLoginData() {
        try {
            Object[][] isLoginData = new Object[LoginView.logon.size()][3];

            for (int i = 0; i < LoginView.logon.size(); i++) {
                String loginName = LoginView.logon.get(i).logonName;
                String password = LoginView.logon.get(i).password;
                String positionTitle = LoginView.logon.get(i).positionTitle;

                isLoginData[i] = new String[]{loginName, password, positionTitle};
            }
            return isLoginData;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
    * Creates a report of all items
    */
    public void itemsReport() {
        StringBuilder orderReport = new StringBuilder();
        orderReport.append("<html><body><h1>All Items</h1>");
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
        orderReport.append("<table border='1'><tr></th><th>Item Name</th><th>Item Category</th><th>Type</th><th>Hardness</th><th>Size</th><th>Weight</th><th>Retail Price</th><th>Cost</th><th>Quantity</th></tr>");
        for (int i = 0; i < LoginView.inventory.size(); i++) {
            Inventory item = LoginView.inventory.get(i);
            String category = "";
            String stoneOrGemstone = "";
            switch (item.categoryID) {
                case 1 ->
                    category = "Igneous";
                case 2 ->
                    category = "Sedimentary";
                case 3 ->
                    category = "Metamorphic";
            }
            switch (item.stoneOrGemstone) {
                case 0 ->
                    stoneOrGemstone = "Stone";
                case 1 -> {
                    int semiOrPrecious = Integer.parseInt(item.semiOrPrecious);
                    switch (semiOrPrecious) {
                        case 0 ->
                            stoneOrGemstone = "Gemstone";
                        case 1 ->
                            stoneOrGemstone = "Precious Gemstone";
                    }
                }

            }
            orderReport.append("<tr>");
            orderReport.append("<td>").append(item.itemName).append("</td>");
            orderReport.append("<td>").append(category).append("</td>");
            orderReport.append("<td>").append(stoneOrGemstone).append("</td>");
            orderReport.append("<td>").append(item.hardness).append("</td>");
            orderReport.append("<td>").append(item.stoneSize).append("</td>");
            orderReport.append("<td>").append(item.stoneWeight).append("</td>");
            orderReport.append("<td>").append("$" + f.format(item.retailPrice)).append("</td>");
            orderReport.append("<td>").append("$" + f.format(item.cost)).append("</td>");
            orderReport.append("<td>").append(item.quantity).append("</td>");
            orderReport.append("<tr>");
        }
        orderReport.append("</table></body></html>");
        try {
            Path documentsDirectory = FileSystems.getDefault().getPath(System.getProperty("user.home"), "Documents");
            if (!documentsDirectory.toFile().exists()) {
                boolean created = documentsDirectory.toFile().mkdirs();
                if (!created) {
                    System.out.println("Directory Not Established: " + documentsDirectory);
                    return;
                }
            }

            String filePath = documentsDirectory.toString() + File.separator + "items.html";
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(orderReport.toString());
            writer.close();

            File file = new File(filePath);
            URI uri = file.toURI();
            Desktop.getDesktop().browse(uri);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
    * Creates a new item using entered values
    */
    public void newItem() {
        boolean addValid = true;
        String itemName = itemsName.getText();
        String itemDescription = itemsDescription.getText();
        int itemCategoryID = 1;
        int itemStoneOrGemstone = 0;
        int itemGrainSize = -1;
        String itemGrainShape = null;
        double itemHeft = -1;
        int itemSemiOrPrecious = -1;
        double itemHardness = 0.00;
        double itemSize = 0.00;
        double itemWeight = 0.00;
        double itemRetail = 0.00;
        double itemCost = 0.00;
        int itemQuantity = 0;
        int itemRestockThreshold = 0;

        if (nameValid) {
            if (itemName.isEmpty()) {
                itemsNameError.setText("Name required");
                addValid = false;
            } else {
                String testItemName = itemName.replaceAll("\\s", "");
                /*
                This try statement contains a query that will test if the login name already exists.
                */
                for (int i = 0; i < LoginView.inventory.size(); i++) {
                    String logN = LoginView.inventory.get(i).itemName;
                    logN = logN.replaceAll("\\s", "");
                    if (testItemName.toUpperCase().equals(logN.toUpperCase())) {
                        itemsNameError.setText("Already in database");
                        addValid = false;
                    }
                }
            }
        } else {
            addValid = false;
        }

        if (descriptionValid) {
            if (itemDescription.isEmpty()) {
                itemsDescriptionError.setText("Description required");
                addValid = false;
            }
        } else {
            addValid = false;
        }

        if (itemIsIgneous.isSelected()) {
            itemCategoryID = 1;
        } else if (itemIsSedimentary.isSelected()) {
            itemCategoryID = 2;
        } else if (itemIsMetamorphic.isSelected()) {
            itemCategoryID = 3;
        }

        if (itemIsStone.isSelected()) {
            itemStoneOrGemstone = 0;
        } else if (itemIsGemstone.isSelected()) {
            itemStoneOrGemstone = 1;
            itemSemiOrPrecious = 0;
        } else if (itemIsPreciousGemstone.isSelected()) {
            itemStoneOrGemstone = 1;
            itemSemiOrPrecious = 1;
        }

        if (itemStoneOrGemstone == 0) {
            if (itemIsCoarseGrained.isSelected()) {
                itemGrainSize = 0;
            } else if (itemIsFineGrained.isSelected()) {
                itemGrainSize = 1;
            }
            if (!itemsGrainShape.getText().equals("")) {
                itemGrainShape = itemsGrainShape.getText();
            }
        } else if (itemStoneOrGemstone == 1) {
            if (heftValid) {
                if (!itemsHeft.getText().equals("")) {
                    itemHeft = Double.parseDouble(itemsHeft.getText());
                } else {
                    itemsHeftError.setText("All gemstones require heft");
                    addValid = false;
                }
            } else {
                addValid = false;
            }
        }

        if (hardnessValid) {
            if (!itemsHardness.getText().equals("")) {
                itemHardness = Double.parseDouble(itemsHardness.getText());
            } else {
                itemsHardnessError.setText("Required");
                addValid = false;
            }
        } else {
            addValid = false;
        }

        if (sizeValid) {
            if (!itemsSize.getText().equals("")) {
                itemSize = Double.parseDouble(itemsSize.getText());
            } else {
                itemsSizeError.setText("Required");
                addValid = false;
            }
        } else {
            addValid = false;
        }

        if (weightValid) {
            if (!itemsWeight.getText().equals("")) {
                itemWeight = Double.parseDouble(itemsWeight.getText());
            } else {
                itemsWeightError.setText("Required");
                addValid = false;
            }
        } else {
            addValid = false;
        }

        if (retailValid) {
            if (!itemsRetail.getText().equals("")) {
                itemRetail = Double.parseDouble(itemsRetail.getText());
            } else {
                itemsRetailError.setText("Req.");
                addValid = false;
            }
        } else {
            addValid = false;
        }

        if (costValid) {
            if (!itemsCost.getText().equals("")) {
                itemCost = Double.parseDouble(itemsCost.getText());
            } else {
                itemsCostError.setText("Req.");
                addValid = false;
            }
        } else {
            addValid = false;
        }

        if (quantityValid) {
            if (!itemsQuantity.getText().equals("")) {
                itemQuantity = Integer.parseInt(itemsQuantity.getText());
            } else {
                itemsQuantityError.setText("Req.");
                addValid = false;
            }
        } else {
            addValid = false;
        }

        if (restockThresholdValid) {
            if (!itemsRestockThreshold.getText().equals("")) {
                itemRestockThreshold = Integer.parseInt(itemsRestockThreshold.getText());
            } else {
                itemsRestockThresholdError.setText("Req.");
                addValid = false;
            }
        } else {
            addValid = false;
        }

        if (addValid) {
            int discontinued = 0;
            try {
                String statement = "INSERT INTO Inventory (ItemName, ItemDescription, CategoryID, StoneOrGemstone, GrainSize, GrainShape, Heft, SemiOrPrecious, Hardness, StoneSize, StoneWeight, RetailPrice, Cost, Quantity, RestockThreshold, Discontinued)"
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
                ps = con.prepareStatement(statement);
                ps.setString(1, itemName);
                ps.setString(2, itemDescription);
                ps.setInt(3, itemCategoryID);
                ps.setInt(4, itemStoneOrGemstone);
                if (itemGrainSize == -1) {
                    ps.setNull(5, java.sql.Types.INTEGER);
                } else {
                    ps.setInt(5, itemGrainSize);
                }
                ps.setString(6, itemGrainShape);
                if (itemHeft == -1) {
                    ps.setNull(7, java.sql.Types.DECIMAL);
                } else {
                    ps.setDouble(7, itemHeft);
                }
                if (itemSemiOrPrecious == -1) {
                    ps.setNull(8, java.sql.Types.INTEGER);
                } else {
                    ps.setInt(8, itemSemiOrPrecious);
                }
                ps.setDouble(9, itemHardness);
                ps.setDouble(10, itemSize);
                ps.setDouble(11, itemWeight);
                ps.setDouble(12, itemRetail);
                ps.setDouble(13, itemCost);
                ps.setInt(14, itemQuantity);
                ps.setInt(15, itemRestockThreshold);
                ps.setInt(16, discontinued);
                ps.execute();

                updatedFromManager = true;
                Thread inventoryData = new Thread(LoginView.inventoryInfo);
                inventoryData.start();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    /**
    * Edits the selected item using entered values
    */
    public void editItem() {
        boolean editValid = true;
        String itemName = itemsName.getText();
        String itemDescription = itemsDescription.getText();
        int itemCategoryID = 1;
        int itemStoneOrGemstone = 0;
        int itemGrainSize = -1;
        String itemGrainShape = null;
        double itemHeft = -1;
        int itemSemiOrPrecious = -1;
        double itemHardness = 0.00;
        double itemSize = 0.00;
        double itemWeight = 0.00;
        double itemRetail = 0.00;
        double itemCost = 0.00;
        int itemQuantity = 0;
        int itemRestockThreshold = 0;

        if (nameValid) {
            if (itemName.isEmpty()) {
                itemsNameError.setText("Name required");
                editValid = false;
            }
        } else {
            editValid = false;
        }

        if (descriptionValid) {
            if (itemDescription.isEmpty()) {
                itemsDescriptionError.setText("Description required");
                editValid = false;
            }
        } else {
            editValid = false;
        }

        if (itemIsIgneous.isSelected()) {
            itemCategoryID = 1;
        } else if (itemIsSedimentary.isSelected()) {
            itemCategoryID = 2;
        } else if (itemIsMetamorphic.isSelected()) {
            itemCategoryID = 3;
        }

        if (itemIsStone.isSelected()) {
            itemStoneOrGemstone = 0;
        } else if (itemIsGemstone.isSelected()) {
            itemStoneOrGemstone = 1;
            itemSemiOrPrecious = 0;
        } else if (itemIsPreciousGemstone.isSelected()) {
            itemStoneOrGemstone = 1;
            itemSemiOrPrecious = 1;
        }

        if (itemStoneOrGemstone == 0) {
            if (itemIsCoarseGrained.isSelected()) {
                itemGrainSize = 0;
            } else if (itemIsFineGrained.isSelected()) {
                itemGrainSize = 1;
            }
            if (!itemsGrainShape.getText().equals("")) {
                itemGrainShape = itemsGrainShape.getText();
            }
        } else if (itemStoneOrGemstone == 1) {
            if (heftValid) {
                if (!itemsHeft.getText().equals("")) {
                    itemHeft = Double.parseDouble(itemsHeft.getText());
                } else {
                    itemsHeftError.setText("All gemstones require heft");
                    editValid = false;
                }
            } else {
                editValid = false;
            }
        }

        if (hardnessValid) {
            if (!itemsHardness.getText().equals("")) {
                itemHardness = Double.parseDouble(itemsHardness.getText());
            } else {
                itemsHardnessError.setText("Required");
                editValid = false;
            }
        } else {
            editValid = false;
        }

        if (sizeValid) {
            if (!itemsSize.getText().equals("")) {
                itemSize = Double.parseDouble(itemsSize.getText());
            } else {
                itemsSizeError.setText("Required");
                editValid = false;
            }
        } else {
            editValid = false;
        }

        if (weightValid) {
            if (!itemsWeight.getText().equals("")) {
                itemWeight = Double.parseDouble(itemsWeight.getText());
            } else {
                itemsWeightError.setText("Required");
                editValid = false;
            }
        } else {
            editValid = false;
        }

        if (retailValid) {
            if (!itemsRetail.getText().equals("")) {
                itemRetail = Double.parseDouble(itemsRetail.getText());
            } else {
                itemsRetailError.setText("Req.");
                editValid = false;
            }
        } else {
            editValid = false;
        }

        if (costValid) {
            if (!itemsCost.getText().equals("")) {
                itemCost = Double.parseDouble(itemsCost.getText());
            } else {
                itemsCostError.setText("Req.");
                editValid = false;
            }
        } else {
            editValid = false;
        }

        if (quantityValid) {
            if (!itemsQuantity.getText().equals("")) {
                itemQuantity = Integer.parseInt(itemsQuantity.getText());
            } else {
                itemsQuantityError.setText("Req.");
                editValid = false;
            }
        } else {
            editValid = false;
        }

        if (restockThresholdValid) {
            if (!itemsRestockThreshold.getText().equals("")) {
                itemRestockThreshold = Integer.parseInt(itemsRestockThreshold.getText());
            } else {
                itemsRestockThresholdError.setText("Req.");
                editValid = false;
            }
        } else {
            editValid = false;
        }

        if (editValid) {
            int discontinued = 0;
            try {
                String statement = "UPDATE Inventory SET ItemName = ?, ItemDescription = ?, CategoryID = ?, StoneOrGemstone = ?, GrainSize = ?, GrainShape = ?, Heft = ?, SemiOrPrecious = ?, Hardness = ?, StoneSize = ?, StoneWeight = ?, RetailPrice = ?, Cost = ?, Quantity = ?, RestockThreshold = ?, Discontinued = ? WHERE InventoryID = ?";
                ps = con.prepareStatement(statement);
                ps.setString(1, itemName);
                ps.setString(2, itemDescription);
                ps.setInt(3, itemCategoryID);
                ps.setInt(4, itemStoneOrGemstone);
                if (itemGrainSize == -1) {
                    ps.setNull(5, java.sql.Types.INTEGER);
                } else {
                    ps.setInt(5, itemGrainSize);
                }
                ps.setString(6, itemGrainShape);
                if (itemHeft == -1) {
                    ps.setNull(7, java.sql.Types.DECIMAL);
                } else {
                    ps.setDouble(7, itemHeft);
                }
                if (itemSemiOrPrecious == -1) {
                    ps.setNull(8, java.sql.Types.INTEGER);
                } else {
                    ps.setInt(8, itemSemiOrPrecious);
                }
                ps.setDouble(9, itemHardness);
                ps.setDouble(10, itemSize);
                ps.setDouble(11, itemWeight);
                ps.setDouble(12, itemRetail);
                ps.setDouble(13, itemCost);
                ps.setInt(14, itemQuantity);
                ps.setInt(15, itemRestockThreshold);
                ps.setInt(16, discontinued);
                ps.setInt(17, LoginView.inventory.get(itemSelected).inventoryID);
                ps.executeUpdate();

                updatedFromManager = true;
                Thread inventoryData = new Thread(LoginView.inventoryInfo);
                inventoryData.start();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    /**
    * Deletes the selected item
    */
    public void deleteItem() {
        try {
            int itemID = LoginView.inventory.get(itemSelected).inventoryID;

            String statement = "UPDATE Inventory SET Discontinued = 1 WHERE InventoryID = " + itemID + ";";
            ps = con.prepareStatement(statement);
            ps.execute();

            itemSelected = -1;
            itemSelectedWarning.setText("");
            updatedFromManager = true;
            Thread inventoryData = new Thread(LoginView.inventoryInfo);
            inventoryData.start();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static Runnable updateData = () -> {
        needsRestock.setText("");
        needsRestock.setVisible(false);
        Color thistle = Color.decode("#D5CBE2");
        GridBagConstraints imageLayout = new GridBagConstraints();
        imageLayout.insets = new Insets(2, 2, 2, 2);
        int x = 0;
        int y = 0;
        itemDisplay.removeAll();
        items.clear();
        itemDisplay.repaint();
        ManagerItemSelection select = new ManagerItemSelection(items);
        for (int i = 0; i < LoginView.inventory.size(); i++) {
            String itemName = LoginView.inventory.get(i).itemName;
            if (LoginView.inventory.get(i).quantity <= LoginView.inventory.get(i).restockThreshold) {
                needsRestock.setText("Items Need Restock");
                needsRestock.setVisible(true);
            }

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
    };

    static Object[][] getDiscountData() {
        try {
            Object[][] isDiscountData = new Object[LoginView.discount.size()][9];

            for (int i = 0; i < LoginView.discount.size(); i++) {
                String discountCode = LoginView.discount.get(i).discountCode;
                String discountLevel = null;
                String inventoryID = null;
                String discountType = null;
                if (LoginView.discount.get(i).discountLevel == 0) {
                    discountLevel = "Cart";
                } else if (LoginView.discount.get(i).discountLevel == 1) {
                    discountLevel = "Item";
                    inventoryID = LoginView.discount.get(i).inventoryID + "";
                }
                if (LoginView.discount.get(i).discountType == 0) {
                    discountType = "Percentage";
                } else if (LoginView.discount.get(i).discountType == 1) {
                    discountType = "Dollar Amount";
                }
                isDiscountData[i] = new String[]{discountCode, discountLevel, inventoryID, discountType, ff.format(LoginView.discount.get(i).discountPercentage * 100) + "%", "$" + f.format(LoginView.discount.get(i).discountDollarAmount) + "", LoginView.discount.get(i).startDate + "", LoginView.discount.get(i).expirationDate + ""};
            }
            return isDiscountData;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    static Object[][] getDiscountItemsData() {
        try {
            Object[][] isDiscountItemData = new Object[LoginView.inventory.size()][1];

            for (int i = 0; i < LoginView.inventory.size(); i++) {
                String itemName = LoginView.inventory.get(i).itemName;
                isDiscountItemData[i] = new String[]{itemName};
            }
            return isDiscountItemData;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
    * Sets the start day for discounts
    */
    public void setDiscountStartDay() {
        discountStartMonth = Integer.parseInt(startMonth.getSelectedItem().toString());
        startDay.removeAllItems();
        discountStartDay = 1;

        /*
        The second discount start calender. This one is often re-used. It will apply values to the start-day combobox as the months change.
        */
        Calendar stayStartCalendar2 = new GregorianCalendar(discountStartYear, discountStartMonth - 1, 1);
        /*
        The proper amount of days, depending on the month, is added to the list. This even updates for leap years (febuary 29th).
        */
        for (int i = 0; i < stayStartCalendar2.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            startDay.addItem(i + 1);
        }
    }

    /**
    * Sets the end day for discounts
    */
    public void setDiscountEndDay() {
        discountExpirationMonth = Integer.parseInt(expireMonth.getSelectedItem().toString());
        expireDay.removeAllItems();
        discountExpirationDay = 1;

        /*
        The second stay end calender. This one is often re-used. It will apply values to the end-day combobox as the months change.
        */
        Calendar stayEndCalendar2 = new GregorianCalendar(discountExpirationYear, discountExpirationMonth - 1, 1);
        /*
        The proper amount of days, depending on the month, is added to the list. This even updates for leap years (febuary 29th).
        */
        for (int i = 0; i < stayEndCalendar2.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            expireDay.addItem(i + 1);
        }
    }

    /**
    * Adapts dates for discounts
    */
    public void adaptDiscountDates() {
        if (discountStartMonth < 10) {
            startingMonth = "0" + discountStartMonth;
        } else {
            startingMonth = discountStartMonth + "";
        }
        if (discountStartDay < 10) {
            startingDay = "0" + discountStartDay;
        } else {
            startingDay = discountStartDay + "";
        }
        if (discountExpirationMonth < 10) {
            expirationMonth = "0" + discountExpirationMonth;
        } else {
            expirationMonth = discountExpirationMonth + "";
        }
        if (discountExpirationDay < 10) {
            expirationDay = "0" + discountExpirationDay;
        } else {
            expirationDay = discountExpirationDay + "";
        }
        discountStartTime = discountStartYear + "-" + startingMonth + "-" + startingDay;
        discountExpirationTime = discountExpirationYear + "-" + expirationMonth + "-" + expirationDay;
    }

    /**
    * Creates a new user using entered values
    */
    public void resetDiscountDates() {
        Calendar today = Calendar.getInstance();
        int currentMonth = today.get(Calendar.MONTH) + 1;
        int currentDay = today.get(Calendar.DAY_OF_MONTH);

        startYear.setSelectedIndex(0);
        startMonth.setSelectedIndex(currentMonth - 1);
        startDay.setSelectedIndex(currentDay - 1);

        expireYear.setSelectedIndex(0);
        expireMonth.setSelectedIndex(currentMonth - 1);
        expireDay.setSelectedIndex(currentDay - 1);
    }

    /**
    * Creates a new discount using entered values
    */
    public void newDiscount() {
        boolean discountValid = true;
        String discountCode = discountsCode.getText();
        String discountDescription = discountsDescription.getText();
        int discountLevel = 0;
        int discountInventoryID = 0;
        int discountType = 0;
        double discountPercent = 0.00;
        double discountAmount = 0.00;
        String discountStart = null;
        if (discountCodeValid) {
            if (discountCode.isEmpty()) {
                discountsCodeError.setText("Code required");
                discountValid = false;
            } else {
                String testDiscountCode = discountCode.replaceAll("\\s", "");
                /*
                This try statement contains a query that will test if the discount code already exists.
                */
                for (int i = 0; i < LoginView.discount.size(); i++) {
                    String disC = LoginView.discount.get(i).discountCode;
                    disC = disC.replaceAll("\\s", "");
                    if (testDiscountCode.toUpperCase().equals(disC.toUpperCase())) {
                        discountsCodeError.setText("Already in database");
                        discountValid = false;
                    }
                }
            }
        } else {
            discountValid = false;
        }

        if (discountDescriptionValid) {
            if (discountDescription.isEmpty()) {
                discountsDescriptionError.setText("Description required");
                discountValid = false;
            }
        } else {
            discountValid = false;
        }

        if (discountIsPercentage.isSelected()) {
            discountType = 0;
            if (percentValid) {
                if (!discountsPercentage.getText().isEmpty()) {
                    discountPercent = Double.parseDouble(discountsPercentage.getText());
                } else {
                    discountsPercentageError.setText("Percent req.");
                    discountValid = false;
                }
            } else {
                discountValid = false;
            }
        }

        if (discountIsAmount.isSelected()) {
            discountType = 1;
            if (amountValid) {
                if (!discountsAmount.getText().isEmpty()) {
                    discountAmount = Double.parseDouble(discountsAmount.getText());
                } else {
                    discountsAmountError.setText("Amount req.");
                    discountValid = false;
                }
            } else {
                discountValid = false;
            }
        }

        if (discountIsItemLevel.isSelected()) {
            if (discountItemSelectedID != -1) {
                discountLevel = 1;
                discountInventoryID = discountItemSelectedID;
            } else {
                discountsInventoryID.setText("Item ID Required");
                discountsInventoryID.setForeground(Color.red);
                discountValid = false;
            }
        }

        discountDateError.setText("");
        LocalDate expirationOfDiscount = LocalDate.of(discountExpirationYear, discountExpirationMonth, discountExpirationDay);
        long compareDateToDiscount = ChronoUnit.DAYS.between(currentDate, expirationOfDiscount);
        if (compareDateToDiscount > 0) {
            adaptDiscountDates();
            if (discountStartDate.isSelected()) {
                LocalDate startOfDiscount = LocalDate.of(discountStartYear, discountStartMonth, discountStartDay);
                long diff = ChronoUnit.DAYS.between(startOfDiscount, expirationOfDiscount);
                if (diff > 0) {
                    discountStart = discountStartTime;
                } else if (diff == 0) {
                    discountDateError.setText("Cannot start and expire on same day");
                    discountValid = false;
                } else if (diff < 0) {
                    discountDateError.setText("Cannot start after expiration");
                    discountValid = false;
                }
            }
        } else {
            discountDateError.setText("Cannot expire on or before today");
            discountValid = false;
        }

        if (discountValid) {
            try {
                String statement = "INSERT INTO Discounts (DiscountCode, Description, DiscountLevel, InventoryID, DiscountType, DiscountPercentage, DiscountDollarAmount, StartDate, ExpirationDate)"
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
                ps = con.prepareStatement(statement);
                ps.setString(1, discountCode);
                ps.setString(2, discountDescription);
                ps.setInt(3, discountLevel);
                if (discountInventoryID == 0) {
                    ps.setNull(4, java.sql.Types.INTEGER);
                } else {
                    ps.setInt(4, discountInventoryID);
                }
                ps.setInt(5, discountType);
                ps.setDouble(6, discountPercent);
                ps.setDouble(7, discountAmount);
                if (discountStart != null) {
                    ps.setString(8, discountStart);
                } else {
                    ps.setString(8, null);
                }
                ps.setString(9, discountExpirationTime);
                ps.execute();

                updatedFromManager = true;
                Thread discountsData = new Thread(LoginView.discountInfo);
                discountsData.start();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
    * Edits the selected discount using entered values
    */
    public void editDiscount() {
        boolean discountValid = true;
        String discountCode = discountsCode.getText();
        String discountDescription = discountsDescription.getText();
        int discountLevel = 0;
        int discountInventoryID = 0;
        int discountType = 0;
        double discountPercent = 0.00;
        double discountAmount = 0.00;
        String discountStart = null;
        if (discountCodeValid) {
            if (discountCode.isEmpty()) {
                discountsCodeError.setText("Code required");
                discountValid = false;
            }
        } else {
            discountValid = false;
        }

        if (discountDescriptionValid) {
            if (discountDescription.isEmpty()) {
                discountsDescriptionError.setText("Description required");
                discountValid = false;
            }
        } else {
            discountValid = false;
        }

        if (discountIsPercentage.isSelected()) {
            discountType = 0;
            if (percentValid) {
                if (!discountsPercentage.getText().isEmpty()) {
                    discountPercent = Double.parseDouble(discountsPercentage.getText());
                } else {
                    discountsPercentageError.setText("Percent req.");
                    discountValid = false;
                }
            } else {
                discountValid = false;
            }
        }

        if (discountIsAmount.isSelected()) {
            discountType = 1;
            if (amountValid) {
                if (!discountsAmount.getText().isEmpty()) {
                    discountAmount = Double.parseDouble(discountsAmount.getText());
                } else {
                    discountsAmountError.setText("Amount req.");
                    discountValid = false;
                }
            } else {
                discountValid = false;
            }
        }

        if (discountIsItemLevel.isSelected()) {
            if (discountItemSelectedID != -1) {
                discountLevel = 1;
                discountInventoryID = discountItemSelectedID;
            } else {
                discountsInventoryID.setText("Item ID Required");
                discountsInventoryID.setForeground(Color.red);
                discountValid = false;
            }
        }

        discountDateError.setText("");
        LocalDate expirationOfDiscount = LocalDate.of(discountExpirationYear, discountExpirationMonth, discountExpirationDay);
        long compareDateToDiscount = ChronoUnit.DAYS.between(currentDate, expirationOfDiscount);
        if (compareDateToDiscount > 0) {
            adaptDiscountDates();
            if (discountStartDate.isSelected()) {
                LocalDate startOfDiscount = LocalDate.of(discountStartYear, discountStartMonth, discountStartDay);
                long diff = ChronoUnit.DAYS.between(startOfDiscount, expirationOfDiscount);
                if (diff > 0) {
                    discountStart = discountStartTime;
                } else if (diff == 0) {
                    discountDateError.setText("Cannot start and expire on same day");
                    discountValid = false;
                } else if (diff < 0) {
                    discountDateError.setText("Cannot start after expiration");
                    discountValid = false;
                }
            }
        } else {
            discountDateError.setText("Cannot expire on or before today");
            discountValid = false;
        }

        if (discountValid) {
            try {
                String editPerson = "UPDATE Discounts SET DiscountCode = ?, Description = ?, DiscountLevel = ?, InventoryID = ?, DiscountType = ?, DiscountPercentage = ?, DiscountDollarAmount = ?, StartDate = ?, ExpirationDate = ? WHERE DiscountCode = ?";
                ps = con.prepareStatement(editPerson);
                ps.setString(1, discountCode);
                ps.setString(2, discountDescription);
                ps.setInt(3, discountLevel);
                if (discountInventoryID == 0) {
                    ps.setNull(4, java.sql.Types.INTEGER);
                } else {
                    ps.setInt(4, discountInventoryID);
                }
                ps.setInt(5, discountType);
                ps.setDouble(6, discountPercent);
                ps.setDouble(7, discountAmount);
                if (discountStart != null) {
                    ps.setString(8, discountStart);
                } else {
                    ps.setNull(8, java.sql.Types.DATE);
                }
                ps.setString(9, discountExpirationTime);
                ps.setString(10, discountCode);
                ps.executeUpdate();

                updatedFromManager = true;
                Thread discountsData = new Thread(LoginView.discountInfo);
                discountsData.start();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
    Uploads an associated image to the database and assigns it to the selected item
    */
    public class UploadImage extends Thread {

        File imageFile = null;

        /**
        * Interacts with the image file
        * @param imageFile 
        */
        UploadImage(File imageFile) {
            this.imageFile = imageFile;
        }

        @Override
        public void run() {
            try {
                if (itemSelected != -1) {
                    byte[] b = Files.readAllBytes(imageFile.toPath());
                    String image = "UPDATE Inventory SET ItemImage = ? WHERE InventoryID = " + LoginView.inventory.get(itemSelected).inventoryID;
                    ps = con.prepareStatement(image);
                    ps.setBytes(1, b);
                    ps.executeUpdate();

                    updatedFromManager = true;
                    Thread inventoryData = new Thread(LoginView.inventoryInfo);
                    inventoryData.start();
                } else {
                    itemSelectedWarning.setText("No item selected");
                    itemSelectedWarning.setBounds(260, 445, 150, 15);
                    itemSelectedWarning.setForeground(Color.red);
                }
            } catch (SQLException | IOException ex) {
                ex.printStackTrace();
            }
        }
    };

    /**
    * Resets customer data to its default state, clearing all fields
    */
    public void clearCustomerData() {
        inventory.setPreferredSize(new Dimension(625, 0));
        jp.validate();

        customerView.jp.remove(CustomerView.cartPanel);
        CustomerView.cartVisible = false;

        CustomerView.itemSelected = -1;

        CustomerView.itemsName.setVisible(false);
        CustomerView.itemsDescription.setVisible(false);
        CustomerView.itemRockOrGem.setVisible(false);
        CustomerView.itemGrainSize.setVisible(false);
        CustomerView.itemGrainShape.setVisible(false);
        CustomerView.itemHeft.setVisible(false);
        CustomerView.itemHardness.setVisible(false);
        CustomerView.stoneSize.setVisible(false);
        CustomerView.stoneWeight.setVisible(false);
        CustomerView.price.setVisible(false);
        CustomerView.quantity.setVisible(false);
        CustomerView.addToCart.setVisible(false);
        CustomerView.returnToSearch.setVisible(false);
        CustomerView.searchBarEntry.setVisible(true);
        CustomerView.searchBar.setVisible(true);
        CustomerView.searchBar.setText("");
        CustomerView.orderError.setText("");
        CustomerView.cardNumberEntry.setVisible(true);
        CustomerView.cardNumber.setVisible(true);
        CustomerView.securityCodeEntry.setVisible(true);
        CustomerView.cardSecurityCode.setVisible(true);
        CustomerView.cardExpireYear.setVisible(true);
        CustomerView.cardExpirationYear.setVisible(true);
        CustomerView.cardExpireMonth.setVisible(true);
        CustomerView.cardExpirationMonth.setVisible(true);
        CustomerView.cardError.setText("");
        CustomerView.cardSecurityError.setText("");
        CustomerView.expirationError.setText("");
        CustomerView.discountEntry.setVisible(true);
        CustomerView.discountCode.setVisible(true);
        CustomerView.discountError.setText("");
        CustomerView.refreshButton.setVisible(true);
        CustomerView.igneousButton.setVisible(true);
        CustomerView.sedimentaryButton.setVisible(true);
        CustomerView.metamorphicButton.setVisible(true);
        CustomerView.stoneButton.setVisible(true);
        CustomerView.gemstoneButton.setVisible(true);
        CustomerView.checkout.setVisible(true);
        CustomerView.clearCart.setVisible(true);
    }

    /**
    * Creates a pdf document describing all items that need to be restocked
    */
    public void restockAlert() {
        Document document = new Document();
        Page page = document.getPages().add();
        TextFragment title = new TextFragment("Items in Need of Restock");
        title.getTextState().setFontSize(18);
        title.getTextState().setFont(FontRepository.findFont("Arial"));
        page.getParagraphs().add(title);
        String header = String.format("%-20s %-10s %-10s", "Item Name", "Quantity", "Restock At");
        page.getParagraphs().add(new TextFragment(header));
        page.getParagraphs().add(new TextFragment("-------------------------------"));
        int itemNumber = 1;
        for (int i = 0; i < LoginView.inventory.size(); i++) {
            if (LoginView.inventory.get(i).quantity <= LoginView.inventory.get(i).restockThreshold) {
                String line = String.format("%-20s %-10d %-10d",
                        LoginView.inventory.get(i).itemName,
                        LoginView.inventory.get(i).quantity,
                        LoginView.inventory.get(i).restockThreshold);
                page.getParagraphs().add(new TextFragment(line));
                itemNumber++;
            }
        }
        document.save("restock.pdf");
        try {
            File pdfFile = new File("restock.pdf");
            Desktop.getDesktop().open(pdfFile);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    Runnable helpSystem = () -> {
        Document document = new Document();
        Page page = document.getPages().add();
        page.getParagraphs().add(new TextFragment("Help Guide for Managers"));
        page.getParagraphs().add(new TextFragment("==================================="));
        page.getParagraphs().add(new TextFragment("----Manager/SOS View"));
        page.getParagraphs().add(new TextFragment("Upon first logging in as a manager, you will be greeted by a slim screen with various features. "
                + "These features allow you to view total sales over a specified time period, or select a customer to see their purchases and manage them.\n"));
        page.getParagraphs().add(new TextFragment("----Edit Users"));
        page.getParagraphs().add(new TextFragment("When you press the edit users button, you will see two tables and several text fields. "
                + "You can edit existing users or delete them. Selecting a customer fills the fields automatically for convenience.\n"));
        page.getParagraphs().add(new TextFragment("----Edit Inventory"));
        page.getParagraphs().add(new TextFragment("Pressing the edit items button shows a scroll pane of buttons and text fields. "
                + "You can add or edit items easily by selecting them.\n"));
        page.getParagraphs().add(new TextFragment("----Edit Discounts"));
        page.getParagraphs().add(new TextFragment("Pressing the edit discounts button displays a table of discounts. "
                + "You can add, edit, or delete discounts by filling in the required fields.\n"));
        document.save("managerhelp.pdf");
        try {
            File pdfFile = new File("managerhelp.pdf");
            Desktop.getDesktop().open(pdfFile);
        } catch (IOException e) {
            System.out.println(e);
        }
    };
}
