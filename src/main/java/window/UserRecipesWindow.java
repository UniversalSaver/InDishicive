package window;

import interface_adapter.UserRecipesViewManagerModel;
import interface_adapter.view_recipes.ViewRecipesViewModel;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class UserRecipesWindow extends JFrame implements PropertyChangeListener {

    public static final String SET_VISIBLE = "set_visible";

    private final JPanel cardPanel;
    private final CardLayout cardLayout;

    private UserRecipesView userRecipesView;
    private ViewRecipesViewModel viewRecipesViewModel;

    private UserRecipesViewManagerModel userRecipesViewManagerModel;
    private UserRecipesViewManager userRecipesViewManager;

    public UserRecipesWindow(JPanel cardPanel, CardLayout cardLayout,
                             UserRecipesViewManager userRecipesViewManager,
                             UserRecipesViewManagerModel userRecipesViewManagerModel) {
        super("User Recipes");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.cardPanel = cardPanel;
        this.cardLayout = cardLayout;
        this.userRecipesViewManager = userRecipesViewManager;
        this.userRecipesViewManagerModel = userRecipesViewManagerModel;

        this.add(cardPanel);
    }

    public void addUserRecipesView(UserRecipesView userRecipesView, ViewRecipesViewModel viewRecipesViewModel) {
        this.userRecipesView = userRecipesView;
        this.viewRecipesViewModel = viewRecipesViewModel;
    }

//    public void addCreateRecipeView(CreateRecipeView createRecipeView) {
//        this.add(createRecipeView);
//    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if  (SET_VISIBLE.equals(evt.getPropertyName())) {
            this.setVisible(true);
        }
    }
}
