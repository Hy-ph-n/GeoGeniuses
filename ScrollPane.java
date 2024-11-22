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
    
    /**
     * Contains the default settings for this program's scroll panes, that being
     * a light cyan background and making it opaque.
     */
    public ScrollPane() {
        setBackground(lightCyan);
        setOpaque(true);
    }
    
    /**
     * References the component view, while also setting the default settings for
     * this program's scroll panes, that being a light cyan background and making
     * it opaque.
     * @param view The component involved in the scroll pane, usually a table
     */
    public ScrollPane(Component view) {
        super(view);
        getViewport().setBackground(lightCyan);
        getViewport().setOpaque(true);
    }
    
}