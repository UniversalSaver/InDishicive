package view.user_recipe_view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class IngredientChoice extends JPanel {
    private final JComboBox<String> ingredientSelectComboBox;
    private final JTextField amountTextField;

    public IngredientChoice(List<String> ingredientList) {

		this.add(new JLabel("Name:"));

        ingredientSelectComboBox = new JComboBox<>(ingredientList.toArray(new String[0]));
        this.add(ingredientSelectComboBox);

        this.add(new JLabel("Amount:"));
        amountTextField = new JTextField(10);
        this.add(amountTextField);

        this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, this.getPreferredSize().height));
    }

    public String getSelectedIngredient() {
        return ingredientSelectComboBox.getSelectedItem().toString();
    }

    public String getAmount() {
        return amountTextField.getText();
    }
}
