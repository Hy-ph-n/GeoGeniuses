package geogeniuses;

import java.sql.*;
import javax.swing.*;

/**
 * State contains all the views associated with the major classes of loginView,
 * registerView, resetPasswordView, customerView, and managerView, as well as
 * some general variables that apply to all the views like the sql connection.
 * @author David Bowen
 */
abstract class State {

    static State loginView, registerView, resetPasswordView, customerView, managerView;
    
    static JFrame jf = new JFrame();
    
    JPanel jp = new JPanel(null);
    
    static JLabel connectionStatus = new JLabel("");
    
    static Connection con = null;
    
    static PreparedStatement ps = null;

}