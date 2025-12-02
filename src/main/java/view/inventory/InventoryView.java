package view.inventory;

import adapters.inventory.add_ingredient.AddIngredientController;
import adapters.inventory.remove_ingredient.RemoveIngredientController;
import adapters.inventory.search_ingredients.SearchIngredientsController;
import adapters.inventory.search_ingredients.SearchIngredientsState;
import adapters.inventory.search_ingredients.SearchIngredientsViewModel;
import databases.inventory.InventoryDataAccessObject;
import entity.Ingredient;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class InventoryView extends JPanel implements PropertyChangeListener {

    private final SearchIngredientsController searchIngredientsController;
    private final AddIngredientController addIngredientController;
    private final RemoveIngredientController removeIngredientController;
    private final SearchIngredientsViewModel searchIngredientsViewModel;
    private final InventoryDataAccessObject inventoryDAO;

    private final JTextField searchField;
    private final DefaultListModel<String> searchResultsModel;
    private final JList<String> searchResultsList;
    private final DefaultListModel<String> inventoryModel;
    private final JList<String> inventoryList;
    private final JTextField amountField;

    public InventoryView(SearchIngredientsController searchIngredientsController,
                         AddIngredientController addIngredientController,
                         RemoveIngredientController removeIngredientController,
                         SearchIngredientsViewModel searchIngredientsViewModel,
                         InventoryDataAccessObject inventoryDAO) {

        this.searchIngredientsController = searchIngredientsController;
        this.addIngredientController = addIngredientController;
        this.removeIngredientController = removeIngredientController;
        this.searchIngredientsViewModel = searchIngredientsViewModel;
        this.inventoryDAO = inventoryDAO;

        this.searchIngredientsViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout(10, 10));

        // Search Panel
        JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Search Ingredients"));

        searchField = new JTextField();
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { search(); }
            public void removeUpdate(DocumentEvent e) { search(); }
            public void insertUpdate(DocumentEvent e) { search(); }
        });

        searchResultsModel = new DefaultListModel<>();
        searchResultsList = new JList<>(searchResultsModel);
        searchResultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        searchPanel.add(new JLabel("Search:"), BorderLayout.NORTH);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(new JScrollPane(searchResultsList), BorderLayout.SOUTH);

        // Add Panel
        JPanel addPanel = new JPanel(new FlowLayout());
        JLabel amountLabel = new JLabel("Amount:");
        amountField = new JTextField(10);
        JButton addButton = new JButton("Add to Inventory");

        addButton.addActionListener(e -> {
            String selected = searchResultsList.getSelectedValue();
            String amount = amountField.getText();

            if (selected != null && !amount.isEmpty()) {
                addIngredientController.execute(selected, amount);
                amountField.setText("");
                updateInventoryList();
                JOptionPane.showMessageDialog(this, "Added: " + selected);
            }
        });

        addPanel.add(amountLabel);
        addPanel.add(amountField);
        addPanel.add(addButton);

        // Inventory Panel
        JPanel inventoryPanel = new JPanel(new BorderLayout(5, 5));
        inventoryPanel.setBorder(BorderFactory.createTitledBorder("My Inventory"));

        inventoryModel = new DefaultListModel<>();
        inventoryList = new JList<>(inventoryModel);
        inventoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JButton removeButton = new JButton("Remove Selected");
        removeButton.addActionListener(e -> {
            String selected = inventoryList.getSelectedValue();
            if (selected != null) {
                String ingredientName = selected.split(" - ")[0];
                removeIngredientController.execute(ingredientName);
                updateInventoryList();
                JOptionPane.showMessageDialog(this, "Removed: " + ingredientName);
            }
        });

        inventoryPanel.add(new JScrollPane(inventoryList), BorderLayout.CENTER);
        inventoryPanel.add(removeButton, BorderLayout.SOUTH);

        // Layout
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(searchPanel, BorderLayout.CENTER);
        leftPanel.add(addPanel, BorderLayout.SOUTH);

        add(leftPanel, BorderLayout.WEST);
        add(inventoryPanel, BorderLayout.CENTER);

        // Initial load
        updateInventoryList();
        searchIngredientsController.execute("");
    }

    private void search() {
        String query = searchField.getText();
        searchIngredientsController.execute(query);
    }

    private void updateInventoryList() {
        inventoryModel.clear();
        for (Ingredient ingredient : inventoryDAO.getAllIngredients()) {
            inventoryModel.addElement(ingredient.getName() + " - " + ingredient.getAmount());
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("searchSuccess".equals(evt.getPropertyName())) {
            SearchIngredientsState state = searchIngredientsViewModel.getState();
            searchResultsModel.clear();
            for (String ingredient : state.getIngredients()) {
                searchResultsModel.addElement(ingredient);
            }
        } else if ("searchFail".equals(evt.getPropertyName())) {
            SearchIngredientsState state = searchIngredientsViewModel.getState();
            JOptionPane.showMessageDialog(this, "Error: " + state.getError());
        }
    }
}

