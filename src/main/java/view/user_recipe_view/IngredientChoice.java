package view.user_recipe_view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class IngredientChoice extends JPanel {
    private JComboBox<String> ingredientSelectComboBox;
    private JTextField amountTextField;

    public IngredientChoice() {
        List<String> ingredientList = new ArrayList<>();
        ingredientList.add("Pasta");
        ingredientList.add("Fish");
        ingredientList.add("Beef");
        ingredientList.add("Peanuts");
        ingredientList.add("Diamonds");

        this.add(new JLabel("Name:"));

        ingredientSelectComboBox = new JComboBox<>(ingredientList.toArray(new String[0]));
        this.add(ingredientSelectComboBox);

        this.add(new JLabel("Amount:"));
        amountTextField = new JTextField(10);
        this.add(amountTextField);

        this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, this.getPreferredSize().height));
    }
}
