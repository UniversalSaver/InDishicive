package view.inventory;

import adapters.generate_recipe.generate_with_inventory.GenerateWithInventoryController;
import adapters.generate_recipe.generate_with_inventory.GenerateWithInventoryViewModel;
import adapters.generate_recipe.view_recipe_details.ViewRecipeDetailsController;
import adapters.favorites.add_favorite.AddFavoriteController;
import adapters.favorites.add_favorite.AddFavoriteViewModel;
import adapters.generate_recipe.random_recipe.RandomRecipeController;

import javax.swing.*;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class GenerateByInventoryPanel extends JPanel implements PropertyChangeListener {

    private final DefaultListModel<String> model = new DefaultListModel<>();
    private final JList<String> list = new JList<>(model);

    private final GenerateWithInventoryViewModel viewModel;

    public GenerateByInventoryPanel(GenerateWithInventoryController controller,
                                    GenerateWithInventoryViewModel vm,
                                    ViewRecipeDetailsController detailsController,
                                    AddFavoriteController addFavoriteController,
                                    AddFavoriteViewModel addFavoriteViewModel,
                                    RandomRecipeController randomRecipeController) {

        this.viewModel = vm;

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

        vm.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String name = evt.getPropertyName();

        if ("recipes".equals(name)) {
            @SuppressWarnings("unchecked")
            List<String> titles = (List<String>) evt.getNewValue();
            model.clear();
            titles.forEach(model::addElement);

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
        }
    }
}