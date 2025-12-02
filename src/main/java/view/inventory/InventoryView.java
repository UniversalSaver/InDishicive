package view.inventory;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import adapters.inventory.add_ingredient.AddIngredientController;
import adapters.inventory.remove_ingredient.RemoveIngredientController;
import adapters.inventory.search_ingredients.SearchIngredientsController;
import adapters.inventory.search_ingredients.SearchIngredientsState;
import adapters.inventory.search_ingredients.SearchIngredientsViewModel;
import databases.inventory.InventoryDataAccessObject;
import entity.Ingredient;

/**
 * View for managing inventory.
 */
public class InventoryView extends JPanel implements PropertyChangeListener {

    private static final int LAYOUT_GAP = 10;
    private static final int SMALL_GAP = 5;
    private static final int TEXT_FIELD_COLUMNS = 10;

    private final SearchIngredientsController searchIngredientsController;
    private final AddIngredientController addIngredientController;
    private final RemoveIngredientController removeIngredientController;
    private final SearchIngredientsViewModel searchIngredientsViewModel;
    private final InventoryDataAccessObject inventoryDao;

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
                         InventoryDataAccessObject inventoryDao) {

        this.searchIngredientsController = searchIngredientsController;
        this.addIngredientController = addIngredientController;
        this.removeIngredientController = removeIngredientController;
        this.searchIngredientsViewModel = searchIngredientsViewModel;
        this.inventoryDao = inventoryDao;

        this.searchIngredientsViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout(LAYOUT_GAP, LAYOUT_GAP));

        final JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Search Ingredients"));

        searchField = new JTextField();
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent event) {
                search();
            }

            public void removeUpdate(DocumentEvent event) {
                search();
            }

            public void insertUpdate(DocumentEvent event) {
                search();
            }
        });

        searchResultsModel = new DefaultListModel<>();
        searchResultsList = new JList<>(searchResultsModel);
        searchResultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        searchPanel.add(new JLabel("Search:"), BorderLayout.NORTH);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(new JScrollPane(searchResultsList), BorderLayout.SOUTH);

        final JPanel addPanel = new JPanel(new FlowLayout());
        final JLabel amountLabel = new JLabel("Amount:");
        amountField = new JTextField(TEXT_FIELD_COLUMNS);
        final JButton addButton = new JButton("Add to Inventory");

        addButton.addActionListener(event -> {
            final String selected = searchResultsList.getSelectedValue();
            final String amount = amountField.getText();

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

        final JPanel inventoryPanel = new JPanel(new BorderLayout(5, 5));
        inventoryPanel.setBorder(BorderFactory.createTitledBorder("My Inventory"));

        inventoryModel = new DefaultListModel<>();
        inventoryList = new JList<>(inventoryModel);
        inventoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        final JButton removeButton = new JButton("Remove Selected");
        removeButton.addActionListener(event -> {
            final String selected = inventoryList.getSelectedValue();
            if (selected != null) {
                final String ingredientName = selected.split(" - ")[0];
                removeIngredientController.execute(ingredientName);
                updateInventoryList();
                JOptionPane.showMessageDialog(this, "Removed: " + ingredientName);
            }
        });

        inventoryPanel.add(new JScrollPane(inventoryList), BorderLayout.CENTER);
        inventoryPanel.add(removeButton, BorderLayout.SOUTH);

        final JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(searchPanel, BorderLayout.CENTER);
        leftPanel.add(addPanel, BorderLayout.SOUTH);

        add(leftPanel, BorderLayout.WEST);
        add(inventoryPanel, BorderLayout.CENTER);

        searchIngredientsController.execute("");
    }

    private void search() {
        final String query = searchField.getText();
        searchIngredientsController.execute(query);
    }

    private void updateInventoryList() {
        inventoryModel.clear();
        for (Ingredient ingredient : inventoryDao.getAllIngredients()) {
            inventoryModel.addElement(ingredient.getName() + " - " + ingredient.getAmount());
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("searchSuccess".equals(evt.getPropertyName())) {
            final SearchIngredientsState state = searchIngredientsViewModel.getState();
            searchResultsModel.clear();
            for (String ingredient : state.getIngredients()) {
                searchResultsModel.addElement(ingredient);
            }
        }
        else if ("searchFail".equals(evt.getPropertyName())) {
            final SearchIngredientsState state = searchIngredientsViewModel.getState();
            JOptionPane.showMessageDialog(this, "Error: " + state.getError());
        }
    }
}
