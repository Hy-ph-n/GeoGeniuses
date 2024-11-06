package geogeniuses;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * The item icon class extends image icon and allows a proper display of images
 * associated with items.
 * @author David Bowen
 */
public class ItemIcon extends ImageIcon {

    int lodgingImageID = 0;

    ItemIcon(Image image) {
        super(image);
    }
    
    ItemIcon(byte[] byt) {
        super(byt);
    }

}
