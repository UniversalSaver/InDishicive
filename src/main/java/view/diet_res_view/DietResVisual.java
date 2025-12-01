package view.diet_res_view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import adapters.dietary_restriction.remove_diet_res.RemoveDietResController;

/**
 * A UI component representing a single dietary restriction item.
 * It displays the ingredient name and provides a button to remove it.
 */
public class DietResVisual extends JPanel {

    public DietResVisual(String title, RemoveDietResController removeDietResController) {
        final int topMatteBorder = 0;
        final int leftMatteBorder = 0;
        final int rightMatteBorder = 0;
        final int bottomMatteBorder = 1;

        final int topEmptyBorder = 5;
        final int leftEmptyBorder = 10;
        final int rightEmptyBorder = 10;
        final int bottomEmptyBorder = 5;

        final int maxSizeHeight = 40;

        this.setLayout(new BorderLayout());

        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, maxSizeHeight));
        this.setBackground(Color.WHITE);

        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(topMatteBorder, leftMatteBorder,
                        bottomMatteBorder, rightMatteBorder, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(topEmptyBorder, leftEmptyBorder, bottomEmptyBorder, rightEmptyBorder)
        ));

        final JLabel nameLabel = new JLabel(title);

        this.add(nameLabel, BorderLayout.CENTER);

        final JButton deleteButton = new JButton("Delete");

        deleteButton.setFocusPainted(false);

        deleteButton.addActionListener(event -> {
            if (removeDietResController != null) {
                removeDietResController.execute(title);
            }
        });

        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(deleteButton);

        this.add(buttonPanel, BorderLayout.EAST);
    }
}
