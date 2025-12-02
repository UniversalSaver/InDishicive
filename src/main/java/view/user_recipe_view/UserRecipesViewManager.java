package view.user_recipe_view;

import java.awt.CardLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

import adapters.UserRecipesViewManagerModel;

/**
 * A view manager for the user recipes views.
 * Used to switch between panels.
 */
public class UserRecipesViewManager implements PropertyChangeListener {

    private final String changeView;

    private final CardLayout cardLayout;
    private final JPanel views;

    public UserRecipesViewManager(CardLayout cardLayout, JPanel views,
                                  UserRecipesViewManagerModel userRecipesViewManagerModel) {
        this.cardLayout = cardLayout;
        this.views = views;

        userRecipesViewManagerModel.addPropertyChangeListener(this);

        changeView = UserRecipesViewManagerModel.CHANGE_VIEW;
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals(changeView)) {
            this.cardLayout.show(views, (String) event.getNewValue());
        }

    }
}
