package view.user_recipe_view;

import interface_adapter.add_recipe.AddRecipeViewModel;
import interface_adapter.view_recipes.ViewRecipesController;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class AddRecipeView extends JPanel implements PropertyChangeListener {

	private JButton cancelButton = new JButton("Cancel");
	private JButton addRecipeButton = new JButton("Add Recipe");

	private JTextField nameTextField = new JTextField();
	private JTextArea descriptionTextArea = new JTextArea();
	private JTextArea stepsTextArea = new JTextArea();

	private JPanel ingredientSelectPanel = new JPanel();
	private JButton addIngredientButton = new JButton("Add Ingredient");

	private List<IngredientChoice> ingredientChoices = new ArrayList<>();

	private JPanel recipeCreatorPanel;

	private AddRecipeViewModel addRecipeViewModel;

	public AddRecipeView(AddRecipeViewModel addRecipeViewModel) {
		this.addRecipeViewModel = addRecipeViewModel;
		this.addRecipeViewModel.addPropertyChangeListener(this);

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


		this.recipeCreatorPanel = new JPanel();
		recipeCreatorPanel.setPreferredSize(new Dimension(550, 350));

		GroupLayout layout = new GroupLayout(recipeCreatorPanel);
		layout.setAutoCreateGaps(true);
		this.recipeCreatorPanel.setLayout(layout);

		JLabel nameLabel = new JLabel("Recipe Name:");
		JLabel descriptionLabel = new JLabel("Recipe Description:");
		JLabel stepsLabel = new JLabel("Steps:");
		JLabel ingredientsLabel = new JLabel("Ingredients:");

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

		JScrollPane scrollPane = new JScrollPane(recipeCreatorPanel);

		this.add(scrollPane);

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BorderLayout());

		buttonsPanel.add(cancelButton, BorderLayout.LINE_START);
		buttonsPanel.add(addRecipeButton, BorderLayout.LINE_END);

		this.add(buttonsPanel);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(AddRecipeViewModel.WIPE_VIEW)) {
			nameTextField.setText("Insert Name of Recipe");
			descriptionTextArea.setText("Insert Description of Recipe");

			ingredientSelectPanel.removeAll();
			ingredientSelectPanel.add(addIngredientButton);

			stepsTextArea.setText("Insert Steps of Recipe");
		}
	}

    public void addCancelButtonUseCase(ViewRecipesController viewRecipesController) {
        cancelButton.addActionListener(e -> viewRecipesController.execute());
    }
}
