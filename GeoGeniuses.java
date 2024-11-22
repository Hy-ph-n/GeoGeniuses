package geogeniuses;

import static geogeniuses.State.con;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.Timer;

/**
* The program's title, GeoGeniuses, is used for the java file where main is
* located, though most of the program's code is in the views.
* @author David Bowen
*/
public class GeoGeniuses extends State {

    static Timer connectionTimer;

    /*
    The sql link is created here.
    */
    static String sqliteURL = "jdbc:sqlite:geogeniuses.db";

    static int delay = 7500;

    /**
    * The main class is where the program truly begins. It sets up the database connection and enters
    * our first state, the login state.
    * @param args The standard parameter for main
    */
    public static void main(String[] args) {

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException c) {
            System.out.print(c);
        }

        try {
            con = DriverManager.getConnection(sqliteURL);
        } catch (SQLException e) {
            System.out.println(e);
        }

        Thread connection = new Thread(ConnectionStatus);
        connection.start();

        /*
        The login state is now in main
        */
        State.loginView = new LoginView();
        State.registerView = new Register();
        State.resetPasswordView = new ResetPassword();
        State.customerView = new CustomerView();
        State.managerView = new ManagerView();

    }

    /*
    The ConnectionStatus method is responsible for checking if the user is still using the database.
    If it is off, certain features are disabled.
    */
    static Runnable ConnectionStatus = () -> {
        connectionTimer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    delay = 2000;
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
                        con = DriverManager.getConnection(sqliteURL);

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
                        con = DriverManager.getConnection(sqliteURL);
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
