package geogeniuses;

import static geogeniuses.State.con;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.Timer;

//The class is just named after me and what this is. I am David Bowen and this is Advanced Java Programming, Mastery Assessment 6.
public class GeoGeniuses extends State {

    static Timer connectionTimer;

    //The sql link is created here.
    static String mySQLURL = "jdbc:mysql://13.58.236.216:3306/dbowensu24";

    static String userName = "dbowensu24";

    static String password = "cpt_Tstc1";

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
        connectionTimer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    if (con.isClosed()) {
                        con = DriverManager.getConnection(mySQLURL, userName, password);
                    }
                } catch (SQLException ex) {
                    System.out.print(ex);
                }
            }
        });
        connectionTimer.setRepeats(true);
        connectionTimer.start();
    };
}
