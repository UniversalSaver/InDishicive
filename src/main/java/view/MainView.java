package view;

import view.inventory.InventoryView;
import view.generate_recipe_view.GenerateByIngredientsPanel;
import javax.swing.*;

public class MainView extends JPanel {
    private final JTabbedPane tabbedPane;

    public MainView() {
        tabbedPane = new JTabbedPane();
        this.add(tabbedPane);
    }

    public void addInventoryTab(InventoryView inventoryView) {
        tabbedPane.addTab("Inventory", inventoryView);
    }
    public void addGenerateByInventoryPanel(JPanel panel) {
        tabbedPane.addTab("Generate with inventory", panel);
    }


    public void addGenerateByIngredientsPanel(GenerateByIngredientsPanel panel) {
        tabbedPane.addTab("Generate by Ingredients", panel);
    }
}
