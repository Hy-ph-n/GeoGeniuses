package geogeniuses;

import static geogeniuses.State.con;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.JLabel;
import javax.swing.Timer;

//The class is just named after me and what this is. I am David Bowen and this is Advanced Java Programming, Mastery Assessment 6.
public class GeoGeniuses extends State {

    static Timer connectionTimer;

    //The sql link is created here.
    static String mySQLURL = "jdbc:mysql://13.58.236.216:3306/dbowensu24";

    static String userName = "dbowensu24";

    static String password = "cpt_Tstc1";

    static int delay = 7500;

    public static void main(String[] args) {

        try {
            con = DriverManager.getConnection(GeoGeniuses.mySQLURL, GeoGeniuses.userName, GeoGeniuses.password);
        } catch (SQLException e) {
            System.out.println(e);
        }

        Thread connection = new Thread(ConnectionStatus);
        connection.start();

        //The login state is now in main
        State.loginView = new LoginView();
        State.registerView = new Register();
        State.resetPasswordView = new ResetPassword();
        State.customerView = new CustomerView();
        State.managerView = new ManagerView();

    }

    static Runnable ConnectionStatus = () -> {
        connectionTimer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    delay = 2000;
                    if (con.isValid(2)) {
                        if (!con.isClosed()) {
                            connectionStatus.setText("");
                            LoginView.resetPasswordButton.setEnabled(true);
                            ResetPassword.confirmAnswersButton.setEnabled(true);
                            ResetPassword.confirmNewPasswordButton.setEnabled(true);
                            Register.registerButton.setEnabled(true);
                            CustomerView.checkout.setEnabled(true);
                            ManagerView.addUser.setEnabled(true);
                            ManagerView.editUser.setEnabled(true);
                            ManagerView.deleteUser.setEnabled(true);
                            ManagerView.addItem.setEnabled(true);
                            ManagerView.addImage.setEnabled(true);
                            ManagerView.editItem.setEnabled(true);
                            ManagerView.deleteItem.setEnabled(true);
                            ManagerView.addDiscount.setEnabled(true);
                            ManagerView.editDiscount.setEnabled(true);
                        } else {
                            connectionStatus.setText("Connection closed");
                            con = DriverManager.getConnection(mySQLURL, userName, password);

                            LoginView.resetPasswordButton.setEnabled(false);
                            ResetPassword.confirmAnswersButton.setEnabled(false);
                            ResetPassword.confirmNewPasswordButton.setEnabled(false);
                            Register.registerButton.setEnabled(false);
                            CustomerView.checkout.setEnabled(false);
                            ManagerView.addUser.setEnabled(false);
                            ManagerView.editUser.setEnabled(false);
                            ManagerView.deleteUser.setEnabled(false);
                            ManagerView.addItem.setEnabled(false);
                            ManagerView.addImage.setEnabled(false);
                            ManagerView.editItem.setEnabled(false);
                            ManagerView.deleteItem.setEnabled(false);
                            ManagerView.addDiscount.setEnabled(false);
                            ManagerView.editDiscount.setEnabled(false);
                        }
                    } else {
                        connectionStatus.setText("Invalid connection");
                        con = DriverManager.getConnection(mySQLURL, userName, password);

                        LoginView.resetPasswordButton.setEnabled(false);
                        ResetPassword.confirmAnswersButton.setEnabled(false);
                        ResetPassword.confirmNewPasswordButton.setEnabled(false);
                        Register.registerButton.setEnabled(false);
                        CustomerView.checkout.setEnabled(false);
                        ManagerView.addUser.setEnabled(false);
                        ManagerView.editUser.setEnabled(false);
                        ManagerView.deleteUser.setEnabled(false);
                        ManagerView.addItem.setEnabled(false);
                        ManagerView.addImage.setEnabled(false);
                        ManagerView.editItem.setEnabled(false);
                        ManagerView.deleteItem.setEnabled(false);
                        ManagerView.addDiscount.setEnabled(false);
                        ManagerView.editDiscount.setEnabled(false);
                    }
                } catch (SQLException ex) {
                    connectionStatus.setText("Connection error");
                    try {
                        con = DriverManager.getConnection(mySQLURL, userName, password);
                    } catch (SQLException exe) {
                        LoginView.resetPasswordButton.setEnabled(false);
                        ResetPassword.confirmAnswersButton.setEnabled(false);
                        ResetPassword.confirmNewPasswordButton.setEnabled(false);
                        Register.registerButton.setEnabled(false);
                        CustomerView.checkout.setEnabled(false);
                        ManagerView.addUser.setEnabled(false);
                        ManagerView.editUser.setEnabled(false);
                        ManagerView.deleteUser.setEnabled(false);
                        ManagerView.addItem.setEnabled(false);
                        ManagerView.addImage.setEnabled(false);
                        ManagerView.editItem.setEnabled(false);
                        ManagerView.deleteItem.setEnabled(false);
                        ManagerView.addDiscount.setEnabled(false);
                        ManagerView.editDiscount.setEnabled(false);
                    }
                }
            }
        });
        connectionTimer.setRepeats(true);
        connectionTimer.start();
    };
}
