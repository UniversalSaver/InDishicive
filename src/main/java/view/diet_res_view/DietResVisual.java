package view.diet_res_view;

import interface_adapter.dietary_restriction.remove_diet_res.RemoveDietResController;

import javax.swing.*;
import java.awt.*;

public class DietResVisual extends JPanel {
    private final String title;
    private final JButton deleteButton;

    /**
     * @param title The name of the ingredient
     * @param removeDietResController The controller to handle the delete action
     */
    public DietResVisual(String title, RemoveDietResController removeDietResController) {
        this.title = title;

        this.setLayout(new BorderLayout());
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY)); // Bottom separator

        JLabel nameLabel = new JLabel("  " + title); // Slight padding
        nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        this.add(nameLabel, BorderLayout.CENTER);

        deleteButton = new JButton("Delete");

        deleteButton.addActionListener(e -> {
            if (removeDietResController != null) {
                removeDietResController.execute(title);
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(deleteButton);
        this.add(buttonPanel, BorderLayout.EAST);
    }
}