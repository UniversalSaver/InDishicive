package window;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import adapters.user_recipe.view_recipes.UserRecipeWindowModel;

/**
 * The window that holds all views for the user recipes user story.
 */
public class UserRecipesWindow extends JFrame implements PropertyChangeListener {

    public static final int WIDTH = 750;
    public static final int HEIGHT = 400;

    public UserRecipesWindow(JPanel cardPanel) {
        super("User Recipes");

        this.setSize(WIDTH, HEIGHT);

        this.add(cardPanel);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(UserRecipeWindowModel.SET_VISIBLE)) {
            this.setVisible(true);
        }
    }
}
