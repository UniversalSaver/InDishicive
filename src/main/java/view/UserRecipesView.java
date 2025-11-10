package view;

import interface_adapter.view_recipes.UserRecipesViewModel;
import interface_adapter.view_recipes.ViewRecipesState;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class UserRecipesView extends JPanel implements PropertyChangeListener {
    private final String viewName;

    private final UserRecipesViewModel userRecipesViewModel;

    public UserRecipesView(UserRecipesViewModel userRecipesViewModel) {
        this.userRecipesViewModel = userRecipesViewModel;

        viewName = UserRecipesViewModel.VIEW_NAME;

        this.add(new JTextField("HI"));
    }

    public String getViewName() {
        return viewName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
