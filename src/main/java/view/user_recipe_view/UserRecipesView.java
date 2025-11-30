package view.user_recipe_view;

import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import adapters.user_recipe.add_recipe.SwitchViewController;
import adapters.user_recipe.delete_recipe.DeleteUserRecipeController;
import adapters.user_recipe.view_recipes.RecipeSummary;
import adapters.user_recipe.view_recipes.UserRecipesViewModel;
import adapters.user_recipe.view_recipes.ViewRecipesState;
import adapters.user_recipe.view_recipes.view_detailed_recipe.ViewUserRecipeDetailsController;

/**
 * A panel with all the recipes the user has already created in an easy-to-read format.
 */
public class UserRecipesView extends JPanel implements PropertyChangeListener {
    public static final int PANEL_WIDTH = 600;
    public static final int PANEL_HEIGHT = 350;

    private DeleteUserRecipeController deleteUserRecipeController;

    private final String viewName;

    private final JPanel recipes = new JPanel();

    private final JButton addRecipeButton;
    private final JPanel addRecipesPanel;
    private final JLabel viewDetailsErrorLabel = new JLabel();

    private ViewUserRecipeDetailsController viewRecipeDetailsController;

    private final JLabel numberOfRecipesLabel = new JLabel();

    public UserRecipesView(UserRecipesViewModel userRecipesViewModel) {
        userRecipesViewModel.addPropertyChangeListener(this);
        viewName = UserRecipesViewModel.VIEW_NAME;

        this.addRecipeButton = new JButton("Add Recipe");

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        recipes.setLayout(new BoxLayout(recipes, BoxLayout.Y_AXIS));

        final JScrollPane scrollPane = new JScrollPane(recipes);

        scrollPane.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        this.add(scrollPane);

        addRecipesPanel = new JPanel();
        addRecipesPanel.setLayout(new BorderLayout());

        addRecipesPanel.add(numberOfRecipesLabel, BorderLayout.LINE_START);
        addRecipesPanel.add(addRecipeButton, BorderLayout.LINE_END);

        this.add(addRecipesPanel);
    }

    /**
     * Adds the ability to switch views to the add recipe button.
     * @param switchViewController controller which will be called for button
     */
    public void addViewCreatorUseCase(SwitchViewController switchViewController) {
		this.addRecipeButton.addActionListener(event -> switchViewController.execute());
	}

    /**
     * Adds the delete use recipe use case to the controller
     * @param controller controller for use case
     */
    public void addDeleteRecipeUseCase(DeleteUserRecipeController controller) {
        this.deleteUserRecipeController = controller;
    }

    public String getViewName() {
        return viewName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(UserRecipesViewModel.SET_SUMMARIES)) {
            final ViewRecipesState summaryState = (ViewRecipesState) evt.getNewValue();

            addSummaries(summaryState);

            if (addRecipesPanel.getComponent(1) == viewDetailsErrorLabel) {
                addRecipesPanel.remove(viewDetailsErrorLabel);
            }

            this.recipes.revalidate();
            this.recipes.repaint();
        } else if (evt.getPropertyName().equals(UserRecipesViewModel.DETAILS_ERROR_MESSAGE)) {
            presentErrorMessage();
        }
    }

    /**
     * Adds the viewRecipeDetails use case, so it is applied every time a summary is added.
     * @param controller controller to be called upon
     */
    public void addViewRecipeDetailsUseCase(ViewUserRecipeDetailsController controller) {
        this.viewRecipeDetailsController = controller;
    }

    private void addSummaries(ViewRecipesState summaryState) {
        this.numberOfRecipesLabel.setText("Currently have " + summaryState.getNumberOfRecipes() + " recipes");
        this.recipes.removeAll();
        for (RecipeSummary recipeSummary : summaryState.getRecipeSummaries()) {
            this.recipes.add(new UserRecipeVisual(recipeSummary.getTitle(), recipeSummary.getDescription()));
        }
        for (Component visual : this.recipes.getComponents()) {
            if (visual instanceof UserRecipeVisual userVisual) {
                userVisual.addViewUseCase(viewRecipeDetailsController);
            }
        }
        for (Component component : this.recipes.getComponents()) {
            if (component instanceof UserRecipeVisual visual) {
                visual.addDeleteUserRecipeUseCase(deleteUserRecipeController);
            }
        }
    }

    private void presentErrorMessage() {
        if (addRecipesPanel.getComponent(1) != viewDetailsErrorLabel) {
            addRecipesPanel.add(viewDetailsErrorLabel, BorderLayout.CENTER);
        }
    }
}
