package geogeniuses;

import java.awt.Color;
import java.awt.*;
import javax.swing.JScrollPane;

public class ScrollPane extends JScrollPane {
    
    ScrollPane() {
        setBackground(Color.BLUE);
        setOpaque(true);
    }
    
    ScrollPane(Component view) {
        super(view);
        getViewport().setBackground(Color.BLUE);
        getViewport().setOpaque(true);
    }
    
}