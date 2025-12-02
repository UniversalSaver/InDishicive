package view.inventory;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import adapters.favorites.add_favorite.AddFavoriteController;
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

    /**
     * Creates a panel for generating recipes by inventory.
     *
     * @param controller           the generate-with-inventory controller
     * @param viewModel            the view model for this use case
     * @param detailsController    controller for viewing recipe details
     * @param addFavoriteController controller for adding favorites
     * @param randomRecipeController controller for random recipe generation
     */
    public GenerateByInventoryPanel(GenerateWithInventoryController controller,
                                    GenerateWithInventoryViewModel viewModel,
                                    ViewRecipeDetailsController detailsController,
                                    AddFavoriteController addFavoriteController,
                                    RandomRecipeController randomRecipeController) {

        this.viewModel = viewModel;

        setLayout(new BorderLayout());

        final JLabel title = new JLabel("Recipes you can make with your inventory");

        final JButton generate = new JButton("Generate Recipes");
        final JButton randomButton = new JButton("Random Recipe");

        final JPanel top = new JPanel(new BorderLayout());
        top.add(title, BorderLayout.CENTER);

        final JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(randomButton);
        buttonsPanel.add(generate);

        top.add(buttonsPanel, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        add(new JScrollPane(list), BorderLayout.CENTER);

        final JButton details = new JButton("View Details");
        details.setEnabled(false);

        final JButton addToFavorites = new JButton("Add to Favorites");
        addToFavorites.setEnabled(false);

        final JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(addToFavorites);
        bottom.add(details);
        add(bottom, BorderLayout.SOUTH);

        generate.addActionListener(event -> controller.execute());

        randomButton.addActionListener(event -> randomRecipeController.execute());

        list.addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                details.setEnabled(list.getSelectedValue() != null);
                addToFavorites.setEnabled(list.getSelectedValue() != null);
            }
        });

        details.addActionListener(event -> {
            final String selected = list.getSelectedValue();
            if (selected != null) {
                detailsController.execute(selected);
            }
        });

        addToFavorites.addActionListener(event -> {
            final String selected = list.getSelectedValue();
            if (selected != null) {
                addFavoriteController.execute(selected);
            }
        });

        viewModel.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        final String name = event.getPropertyName();

        if ("recipes".equals(name)) {
            @SuppressWarnings("unchecked")
            final List<String> titles = (List<String>) event.getNewValue();
            model.clear();
            titles.forEach(model::addElement);
        }
        else if ("error".equals(name)) {
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
        }
        }
    }
