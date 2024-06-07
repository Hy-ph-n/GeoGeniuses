package geogeniuses;

import javax.swing.*;
import javax.swing.JButton;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.awt.Color;
import java.sql.SQLException;
import java.util.Random;

public class Register extends State {

    JTextField registerLogName;
    JLabel logNameError;

    JTextField registerFirstName;
    JTextField registerMiddleName;
    JTextField registerLastName;
    JLabel firstNameError;
    JLabel lastNameError;

    JPasswordField registerPassword;
    JLabel passError;
    JLabel specialError;

    int questionID1;
    int questionID2;
    int questionID3;
    JLabel securityQuestion1;
    JLabel securityQuestion2;
    JLabel securityQuestion3;

    JTextField securityAnswer1;
    JTextField securityAnswer2;
    JTextField securityAnswer3;
    JLabel answerError;

    JTextField addressField1;
    JTextField addressField2;
    JTextField addressField3;
    JLabel addressError;

    JTextField cityField;
    JLabel cityError;

    JTextField zipField;
    JLabel zipError;

    JComboBox stateBox;

    JTextField emailField;
    JLabel emailError;

    JTextField phonePrimary1;
    JTextField phonePrimary2;
    JTextField phonePrimary3;
    JTextField phoneSecondary1;
    JTextField phoneSecondary2;
    JTextField phoneSecondary3;
    JLabel phoneError1;
    JLabel phoneError2;

    JComboBox prefix;
    JComboBox postfix;

