package geogeniuses;

//Imports for the scanner, array list, filewriter, filereater, the buffered writer and reader, and ioexceptions.
import com.aspose.pdf.Document;
import com.aspose.pdf.Page;
import com.aspose.pdf.TextFragment;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class LoginView extends State {

    //Creates an arraylist of people, items, and discounts
    static ArrayList<Person> person = new ArrayList();
    static ArrayList<Logon> logon = new ArrayList();
    static ArrayList<SecurityQuestion> securityQuestions = new ArrayList();
    static ArrayList<Inventory> inventory = new ArrayList();
    static ArrayList<Discount> discount = new ArrayList();
    static ArrayList<Orders> orders = new ArrayList();
    static ArrayList<OrderDetails> orderDetails = new ArrayList();

    static int currentPerson = 0;

    JTextField logName;
    JPasswordField logPassword;

    JLabel logonError;

    boolean loginValid = true;

    static JButton resetPasswordButton;
    static boolean resetPassword = true;

    LoginView() {

        ImageIcon icon = new ImageIcon(getClass().getResource("/Geo-Geniuses.png"));
        jf.setIconImage(icon.getImage());

        load();

        Color lightCyan = Color.decode("#DFFDFF");
        Color thistle = Color.decode("#D5CBE2");
        Color royalPurple = Color.decode("#8062A7");

        jf.setBackground(lightCyan);
        jp.setBackground(lightCyan);
        jf.setTitle("Login");
        jf.setBounds(550, 200, 800, 500);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jp.setLayout(null);
        jp.setBounds(0, 0, 600, 700);
        jf.add(jp);

        connectionStatus = new JLabel("");
        connectionStatus.setBounds(5, 445, 200, 15);
        connectionStatus.setForeground(Color.red);
        jp.add(connectionStatus);

        JLabel pleaseLogin = new JLabel("Login Name");
        pleaseLogin.setBounds(275, 25, pleaseLogin.getPreferredSize().width, pleaseLogin.getPreferredSize().height);
        jp.add(pleaseLogin);

        logName = new JTextField("");
        logName.setBounds(275, 40, 200, 20);
        jp.add(logName);

        JLabel pleasePassword = new JLabel("Password");
        pleasePassword.setBounds(275, 60, pleasePassword.getPreferredSize().width, pleasePassword.getPreferredSize().height);
        jp.add(pleasePassword);

        logPassword = new JPasswordField("");
        logPassword.setBounds(275, 75, 200, 20);
        logPassword.setEchoChar('*');
        jp.add(logPassword);

        logonError = new JLabel("");
        logonError.setBounds(475, 42, 200, 15);
        logonError.setForeground(Color.red);
        jp.add(logonError);

        JLabel help = new JLabel("Help");
        help.setBounds(745, 0, 50, 20);
        help.setForeground(royalPurple);
        jp.add(help);

        help.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                helpSystem();
            }
        });

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(310, 130, 130, 50);
        loginButton.setBackground(thistle);
        loginButton.addActionListener((e) -> {
            String loginName = logName.getText();
            String loginPassword = new String(logPassword.getPassword());
            logonError.setText("");
            if (!loginName.isEmpty() && !loginPassword.isEmpty()) {
                if (loginValid) {
                    //A for loop that will test to see if the name and password match up with a manager or customer.
                    boolean validPerson = false;
                    for (int i = 0; i < logon.size(); i++) {
                        try {
                            if (loginName.equals(logon.get(i).logonName) && loginPassword.equals(logon.get(i).password) && person.get(i).personDeleted != 1) {
                                validPerson = true;

                                String positionTitle = logon.get(i).positionTitle;

                                SetPersonID(loginName);

                                //Resets the login name and login password fields to their default states
                                logName.setText("");
                                logPassword.setText("");

                                if (positionTitle.equals("Manager")) {
                                    jp.remove(connectionStatus);
                                    connectionStatus = new JLabel("");
                                    connectionStatus.setBounds(0, 0, 200, 15);
                                    connectionStatus.setForeground(Color.red);
                                    ManagerView.panel.add(connectionStatus);

                                    ManagerView.searchForCustomer.setSelected(true);
                                    ManagerView.searchForManager.setSelected(false);

                                    CustomerView.panel.setPreferredSize(new Dimension(265, 610));
                                    CustomerView.cartPanel.setPreferredSize(new Dimension(265, 610));
                                    CustomerView.cartPanel.setBounds(0, 0, 267, 570);
                                    CustomerView.cartjs.setBounds(0, 0, CustomerView.cartPanel.getWidth(), CustomerView.cartPanel.getHeight());
                                    CustomerView.logOut.setVisible(false);

                                    ManagerView.includeQuestions();

                                    for (int l = 0; l < person.size(); l++) {
                                        if (currentPerson == person.get(l).personID) {
                                            ManagerView.managerDetails = l;
                                        }
                                    }

                                    //A switch to the the manager's view
                                    jf.setTitle("Manager View");
                                    jp.setVisible(false);
                                    jf.remove(jp);
                                    jf.setBounds(jf.getX(), jf.getY(), 390, 610);
                                    jf.add(managerView.jp);
                                    managerView.jp.setVisible(true);
                                }

                                if (positionTitle.equals("Customer")) {
                                    for (int l = 0; l < person.size(); l++) {
                                        if (currentPerson == person.get(l).personID) {
                                            CustomerView.personDetails = l;
                                        }
                                    }

                                    jp.remove(connectionStatus);
                                    connectionStatus = new JLabel("");
                                    connectionStatus.setBounds(5, 465, 200, 15);
                                    connectionStatus.setForeground(Color.red);
                                    CustomerView.panel.add(connectionStatus);

                                    //Updates the inventory for the customer
                                    ((CustomerView) customerView).updateData();
                                    //A switch to the customer's view
                                    jf.setTitle("Customer View");
                                    jp.setVisible(false);
                                    jf.remove(jp);
                                    jf.add(customerView.jp);
                                    jf.setBounds(jf.getX(), jf.getY(), 1050, 523);
                                    customerView.jp.setVisible(true);
                                }

                            }
                        } catch (IndexOutOfBoundsException ex) {
                            System.out.println(ex);
                        }
                    }
                    if (!validPerson) {
                        logonError.setText("Invalid Credentials");
                        logPassword.setText("");
                    }
                }
            }
        }
        );
        jp.add(loginButton);

        JButton guestButton = new JButton("Guest Login");
        guestButton.setBounds(310, 205, 130, 50);
        guestButton.setBackground(thistle);
        guestButton.addActionListener((e) -> {
            //Resets the login name and login password fields to their default states
            logName.setText("");
            logPassword.setText("");
            logonError.setText("");

            CustomerView.searchBarEntry.setVisible(true);
            CustomerView.searchBar.setVisible(true);
            CustomerView.cardNumberEntry.setVisible(false);
            CustomerView.cardNumber.setVisible(false);
            CustomerView.securityCodeEntry.setVisible(false);
            CustomerView.cardSecurityCode.setVisible(false);
            CustomerView.cardExpireYear.setVisible(false);
            CustomerView.cardExpirationYear.setVisible(false);
            CustomerView.cardExpireMonth.setVisible(false);
            CustomerView.cardExpirationMonth.setVisible(false);
            CustomerView.discountEntry.setVisible(false);
            CustomerView.discountCode.setVisible(false);
            CustomerView.checkout.setVisible(false);
            CustomerView.clearCart.setVisible(false);

            jp.remove(connectionStatus);
            connectionStatus = new JLabel("");
            connectionStatus.setBounds(5, 465, 200, 15);
            connectionStatus.setForeground(Color.red);
            CustomerView.panel.add(connectionStatus);

            //Updates the inventory for the customer
            ((CustomerView) customerView).updateData();
            //A switch to the customer's view
            jf.setTitle("Customer View");
            jp.setVisible(false);
            jf.remove(jp);
            jf.add(customerView.jp);
            jf.setBounds(jf.getX(), jf.getY(), 1050, 523);
            customerView.jp.setVisible(true);
        }
        );
        jp.add(guestButton);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(310, 280, 130, 50);
        registerButton.setBackground(thistle);
        registerButton.addActionListener((e) -> {
            logName.setText("");
            logPassword.setText("");
            logonError.setText("");

            Register.includeQuestions();

            jp.remove(connectionStatus);
            connectionStatus = new JLabel("");
            connectionStatus.setBounds(5, 470, 200, 15);
            connectionStatus.setForeground(Color.red);
            registerView.jp.add(connectionStatus);

            jf.setTitle("Register");
            jp.setVisible(false);
            jf.remove(jp);
            jf.add(registerView.jp);
            jf.setBounds(jf.getX(), jf.getY(), 820, 523);
            registerView.jp.setVisible(true);
        }
        );
        jp.add(registerButton);

        resetPasswordButton = new JButton("Reset Password");
        resetPasswordButton.setBounds(310, 355, 130, 50);
        resetPasswordButton.setBackground(thistle);
        resetPasswordButton.addActionListener((e) -> {
            resetPassword = true;
            String loginName = logName.getText();

            jp.remove(connectionStatus);
            connectionStatus = new JLabel("");
            connectionStatus.setBounds(5, 465, 200, 15);
            connectionStatus.setForeground(Color.red);
            resetPasswordView.jp.add(connectionStatus);

            if (!loginName.isEmpty()) {
                SetPersonID(loginName);
                if (resetPassword) {
                    logName.setText("");
                    logPassword.setText("");
                    logonError.setText("");
                    ResetPassword.currentPerson = LoginView.currentPerson;
                    ResetPassword.CollectQuestions();

                    jf.setTitle("Reset Password");
                    jp.setVisible(false);
                    jf.remove(jp);
                    jf.add(resetPasswordView.jp);
                    jf.setBounds(jf.getX(), jf.getY(), 650, 523);
                    resetPasswordView.jp.setVisible(true);
                }
            }
        }
        );
        jp.add(resetPasswordButton);

        jf.setVisible(true);
    }

    void load() {
        try {
            if (con != null & !con.isClosed()) {
                Thread personData = new Thread(personInfo);
                personData.start();

                Thread logonData = new Thread(logonInfo);
                logonData.start();

                Thread securityData = new Thread(securityInfo);
                securityData.start();

                Thread inventoryData = new Thread(inventoryInfo);
                inventoryData.start();

                Thread discountData = new Thread(discountInfo);
                discountData.start();

                Thread orderData = new Thread(orderInfo);
                orderData.start();

                Thread orderDetailsData = new Thread(orderDetailsInfo);
                orderDetailsData.start();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    static Runnable personInfo = () -> {
        String e[];
        person.clear();
        try {
            String query = "SELECT PersonID, Title, NameFirst, NameMiddle, NameLast, Suffix, Address1, Address2, Address3, City, Zipcode, State, Email, PhonePrimary, PhoneSecondary, PersonDeleted FROM Person WHERE PersonDeleted = 0;";
            ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            while (rs.next()) {
                e = new String[md.getColumnCount() + 1];
                for (int i = 1; i < md.getColumnCount() + 1; i++) {
                    e[i - 1] = rs.getString(i);
                }

                //Several of the arrays positions need to be converted into forms that the arrayList can use.
                int personID = Integer.parseInt(e[0]);
                String title = e[1];
                String nameFirst = e[2];
                String nameMiddle = e[3];
                String nameLast = e[4];
                String suffix = e[5];
                String address1 = e[6];
                String address2 = e[7];
                String address3 = e[8];
                String city = e[9];
                String zipCode = e[10];
                String state = e[11];
                String email = e[12];
                String phonePrimary = e[13];
                String phoneSecondary = e[14];
                int personDeleted = Integer.parseInt(e[15]);

                //Adds everything
                person.add(new Person(personID, title, nameFirst, nameMiddle, nameLast, suffix, address1, address2, address3, city, zipCode, state, email, phonePrimary, phoneSecondary, null, personDeleted));
            }
            if (ManagerView.updatedFromManagerPerson) {
                ManagerView.updatedFromManagerPerson = false;
                //Updates the person table for manager
                ManagerView.personData = ManagerView.getPersonData();
                DefaultTableModel ptable = (DefaultTableModel) ManagerView.personjt.getModel();
                ptable.setDataVector(ManagerView.personData, ManagerView.personCol);
                managerView.jp.repaint();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    };

    static Runnable logonInfo = () -> {
        String e[];
        logon.clear();
        try {
            String query = "SELECT PersonID, LogonName, Password, FirstChallengeAnswer, SecondChallengeAnswer, ThirdChallengeAnswer, PositionTitle FROM Logon WHERE AccountDisabled = 0;";
            ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            while (rs.next()) {
                e = new String[md.getColumnCount() + 1];
                for (int i = 1; i < md.getColumnCount() + 1; i++) {
                    e[i - 1] = rs.getString(i);
                }

                //Several of the arrays positions need to be converted into forms that the arrayList can use.
                int personID = Integer.parseInt(e[0]);
                String logonName = e[1];
                String password = e[2];
                String firstChallengeAnswer = e[3];
                String secondChallengeAnswer = e[4];
                String thirdChallengeAnswer = e[5];
                String positionTitle = e[6];

                //Adds everything
                logon.add(new Logon(personID, logonName, password, firstChallengeAnswer, secondChallengeAnswer, thirdChallengeAnswer, positionTitle));
            }
            if (ManagerView.updatedFromManagerLogon) {
                ManagerView.updatedFromManagerLogon = false;
                //Updates the login table for manager
                ManagerView.loginData = ManagerView.getLoginData();
                DefaultTableModel ltable = (DefaultTableModel) ManagerView.loginjt.getModel();
                ltable.setDataVector(ManagerView.loginData, ManagerView.loginCol);
                managerView.jp.repaint();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    };

    static Runnable securityInfo = () -> {
        String e[];
        securityQuestions.clear();
        try {
            String query = "SELECT QuestionID, QuestionPrompt FROM SecurityQuestions;";
            ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            while (rs.next()) {
                e = new String[md.getColumnCount() + 1];
                for (int i = 1; i < md.getColumnCount() + 1; i++) {
                    e[i - 1] = rs.getString(i);
                }

                //Several of the arrays positions need to be converted into forms that the arrayList can use.
                int questionID = Integer.parseInt(e[0]);
                String questionPrompt = e[1];

                //Adds everything to the security questions arraylist
                securityQuestions.add(new SecurityQuestion(questionID, questionPrompt));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    };

    static Runnable inventoryInfo = () -> {
        String e[];
        int number = 0;
        inventory.clear();
        try {
            Blob imgBlob = null;

            String query = "SELECT InventoryID, ItemName, ItemDescription, CategoryID, StoneOrGemstone, GrainSize, GrainShape, Heft, SemiOrPrecious, Hardness, StoneSize, StoneWeight, RetailPrice, Cost, Quantity, RestockThreshold, ItemImage, Discontinued FROM Inventory WHERE Discontinued = 0;";
            ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            while (rs.next()) {
                e = new String[md.getColumnCount() + 1];
                for (int i = 1; i < md.getColumnCount() + 1; i++) {
                    e[i - 1] = rs.getString(i);
                }

                //Several of the arrays positions need to be converted into forms that the arrayList can use.
                int inventoryID = Integer.parseInt(e[0]);
                String itemName = e[1];
                String itemDescription = e[2];
                int categoryID = Integer.parseInt(e[3]);
                int stoneOrGemstone = Integer.parseInt(e[4]);
                String grainSize = null;
                if (e[5] != null) {
                    grainSize = e[5];
                }
                String grainShape = null;
                if (e[6] != null) {
                    grainShape = e[6];
                }
                String heft = null;
                if (e[7] != null) {
                    heft = e[7];
                }
                String semiOrPrecious = null;
                if (e[8] != null) {
                    semiOrPrecious = e[8];
                }
                double hardness = Double.parseDouble(e[9]);
                double stoneSize = Double.parseDouble(e[10]);
                double stoneWeight = Double.parseDouble(e[11]);
                double retailPrice = Double.parseDouble(e[12]);
                double cost = Double.parseDouble(e[13]);
                int quantity = Integer.parseInt(e[14]);
                int restockThreshold = Integer.parseInt(e[15]);
                Blob itemBlob = null;
                byte[] itemImage = null;
                if (e[16] != null) {
                    itemBlob = rs.getBlob("ItemImage");
                    try {
                        itemImage = itemBlob.getBinaryStream(1, itemBlob.length()).readAllBytes();
                    } catch (IOException ex) {
                        System.out.println(ex);
                    }
                }

                //Adds everything
                inventory.add(new Inventory(inventoryID, itemName, itemDescription, categoryID, stoneOrGemstone, grainSize, grainShape, heft, semiOrPrecious, hardness, stoneSize, stoneWeight, retailPrice, cost, quantity, restockThreshold, itemImage));
            }
            if (ManagerView.updatedFromManager) {
                ManagerView.updatedFromManager = false;
                //Updates the inventory for the manager
                ((ManagerView) managerView).updateData();
                //Updates the discount items table for manager
                ManagerView.discountitemsData = ManagerView.getDiscountItemsData();
                DefaultTableModel ditable = (DefaultTableModel) ManagerView.discountitemsjt.getModel();
                ditable.setDataVector(ManagerView.discountitemsData, ManagerView.discountitemsCol);
                managerView.jp.repaint();

            }
        } catch (SQLException ex) {
            System.out.println(ex);
            System.out.println(number);
        }
    };

    static Runnable discountInfo = () -> {
        String e[];
        discount.clear();
        try {
            String query = "SELECT DiscountID, DiscountCode, Description, DiscountLevel, InventoryID, DiscountType, DiscountPercentage, DiscountDollarAmount, StartDate, ExpirationDate FROM Discounts";
            ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            while (rs.next()) {
                e = new String[md.getColumnCount() + 1];
                for (int i = 1; i < md.getColumnCount() + 1; i++) {
                    e[i - 1] = rs.getString(i);
                }

                int discountID = Integer.parseInt(e[0]);
                String discountCode = e[1];
                String discountDescription = e[2];
                int discountLevel = Integer.parseInt(e[3]);
                int inventoryID = 0;
                if (discountLevel == 1) {
                    inventoryID = Integer.parseInt(e[4]);
                }
                int discountType = Integer.parseInt(e[5]);
                double discountPercentage = Double.parseDouble(e[6]);
                double discountDollarAmount = Double.parseDouble(e[7]);
                Date startDate = rs.getDate("StartDate");
                Date expirationDate = rs.getDate("ExpirationDate");

                //Adds everything
                discount.add(new Discount(discountID, discountCode, discountDescription, discountLevel, inventoryID, discountType, discountPercentage, discountDollarAmount, startDate, expirationDate));
            }
            if (ManagerView.updatedFromManager) {
                ManagerView.updatedFromManager = false;
                //Updates the discount table for manager
                ManagerView.discountData = ManagerView.getDiscountData();
                DefaultTableModel dstable = (DefaultTableModel) ManagerView.discountjt.getModel();
                dstable.setDataVector(ManagerView.discountData, ManagerView.discountCol);
                managerView.jp.repaint();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    };

    static Runnable orderInfo = () -> {
        String e[];
        orders.clear();
        try {
            String query = "SELECT OrderID, DiscountID, PersonID, EmployeeID, OrderDate, CC_Number, ExpDate, CCV FROM Orders";
            ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            while (rs.next()) {
                e = new String[md.getColumnCount() + 1];
                for (int i = 1; i < md.getColumnCount() + 1; i++) {
                    e[i - 1] = rs.getString(i);
                }

                int orderID = Integer.parseInt(e[0]);
                int discountID;
                if (e[1] == null) {
                    discountID = 0;
                } else {
                    discountID = Integer.parseInt(e[1]);
                }
                int personID = Integer.parseInt(e[2]);
                int managerID;
                if (e[3] == null) {
                    managerID = 0;
                } else {
                    managerID = Integer.parseInt(e[3]);
                }
                Date orderDate = Date.valueOf(e[4]);
                long ccNumber = Long.parseLong(e[5]);
                String expDate = e[6];
                int ccv = Integer.parseInt(e[7]);

                //Adds everything
                orders.add(new Orders(orderID, discountID, personID, managerID, orderDate, ccNumber, expDate, ccv));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    };

    static Runnable orderDetailsInfo = () -> {
        String e[];
        orderDetails.clear();
        try {
            String query = "SELECT OrderID, InventoryID, DiscountID, Quantity FROM OrderDetails";
            ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            while (rs.next()) {
                e = new String[md.getColumnCount() + 1];
                for (int i = 1; i < md.getColumnCount() + 1; i++) {
                    e[i - 1] = rs.getString(i);
                }

                int orderID = Integer.parseInt(e[0]);
                int inventoryID = Integer.parseInt(e[1]);
                int discountID;
                if (e[2] == null) {
                    discountID = 0;
                } else {
                    discountID = Integer.parseInt(e[2]);
                }
                int quantity = Integer.parseInt(e[3]);

                //Adds everything
                orderDetails.add(new OrderDetails(orderID, inventoryID, discountID, quantity));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    };

    static void SetPersonID(String logonName) {
        for (int i = 0; i < person.size(); i++) {
            if (logon.get(i).logonName.equals(logonName)) {
                currentPerson = logon.get(i).personID;
            }
        }
    }

    void helpSystem() {
        Document document = new Document();

        Page page = document.getPages().add();

        page.getParagraphs().add(new TextFragment("Logging In - Please enter a valid username and password and press 'Login' to login\n\nRegistration - If you wish to register, you must press the register button, you must enter all required fields in registration\n\nReset Password - If you wish to reset your password, you must first enter your username"));

        document.save("help.pdf");

        try {
            File pdfFile = new File("help.pdf");
            Desktop.getDesktop().open(pdfFile);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
