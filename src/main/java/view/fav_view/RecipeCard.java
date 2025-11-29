package view.fav_view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import adapters.favorites.remove_favorites.RemoveFavoriteController;
import adapters.generate_recipe.view_recipe_details.ViewRecipeDetailsController;
import entity.Recipe;

/**
 * A card component that displays a single recipe with its title, category, and action buttons.
 * This class follows the Single Responsibility Principle by handling only the display of a recipe card.
 */
public class RecipeCard extends JPanel {
    private static final int BORDER_WIDTH = 1;
    private static final int PADDING = 10;
    private static final int MAX_HEIGHT = 80;
    private static final int INSET_VERTICAL = 2;
    private static final int INSET_HORIZONTAL = 5;
    private static final int DELETE_BUTTON_WIDTH = 45;
    private static final int DELETE_BUTTON_HEIGHT = 25;
    private static final int GRID_WIDTH_FULL = 3;

    /**
     * Constructs a RecipeCard with the specified recipe and controllers.
     *
     * @param recipe the recipe to display
     * @param viewRecipeDetailsController the controller for viewing recipe details
     * @param removeFavoriteController the controller for removing the recipe from favorites
     */
    public RecipeCard(Recipe recipe, ViewRecipeDetailsController viewRecipeDetailsController,
                      RemoveFavoriteController removeFavoriteController) {

        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, BORDER_WIDTH),
                BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING)
        ));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, MAX_HEIGHT));

        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(INSET_VERTICAL, INSET_HORIZONTAL, INSET_VERTICAL, INSET_HORIZONTAL);

        addNameLabel(recipe, gbc);
        addShowButton(recipe, viewRecipeDetailsController, gbc);
        addDeleteButton(recipe, removeFavoriteController, gbc);
        addCategoryLabel(recipe, gbc);
    }

    private void addNameLabel(Recipe recipe, GridBagConstraints gbc) {
        final JLabel nameLabel = new JLabel(recipe.getTitle());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0;
        add(nameLabel, gbc);
    }

    private void addShowButton(Recipe recipe, ViewRecipeDetailsController viewRecipeDetailsController,
                               GridBagConstraints gbc) {
        final JButton showButton = new JButton("Show");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0;
        showButton.addActionListener(event -> viewRecipeDetailsController.execute(recipe.getTitle()));
        add(showButton, gbc);
    }

    private void addDeleteButton(Recipe recipe, RemoveFavoriteController removeFavoriteController,
                                 GridBagConstraints gbc) {
        final JButton deleteButton = new JButton("X");
        deleteButton.setPreferredSize(new Dimension(DELETE_BUTTON_WIDTH, DELETE_BUTTON_HEIGHT));
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0;
        deleteButton.addActionListener(event -> removeFavoriteController.execute(recipe));
        add(deleteButton, gbc);
    }

    private void addCategoryLabel(Recipe recipe, GridBagConstraints gbc) {
        final JLabel categoryLabel = new JLabel(recipe.getCategory());
        categoryLabel.setForeground(Color.GRAY);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = GRID_WIDTH_FULL;
        add(categoryLabel, gbc);
    }
}
