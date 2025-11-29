package view.user_recipe_view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import adapters.user_recipe.add_recipe.SwitchViewController;
import adapters.user_recipe.view_recipes.RecipeSummary;
import adapters.user_recipe.view_recipes.UserRecipesViewModel;
import adapters.user_recipe.view_recipes.ViewRecipesState;

/**
 * A panel with all the recipes the user has already created in an easy-to-read format.
 */
public class UserRecipesView extends JPanel implements PropertyChangeListener {
    public static final int PANEL_WIDTH = 600;
    public static final int PANEL_HEIGHT = 350;

    private final String viewName;

    private final JPanel recipes = new JPanel();

    private final JButton addRecipeButton;

    private JLabel numberOfRecipesLabel = new JLabel();

    public UserRecipesView(UserRecipesViewModel userRecipesViewModel) {
        userRecipesViewModel.addPropertyChangeListener(this);
        viewName = UserRecipesViewModel.VIEW_NAME;

        this.addRecipeButton = new JButton("Add Recipe");

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        recipes.setLayout(new BoxLayout(recipes, BoxLayout.Y_AXIS));

        final JScrollPane scrollPane = new JScrollPane(recipes);

        scrollPane.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        this.add(scrollPane);

        final JPanel addRecipesPanel = new JPanel();
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

    public String getViewName() {
        return viewName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(UserRecipesViewModel.SET_SUMMARIES)) {
            final ViewRecipesState summaryState = (ViewRecipesState) evt.getNewValue();

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
