package view.user_recipe_view;

import interface_adapter.add_recipe.AddRecipeViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AddRecipeView extends JPanel implements PropertyChangeListener {

	private JButton cancelButton = new JButton("Cancel");
	private JButton addRecipeButton = new JButton("Add Recipe");

	private JTextField nameTextField = new JTextField();
	private JTextArea descriptionTextArea = new JTextArea();
	private JTextArea stepsTextArea = new JTextArea();

	private JPanel ingredientSelectPanel = new JPanel();
	private JButton addIngredientButton = new JButton("Add Ingredient");

	private JPanel recipeCreatorPanel;
    private JScrollPane scrollPane;

	public AddRecipeView(AddRecipeViewModel addRecipeViewModel) {
		addRecipeViewModel.addPropertyChangeListener(this);

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        recipeCreatorPanel = createRecipeCreator();

        scrollPane = new JScrollPane(recipeCreatorPanel);

		this.add(scrollPane);

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BorderLayout());

		buttonsPanel.add(cancelButton, BorderLayout.LINE_START);
		buttonsPanel.add(addRecipeButton, BorderLayout.LINE_END);

		this.add(buttonsPanel);

        addIngredientButton.addActionListener(e -> addIngredient());
	}

    private void addIngredient() {
        IngredientChoice ingredientChoice = new IngredientChoice();
        ingredientChoice.setAlignmentX(Component.CENTER_ALIGNMENT);
        ingredientSelectPanel.add(ingredientChoice, ingredientSelectPanel.getComponentCount() - 3);

        recipeCreatorPanel.setPreferredSize(new Dimension(550, 350 +
                ingredientSelectPanel.getPreferredSize().height));
        recipeCreatorPanel.revalidate();
        recipeCreatorPanel.repaint();
        ingredientSelectPanel.revalidate();
        ingredientSelectPanel.repaint();
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

    @Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(AddRecipeViewModel.WIPE_VIEW)) {
			nameTextField.setText("Insert Name of Recipe");
			descriptionTextArea.setText("Insert Description of Recipe");

			ingredientSelectPanel.removeAll();
            ingredientSelectPanel.add(Box.createHorizontalGlue());
			ingredientSelectPanel.add(addIngredientButton);
            ingredientSelectPanel.add(Box.createVerticalGlue());

			stepsTextArea.setText("Insert Steps of Recipe");

            recipeCreatorPanel.setPreferredSize(new Dimension(550, 350 +
                    ingredientSelectPanel.getPreferredSize().height));

            recipeCreatorPanel.revalidate();
            recipeCreatorPanel.repaint();
		}
	}
}
