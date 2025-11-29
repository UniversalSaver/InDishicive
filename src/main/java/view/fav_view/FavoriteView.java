package view.fav_view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import adapters.favorites.remove_favorites.RemoveFavoriteController;
import adapters.favorites.view_favorite.ViewFavoriteViewModel;
import adapters.generate_recipe.view_recipe_details.ViewRecipeDetailsController;
import entity.Recipe;

/**
 * View for displaying favorite recipes.
 * Shows a scrollable list of recipe cards with options to view details and remove favorites.
 */
public class FavoriteView extends JPanel implements PropertyChangeListener {
    private static final int CARD_SPACING = 10;

    private final ViewFavoriteViewModel viewModel;
    private final ViewRecipeDetailsController viewRecipeDetailsController;
    private final RemoveFavoriteController removeFavoriteController;
    private final JPanel cardsPanel;

    /**
     * Constructs a FavoriteView with the specified controllers and view model.
     *
     * @param viewModel the view model for managing favorite state
     * @param viewRecipeDetailsController the controller for viewing recipe details
     * @param removeFavoriteController the controller for removing favorites
     */
    public FavoriteView(ViewFavoriteViewModel viewModel, ViewRecipeDetailsController viewRecipeDetailsController,
                        RemoveFavoriteController removeFavoriteController) {

        this.viewModel = viewModel;
        this.viewRecipeDetailsController = viewRecipeDetailsController;
        this.removeFavoriteController = removeFavoriteController;

        setLayout(new BorderLayout());

        final JLabel title = new JLabel("My Favorite Recipes", SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        cardsPanel = new JPanel();
        cardsPanel.setLayout(new BoxLayout(cardsPanel, BoxLayout.Y_AXIS));

        final JScrollPane scrollPane = new JScrollPane(cardsPanel);
        add(scrollPane, BorderLayout.CENTER);

        this.viewModel.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final List<Recipe> favoriteRecipes = viewModel.getState();

        if (favoriteRecipes == null || favoriteRecipes.isEmpty()) {
            displayEmptyMessage("No favorite recipes have been added");
        }
        else {
            displayMessage(favoriteRecipes);
        }
    }

    private void displayEmptyMessage(String message) {
        cardsPanel.removeAll();
        final JLabel emptyLabel = new JLabel(message, SwingConstants.CENTER);
        cardsPanel.add(emptyLabel);
        cardsPanel.revalidate();
        cardsPanel.repaint();
    }

    private void displayMessage(List<Recipe> recipes) {
        cardsPanel.removeAll();

        for (Recipe recipe : recipes) {
            final RecipeCard card = new RecipeCard(recipe, viewRecipeDetailsController, removeFavoriteController);
            cardsPanel.add(card);
            cardsPanel.add(Box.createRigidArea(new Dimension(0, CARD_SPACING)));
        }

        cardsPanel.revalidate();
        cardsPanel.repaint();
    }
}
