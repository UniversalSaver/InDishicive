package window;

import interface_adapter.view_recipes.ViewRecipesViewModel;
import view.UserRecipesView;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class UserRecipesWindow extends JFrame implements PropertyChangeListener {

    private final ViewRecipesViewModel viewRecipesViewModel;

    public UserRecipesWindow(ViewRecipesViewModel viewRecipesViewModel) {
        super("User Recipes");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.viewRecipesViewModel = viewRecipesViewModel;
        this.viewRecipesViewModel.addPropertyChangeListener(this);
    }

    public void addUserRecipesView(UserRecipesView userRecipesView) {
        this.add(userRecipesView);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.setVisible(true);
    }
}
