package window;

import adapters.generate_recipe.view_recipe_details.ViewRecipeDetailsState;
import adapters.generate_recipe.view_recipe_details.ViewRecipeDetailsViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URI;

public class RecipeDetailsWindow extends JFrame implements PropertyChangeListener {

    private final JLabel titleLabel = new JLabel();
    private final JTextArea ingredientsArea = new JTextArea();
    private final JTextArea instructionsArea = new JTextArea();
    private final JButton youtubeButton = new JButton("Watch on YouTube");

    private String youtubeLink = "";

    public RecipeDetailsWindow(ViewRecipeDetailsViewModel viewModel) {
        super("Recipe Details");
        viewModel.addPropertyChangeListener(this);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(600, 500);

        ingredientsArea.setEditable(false);
        ingredientsArea.setLineWrap(true);
        ingredientsArea.setWrapStyleWord(true);

        instructionsArea.setEditable(false);
        instructionsArea.setLineWrap(true);
        instructionsArea.setWrapStyleWord(true);

        youtubeButton.setVisible(false); // Hidden by default
        youtubeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        youtubeButton.addActionListener(e -> openYoutubeLink());

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
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
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
            } else {
                JOptionPane.showMessageDialog(this, "Browser not supported.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error opening YouTube link: " + e.getMessage());
        }
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
        ingredientsArea.setText(String.join("\n", state.getIngredients()));
        instructionsArea.setText(state.getInstructions());

        //If the YouTube link exists, then the button appears.
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