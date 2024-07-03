package geogeniuses;

import static geogeniuses.LoginView.inventoryInfo;
import static geogeniuses.State.con;
import static geogeniuses.State.ps;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ManagerView extends State {

    ManagerView() {
        JFileChooser imageChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG & PNG Images", "jpg", "jpeg", "png");
        imageChooser.setFileFilter(filter);
        imageChooser.setMultiSelectionEnabled(true);
        imageChooser.setCurrentDirectory(new java.io.File("Pictures"));
        imageChooser.setDialogTitle("Test");

        JButton addImage = new JButton("Add Image");
        addImage.setBounds(581, 130, 100, 50);
        addImage.addActionListener((e) -> {
            File imageFile = null;
            if (imageChooser.showOpenDialog(addImage) == JFileChooser.APPROVE_OPTION) {
                imageFile = imageChooser.getSelectedFile();
                UploadImage uploadImage = new UploadImage(imageFile);
                uploadImage.start();
            } else {
                //imageError.setText("At least one image must be chosen");
            }
        }
        );
        jp.add(addImage);
    }

    class UploadImage extends Thread {

        File imageFile = null;

        UploadImage(File imageFile) {
            this.imageFile = imageFile;
        }

        @Override
        public void run() {
            try {
                byte[] b = Files.readAllBytes(imageFile.toPath());
                System.out.println(b.length);
                String image = "UPDATE Inventory SET ItemImage = ? WHERE InventoryID = 1;";
                ps = con.prepareStatement(image);
                ps.setBinaryStream(1, new ByteArrayInputStream(b, 0, b.length));
                int rows = ps.executeUpdate();
                System.out.println("Rows modified: " + rows);

                Thread inventoryData = new Thread(inventoryInfo);
                inventoryData.start();
            } catch (SQLException | IOException ex) {
                ex.printStackTrace();
            }
        }
    };
}
