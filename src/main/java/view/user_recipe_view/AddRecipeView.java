package view.user_recipe_view;

import interface_adapter.add_recipe.AddIngredientController;
import interface_adapter.add_recipe.AddRecipeController;
import interface_adapter.add_recipe.AddRecipeViewModel;
import interface_adapter.view_recipes.ViewRecipesController;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class AddRecipeView extends JPanel implements PropertyChangeListener {

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
		JScrollPane scrollPane = new JScrollPane(recipeCreatorPanel);
		this.add(scrollPane);

		buttonsPanel.setLayout(new BorderLayout());

		buttonsPanel.add(cancelButton, BorderLayout.LINE_START);
		buttonsPanel.add(addRecipeButton, BorderLayout.LINE_END);

		this.add(buttonsPanel);

		databaseNotFoundLabel.setText("Database could not be accessed. Try again later");
		databaseNotFoundLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	}

	public void addIngredientUseCase(AddIngredientController addIngredientController) {
		addIngredientButton.addActionListener(e -> addIngredientController.execute());
	}

	public void addCancelButtonUseCase(ViewRecipesController viewRecipesController) {
		cancelButton.addActionListener(e -> viewRecipesController.execute());
	}

	public void addAddRecipeUseCase(AddRecipeController addRecipeController) {
		addRecipeButton.addActionListener(e -> addRecipe(addRecipeController));
	}

	@Override
	@SuppressWarnings("unchecked")
	public void propertyChange(PropertyChangeEvent evt) {
		switch (evt.getPropertyName()) {
			case AddRecipeViewModel.WIPE_VIEW -> wipeView();
			case AddRecipeViewModel.ADD_INGREDIENT -> addIngredient((List<String>) evt.getNewValue());
			case AddRecipeViewModel.DATABASE_NOT_FOUND -> addIngredientWarning();
			case AddRecipeViewModel.ADD_RECIPE_FAIL -> recipeFail(((List<String>) evt.getNewValue()).get(0));
		}
	}

	private void addRecipe(AddRecipeController addRecipeController) {
		List<String> ingredientNames = getIngredientNames();
		List<String> ingredientAmounts = getIngredientAmounts();
		String title = nameTextField.getText();
		String description = descriptionTextArea.getText();
		String steps = stepsTextArea.getText();

		addRecipeController.execute(ingredientNames, ingredientAmounts, title, description, steps);
	}

	@NotNull
	private List<String> getIngredientNames() {
		List<String> ingredientNames = new ArrayList<>();

		for (Component component : ingredientSelectPanel.getComponents()) {
			if (component instanceof IngredientChoice ingredientChoice) {
				ingredientNames.add(ingredientChoice.getSelectedIngredient());
			}
		}
		return ingredientNames;
	}

	private List<String> getIngredientAmounts() {
		List<String> result = new ArrayList<>();
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
        IngredientChoice ingredientChoice = new IngredientChoice(ingredientList);
        ingredientChoice.setAlignmentX(Component.CENTER_ALIGNMENT);
        ingredientSelectPanel.add(ingredientChoice, ingredientSelectPanel.getComponentCount() - 3);

		removeWarningLabel();

		resizeIngredientPanel();
	}

	private void removeWarningLabel() {
		if (ingredientSelectPanel.getComponent(ingredientSelectPanel.getComponentCount() - 1) ==
				databaseNotFoundLabel) {
			ingredientSelectPanel.remove(ingredientSelectPanel.getComponent(
					ingredientSelectPanel.getComponentCount() - 1));
		}
	}

	private void resizeIngredientPanel() {
		recipeCreatorPanel.setPreferredSize(new Dimension(550, 350 +
				ingredientSelectPanel.getPreferredSize().height));
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
        JPanel result = new JPanel();
        result.setPreferredSize(new Dimension(550, 350));
        result.setBorder(new EmptyBorder(20, 20, 20, 20));

        GroupLayout layout = new GroupLayout(result);
        layout.setAutoCreateGaps(true);
        result.setLayout(layout);

        JLabel nameLabel = new JLabel("Recipe Name:");
        JLabel descriptionLabel = new JLabel("Recipe Description:");
        JLabel stepsLabel = new JLabel("Steps:");
        JLabel ingredientsLabel = new JLabel("Ingredients:");

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

		recipeCreatorPanel.setPreferredSize(new Dimension(550, 350 +
				ingredientSelectPanel.getPreferredSize().height));

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
