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

import entity.Recipe;
import interface_adapter.view_favorite.ViewFavoriteViewModel;
import interface_adapter.view_recipe_details.ViewRecipeDetailsController;


public class FavoriteView extends JPanel implements PropertyChangeListener{
    private final ViewFavoriteViewModel viewModel;
    private final ViewRecipeDetailsController viewRecipeDetailsController;
    private final JPanel cardsPanel;

    public FavoriteView(ViewFavoriteViewModel viewModel, ViewRecipeDetailsController viewRecipeDetailsController) {
        this.viewModel = viewModel;
        this.viewRecipeDetailsController = viewRecipeDetailsController;

        setLayout(new BorderLayout());

        JLabel title = new JLabel("My Favorite Recipes", SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        cardsPanel = new JPanel();
        cardsPanel.setLayout(new BoxLayout(cardsPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(cardsPanel);
        add(scrollPane, BorderLayout.CENTER);

        this.viewModel.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        List<Recipe> favoriteRecipes = viewModel.getState();

        if (favoriteRecipes == null || favoriteRecipes.isEmpty()){
            displayEmptyMessage("No favorite recipes have been added");
        } else{
            displayMessage(favoriteRecipes);
        }
    }

    private void displayEmptyMessage(String message){
        cardsPanel.removeAll();
        JLabel emptyLabel = new JLabel(message, SwingConstants.CENTER);
        cardsPanel.add(emptyLabel);
        cardsPanel.revalidate();
        cardsPanel.repaint();
    }

    private  void displayMessage(List<Recipe> recipes){
        cardsPanel.removeAll();

        for(Recipe recipe : recipes){
            RecipeCard card = new RecipeCard(recipe, viewRecipeDetailsController);
            cardsPanel.add(card);
            cardsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        cardsPanel.revalidate();
        cardsPanel.repaint();
    }
}
