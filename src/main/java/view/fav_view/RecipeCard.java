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

import entity.Recipe;
import adapters.favorites.remove_favorites.RemoveFavoriteController;
import adapters.generate_recipe.view_recipe_details.ViewRecipeDetailsController;

/**
 * A card component that displays a single recipe with its title, category, and a show button.
 * This class follows the Single Responsibility Principle by handling only the display of a recipe card.
 */
public class RecipeCard extends JPanel {

    public RecipeCard(Recipe recipe, ViewRecipeDetailsController viewRecipeDetailsController,
                      RemoveFavoriteController removeFavoriteController) {

        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 5, 2, 5);

        JLabel nameLabel = new JLabel(recipe.getTitle());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0;
        add(nameLabel, gbc);

        JButton showButton = new JButton("Show");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0;
        showButton.addActionListener(e -> viewRecipeDetailsController.execute(recipe.getTitle()));
        add(showButton, gbc);

        JButton deleteButton = new JButton("X");
        deleteButton.setPreferredSize(new Dimension(45, 25));
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0;
        deleteButton.addActionListener(e -> removeFavoriteController.execute(recipe));
        add(deleteButton, gbc);

        JLabel categoryLabel = new JLabel(recipe.getCategory());
        categoryLabel.setForeground(Color.GRAY);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 3;
        add(categoryLabel, gbc);
    }
}
