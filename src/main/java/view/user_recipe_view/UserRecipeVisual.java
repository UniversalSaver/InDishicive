package view.user_recipe_view;

import adapters.user_recipe.delete_recipe.DeleteUserRecipeController;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import adapters.user_recipe.view_recipes.view_detailed_recipe.ViewUserRecipeDetailsController;
import logic.user_recipe.view_recipes.view_detailed_recipe.ViewUserRecipeDetailsInputData;

/**
 * A user recipe visual. Used for showing a basic description of the recipe.
 */
public class UserRecipeVisual extends JPanel {
    public static final int PANEL_WIDTH = 600;
    public static final int PANEL_HEIGHT = 100;

    public static final int INFO_WIDTH = 550;
    public static final int INFO_HEIGHT = 100;

    private final String title;
    private final String description;

    private final JPanel infoPanel = new JPanel();
    private final JPanel infoPanelNames = new JPanel();
    private final JPanel infoPanelData = new JPanel();

    private final JPanel infoPanelButtons = new JPanel();
    private final JButton deleteButton = new JButton("Delete");
    private final JButton viewButton = new JButton("View");

    public UserRecipeVisual(String title, String description) {
        this.title = title;
        this.description = description;

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setMaximumSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBorder(BorderFactory.createLineBorder(Color.black));

        createInfoPanel();

        infoPanelButtons.setLayout(new BoxLayout(infoPanelButtons, BoxLayout.Y_AXIS));

        this.deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.viewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanelButtons.add(this.deleteButton);
        infoPanelButtons.add(this.viewButton);

        this.add(infoPanelButtons);
    }

    /**
     * This adds the view use case to the button.
     * @param viewUserRecipeDetailsController controller to be called for execution
     */
    public void addViewUseCase(ViewUserRecipeDetailsController viewUserRecipeDetailsController) {
        viewButton.addActionListener(buttonPress -> {
            viewUserRecipeDetailsController.execute(new ViewUserRecipeDetailsInputData(title));
        });
    }

    /**
     * Adds the delete user recipe use case to the delete button
     * @param deleteUserRecipeController controller for the use case
     */
    public void addDeleteUserRecipeUseCase(DeleteUserRecipeController deleteUserRecipeController) {
        deleteButton.addActionListener(e -> {
            deleteUserRecipeController.execute(title);
        });
    }

    private void createInfoPanel() {
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
        infoPanel.setMaximumSize(new Dimension(INFO_WIDTH, INFO_HEIGHT));

        infoPanelNames.setLayout(new BoxLayout(infoPanelNames, BoxLayout.Y_AXIS));

        infoPanelNames.add(new JLabel("Title:"));
        infoPanelNames.add(new JLabel("Description:"));
        infoPanelNames.add(Box.createVerticalGlue());
        infoPanelNames.add(Box.createHorizontalGlue());
        infoPanel.add(infoPanelNames);

        infoPanelData.setLayout(new BoxLayout(infoPanelData, BoxLayout.Y_AXIS));

        final JLabel titleLabel = new JLabel(this.title);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        infoPanelData.add(titleLabel);

        final JTextArea descriptionTextArea = new JTextArea(5, 25);
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setEditable(false);
        descriptionTextArea.setText(this.description);

        final JScrollPane descriptionScrollPane = new JScrollPane(descriptionTextArea);
        descriptionScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanelData.add(descriptionScrollPane);

        infoPanel.add(infoPanelData);
        this.add(infoPanel);
    }
}
