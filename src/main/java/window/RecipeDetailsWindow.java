package window;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URI;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import adapters.generate_recipe.view_recipe_details.ViewRecipeDetailsState;
import adapters.generate_recipe.view_recipe_details.ViewRecipeDetailsViewModel;
import adapters.inventory.missing_ingredients.MissingIngredientsController;

/**
 * Window for displaying recipe details.
 */
public class RecipeDetailsWindow extends JFrame implements PropertyChangeListener {

    private final JLabel titleLabel = new JLabel();
    private final JTextArea ingredientsArea = new JTextArea();
    private final JTextArea instructionsArea = new JTextArea();
    private final MissingIngredientsController missingIngredientsController;
    private final JButton youtubeButton = new JButton("Watch on YouTube");

    private List<String> currentIngredients;
    private String youtubeLink = "";

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

        youtubeButton.setVisible(false);
        youtubeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        youtubeButton.addActionListener(event -> openYoutubeLink());

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

        final JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));

        final JButton missingIngredientsButton = new JButton("Check Missing Ingredients");
        missingIngredientsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        missingIngredientsButton.addActionListener(event -> {
            if (currentIngredients != null && missingIngredientsController != null) {
                missingIngredientsController.execute(currentIngredients);
            }
        });

        bottom.add(Box.createVerticalStrut(10));
        bottom.add(missingIngredientsButton);
        bottom.add(Box.createVerticalStrut(10));
        bottom.add(youtubeButton);
        bottom.add(Box.createVerticalStrut(10));

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(top, BorderLayout.NORTH);
        getContentPane().add(center, BorderLayout.CENTER);
        getContentPane().add(bottom, BorderLayout.SOUTH);
    }

    private void openYoutubeLink() {
        if (youtubeLink == null || youtubeLink.isEmpty()) {
            return;
        }
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(youtubeLink));
            }
            else {
                JOptionPane.showMessageDialog(this, "Browser not supported.");
            }
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error opening YouTube link: " + ex.getMessage());
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!ViewRecipeDetailsViewModel.DETAILS_PROPERTY.equals(evt.getPropertyName())) {
            return;
        }

        final ViewRecipeDetailsState state = (ViewRecipeDetailsState) evt.getNewValue();
        if (state == null) {
            return;
        }

        titleLabel.setText(state.getTitle());
        this.currentIngredients = state.getIngredients();
        ingredientsArea.setText(String.join("\n", currentIngredients));
        instructionsArea.setText(state.getInstructions());

        this.youtubeLink = state.getYoutubeLink();
        boolean hasLink = youtubeLink != null && !youtubeLink.trim().isEmpty();
        youtubeButton.setVisible(hasLink);

        this.getContentPane().revalidate();
        this.getContentPane().repaint();
        if (!this.isVisible()) {
            this.setVisible(true);
        }
    }
}
