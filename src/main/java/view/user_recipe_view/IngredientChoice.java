package view.user_recipe_view;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * A panel to represent the choices the user can make when it comes to ingredients in their recipe.
 */
public class IngredientChoice extends JPanel {
    public static final int TEXT_FIELD_WIDTH = 10;

    private final JComboBox<String> ingredientSelectComboBox;
    private final JTextField amountTextField;

    public IngredientChoice(List<String> ingredientList) {

		this.add(new JLabel("Name:"));

        ingredientSelectComboBox = new JComboBox<>(ingredientList.toArray(new String[0]));
        this.add(ingredientSelectComboBox);

        this.add(new JLabel("Amount:"));
        amountTextField = new JTextField(TEXT_FIELD_WIDTH);
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
