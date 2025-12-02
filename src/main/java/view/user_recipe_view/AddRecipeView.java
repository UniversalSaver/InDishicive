package view.user_recipe_view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.jetbrains.annotations.NotNull;

import adapters.user_recipe.add_recipe.AddRecipeController;
import adapters.user_recipe.add_recipe.AddRecipeViewModel;
import adapters.user_recipe.add_recipe.add_ingredient.AddRecipeIngredientController;
import adapters.user_recipe.view_recipes.ViewRecipesController;

/**
 * The view where the user can add their own recipe to the database.
 */
public class AddRecipeView extends JPanel implements PropertyChangeListener {
    public static final int CREATOR_HEIGHT = 350;
    public static final int CREATOR_WIDTH = 550;
    public static final int CREATOR_PADDING = 20;

    private final JButton cancelButton = new JButton("Cancel");
    private final JButton addRecipeButton = new JButton("Add Recipe");
    private final JPanel buttonsPanel = new JPanel();

    private final JTextField nameTextField = new JTextField();
    private final JTextArea descriptionTextArea = new JTextArea();
    private final JTextArea stepsTextArea = new JTextArea();

    private final JPanel ingredientSelectPanel = new JPanel();
    private final JButton addIngredientButton = new JButton("Add Ingredient");
    private final JLabel databaseNotFoundLabel = new JLabel();

    private final JPanel recipeCreatorPanel;

	public AddRecipeView(AddRecipeViewModel addRecipeViewModel) {
		addRecipeViewModel.addPropertyChangeListener(this);

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        recipeCreatorPanel = createRecipeCreator();
		final JScrollPane scrollPane = new JScrollPane(recipeCreatorPanel);
		this.add(scrollPane);

		buttonsPanel.setLayout(new BorderLayout());

		buttonsPanel.add(cancelButton, BorderLayout.LINE_START);
		buttonsPanel.add(addRecipeButton, BorderLayout.LINE_END);

		this.add(buttonsPanel);

		databaseNotFoundLabel.setText("Database could not be accessed. Try again later");
		databaseNotFoundLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	}

    /**
     * Adds the addIngredient functionality to the add ingredients button.
     * @param addIngredientController a controller which will be called for the button
     */
    public void addIngredientUseCase(AddRecipeIngredientController addIngredientController) {
        addIngredientButton.addActionListener(event -> addIngredientController.execute());
    }

    /**
     * Adds the cancel functionality to the cancel button.
     * @param viewRecipesController a controller which will be called for the button
     */
    public void addCancelButtonUseCase(ViewRecipesController viewRecipesController) {
        cancelButton.addActionListener(event -> viewRecipesController.execute());
    }

    /**
     * Adds the add recipe functionality to the add recipe button.
     * @param addRecipeController a controller which will be called for the button
     */
    public void addAddRecipeUseCase(AddRecipeController addRecipeController) {
        addRecipeButton.addActionListener(event -> addRecipe(addRecipeController));
    }

