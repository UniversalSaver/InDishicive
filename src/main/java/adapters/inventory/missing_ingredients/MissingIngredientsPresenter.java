package adapters.inventory.missing_ingredients;

import logic.inventory.missing_ingredients.MissingIngredientsOutputBoundary;
import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MissingIngredientsPresenter implements MissingIngredientsOutputBoundary {
    
    private final JFrame parentFrame;
    
    public MissingIngredientsPresenter(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }
    
    @Override
    public void presentMissingIngredients(List<String> missingIngredients) {
        JDialog dialog = new JDialog(parentFrame, "Missing Ingredients", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(parentFrame);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        JLabel titleLabel = new JLabel("You are missing these ingredients:");
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setText(String.join("\n", missingIngredients));
        mainPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        JButton exportButton = new JButton("Export Shopping List");
        JButton closeButton = new JButton("Close");
        
        exportButton.addActionListener(e -> {
            exportToFile(missingIngredients);
            JOptionPane.showMessageDialog(dialog, 
                "Shopping list exported to shopping_list.txt", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        });
        
        closeButton.addActionListener(e -> dialog.dispose());
        
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
        } catch (IOException e) {
            JOptionPane.showMessageDialog(parentFrame,
                "Failed to export shopping list: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}

