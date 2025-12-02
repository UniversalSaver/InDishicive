package view.inventory;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import adapters.favorites.add_favorite.AddFavoriteController;
import adapters.favorites.add_favorite.AddFavoriteViewModel;
import adapters.generate_recipe.filter_by_cuisine.FilterByCuisineController;
import adapters.generate_recipe.generate_with_inventory.GenerateWithInventoryController;
import adapters.generate_recipe.generate_with_inventory.GenerateWithInventoryViewModel;
import adapters.generate_recipe.random_recipe.RandomRecipeController;
import adapters.generate_recipe.view_recipe_details.ViewRecipeDetailsController;

/**
 * Panel for generating recipes using the user's inventory.
 */
public class GenerateByInventoryPanel extends JPanel implements PropertyChangeListener {

    private final DefaultListModel<String> model = new DefaultListModel<>();
    private final JList<String> list = new JList<>(model);

    private final GenerateWithInventoryViewModel viewModel;
    private final AddFavoriteViewModel addFavoriteViewModel;

    private final JComboBox<String> cuisineBox = new JComboBox<>();

    public GenerateByInventoryPanel(
            final GenerateWithInventoryController controller,
            final GenerateWithInventoryViewModel viewModel,
            final ViewRecipeDetailsController detailsController,
            final AddFavoriteController addFavoriteController,
            final AddFavoriteViewModel addFavoriteViewModel,
            final FilterByCuisineController filterController,
            final RandomRecipeController randomRecipeController) {

        this.viewModel = viewModel;
        this.addFavoriteViewModel = addFavoriteViewModel;

        setLayout(new BorderLayout());

        // Top: title + buttons + filter bar
        final JLabel title = new JLabel("Recipes you can make with your inventory");

        final JButton generate = new JButton("Generate Recipes");
        final JButton randomButton = new JButton("Random Recipe");

        final JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.add(title, BorderLayout.CENTER);

        final JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(randomButton);
        buttonsPanel.add(generate);
        titleBar.add(buttonsPanel, BorderLayout.EAST);

        final JPanel filterBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        filterBar.add(new JLabel("Cuisine:"));
        filterBar.add(cuisineBox);
        final JButton applyFilter = new JButton("Filter");
        filterBar.add(applyFilter);

        final JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.add(titleBar, BorderLayout.NORTH);
        topContainer.add(filterBar, BorderLayout.SOUTH);
        add(topContainer, BorderLayout.NORTH);

        // Center: results list
        add(new JScrollPane(list), BorderLayout.CENTER);

        // Bottom: actions
        final JButton details = new JButton("View Details");
        details.setEnabled(false);

        final JButton addToFavorites = new JButton("Add to Favorites");
        addToFavorites.setEnabled(false);

        final JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(addToFavorites);
        bottom.add(details);
        add(bottom, BorderLayout.SOUTH);

        // Actions
        generate.addActionListener(e -> controller.execute());
        randomButton.addActionListener(e -> randomRecipeController.execute());

        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                final boolean hasSelection = list.getSelectedValue() != null;
                details.setEnabled(hasSelection);
                addToFavorites.setEnabled(hasSelection);
            }
        });

        details.addActionListener(e -> {
            final String selected = list.getSelectedValue();
            if (selected != null) {
                detailsController.execute(selected);
            }
        });

        addToFavorites.addActionListener(e -> {
            final String selected = list.getSelectedValue();
            if (selected != null) {
                addFavoriteController.execute(selected);
            }
        });

        applyFilter.addActionListener(e -> {
            final Object choice = cuisineBox.getSelectedItem();
            final String cuisine = (choice == null) ? "Any" : choice.toString().trim();
            filterController.execute(cuisine);
        });

        // Listen to updates
        viewModel.addPropertyChangeListener(this);
        addFavoriteViewModel.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(final PropertyChangeEvent event) {
        final String name = event.getPropertyName();

        if ("recipes".equals(name)) {
            @SuppressWarnings("unchecked")
            final List<String> titles = (List<String>) event.getNewValue();
            model.clear();
            if (titles != null) {
                titles.forEach(model::addElement);
            }
            return;
        }

        if ("error".equals(name)) {
            final String msg = viewModel.getErrorMessage();
            if (msg != null && !msg.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        msg,
                        "No Recipes Available",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
            model.clear();
            return;
        }

        if ("cuisines".equals(name)) {
            final List<String> cuisines = viewModel.getCuisines();
            cuisineBox.removeAllItems();
            if (cuisines != null) {
                for (final String c : cuisines) {
                    cuisineBox.addItem(c);
                }
                if (cuisines.contains("Any")) {
                    cuisineBox.setSelectedItem("Any");
                } else if (!cuisines.isEmpty()) {
                    cuisineBox.setSelectedIndex(0);
                }
            }
            return;
        }

        if (AddFavoriteViewModel.FAVORITE_ADDED.equals(name)) {
            final String msg = addFavoriteViewModel.getState().getStatusMessage();
            JOptionPane.showMessageDialog(
                    this,
                    msg,
                    "Successfully Added!",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        if (AddFavoriteViewModel.FAVORITE_FAILED.equals(name)) {
            final String msg = addFavoriteViewModel.getState().getStatusMessage();
            JOptionPane.showMessageDialog(
                    this,
                    msg,
                    "Adding to Favorites Failed!",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }
}
