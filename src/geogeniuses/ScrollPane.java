package geogeniuses;

import java.awt.Color;
import java.awt.*;
import javax.swing.JScrollPane;

/**
 * The scroll pane class changes the background color and opacity of the basic
 * JScrollPane class to be more in line with the program's style.
 * @author David Bowen
 */
public class ScrollPane extends JScrollPane {
    
    Color lightCyan = Color.decode("#DFFDFF");
    
    ScrollPane() {
        setBackground(lightCyan);
        setOpaque(true);
    }
    
    ScrollPane(Component view) {
        super(view);
        getViewport().setBackground(lightCyan);
        getViewport().setOpaque(true);
    }
    
}