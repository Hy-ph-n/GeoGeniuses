package geogeniuses;

import javax.swing.*;
import java.awt.Color;
import java.sql.*;

/**
 * The ResetPassword view allows a user, chosen by their unique login name, to
 * change the password associated with their account by answering security
 * questions.
 * @author David Bowen
 */
public class ResetPassword extends State {

    static JLabel securityQuestion1;
    static JLabel securityQuestion2;
    static JLabel securityQuestion3;

    static int questionID1;
    static int questionID2;
    static int questionID3;

    JTextField securityAnswer1;
    JTextField securityAnswer2;
    JTextField securityAnswer3;

    JLabel answerError1;
    JLabel answerError2;
    JLabel answerError3;

    JLabel passLabel;
    JPasswordField passwordField;
    JLabel passError;
    JLabel specialError;

    JLabel validationLabel;
    JPasswordField passwordValidationField;
    JLabel validationError;

    static JButton confirmAnswersButton;
    static JButton confirmNewPasswordButton;

    static int currentPerson = LoginView.currentPerson;

    ResetPassword() {

        Color lightCyan = Color.decode("#DFFDFF");
        Color thistle = Color.decode("#D5CBE2");
        jp.setBackground(lightCyan);

        securityQuestion1 = new JLabel("");
        securityQuestion1.setBounds(200, 35, 300, 15);
        jp.add(securityQuestion1);

        securityQuestion2 = new JLabel("");
        securityQuestion2.setBounds(200, 105, 300, 15);
        jp.add(securityQuestion2);

        securityQuestion3 = new JLabel("");
        securityQuestion3.setBounds(200, 175, 300, 15);
        jp.add(securityQuestion3);

        securityAnswer1 = new JTextField("");
        securityAnswer1.setBounds(200, 50, 200, 20);
        jp.add(securityAnswer1);

        securityAnswer2 = new JTextField("");
        securityAnswer2.setBounds(200, 120, 200, 20);
        jp.add(securityAnswer2);

        securityAnswer3 = new JTextField("");
        securityAnswer3.setBounds(200, 190, 200, 20);
        jp.add(securityAnswer3);

        answerError1 = new JLabel("");
        answerError1.setBounds(400, 49, 200, 20);
        answerError1.setForeground(Color.red);
        jp.add(answerError1);

        answerError2 = new JLabel("");
        answerError2.setBounds(400, 119, 200, 20);
        answerError2.setForeground(Color.red);
        jp.add(answerError2);

        answerError3 = new JLabel("");
        answerError3.setBounds(400, 189, 200, 20);
        answerError3.setForeground(Color.red);
        jp.add(answerError3);

        passLabel = new JLabel("Password");
        passLabel.setBounds(200, 245, 300, 15);
        jp.add(passLabel);

        passwordField = new JPasswordField("");
        passwordField.setBounds(200, 260, 200, 20);
        jp.add(passwordField);

        passError = new JLabel("");
        passError.setBounds(400, 262, 400, 15);
        passError.setForeground(Color.red);
        jp.add(passError);

        specialError = new JLabel("");
        specialError.setBounds(400, 268, 400, 15);
        specialError.setForeground(Color.red);
        jp.add(specialError);

        validationLabel = new JLabel("Validate Password");
        validationLabel.setBounds(200, 315, 300, 15);
        jp.add(validationLabel);

        passwordValidationField = new JPasswordField("");
        passwordValidationField.setBounds(200, 330, 200, 20);
        jp.add(passwordValidationField);

        validationError = new JLabel("");
        validationError.setBounds(400, 332, 400, 15);
        validationError.setForeground(Color.red);
        jp.add(validationError);

        confirmAnswersButton = new JButton("Confirm Answers");
        confirmAnswersButton.setBounds(225, 400, 150, 50);
        confirmAnswersButton.setBackground(thistle);
        confirmAnswersButton.addActionListener((e) -> {
            boolean validUser = true;
            answerError1.setText("");
            answerError2.setText("");
            answerError3.setText("");

            try {
                /*
                Collects challenge answers for comparison purposes, also makes them all uppercase so there is no case sensitivity
                */
                String query = "SELECT FirstChallengeAnswer FROM Logon WHERE PersonID = " + currentPerson + ";";
                ps = con.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                rs.next();

                String correctAnswer1 = rs.getString(1);
                correctAnswer1 = correctAnswer1.toUpperCase();

                query = "SELECT SecondChallengeAnswer FROM Logon WHERE PersonID = " + currentPerson + ";";
                ps = con.prepareStatement(query);
                rs = ps.executeQuery();
                rs.next();

                String correctAnswer2 = rs.getString(1);
                correctAnswer2 = correctAnswer2.toUpperCase();

                query = "SELECT ThirdChallengeAnswer FROM Logon WHERE PersonID = " + currentPerson + ";";
                ps = con.prepareStatement(query);
                rs = ps.executeQuery();
                rs.next();

                String correctAnswer3 = rs.getString(1);
                correctAnswer3 = correctAnswer3.toUpperCase();

                if (!securityAnswer1.getText().toUpperCase().equals(correctAnswer1)) {
                    validUser = false;
                    answerError1.setText("Invalid answer");
                }

                if (!securityAnswer2.getText().toUpperCase().equals(correctAnswer2)) {
                    validUser = false;
                    answerError2.setText("Invalid answer");
                }

                if (!securityAnswer3.getText().toUpperCase().equals(correctAnswer3)) {
                    validUser = false;
                    answerError3.setText("Invalid answer");
                }

                /*
                If the user answered all questions correctly, they can see the new password and validate password fields
                */
                if (validUser) {
                    ShowFields();
                }
            } catch (SQLException ex) {
                System.out.println(e);
            }
        }
        );
        jp.add(confirmAnswersButton);

        confirmNewPasswordButton = new JButton("Confirm Password");
        confirmNewPasswordButton.setBounds(225, 400, 150, 50);
        confirmNewPasswordButton.setBackground(thistle);
        confirmNewPasswordButton.addActionListener((e) -> {
            NewPassword();
        }
        );
        jp.add(confirmNewPasswordButton);

        JButton returnButton = new JButton("Return");
        returnButton.setBounds(500, 400, 100, 50);
        returnButton.setBackground(thistle);
        returnButton.addActionListener((e) -> {
            /*
            Hides password and validate password fields
            */
            HideFields();

            /*
            Resets all entries to their default states
            */
            Reset();
            
            jp.remove(connectionStatus);
            connectionStatus = new JLabel("");
            connectionStatus.setBounds(5, 445, 200, 15);
            connectionStatus.setForeground(Color.red);
            loginView.jp.add(connectionStatus);

            /*
            Sets the current person back to nothing
            */
            currentPerson = 0;
            LoginView.currentPerson = 0;

            /*
            A switch to the login view
            */
            jf.setTitle("Login");
            jp.setVisible(false);
            jf.remove(jp);
            jf.add(loginView.jp);
            jf.setBounds(550, 200, 800, 500);
            loginView.jp.setVisible(true);
        }
        );
        jp.add(returnButton);

        /*
        Hides password and validate password fields
        */
        HideFields();
    }

