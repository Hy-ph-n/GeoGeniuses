package geogeniuses;

import java.sql.*;
import javax.swing.*;
import java.awt.Color;

//Contains all the classes.
abstract class State {

    static State loginView, registerView, resetPasswordView, customerView, managerView;
    
    static JFrame jf = new JFrame();
    
    JPanel jp = new JPanel(null);
    
    static Connection con = null;
    
    static PreparedStatement ps = null;

}