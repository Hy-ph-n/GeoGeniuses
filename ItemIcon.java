package geogeniuses;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
* The item icon class extends image icon {@link javax.swing.ImageIcon} and allows a proper display of images
* associated with items.
* @author David Bowen
*/
public class ItemIcon extends ImageIcon {

    /**
    * A version of the method that receives images
    * @param image The basic image variable, unmodified
    */
    public ItemIcon(Image image) {
        super(image);
    }
    
    /**
    * A version of the method that receives bytes
    * @param byt The basic byt variable, unmodified
    */
    public ItemIcon(byte[] byt) {
        super(byt);
    }

}
