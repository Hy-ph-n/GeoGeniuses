package geogeniuses;

import java.sql.*;
import javax.swing.*;

//Contains all the classes.
abstract class State {

    static State loginView, registerView, resetPasswordView, customerView, managerView;
    
    static JFrame jf = new JFrame();
    
    JPanel jp = new JPanel(null);
    
    static JLabel connectionStatus = new JLabel("");
    
    static Connection con = null;
    
    static PreparedStatement ps = null;

}