package adapters.inventory.missing_ingredients;

import java.awt.BorderLayout;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import logic.inventory.missing_ingredients.MissingIngredientsOutputBoundary;

/**
 * Presenter for displaying missing ingredients.
 */
public class MissingIngredientsPresenter implements MissingIngredientsOutputBoundary {

    private static final int DIALOG_WIDTH = 400;
    private static final int DIALOG_HEIGHT = 300;
    private static final int BORDER_SIZE = 10;

    private final JFrame parentFrame;

    public MissingIngredientsPresenter(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    @Override
    public void presentMissingIngredients(List<String> missingIngredients) {
        final JDialog dialog = new JDialog(parentFrame, "Missing Ingredients", true);
        dialog.setSize(DIALOG_WIDTH, DIALOG_HEIGHT);
        dialog.setLocationRelativeTo(parentFrame);

        final JPanel mainPanel = new JPanel(new BorderLayout());

        final JLabel titleLabel = new JLabel("You are missing these ingredients:");
        titleLabel.setBorder(BorderFactory.createEmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        final JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setText(String.join("\n", missingIngredients));
        mainPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        final JPanel buttonPanel = new JPanel();
        final JButton exportButton = new JButton("Export Shopping List");
        final JButton closeButton = new JButton("Close");

        exportButton.addActionListener(event -> {
            exportToFile(missingIngredients);
            JOptionPane.showMessageDialog(dialog,
                "Shopping list exported to shopping_list.txt",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
        });

        closeButton.addActionListener(event -> dialog.dispose());

        buttonPanel.add(exportButton);
        buttonPanel.add(closeButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.add(mainPanel);
        dialog.setVisible(true);
    }

    @Override
    public void presentAllIngredientsAvailable() {
        JOptionPane.showMessageDialog(parentFrame,
            "Great! You have all the ingredients for this recipe!",
            "All Ingredients Available",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void exportToFile(List<String> ingredients) {
        try (FileWriter writer = new FileWriter("shopping_list.txt")) {
            writer.write("Shopping List\n");
            writer.write("=============\n\n");
            for (String ingredient : ingredients) {
                writer.write("[ ] " + ingredient + "\n");
            }
        }
        catch (IOException ex) {
            JOptionPane.showMessageDialog(parentFrame,
                "Failed to export shopping list: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
