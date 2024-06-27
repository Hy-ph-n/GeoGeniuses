package geogeniuses;

import java.awt.Image;
import javax.swing.ImageIcon;

public class ItemIcon extends ImageIcon {

    int lodgingImageID = 0;

    ItemIcon(Image image) {
        super(image);
    }
    
    ItemIcon(byte[] byt) {
        super(byt);
    }

}
