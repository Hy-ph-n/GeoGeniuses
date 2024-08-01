package geogeniuses;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;

public class ManagerItemSelection extends State implements ActionListener {

    private ArrayList<JButton> items;

    public ManagerItemSelection(ArrayList<JButton> items) {
        this.items = items;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String itemName = event.getActionCommand();
        for (int i = 0; i < LoginView.inventory.size(); i++) {
            if (LoginView.inventory.get(i).itemName.equals(itemName)) {
                ManagerView.itemSelected = i;
            }
        }
        Inventory inventory = LoginView.inventory.get(ManagerView.itemSelected);
        ManagerView.itemsName.setText(inventory.itemName);
        ManagerView.itemsDescription.setText(inventory.itemDescription);
        if (inventory.categoryID == 1) {
            ManagerView.itemIsIgneous.setSelected(true);
            ManagerView.itemIsSedimentary.setSelected(false);
            ManagerView.itemIsMetamorphic.setSelected(false);
        } else if (inventory.categoryID == 2) {
            ManagerView.itemIsIgneous.setSelected(false);
            ManagerView.itemIsSedimentary.setSelected(true);
            ManagerView.itemIsMetamorphic.setSelected(false);
        } else if (inventory.categoryID == 3) {
            ManagerView.itemIsIgneous.setSelected(false);
            ManagerView.itemIsSedimentary.setSelected(false);
            ManagerView.itemIsMetamorphic.setSelected(true);
        }
        if (inventory.stoneOrGemstone == 0) {
            ManagerView.itemIsStone.setSelected(true);
            ManagerView.itemIsGemstone.setSelected(false);
            ManagerView.itemIsPreciousGemstone.setSelected(false);

            ManagerView.grainSizeOfItem.setVisible(true);
            ManagerView.itemIsCoarseGrained.setVisible(true);
            ManagerView.itemIsFineGrained.setVisible(true);
            ManagerView.grainShapeOfItem.setVisible(true);
            ManagerView.itemsGrainShape.setVisible(true);
            ManagerView.heftOfItem.setVisible(false);
            ManagerView.itemsHeft.setVisible(false);
            ManagerView.itemsHeftError.setVisible(false);
            if (inventory.grainSize != null) {
                if (Integer.parseInt(inventory.grainSize) == 0) {
                    ManagerView.itemIsCoarseGrained.setSelected(true);
                    ManagerView.itemIsFineGrained.setSelected(false);
                } else if (Integer.parseInt(inventory.grainSize) == 1) {
                    ManagerView.itemIsCoarseGrained.setSelected(false);
                    ManagerView.itemIsFineGrained.setSelected(true);
                }
            } else {
                ManagerView.itemIsCoarseGrained.setSelected(false);
                ManagerView.itemIsFineGrained.setSelected(false);
            }
            if (inventory.grainShape != null) {
                ManagerView.itemsGrainShape.setText(inventory.grainShape);
                ManagerView.itemsGrainShape.setVisible(true);
            } else {
                ManagerView.itemsGrainShape.setText("");
            }
            ManagerView.itemsHeft.setText("");
        } else if (inventory.stoneOrGemstone == 1) {
            if (Integer.parseInt(inventory.semiOrPrecious) == 0) {
                ManagerView.itemIsStone.setSelected(false);
                ManagerView.itemIsGemstone.setSelected(true);
                ManagerView.itemIsPreciousGemstone.setSelected(false);

                ManagerView.grainSizeOfItem.setVisible(false);
                ManagerView.itemIsCoarseGrained.setVisible(false);
                ManagerView.itemIsFineGrained.setVisible(false);
                ManagerView.grainShapeOfItem.setVisible(false);
                ManagerView.itemsGrainShape.setVisible(false);
                ManagerView.heftOfItem.setVisible(true);
                ManagerView.itemsHeft.setVisible(true);
                ManagerView.itemsHeftError.setVisible(true);
            } else if (Integer.parseInt(inventory.semiOrPrecious) == 1) {
                ManagerView.itemIsStone.setSelected(false);
                ManagerView.itemIsGemstone.setSelected(false);
                ManagerView.itemIsPreciousGemstone.setSelected(true);

                ManagerView.grainSizeOfItem.setVisible(false);
                ManagerView.itemIsCoarseGrained.setVisible(false);
                ManagerView.itemIsFineGrained.setVisible(false);
                ManagerView.grainShapeOfItem.setVisible(false);
                ManagerView.itemsGrainShape.setVisible(false);
                ManagerView.heftOfItem.setVisible(true);
                ManagerView.itemsHeft.setVisible(true);
                ManagerView.itemsHeftError.setVisible(true);
            }
            ManagerView.itemIsCoarseGrained.setSelected(false);
            ManagerView.itemIsFineGrained.setSelected(false);
            ManagerView.itemsGrainShape.setText("");
            ManagerView.itemsHeft.setText(inventory.heft);
        }
        ManagerView.itemsHardness.setText(inventory.hardness + "");
        if (inventory.stoneOrGemstone == 0) {
            ManagerView.sizeOfItem.setText("Size [Centimeters]");
            ManagerView.weightOfItem.setText("Weight [Grams]");
        } else if (inventory.stoneOrGemstone == 1) {
            ManagerView.sizeOfItem.setText("Size [Millimeters]");
            ManagerView.weightOfItem.setText("Weight [Carats]");
        }
        ManagerView.itemsSize.setText(inventory.stoneSize + "");
        ManagerView.itemsWeight.setText(inventory.stoneWeight + "");
        ManagerView.itemsRetail.setText(inventory.retailPrice + "");
        ManagerView.itemsCost.setText(inventory.cost + "");
        ManagerView.itemsQuantity.setText(inventory.quantity + "");
        ManagerView.itemsRestockThreshold.setText(inventory.restockThreshold + "");
        ManagerView.itemsNameError.setText("");
        ManagerView.itemsDescriptionError.setText("");
        ManagerView.itemsHeftError.setText("");
        ManagerView.itemsHardnessError.setText("");
        ManagerView.itemsSizeError.setText("");
        ManagerView.itemsWeightError.setText("");
        ManagerView.itemsRetailError.setText("");
        ManagerView.itemsCostError.setText("");
        ManagerView.itemsQuantityError.setText("");
        ManagerView.itemsRestockThresholdError.setText("");
        ManagerView.itemSelectedWarning.setBounds(268, 445, 150, 15);
        ManagerView.itemSelectedWarning.setForeground(Color.GRAY);
        ManagerView.itemSelectedWarning.setText("Item Selected");
    }
}
