package geogeniuses;

import java.awt.Color;
import java.awt.*;
import javax.swing.JScrollPane;

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