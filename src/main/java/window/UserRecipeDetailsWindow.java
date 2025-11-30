package window;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import adapters.user_recipe.view_recipes.view_detailed_recipe.RecipeDetails;
import adapters.user_recipe.view_recipes.view_detailed_recipe.UserRecipeDetailsViewModel;

/**
 * A window to present the user recipe details.
 */
public class UserRecipeDetailsWindow extends JFrame implements PropertyChangeListener {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 500;

    private final JLabel titleLabel = new JLabel();
    private final JTextArea ingredientsArea = new JTextArea();
    private final JTextArea instructionsArea = new JTextArea();

    public UserRecipeDetailsWindow(UserRecipeDetailsViewModel viewModel) {
        super("Recipe Details");
        viewModel.addPropertyChangeListener(this);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(WIDTH, HEIGHT);

        ingredientsArea.setEditable(false);
        ingredientsArea.setLineWrap(true);
        ingredientsArea.setWrapStyleWord(true);

        instructionsArea.setEditable(false);
        instructionsArea.setLineWrap(true);
        instructionsArea.setWrapStyleWord(true);

        final JPanel top = new JPanel(new BorderLayout());
        top.add(titleLabel, BorderLayout.CENTER);

        final JPanel center = new JPanel(new GridLayout(2, 1));

        final JPanel ingredientsPanel = new JPanel(new BorderLayout());
        ingredientsPanel.add(new JLabel("Ingredients:"), BorderLayout.NORTH);
        ingredientsPanel.add(new JScrollPane(ingredientsArea), BorderLayout.CENTER);

        final JPanel instructionsPanel = new JPanel(new BorderLayout());
        instructionsPanel.add(new JLabel("Instructions:"), BorderLayout.NORTH);
        instructionsPanel.add(new JScrollPane(instructionsArea), BorderLayout.CENTER);

        center.add(ingredientsPanel);
        center.add(instructionsPanel);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(top, BorderLayout.NORTH);
        getContentPane().add(center, BorderLayout.CENTER);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(UserRecipeDetailsViewModel.SHOW_RECIPE_DETAILS)) {
            final RecipeDetails recipeDetails = (RecipeDetails) evt.getNewValue();

            this.titleLabel.setText(recipeDetails.getTitle());
            this.instructionsArea.setText(recipeDetails.getSteps());

            this.ingredientsArea.setText(formatIngredients(recipeDetails.getIngredientNames(),
                    recipeDetails.getIngredientAmounts()));

            this.setVisible(true);
        }
    }

    private String formatIngredients(List<String> ingredientNames, List<String> ingredientAmounts) {
        final StringBuilder builder = new StringBuilder();

        for (int i = 0; i < ingredientNames.size(); i++) {
            builder.append(ingredientNames.get(i))
                    .append(": ")
                    .append(ingredientAmounts.get(i))
                    .append("\n");
        }
        return builder.toString();
    }

}
