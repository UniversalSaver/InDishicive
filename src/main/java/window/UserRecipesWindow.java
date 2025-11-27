package window;

import interface_adapter.UserRecipesViewManagerModel;
import interface_adapter.user_recipe.view_recipes.UserRecipeWindowModel;
import interface_adapter.user_recipe.view_recipes.UserRecipesViewModel;
import view.user_recipe_view.UserRecipesView;
import view.user_recipe_view.UserRecipesViewManager;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class UserRecipesWindow extends JFrame implements PropertyChangeListener {

    private final JPanel cardPanel;
    private final CardLayout cardLayout;
    private UserRecipeWindowModel userRecipeWindowModel;

    private UserRecipesView userRecipesView;
    private UserRecipesViewModel userRecipesViewModel;

    private UserRecipesViewManagerModel userRecipesViewManagerModel;
    private UserRecipesViewManager userRecipesViewManager;

    public UserRecipesWindow(JPanel cardPanel, CardLayout cardLayout,
                             UserRecipesViewManager userRecipesViewManager,
                             UserRecipesViewManagerModel userRecipesViewManagerModel,
                             UserRecipeWindowModel userRecipesWindowModel) {
        super("User Recipes");

        this.setSize(600, 400);

        this.cardPanel = cardPanel;
        this.cardLayout = cardLayout;
        this.userRecipesViewManager = userRecipesViewManager;
        this.userRecipesViewManagerModel = userRecipesViewManagerModel;
        this.userRecipeWindowModel = userRecipesWindowModel;


        this.add(cardPanel);
    }

    public void addUserRecipesView(UserRecipesView userRecipesView, UserRecipesViewModel userRecipesViewModel) {
        this.userRecipesView = userRecipesView;
        this.userRecipesViewModel = userRecipesViewModel;
    }


// TODO
//    public void addCreateRecipeView(CreateRecipeView createRecipeView) {
//        this.add(createRecipeView);
//    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if  (evt.getPropertyName().equals(UserRecipeWindowModel.SET_VISIBLE)) {
            this.setVisible(true);
        }
    }
}
