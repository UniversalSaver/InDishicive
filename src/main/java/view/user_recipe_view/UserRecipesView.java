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

    private final JPanel recipes = new JPanel();

    private final UserRecipesViewModel userRecipesViewModel;

    private final JButton addRecipeButton;

    private JLabel numberOfRecipesLabel = new JLabel();

    public UserRecipesView(UserRecipesViewModel userRecipesViewModel) {
        this.userRecipesViewModel = userRecipesViewModel;
        this.userRecipesViewModel.addPropertyChangeListener(this);
        viewName = UserRecipesViewModel.VIEW_NAME;

        this.addRecipeButton = new JButton("Add Recipe");

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(new JLabel(viewName));

        recipes.setLayout(new BoxLayout(recipes, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(recipes);
        scrollPane.setPreferredSize(new Dimension(600, 350));

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
            this.recipes.removeAll();
            for (RecipeSummary recipeSummary : summaryState.getRecipeSummaries()) {
                this.recipes.add(new UserRecipeVisual(recipeSummary.getTitle(), recipeSummary.getDescription()));
            }
            this.recipes.revalidate();
            this.recipes.repaint();
        }
    }
}