    /**
     * Collects all questions tied to the associated customer resetting their password
     * @return void
     */
    static void CollectQuestions() {

        try {
            String query = "SELECT FirstChallengeQuestion FROM Logon WHERE PersonID = " + currentPerson + ";";
            ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            rs.next();

            questionID1 = rs.getInt(1);

            query = "SELECT QuestionPrompt FROM SecurityQuestions WHERE QuestionID = " + questionID1 + ";";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            rs.next();
            securityQuestion1.setText(rs.getString(1));

            query = "SELECT SecondChallengeQuestion FROM Logon WHERE PersonID = " + currentPerson + ";";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            rs.next();

            questionID2 = rs.getInt(1);

            query = "SELECT QuestionPrompt FROM SecurityQuestions WHERE QuestionID = " + questionID2 + ";";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            rs.next();
            securityQuestion2.setText(rs.getString(1));

            query = "SELECT ThirdChallengeQuestion FROM Logon WHERE PersonID = " + currentPerson + ";";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            rs.next();

            questionID3 = rs.getInt(1);

            query = "SELECT QuestionPrompt FROM SecurityQuestions WHERE QuestionID = " + questionID3 + ";";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            rs.next();
            securityQuestion3.setText(rs.getString(1));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Shows all fields involved in creating and validating a new password while
     * hiding all fields relating to confirming answers.
     * @return void
     */
    void ShowFields() {
        passLabel.setVisible(true);
        passwordField.setVisible(true);
        passError.setVisible(true);
        specialError.setVisible(true);
        validationLabel.setVisible(true);
        passwordValidationField.setVisible(true);
        validationError.setVisible(true);
        confirmAnswersButton.setVisible(false);
        confirmNewPasswordButton.setVisible(true);
    }

    /**
     * Hides all fields involved in creating and validating a new password while
     * showing all fields relating to confirming answers.
     * @return void
     */
    void HideFields() {
        passLabel.setVisible(false);
        passwordField.setVisible(false);
        passError.setVisible(false);
        specialError.setVisible(false);
        validationLabel.setVisible(false);
        passwordValidationField.setVisible(false);
        validationError.setVisible(false);
        confirmAnswersButton.setVisible(true);
        confirmNewPasswordButton.setVisible(false);
    }

    /**
     * Resets all fields to their default states
     * @return void
     */
    void Reset() {
        securityAnswer1.setText("");
        securityAnswer2.setText("");
        securityAnswer3.setText("");
        answerError1.setText("");
        answerError2.setText("");
        answerError3.setText("");
        passwordField.setText("");
        passError.setText("");
        specialError.setText("");
        passwordValidationField.setText("");
        validationError.setText("");
    }

    /**
     * Creates a new password as long as one has been entered and validated
     * @return void
     */
    void NewPassword() {
        boolean valid = true;
        String newPassword = new String(passwordField.getPassword());
        passError.setText("");
        specialError.setText("");
        String validatePassword = new String(passwordValidationField.getPassword());
        validationError.setText("");

        /*
        If statement ensuring the user entered a password
        */
        if (!newPassword.isEmpty()) {
            boolean whitespace = false;
            /*
            Removes any whitespaces from the beginning or end of the password
            */
            newPassword = newPassword.trim();
            passwordField.setText(newPassword);

            /*
            Ensures no whitespaces are present in the password
            */
            for (char c : newPassword.toCharArray()) {
                if (Character.isWhitespace(c)) {
                    whitespace = true;
                }
            }

            if (!whitespace) {
                /*
                If statement ensuring the password is not less than 8 characters or more than 20
                */
                if (newPassword.length() < 8) {
                    valid = false;
                    passError.setText("Must be at least 7 characters");
                } else if (newPassword.length() > 20) {
                    valid = false;
                    passError.setText("Cannot be more than 20 characters");
                } else {
                    int requirementsMet = 0;
                    char c;
                    boolean uppercasePresent = false;
                    boolean lowercasePresent = false;
                    boolean numberPresent = false;
                    boolean specialPresent = false;
                    for (int i = 0; i < newPassword.length(); i++) {
                        c = newPassword.charAt(i);
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
                        passError.setBounds(400, 256, 400, 15);
                        passError.setText("Requires capital and/or lowercase letter");
                        specialError.setText("Requires special char and/or number");
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

        /*
        If statement ensuring the user entered a validation password
        */
        if (!validatePassword.isEmpty()) {
            /*
            Removes any whitespaces from the beginning or end of the validation password
            */
            validatePassword = validatePassword.trim();
            passwordValidationField.setText(validatePassword);

            if (!validatePassword.equals(newPassword)) {
                valid = false;
                validationError.setText("Password confirmation must match");
            }
        } else {
            valid = false;
            validationError.setText("Must validate password");
        }

        if (valid) {
            try {
                String updatePassword = "UPDATE Logon SET Password = '" + newPassword + "' WHERE PersonID = " + currentPerson + ";";
                ps = con.prepareStatement(updatePassword);
                int rows = ps.executeUpdate();
                System.out.println("Rows affected: " + rows);

                Thread logonData = new Thread(LoginView.logonInfo);
                logonData.start();

                /*
                Hides password and validate password fields
                */
                HideFields();

                /*
                Resets all entries to their default states
                */
                Reset();

                /*
                Sets the current person back to nothing
                */
                currentPerson = 0;
                LoginView.currentPerson = 0;

                /*
                A switch to the login view
                */
                jf.setTitle("Login");
                jp.setVisible(false);
                jf.remove(jp);
                jf.add(loginView.jp);
                jf.setBounds(550, 200, 800, 500);
                loginView.jp.setVisible(true);
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

}