    @Override
    @SuppressWarnings("unchecked")
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case AddRecipeViewModel.WIPE_VIEW -> wipeView();
            case AddRecipeViewModel.ADD_INGREDIENT -> addIngredient((List<String>) evt.getNewValue());
            case AddRecipeViewModel.DATABASE_NOT_FOUND -> addIngredientWarning();
            case AddRecipeViewModel.ADD_RECIPE_FAIL ->
                recipeFail(((List<String>) evt.getNewValue()).get(0));
            default -> throw new UnsupportedOperationException("Improper property change call");
        }
    }

    private void addRecipe(AddRecipeController addRecipeController) {
        final List<String> ingredientNames = getIngredientNames();
        final List<String> ingredientAmounts = getIngredientAmounts();
        final String title = nameTextField.getText();
        final String description = descriptionTextArea.getText();
        final String steps = stepsTextArea.getText();

        addRecipeController.execute(ingredientNames, ingredientAmounts, title, description, steps);
    }

    @NotNull
    private List<String> getIngredientNames() {
        final List<String> ingredientNames = new ArrayList<>();

        for (Component component : ingredientSelectPanel.getComponents()) {
            if (component instanceof IngredientChoice ingredientChoice) {
                ingredientNames.add(ingredientChoice.getSelectedIngredient());
            }
        }
        return ingredientNames;
    }

    private List<String> getIngredientAmounts() {
        final List<String> result = new ArrayList<>();
        for (Component component : ingredientSelectPanel.getComponents()) {
            if (component instanceof IngredientChoice ingredientChoice) {
                result.add(ingredientChoice.getAmount());
            }
        }
        return result;
    }

    private void recipeFail(String message) {
        if (buttonsPanel.getComponent(1) instanceof JLabel label) {
            label.setText(message);
        } else {
            buttonsPanel.add(new JLabel(message), 1);
        }
        buttonsPanel.revalidate();
        buttonsPanel.repaint();
    }

    private void addIngredient(java.util.List<String> ingredientList) {
        final IngredientChoice ingredientChoice = new IngredientChoice(ingredientList);
        ingredientChoice.setAlignmentX(Component.CENTER_ALIGNMENT);
        ingredientSelectPanel.add(ingredientChoice, ingredientSelectPanel.getComponentCount() - 3);

        removeWarningLabel();

        resizeIngredientPanel();
    }

    private void removeWarningLabel() {
        if (ingredientSelectPanel.getComponent(ingredientSelectPanel.getComponentCount() - 1)
                == databaseNotFoundLabel) {
            ingredientSelectPanel.remove(ingredientSelectPanel.getComponent(
                    ingredientSelectPanel.getComponentCount() - 1));
        }
    }

    private void resizeIngredientPanel() {
        recipeCreatorPanel.setPreferredSize(new Dimension(CREATOR_WIDTH, CREATOR_HEIGHT
                + ingredientSelectPanel.getPreferredSize().height));
        recipeCreatorPanel.revalidate();
        recipeCreatorPanel.repaint();
        ingredientSelectPanel.revalidate();
        ingredientSelectPanel.repaint();
    }

    private void addIngredientWarning() {
        ingredientSelectPanel.add(databaseNotFoundLabel);

        resizeIngredientPanel();
    }

    private JPanel createRecipeCreator() {
        final JPanel result = new JPanel();
        result.setPreferredSize(new Dimension(CREATOR_WIDTH, CREATOR_HEIGHT));
        result.setBorder(new EmptyBorder(CREATOR_PADDING, CREATOR_PADDING, CREATOR_PADDING, CREATOR_PADDING));

        final GroupLayout layout = new GroupLayout(result);
        layout.setAutoCreateGaps(true);
        result.setLayout(layout);

        final JLabel nameLabel = new JLabel("Recipe Name:");
        final JLabel descriptionLabel = new JLabel("Recipe Description:");
        final JLabel stepsLabel = new JLabel("Steps:");
        final JLabel ingredientsLabel = new JLabel("Ingredients:");

        descriptionTextArea.setLineWrap(true);
        stepsTextArea.setLineWrap(true);

        ingredientSelectPanel.setLayout(new BoxLayout(ingredientSelectPanel, BoxLayout.Y_AXIS));
        addIngredientButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(nameLabel)
                                .addComponent(descriptionLabel)
                                .addComponent(ingredientsLabel)
                                .addComponent(stepsLabel))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(nameTextField)
                                .addComponent(descriptionTextArea)
                                .addComponent(ingredientSelectPanel)
                                .addComponent(stepsTextArea))
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(nameLabel)
                                .addComponent(nameTextField))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(descriptionLabel)
                                .addComponent(descriptionTextArea))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(ingredientsLabel)
                                .addComponent(ingredientSelectPanel))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(stepsLabel)
                                .addComponent(stepsTextArea))
        );
        return result;
    }

    private void wipeView() {
        nameTextField.setText("Insert Name of Recipe");
        descriptionTextArea.setText("Insert Description of Recipe");

        ingredientSelectPanel.removeAll();
        ingredientSelectPanel.add(Box.createHorizontalGlue());
        ingredientSelectPanel.add(addIngredientButton);
        ingredientSelectPanel.add(Box.createVerticalGlue());

        stepsTextArea.setText("Insert Steps of Recipe");

        recipeCreatorPanel.setPreferredSize(new Dimension(CREATOR_WIDTH, CREATOR_HEIGHT
                + ingredientSelectPanel.getPreferredSize().height));

        removeButtonErrorLabel();

        recipeCreatorPanel.revalidate();
        recipeCreatorPanel.repaint();
    }

    private void removeButtonErrorLabel() {
        if (buttonsPanel.getComponent(1) instanceof JLabel errorLabel) {
            buttonsPanel.remove(errorLabel);
        }
    }
}
