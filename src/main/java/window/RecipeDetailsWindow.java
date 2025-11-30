package window;

import adapters.generate_recipe.view_recipe_details.ViewRecipeDetailsState;
import adapters.generate_recipe.view_recipe_details.ViewRecipeDetailsViewModel;
import adapters.inventory.missing_ingredients.MissingIngredientsController;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class RecipeDetailsWindow extends JFrame implements PropertyChangeListener {

    private final JLabel titleLabel = new JLabel();
    private final JTextArea ingredientsArea = new JTextArea();
    private final JTextArea instructionsArea = new JTextArea();
    private final MissingIngredientsController missingIngredientsController;
    private List<String> currentIngredients;

    public RecipeDetailsWindow(ViewRecipeDetailsViewModel viewModel, 
                               MissingIngredientsController missingIngredientsController) {
        super("Recipe Details");
        this.missingIngredientsController = missingIngredientsController;
        viewModel.addPropertyChangeListener(this);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(600, 500);

        ingredientsArea.setEditable(false);
        ingredientsArea.setLineWrap(true);
        ingredientsArea.setWrapStyleWord(true);

        instructionsArea.setEditable(false);
        instructionsArea.setLineWrap(true);
        instructionsArea.setWrapStyleWord(true);

        JPanel top = new JPanel(new BorderLayout());
        top.add(titleLabel, BorderLayout.CENTER);

        JPanel center = new JPanel(new GridLayout(2, 1));

        JPanel ingredientsPanel = new JPanel(new BorderLayout());
        ingredientsPanel.add(new JLabel("Ingredients:"), BorderLayout.NORTH);
        ingredientsPanel.add(new JScrollPane(ingredientsArea), BorderLayout.CENTER);

        JPanel instructionsPanel = new JPanel(new BorderLayout());
        instructionsPanel.add(new JLabel("Instructions:"), BorderLayout.NORTH);
        instructionsPanel.add(new JScrollPane(instructionsArea), BorderLayout.CENTER);

        center.add(ingredientsPanel);
        center.add(instructionsPanel);

        JPanel bottom = new JPanel();
        JButton missingIngredientsButton = new JButton("Check Missing Ingredients");
        missingIngredientsButton.addActionListener(e -> {
            if (currentIngredients != null) {
                missingIngredientsController.execute(currentIngredients);
            }
        });
        bottom.add(missingIngredientsButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(top, BorderLayout.NORTH);
        getContentPane().add(center, BorderLayout.CENTER);
        getContentPane().add(bottom, BorderLayout.SOUTH);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!ViewRecipeDetailsViewModel.DETAILS_PROPERTY.equals(evt.getPropertyName())) {
            return;
        }

        ViewRecipeDetailsState state = (ViewRecipeDetailsState) evt.getNewValue();
        if (state == null) {
            return;
        }

        titleLabel.setText(state.getTitle());
        this.currentIngredients = state.getIngredients();
        ingredientsArea.setText(String.join("\n", currentIngredients));
        instructionsArea.setText(state.getInstructions());

        if (!this.isVisible()) {
            this.setVisible(true);
        }
    }
}