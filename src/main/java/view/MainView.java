package view;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import view.generate_recipe_view.GenerateByIngredientsPanel;
import view.inventory.InventoryView;

/**
 * Main application view that contains all feature tabs.
 */
public class MainView extends JPanel {

    private final JTabbedPane tabbedPane;

    /**
     * Creates the main view with an empty tabbed pane.
     */
    public MainView() {
        tabbedPane = new JTabbedPane();
        this.add(tabbedPane);
    }

    /**
     * Adds the inventory tab.
     *
     * @param inventoryView the inventory view panel
     */
    public void addInventoryTab(InventoryView inventoryView) {
        tabbedPane.addTab("Inventory", inventoryView);
    }

    /**
     * Adds the "generate with inventory" tab.
     *
     * @param panel the panel for generating recipes with inventory
     */
    public void addGenerateByInventoryPanel(JPanel panel) {
        tabbedPane.addTab("Generate with inventory", panel);
    }

    /**
     * Adds the "generate by ingredients" tab.
     *
     * @param panel the panel for generating recipes by ingredients
     */
    public void addGenerateByIngredientsPanel(GenerateByIngredientsPanel panel) {
        tabbedPane.addTab("Generate by Ingredients", panel);
    }
}