    Register() {
        
        Color lightCyan = Color.decode("#DFFDFF");
        jp.setBackground(lightCyan);

        JLabel pleaseRegister = new JLabel("Login Name");
        pleaseRegister.setBounds(100, 5, pleaseRegister.getPreferredSize().width, pleaseRegister.getPreferredSize().height);
        jp.add(pleaseRegister);

        registerLogName = new JTextField("");
        registerLogName.setBounds(100, 20, 200, 20);
        jp.add(registerLogName);

        JLabel firstNameLabel = new JLabel("First Name");
        firstNameLabel.setBounds(100, 50, firstNameLabel.getPreferredSize().width, firstNameLabel.getPreferredSize().height);
        jp.add(firstNameLabel);

        registerFirstName = new JTextField("");
        registerFirstName.setBounds(100, 65, 200, 20);
        jp.add(registerFirstName);

        JLabel middleNameLabel = new JLabel("Middle Name");
        middleNameLabel.setBounds(300, 50, middleNameLabel.getPreferredSize().width, middleNameLabel.getPreferredSize().height);
        jp.add(middleNameLabel);

        registerMiddleName = new JTextField("");
        registerMiddleName.setBounds(300, 65, 200, 20);
        jp.add(registerMiddleName);

        JLabel lastNameLabel = new JLabel("Last Name");
        lastNameLabel.setBounds(500, 50, lastNameLabel.getPreferredSize().width, lastNameLabel.getPreferredSize().height);
        jp.add(lastNameLabel);

        registerLastName = new JTextField("");
        registerLastName.setBounds(500, 65, 200, 20);
        jp.add(registerLastName);

        JLabel pleasePassword = new JLabel("Password");
        pleasePassword.setBounds(300, 5, pleasePassword.getPreferredSize().width, pleasePassword.getPreferredSize().height);
        jp.add(pleasePassword);

        registerPassword = new JPasswordField("");
        registerPassword.setBounds(300, 20, 200, 20);
        registerPassword.setEchoChar('*');
        jp.add(registerPassword);

        logNameError = new JLabel("");
        logNameError.setBounds(175, 5, 300, 15);
        logNameError.setForeground(Color.red);
        jp.add(logNameError);

        firstNameError = new JLabel("");
        firstNameError.setBounds(175, 50, 300, 15);
        firstNameError.setForeground(Color.red);
        jp.add(firstNameError);

        lastNameError = new JLabel("");
        lastNameError.setBounds(575, 50, 300, 15);
        lastNameError.setForeground(Color.red);
        jp.add(lastNameError);

        passError = new JLabel("");
        passError.setBounds(500, 22, 400, 15);
        passError.setForeground(Color.red);
        jp.add(passError);

        specialError = new JLabel("");
        specialError.setBounds(500, 28, 400, 15);
        specialError.setForeground(Color.red);
        jp.add(specialError);

        JLabel prefixx = new JLabel("Prefix");
        prefixx.setBounds(45, 50, 300, 15);
        jp.add(prefixx);

        String[] preFix = {"", "Mr.", "Ms.", "Mrs.", "Dr."};
        prefix = new JComboBox(preFix);
        prefix.setBounds(45, 64, 55, 20);
        jp.add(prefix);

        JLabel suffix = new JLabel("Suffix");
        suffix.setBounds(700, 50, 300, 15);
        jp.add(suffix);

        String[] sufFix = {"", "Jr.", "Sr.", "I", "II", "III"};
        postfix = new JComboBox(sufFix);
        postfix.setBounds(700, 64, 55, 20);
        jp.add(postfix);

        securityQuestion1 = new JLabel("");
        securityQuestion1.setBounds(100, 230, 200, 15);
        jp.add(securityQuestion1);

        securityQuestion2 = new JLabel("");
        securityQuestion2.setBounds(300, 230, 200, 15);
        jp.add(securityQuestion2);

        securityQuestion3 = new JLabel("");
        securityQuestion3.setBounds(500, 230, 200, 15);
        jp.add(securityQuestion3);

        securityAnswer1 = new JTextField("");
        securityAnswer1.setBounds(100, 247, 200, 20);
        jp.add(securityAnswer1);

        securityAnswer2 = new JTextField("");
        securityAnswer2.setBounds(300, 247, 200, 20);
        jp.add(securityAnswer2);

        securityAnswer3 = new JTextField("");
        securityAnswer3.setBounds(500, 247, 200, 20);
        jp.add(securityAnswer3);

        answerError = new JLabel("");
        answerError.setBounds(305, 264, 200, 20);
        answerError.setForeground(Color.red);
        jp.add(answerError);

        JLabel addressLabel1 = new JLabel("Address 1");
        addressLabel1.setBounds(100, 95, 200, 20);
        jp.add(addressLabel1);

        addressField1 = new JTextField("");
        addressField1.setBounds(100, 112, 200, 20);
        jp.add(addressField1);

        JLabel addressLabel2 = new JLabel("Address 2");
        addressLabel2.setBounds(300, 95, 200, 20);
        jp.add(addressLabel2);

        addressField2 = new JTextField("");
        addressField2.setBounds(300, 112, 200, 20);
        jp.add(addressField2);

        JLabel addressLabel3 = new JLabel("Address 3");
        addressLabel3.setBounds(500, 95, 200, 20);
        jp.add(addressLabel3);

        addressField3 = new JTextField("");
        addressField3.setBounds(500, 112, 200, 20);
        jp.add(addressField3);

        addressError = new JLabel("");
        addressError.setBounds(175, 98, 300, 15);
        addressError.setForeground(Color.red);
        jp.add(addressError);

        JLabel cityLabel = new JLabel("City");
        cityLabel.setBounds(100, 140, 200, 20);
        jp.add(cityLabel);

        cityField = new JTextField("");
        cityField.setBounds(100, 157, 200, 20);
        jp.add(cityField);

        cityError = new JLabel("");
        cityError.setBounds(175, 140, 200, 20);
        cityError.setForeground(Color.red);
        jp.add(cityError);

        JLabel zipLabel = new JLabel("Zip Code");
        zipLabel.setBounds(300, 140, 200, 20);
        jp.add(zipLabel);

        zipField = new JTextField("");
        zipField.setBounds(300, 157, 200, 20);
        jp.add(zipField);

        zipError = new JLabel("");
        zipError.setBounds(375, 140, 200, 20);
        zipError.setForeground(Color.red);
        jp.add(zipError);

        JLabel stateLabel = new JLabel("States");
        stateLabel.setBounds(500, 140, 200, 20);
        jp.add(stateLabel);

        String[] states = {"", "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"};
        stateBox = new JComboBox(states);
        stateBox.setBounds(500, 157, 125, 20);
        jp.add(stateBox);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(100, 185, 200, 20);
        jp.add(emailLabel);

        emailField = new JTextField("");
        emailField.setBounds(100, 202, 200, 20);
        jp.add(emailField);

        emailError = new JLabel("");
        emailError.setBounds(175, 187, 400, 15);
        emailError.setForeground(Color.red);
        jp.add(emailError);

        JLabel phoneLabel1 = new JLabel("Phone #1");
        phoneLabel1.setBounds(300, 185, 200, 20);
        jp.add(phoneLabel1);

        phonePrimary1 = new JTextField("");
        phonePrimary1.setBounds(300, 202, 67, 20);
        jp.add(phonePrimary1);

        phonePrimary2 = new JTextField("");
        phonePrimary2.setBounds(367, 202, 66, 20);
        jp.add(phonePrimary2);

        phonePrimary3 = new JTextField("");
        phonePrimary3.setBounds(433, 202, 67, 20);
        jp.add(phonePrimary3);

        phoneError1 = new JLabel("");
        phoneError1.setBounds(375, 187, 400, 15);
        phoneError1.setForeground(Color.red);
        jp.add(phoneError1);

        JLabel phoneLabel2 = new JLabel("Phone #2");
        phoneLabel2.setBounds(500, 185, 200, 20);
        jp.add(phoneLabel2);

        phoneSecondary1 = new JTextField("");
        phoneSecondary1.setBounds(500, 202, 67, 20);
        jp.add(phoneSecondary1);

        phoneSecondary2 = new JTextField("");
        phoneSecondary2.setBounds(567, 202, 66, 20);
        jp.add(phoneSecondary2);

        phoneSecondary3 = new JTextField("");
        phoneSecondary3.setBounds(633, 202, 67, 20);
        jp.add(phoneSecondary3);

        phoneError2 = new JLabel("");
        phoneError2.setBounds(575, 187, 400, 15);
        phoneError2.setForeground(Color.red);
        jp.add(phoneError2);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(200, 375, 100, 50);
        registerButton.addActionListener((e) -> {
            createNewAccount();
        }
        );
        jp.add(registerButton);

        JButton returnButton = new JButton("Return");
        returnButton.setBounds(500, 375, 100, 50);
        returnButton.addActionListener((e) -> {
            //Resets all entries to their default states
            Reset();

            // Randomizes questions for the next user
            randomizeQuestions();

            //A switch to the login view
            jf.setTitle("Login");
            jp.setVisible(false);
            jf.remove(jp);
            jf.add(loginView.jp);
            jf.setBounds(550, 200, 800, 500);
            loginView.jp.setVisible(true);
        }
        );
        jp.add(returnButton);

        randomizeQuestions();
    }

