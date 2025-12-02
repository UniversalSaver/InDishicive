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
import adapters.favorites.add_favorite.AddFavoriteViewModel;
import adapters.generate_recipe.generate_with_inventory.GenerateWithInventoryController;
import adapters.generate_recipe.generate_with_inventory.GenerateWithInventoryViewModel;
import adapters.generate_recipe.view_recipe_details.ViewRecipeDetailsController;

/**
 * Panel for generating recipes by inventory.
 */
public class GenerateByInventoryPanel extends JPanel implements PropertyChangeListener {

    private final DefaultListModel<String> model = new DefaultListModel<>();
    private final JList<String> list = new JList<>(model);

    private final GenerateWithInventoryViewModel viewModel;
    private final AddFavoriteViewModel addFavoriteViewModel;

    public GenerateByInventoryPanel(GenerateWithInventoryController controller,
                                    GenerateWithInventoryViewModel viewModelParam,
                                    ViewRecipeDetailsController detailsController,
                                    AddFavoriteController addFavoriteController,
                                    AddFavoriteViewModel addFavoriteViewModelParam) {

        this.viewModel = viewModelParam;
        this.addFavoriteViewModel = addFavoriteViewModelParam;

        setLayout(new BorderLayout());

        final JLabel title = new JLabel("Recipes you can make with your inventory");
        final JButton generate = new JButton("Generate Recipes");

        final JPanel top = new JPanel(new BorderLayout());
        top.add(title, BorderLayout.CENTER);
        top.add(generate, BorderLayout.EAST);
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

        viewModelParam.addPropertyChangeListener(this);
        addFavoriteViewModelParam.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final String name = evt.getPropertyName();

        if ("recipes".equals(name)) {
            @SuppressWarnings("unchecked")
            final List<String> titles = (List<String>) evt.getNewValue();
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
        else if (AddFavoriteViewModel.FAVORITE_ADDED.equals(name)) {
            final String msg = addFavoriteViewModel.getState().getStatusMessage();
            JOptionPane.showMessageDialog(
                    this,
                    msg,
                    "Successfully Added!",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
        else if (AddFavoriteViewModel.FAVORITE_FAILED.equals(name)) {
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
