package view.generate_recipe_view;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import adapters.favorites.add_favorite.AddFavoriteController;
import adapters.favorites.add_favorite.AddFavoriteViewModel;
import adapters.generate_recipe.generate_by_ingredients.GenerateByIngredientsController;
import adapters.generate_recipe.generate_by_ingredients.GenerateByIngredientsViewModel;
import adapters.generate_recipe.view_recipe_details.ViewRecipeDetailsController;

public class GenerateByIngredientsPanel extends JPanel implements PropertyChangeListener {

    private static final int GAP = 5;

    private final GenerateByIngredientsViewModel viewModel;
    private final AddFavoriteViewModel addFavoriteViewModel;

    private final GenerateByIngredientsController generateByIngredientsController;
    private final ViewRecipeDetailsController viewRecipeDetailsController;
    private final AddFavoriteController addFavoriteController;

    private final JTextField ingredientsField;
    private final JList<String> list = new JList<>();

    private List<String> lastSearchedIngredients = List.of();

    public GenerateByIngredientsPanel(GenerateByIngredientsController controller,
                                      GenerateByIngredientsViewModel generateByIngredientsViewModel,
                                      ViewRecipeDetailsController detailsController,
                                      AddFavoriteController addFavoriteController,
                                      AddFavoriteViewModel addFavoriteViewModel) {

        this.viewModel = generateByIngredientsViewModel;
        this.addFavoriteViewModel = addFavoriteViewModel;
        this.generateByIngredientsController = controller;
        this.viewRecipeDetailsController = detailsController;
        this.addFavoriteController = addFavoriteController;

        ingredientsField = new JTextField();

        final JButton generateButton = new JButton("Generate Recipes");
        final JButton detailsButton = new JButton("View Details");
        final JButton addToFavoritesButton = new JButton("Add to Favorites");

        setLayout(new BorderLayout(GAP, GAP));

        configureLayout(generateButton, detailsButton, addToFavoritesButton);
        configureListeners(generateButton, detailsButton, addToFavoritesButton);

        generateByIngredientsViewModel.addPropertyChangeListener(this);
        addFavoriteViewModel.addPropertyChangeListener(this);
    }

    private void configureLayout(JButton generateButton,
                                 JButton detailsButton,
                                 JButton addToFavoritesButton) {

        final JLabel header =
                new JLabel("<html>Generate recipes by ingredients<br/>"
                        + "Enter ingredients separated by commas, e.g. chicken, rice, onion</html>");

        final JPanel top = new JPanel(new BorderLayout(GAP, GAP));
        top.add(header, BorderLayout.NORTH);
        top.add(ingredientsField, BorderLayout.CENTER);
        top.add(generateButton, BorderLayout.EAST);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(list), BorderLayout.CENTER);

        final JPanel bottom = new JPanel(new BorderLayout());
        final JPanel buttonsPanel = new JPanel();

        buttonsPanel.add(addToFavoritesButton);
        buttonsPanel.add(detailsButton);

        bottom.add(buttonsPanel, BorderLayout.EAST);
        add(bottom, BorderLayout.SOUTH);
    }

    private void configureListeners(JButton generateButton,
                                    JButton detailsButton,
                                    JButton addToFavoritesButton) {

        generateButton.addActionListener(event -> onGenerateClicked());

        detailsButton.setEnabled(false);
        addToFavoritesButton.setEnabled(false);

        list.addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                final boolean hasSelection = list.getSelectedValue() != null;
                detailsButton.setEnabled(hasSelection);
                addToFavoritesButton.setEnabled(hasSelection);
            }
        });

        detailsButton.addActionListener(event -> {
            final String selectedTitle = list.getSelectedValue();
            if (selectedTitle != null) {
                viewRecipeDetailsController.execute(selectedTitle);
            }
        });

        addToFavoritesButton.addActionListener(event -> {
            final String selectedTitle = list.getSelectedValue();
            if (selectedTitle != null) {
                addFavoriteController.execute(selectedTitle);
            }
        });
    }

    private void onGenerateClicked() {
        final String text = ingredientsField.getText();
        final List<String> ingredients = Arrays.stream(text.split(","))
                .map(String::trim)
                .filter(ingredientText -> !ingredientText.isEmpty())
                .toList();

        if (ingredients.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please add at least one ingredient.",
                    "No ingredients",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
        else {
            lastSearchedIngredients = ingredients;
            generateByIngredientsController.execute(ingredients);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        final String propertyName = event.getPropertyName();

        if (GenerateByIngredientsViewModel.SET_TITLES.equals(propertyName)) {
            final List<String> titles = viewModel.getState();
            list.setListData(titles.toArray(new String[0]));

            if (!lastSearchedIngredients.isEmpty() && titles.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "No recipes found for those ingredients.",
                        "No results",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        }
        else if (AddFavoriteViewModel.FAVORITE_ADDED.equals(propertyName)) {
            final String message = addFavoriteViewModel.getState().getStatusMessage();
            JOptionPane.showMessageDialog(
                    this,
                    message,
                    "Successfully Added!",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
        else if (AddFavoriteViewModel.FAVORITE_FAILED.equals(propertyName)) {
            final String message = addFavoriteViewModel.getState().getStatusMessage();
            JOptionPane.showMessageDialog(
                    this,
                    message,
                    "Adding to Favorites Failed!",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }
}
