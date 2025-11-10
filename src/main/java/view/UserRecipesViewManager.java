package view;

import interface_adapter.UserRecipesViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class UserRecipesViewManager implements PropertyChangeListener {

    public final String changeView;

    private final CardLayout cardLayout;
    private final JPanel views;
    private final UserRecipesViewManagerModel userRecipesViewManagerModel;

    public UserRecipesViewManager(CardLayout cardLayout, JPanel views, UserRecipesViewManagerModel userRecipesViewManagerModel) {
        this.cardLayout = cardLayout;
        this.views = views;
        this.userRecipesViewManagerModel = userRecipesViewManagerModel;

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