    void randomizeQuestions() {
        String e[];
        Random rand = new Random();
        int random = rand.nextInt(3) + 1;

        try {
            String query = "SELECT QuestionID, QuestionPrompt FROM SecurityQuestions WHERE SetID = " + random + ";";
            ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            while (rs.next()) {
                e = new String[md.getColumnCount() + 1];
                for (int i = 1; i < md.getColumnCount() + 1; i++) {
                    e[i - 1] = rs.getString(i);
                }

                if (securityQuestion1.getText().equals("")) {
                    //Sets up the question id and security question
                    questionID1 = Integer.parseInt(e[0]);
                    securityQuestion1.setText(e[1]);
                } else if (securityQuestion2.getText().equals("")) {
                    //Sets up the question id and security question
                    questionID2 = Integer.parseInt(e[0]);
                    securityQuestion2.setText(e[1]);
                } else if (securityQuestion3.getText().equals("")) {
                    //Sets up the question id and security question
                    questionID3 = Integer.parseInt(e[0]);
                    securityQuestion3.setText(e[1]);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    void createNewAccount() {
        //A variable testing if a new account can be created. This value
        //only needs to get switched once to prevent account creation.
        boolean valid = true;

        logNameError.setText("");
        passError.setText("");
        passError.setBounds(500, 22, 400, 15);
        specialError.setText("");
        firstNameError.setText("");
        lastNameError.setText("");
        addressError.setText("");
        cityError.setText("");
        zipError.setText("");
        emailError.setText("");
        phoneError1.setText("");
        phoneError2.setText("");
        answerError.setText("");

        String registrationLogonName = registerLogName.getText();
        String registrationFirstName = registerFirstName.getText();
        String registrationMiddleName = registerMiddleName.getText();
        String registrationLastName = registerLastName.getText();
        String registrationPassword = new String(registerPassword.getPassword());
        String answer1 = securityAnswer1.getText();
        String answer2 = securityAnswer2.getText();
        String answer3 = securityAnswer3.getText();
        String address1 = addressField1.getText();
        String city = cityField.getText();
        String zipCode = zipField.getText();
        String state = stateBox.getSelectedItem().toString();
        String email = emailField.getText();
        String phonePrimary = "";
        String primary1 = phonePrimary1.getText();
        String primary2 = phonePrimary2.getText();
        String primary3 = phonePrimary3.getText();
        String phoneSecondary = "";
        String secondary1 = phoneSecondary1.getText();
        String secondary2 = phoneSecondary2.getText();
        String secondary3 = phoneSecondary3.getText();

        //If statement ensuring the user entered a login name
        if (!registrationLogonName.isEmpty()) {
            //If statement ensuring the login name does not begin with a number
            if (!registrationLogonName.matches("^[0-9].*")) {
                //If statement ensuring the login name does not contain any special characters
                boolean specialChar = false;
                for (char c : registrationLogonName.toCharArray()) {
                    if (!Character.isLetterOrDigit(c) && !Character.isWhitespace(c)) {
                        specialChar = true;
                    }
                }
                if (!specialChar) {
                    boolean whitespace = false;
                    //Line of code ensuring the login name does not contain a space at the start or end
                    registrationLogonName = registrationLogonName.trim();
                    registerLogName.setText(registrationLogonName);

                    //Ensures no whitespaces are present in the password
                    for (char c : registrationLogonName.toCharArray()) {
                        if (Character.isWhitespace(c)) {
                            whitespace = true;
                        }
                    }

                    if (!whitespace) {
                        //If statement ensuring the login name is not less than 8 characters or more than 20
                        if (registrationLogonName.length() >= 8 && registrationLogonName.length() <= 20) {
                            //This try statement contains a query that will test if the login name already exists.
                            try {
                                String query = "SELECT LogonName FROM Logon";
                                ps = con.prepareStatement(query);
                                ResultSet rs = ps.executeQuery();
                                while (rs.next()) {
                                    String logN = rs.getString(1);
                                    if (registrationLogonName.equals(logN)) {
                                        logNameError.setText("Already in use");
                                        valid = false;
                                    }
                                }
                            } catch (SQLException ex) {
                            }
                        } else if (registrationLogonName.length() < 8) {
                            valid = false;
                            logNameError.setText("Below 8 letters");
                        } else if (registrationLogonName.length() > 20) {
                            logNameError.setText("Above 20 letters");
                        }
                    } else {
                        valid = false;
                        logNameError.setText("Whitespace present");
                    }
                } else {
                    valid = false;
                    logNameError.setText("No special chars");
                }
            } else {
                valid = false;
                logNameError.setText("Starts with number");
            }
        } else {
            valid = false;
            logNameError.setText("Username required");
        }

        //If statement ensuring the user entered a password
        if (!registrationPassword.isEmpty()) {
            boolean whitespace = false;
            //Removes any whitespaces from the beginning or end of the password
            registrationPassword = registrationPassword.trim();
            registerPassword.setText(registrationPassword);

            //Ensures no whitespaces are present in the password
            for (char c : registrationPassword.toCharArray()) {
                if (Character.isWhitespace(c)) {
                    whitespace = true;
                }
            }

            if (!whitespace) {
                //If statement ensuring the password is not less than 8 characters or more than 20
                if (registrationPassword.length() < 8) {
                    valid = false;
                    passError.setText("Password must be at least 7 characters long");
                } else if (registrationPassword.length() > 20) {
                    valid = false;
                    passError.setText("Password cannot be more than 20 characters long");
                } else {
                    int requirementsMet = 0;
                    char c;
                    boolean uppercasePresent = false;
                    boolean lowercasePresent = false;
                    boolean numberPresent = false;
                    boolean specialPresent = false;
                    for (int i = 0; i < registrationPassword.length(); i++) {
                        c = registrationPassword.charAt(i);
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
                    //A series of if statements to test if the user meets at least 3 out of 4 requirements
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
                        passError.setBounds(500, 16, 400, 15);
                        passError.setText("Password must hold a upper and/or lowercase letter");
                        specialError.setText("Password must hold a number and/or special letter");
                    }
                }
            } else {
                valid = false;
                passError.setText("No whitespace allowed");
            }
        } else {
            valid = false;
            passError.setText("Must enter a password");
        }

        //If statement ensuring the user has entered their first name
        if (registrationFirstName.isEmpty()) {
            valid = false;
            firstNameError.setText("First name required");
        }

        //If statement ensuring the user has entered their last name
        if (registrationLastName.isEmpty()) {
            valid = false;
            lastNameError.setText("Last name required");
        }

        //If statement ensuring the user has entered something for the address
        if (address1.isEmpty()) {
            valid = false;
            addressError.setText("Address required");
        }

        //If statement ensuring the user has entered something for the city
        if (city.isEmpty()) {
            valid = false;
            cityError.setText("City required");
        }

        //If statement ensuring the user has entered something valid for the zip
        if (zipCode.isEmpty()) {
            valid = false;
            zipError.setText("Zip required");
        } else if (zipCode.length() > 10) {
            valid = false;
            zipError.setText("Zip too long");
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
                    // Keeps zip code length at 5 if a hyphen is not present
                    if (zipCode.length() < 5) {
                        valid = false;
                        zipError.setText("Zip too short");
                    } else if (zipCode.length() > 5) {
                        valid = false;
                        zipError.setText("Zip too long");
                    }
                } else if (isHyphen.equals("-")) {
                    // Makes sure the zip code length is long enough to be standard
                    if (hyphenChar == 5) {
                        if (zipCode.length() < 10) {
                            valid = false;
                            zipError.setText("Zip too short");
                        } else if (zipCode.length() > 10) {
                            valid = false;
                            zipError.setText("Zip too long");
                        }
                    } else {
                        valid = false;
                        zipError.setText("Zip invalid");
                    }
                } else {
                    valid = false;
                    zipError.setText("Zip invalid");
                }
            } else {
                valid = false;
                zipError.setText("Zip invalid");
            }
        }

        //If statement ensuring the user has entered a valid email if they entered one
        if (email.isEmpty()) {
            email = null;
        } else {
            if (email.contains("\u0040") && email.contains(".com")) {

            } else {
                valid = false;
                emailError.setText("Email invalid");
            }
        }

        //If statements for the primary phone number (optional) will give warnings if an entry is made but not prevent new account creation
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
                phoneError1.setText("Enter only numbers");
            }
        }

        //If statements for the secondary phone number (optional) will give warnings if an entry is made but not prevent new account creation
        if (!secondary1.isEmpty() || !primary2.isEmpty() || !primary3.isEmpty()) {
            boolean phoneValid = true;
            if (secondary1.length() != 3) {
                phoneValid = false;
            } else {
                for (int i = 1; i < secondary1.length(); i++) {
                    if (!Character.isDigit(primary1.charAt(i))) {
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
                phoneError2.setText("Enter only numbers");
            }
        }

        //If statement ensuring the user has entered something for all three security questions
        if (answer1.isEmpty() || answer2.isEmpty() || answer3.isEmpty()) {
            valid = false;
            answerError.setText("Answer ALL security questions");
        }

        if (valid) {
            int personID = -1;
            String title = prefix.getSelectedItem().toString();
            String suffix = postfix.getSelectedItem().toString();
            String address2 = addressField2.getText();
            String address3 = addressField3.getText();

            try {
                String newPerson = "INSERT INTO Person (Title, NameFirst, NameMiddle, NameLast, Suffix, Address1, Address2, Address3, City, Zipcode, State, Email, PhonePrimary, PhoneSecondary, Image, PersonDeleted) "
                        + "VALUES ('" + title + "', '" + registrationFirstName + "', '" + registrationMiddleName + "', '" + registrationLastName + "', '" + suffix + "', '" + address1 + "', '" + address2 + "', '" + address3 + "', '" + city + "', '" + zipCode + "', '" + state + "', '" + email + "', '" + phonePrimary + "', '" + phoneSecondary + "', '" + null + "', " + 0 + ");";
                ps = con.prepareStatement(newPerson);
                int rows = ps.executeUpdate();
                System.out.println("Rows affected: " + rows);

                String query = "SELECT MAX(PersonID) FROM Person;";
                ps = con.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                rs.next();
                personID = Integer.parseInt(rs.getString(1));

                String newLogon = "INSERT INTO Logon "
                        + "(PersonID, LogonName, Password, FirstChallengeQuestion, FirstChallengeAnswer, SecondChallengeQuestion, SecondChallengeAnswer, ThirdChallengeQuestion, ThirdChallengeAnswer, PositionTitle, AccountDisabled, AccountDeleted)"
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 'Customer', 0, 0)";
                ps = con.prepareStatement(newLogon);
                ps.setInt(1, personID);
                ps.setString(2, registrationLogonName);
                ps.setString(3, registrationPassword);
                ps.setInt(4, questionID1);
                ps.setString(5, answer1.replaceAll("'", "''"));
                ps.setInt(6, questionID2);
                ps.setString(7, answer2.replaceAll("'", "''"));
                ps.setInt(8, questionID3);
                ps.setString(9, answer3.replaceAll("'", "''"));
                rows = ps.executeUpdate();
                System.out.println("Rows affected: " + rows);
            } catch (SQLException ex) {
                System.out.println(ex);
            }

            passError.setText("");
            byte[] image = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09};

            LoginView.person.add(new Person(personID, title, registrationFirstName, registrationMiddleName, registrationLastName, suffix, address1, address2, address3, city, zipCode, state, email, phonePrimary, phoneSecondary, image, 0));
            LoginView.logon.add(new Logon(personID, registrationLogonName, registrationPassword));

            // Randomizes questions for the next user
            randomizeQuestions();

            //Resets all entries to their default states
            Reset();

            //Updates the inventory for the customer
            //((CustomerView) customerView).updateData();
            //A switch to the customer's view
            //jf.setTitle("Customer View");
            //jp.setVisible(false);
            //jf.remove(jp);
            //jf.add(customerView.jp);
            //jf.setBounds(jf.getX(), jf.getY(), 1050, 523);
            //customerView.jp.setVisible(true);
            JOptionPane.showMessageDialog(null, "Hi", "Type of Account: Customer", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    void Reset() {
        registerLogName.setText("");
        logNameError.setText("");
        registerPassword.setText("");
        passError.setText("");
        prefix.setSelectedItem("");
        registerFirstName.setText("");
        firstNameError.setText("");
        lastNameError.setText("");
        registerMiddleName.setText("");
        registerLastName.setText("");
        postfix.setSelectedItem("");
        addressField1.setText("");
        addressField2.setText("");
        addressField3.setText("");
        addressError.setText("");
        cityField.setText("");
        cityError.setText("");
        zipField.setText("");
        zipError.setText("");
        stateBox.setSelectedItem("");
        emailField.setText("");
        emailError.setText("");
        phonePrimary1.setText("");
        phonePrimary2.setText("");
        phonePrimary3.setText("");
        phoneSecondary1.setText("");
        phoneSecondary2.setText("");
        phoneSecondary3.setText("");
        phoneError1.setText("");
        phoneError2.setText("");
        questionID1 = 0;
        questionID2 = 0;
        questionID3 = 0;
        securityQuestion1.setText("");
        securityQuestion2.setText("");
        securityQuestion3.setText("");
        securityAnswer1.setText("");
        securityAnswer2.setText("");
        securityAnswer3.setText("");
        answerError.setText("");
    }
}