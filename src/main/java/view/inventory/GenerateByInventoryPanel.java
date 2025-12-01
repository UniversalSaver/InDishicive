package view.inventory;

import adapters.favorites.add_favorite.AddFavoriteViewModel;
import adapters.generate_recipe.filter_by_cuisine.FilterByCuisineController;
import adapters.generate_recipe.generate_with_inventory.GenerateWithInventoryController;
import adapters.generate_recipe.generate_with_inventory.GenerateWithInventoryViewModel;
import adapters.generate_recipe.view_recipe_details.ViewRecipeDetailsController;
import adapters.favorites.add_favorite.AddFavoriteController;
import adapters.generate_recipe.random_recipe.RandomRecipeController;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * The panel for generating from inventory.
 */
public class GenerateByInventoryPanel extends JPanel implements PropertyChangeListener {

    private final DefaultListModel<String> model = new DefaultListModel<>();
    private final JList<String> list = new JList<>(model);

    private final GenerateWithInventoryViewModel viewModel;
    private final AddFavoriteViewModel addFavoriteViewModel;

    private final FilterByCuisineController filterController;
    private final JComboBox<String> cuisineBox = new JComboBox<>();
    private final JButton applyFilter = new JButton("Filter");

    public GenerateByInventoryPanel(GenerateWithInventoryController controller,
                                    GenerateWithInventoryViewModel vm,
                                    ViewRecipeDetailsController detailsController,
                                    AddFavoriteController addFavoriteController,
                                    RandomRecipeController randomRecipeController,
                                    FilterByCuisineController filterController,
                                    AddFavoriteViewModel addFavoriteViewModel) {

        this.viewModel = vm;
        this.addFavoriteViewModel = addFavoriteViewModel;
        this.filterController = filterController;

        setLayout(new BorderLayout());

        JLabel title = new JLabel("Recipes you can make with your inventory");

        JButton generate = new JButton("Generate Recipes");
        JButton randomButton = new JButton("Random Recipe"); // New Button

        JPanel top = new JPanel(new BorderLayout());
        top.add(title, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(randomButton);
        buttonsPanel.add(generate);

        top.add(buttonsPanel, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        JPanel filterBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        filterBar.add(new JLabel("Cuisine:"));
        filterBar.add(cuisineBox);
        filterBar.add(applyFilter);

        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.add(top, BorderLayout.NORTH);
        topContainer.add(filterBar, BorderLayout.SOUTH);

        add(topContainer, BorderLayout.NORTH);

        add(new JScrollPane(list), BorderLayout.CENTER);

        JButton details = new JButton("View Details");
        details.setEnabled(false);

        JButton addToFavorites = new JButton("Add to Favorites");
        addToFavorites.setEnabled(false);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add((addToFavorites));
        bottom.add(details);
        add(bottom, BorderLayout.SOUTH);

        generate.addActionListener(e -> controller.execute());

        randomButton.addActionListener(e -> randomRecipeController.execute());

        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                details.setEnabled(list.getSelectedValue() != null);
                addToFavorites.setEnabled(list.getSelectedValue() != null);
            }
        });

        details.addActionListener(e -> {
            String selected = list.getSelectedValue();
            if (selected != null) {
                detailsController.execute(selected);
            }
        });

        addToFavorites.addActionListener(e -> {
            String selected = list.getSelectedValue();
            if (selected != null) {
                addFavoriteController.execute(selected);
            }
        });

        applyFilter.addActionListener(e -> {
            Object choice = cuisineBox.getSelectedItem();
            String cuisine = (choice == null) ? "Any" : choice.toString().trim();
            filterController.execute(cuisine);
        });

        vm.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String name = evt.getPropertyName();

        if ("recipes".equals(name)) {
            @SuppressWarnings("unchecked")
            List<String> titles = (List<String>) evt.getNewValue();
            model.clear();
            if (titles != null) {
                titles.forEach(model::addElement);
            }

        } else if ("error".equals(name)) {
            String msg = viewModel.getErrorMessage();
            if (msg != null && !msg.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        msg,
                        "No Recipes Available",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
            model.clear();

        } else if ("cuisines".equals(name)) {
            List<String> cuisines = viewModel.getCuisines();
            cuisineBox.removeAllItems();
            if (cuisines != null) {
                for (String c : cuisines) {
                    cuisineBox.addItem(c);
                }
                if (cuisines.contains("Any")) {
                    cuisineBox.setSelectedItem("Any");
                } else if (!cuisines.isEmpty()) {
                    cuisineBox.setSelectedIndex(0);
                }
            }

        } else if (AddFavoriteViewModel.FAVORITE_ADDED.equals(name)) {
            String msg = addFavoriteViewModel.getState().getStatusMessage();
            JOptionPane.showMessageDialog(
                    this,
                    msg,
                    "Successfully Added!",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } else if (AddFavoriteViewModel.FAVORITE_FAILED.equals(name)) {
            String msg = addFavoriteViewModel.getState().getStatusMessage();
            JOptionPane.showMessageDialog(
                    this,
                    msg,
                    "Adding to Favorites Failed!",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }
}