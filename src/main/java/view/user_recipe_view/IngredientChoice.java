package view.user_recipe_view;

import javax.swing.*;
import java.util.List;

public class IngredientChoice extends JPanel {

	private String ingredientName;
	private String ingredientAmount;

	private JComboBox<String> ingredientComboBox;
	private JTextField amountField = new JTextField();

	public String getIngredientName() {
		return ingredientName;
	}

	public String getIngredientAmount() {
		return ingredientAmount;
	}

	public IngredientChoice(List<String> possibleIngredients) {
		GroupLayout layout = new GroupLayout(this);
		layout.setAutoCreateGaps(true);


		ingredientComboBox = new JComboBox(possibleIngredients.toArray());
		ingredientComboBox.setEditable(true);

		JLabel nameLabel = new JLabel("Name:");
		JLabel amountLabel = new JLabel("Amount:");

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(nameLabel)
						.addComponent(amountLabel)
				)
				.addGroup(layout.createParallelGroup()
						.addComponent(ingredientComboBox)
						.addComponent(amountField)
				)
		);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(nameLabel)
						.addComponent(ingredientComboBox)
				)
				.addGroup(layout.createParallelGroup()
						.addComponent(amountLabel)
						.addComponent(amountField)
				)
		);

	}
}
