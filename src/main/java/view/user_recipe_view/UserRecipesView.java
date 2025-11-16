package view.user_recipe_view;

import interface_adapter.view_recipes.RecipeSummary;
import interface_adapter.view_recipes.UserRecipesViewModel;
import interface_adapter.view_recipes.ViewRecipesState;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class UserRecipesView extends JPanel implements PropertyChangeListener {
    private final String viewName;

    private final JScrollPane scrollPane;

    private final UserRecipesViewModel userRecipesViewModel;

    private final JButton addRecipeButton;

    private JLabel numberOfRecipesLabel;

    public UserRecipesView(UserRecipesViewModel userRecipesViewModel) {
        this.userRecipesViewModel = userRecipesViewModel;
        this.userRecipesViewModel.addPropertyChangeListener(this);
        viewName = UserRecipesViewModel.VIEW_NAME;

        this.addRecipeButton = new JButton("Add Recipe");

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(new JLabel(viewName));

        JPanel recipes = new JPanel();
        recipes.setLayout(new BoxLayout(recipes, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(recipes);

        this.add(scrollPane);

        JPanel addRecipesPanel = new JPanel();
        addRecipesPanel.setLayout(new BorderLayout());

        addRecipesPanel.add(numberOfRecipesLabel, BorderLayout.LINE_START);
        addRecipesPanel.add(addRecipeButton, BorderLayout.LINE_END);

        this.add(addRecipesPanel);
    }

    public String getViewName() {
        return viewName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(UserRecipesViewModel.SET_SUMMARIES)) {
            ViewRecipesState summaryState = (ViewRecipesState) evt.getNewValue();

            this.numberOfRecipesLabel.setText("Currently have " + summaryState.getNumberOfRecipes() + " recipes");
            this.scrollPane.removeAll();
            for (RecipeSummary recipeSummary : summaryState.getRecipeSummaries()) {
                this.scrollPane.add(new UserRecipeVisual(recipeSummary.getTitle(), recipeSummary.getDescription()));
            }
            this.scrollPane.revalidate();
            this.scrollPane.repaint();
        }
    }
}
