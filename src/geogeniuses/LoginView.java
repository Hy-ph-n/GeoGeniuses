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

public class LoginView extends State {

    //Creates an arraylist of people and items.
    static ArrayList<Person> person = new ArrayList();
    static ArrayList<Logon> logon = new ArrayList();
    static ArrayList<Inventory> inventory = new ArrayList();

    static int currentPerson = 0;

    JTextField logName;
    JPasswordField logPassword;

    JLabel logonError;

    boolean loginValid = true;

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
            String positionTitle = "None";
            String loginName = logName.getText();
            String loginPassword = new String(logPassword.getPassword());
            logonError.setText("");
            if (!loginName.isEmpty() && !loginPassword.isEmpty()) {
                if (loginValid) {
                    try {
                        String query = "SELECT PersonID, PositionTitle FROM Logon WHERE LogonName = '" + loginName + "';";
                        ps = con.prepareStatement(query);
                        ResultSet rs = ps.executeQuery();
                        rs.next();
                        positionTitle = rs.getString("PositionTitle");

                        //A for loop that will test to see if the name and password match up with a manager or customer.
                        if (!positionTitle.equals("None")) {
                            boolean validPerson = false;
                            for (int i = 0; i < logon.size(); i++) {
                                try {
                                    if (loginName.equals(logon.get(i).logonName) && loginPassword.equals(logon.get(i).password) && person.get(i).personDeleted != 1) {
                                        validPerson = true;

                                        SetPersonID(loginName);

                                        //Resets the login name and login password fields to their default states
                                        logName.setText("");
                                        logPassword.setText("");

                                        if (positionTitle.equals("Manager")) {
                                            //A switch to the the manager's view
                                            //jf.setTitle("Manager View");
                                            //jp.setVisible(false);
                                            //jf.remove(jp);
                                            //jf.setBounds(jf.getX(), jf.getY(), 1216, 610);
                                            //jf.add(managerView.jp);
                                            //managerView.jp.setVisible(true);
                                            JOptionPane.showMessageDialog(null, "Hi", "Type of Account: Manager", JOptionPane.INFORMATION_MESSAGE);
                                        }

                                        if (positionTitle.equals("Employee")) {
                                            //A switch to the the employee's view
                                            //jf.setTitle("Employee View");
                                            //jp.setVisible(false);
                                            //jf.remove(jp);
                                            //jf.setBounds(jf.getX(), jf.getY(), 1216, 610);
                                            //jf.add(employeeView.jp);
                                            //employeeView.jp.setVisible(true);
                                            JOptionPane.showMessageDialog(null, "Hi", "Type of Account: Employee", JOptionPane.INFORMATION_MESSAGE);
                                        }

                                        if (positionTitle.equals("Customer")) {
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
                    } catch (SQLException ex) {
                        logonError.setText("User not found");
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
            CustomerView.cvvEntry.setVisible(false);
            CustomerView.cardCVV.setVisible(false);
            CustomerView.cardExpireYear.setVisible(false);
            CustomerView.cardExpirationYear.setVisible(false);
            CustomerView.cardExpireMonth.setVisible(false);
            CustomerView.cardExpirationMonth.setVisible(false);
            CustomerView.discountEntry.setVisible(false);
            CustomerView.discountCode.setVisible(false);
            CustomerView.checkout.setVisible(false);
            CustomerView.clearCart.setVisible(false);

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

            jf.setTitle("Register");
            jp.setVisible(false);
            jf.remove(jp);
            jf.add(registerView.jp);
            jf.setBounds(jf.getX(), jf.getY(), 820, 523);
            registerView.jp.setVisible(true);
        }
        );
        jp.add(registerButton);

        JButton resetPasswordButton = new JButton("Reset Password");
        resetPasswordButton.setBounds(310, 355, 130, 50);
        resetPasswordButton.setBackground(thistle);
        resetPasswordButton.addActionListener((e) -> {
            resetPassword = true;
            String loginName = logName.getText();

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
        person.clear();
        logon.clear();

        try {
            if (con != null & !con.isClosed()) {
                Thread personData = new Thread(personInfo);
                personData.start();

                Thread logonData = new Thread(logonInfo);
                logonData.start();

                Thread inventoryData = new Thread(inventoryInfo);
                inventoryData.start();
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    Runnable personInfo = () -> {
        String e[];
        try {
            String query = "SELECT PersonID, Title, NameFirst, NameMiddle, NameLast, Suffix, Address1, Address2, Address3, City, Zipcode, State, Email, PhonePrimary, PhoneSecondary, PersonDeleted FROM Person";
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
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    };

    static Runnable logonInfo = () -> {
        String e[];
        try {
            String query = "SELECT PersonID, LogonName, Password FROM Logon";
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

                //Adds everything
                logon.add(new Logon(personID, logonName, password));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    };

    static Runnable inventoryInfo = () -> {
        String e[];
        try {
            Blob imgBlob = null;

            String query = "SELECT InventoryID, ItemName, ItemDescription, CategoryID, StoneOrGemstone, GrainSize, GrainShape, Heft, SemiOrPrecious, Hardness, RetailPrice, Cost, Quantity, RestockThreshold, Discontinued FROM Inventory";
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
                double retailPrice = Double.parseDouble(e[10]);
                double cost = Double.parseDouble(e[11]);
                int quantity = Integer.parseInt(e[12]);
                int restockThreshold = Integer.parseInt(e[13]);
                int discontinued = Integer.parseInt(e[14]);

                //Adds everything
                inventory.add(new Inventory(inventoryID, itemName, itemDescription, categoryID, stoneOrGemstone, grainSize, grainShape, heft, semiOrPrecious, hardness, retailPrice, cost, quantity, restockThreshold, discontinued));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    };

    static void SetPersonID(String logonName) {
        try {
            String query = "SELECT PersonID FROM Logon WHERE LogonName = '" + logonName + "';";
            ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            rs.next();
            currentPerson = Integer.parseInt(rs.getString(1));
        } catch (SQLException e) {
            resetPassword = false;
        } catch (Exception e) {
            resetPassword = false;
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
