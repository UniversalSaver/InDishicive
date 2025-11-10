package view;

import interface_adapter.UserRecipesViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class UserRecipesViewManager implements PropertyChangeListener {

    public static final String CHANGE_VIEW = "change_view";

    private final CardLayout cardLayout;
    private final JPanel views;
    private final UserRecipesViewManagerModel userRecipesViewManagerModel;

    public UserRecipesViewManager(CardLayout cardLayout, JPanel views, UserRecipesViewManagerModel userRecipesViewManagerModel) {
        this.cardLayout = cardLayout;
        this.views = views;
        this.userRecipesViewManagerModel = userRecipesViewManagerModel;

        userRecipesViewManagerModel.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (CHANGE_VIEW.equals(event.getPropertyName())) {
            this.cardLayout.show(views, (String) event.getNewValue());
        }

    }
}
