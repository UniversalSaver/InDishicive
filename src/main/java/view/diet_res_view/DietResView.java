package view.diet_res_view;

import interface_adapter.add_diet_res.AddDietResController;
import interface_adapter.add_diet_res.AddDietResState;
import interface_adapter.add_diet_res.AddDietResViewModel;
import interface_adapter.remove_diet_res.RemoveDietResController;
import interface_adapter.remove_diet_res.RemoveDietResState;
import interface_adapter.remove_diet_res.RemoveDietResViewModel;
import interface_adapter.view_diet_res.DietResViewModel;
import interface_adapter.view_diet_res.ViewRestrictionsController;
import interface_adapter.view_diet_res.ViewRestrictionsState;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class DietResView extends JPanel implements PropertyChangeListener {
    public final String viewName;

    private final DietResViewModel dietResViewModel;
    private final AddDietResViewModel addDietResViewModel;
    private final RemoveDietResViewModel removeDietResViewModel;

    private final AddDietResController addDietResController;
    private final ViewRestrictionsController viewRestrictionsController;
    private final RemoveDietResController removeDietResController;

    private final JScrollPane scrollPane;
    private final JPanel restIngrPanel;
    private final JTextArea newRestArea;
    private final JButton addDietResButton;

    public DietResView(DietResViewModel dietResViewModel,
                       AddDietResViewModel addDietResViewModel,
                       RemoveDietResViewModel removeDietResViewModel,
                       AddDietResController addDietResController,
                       ViewRestrictionsController viewRestrictionsController,
                       RemoveDietResController removeDietResController) {

        this.dietResViewModel = dietResViewModel;
        this.addDietResViewModel = addDietResViewModel;
        this.removeDietResViewModel = removeDietResViewModel;

        this.addDietResController = addDietResController;
        this.viewRestrictionsController = viewRestrictionsController;
        this.removeDietResController = removeDietResController;

        this.viewName = dietResViewModel.VIEW_NAME;

        this.dietResViewModel.addPropertyChangeListener(this);
        this.addDietResViewModel.addPropertyChangeListener(this);
        this.removeDietResViewModel.addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel title = new JLabel(DietResViewModel.RESTRICTIONS_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(title);

        restIngrPanel = new JPanel();
        restIngrPanel.setLayout(new BoxLayout(restIngrPanel, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(restIngrPanel);
        this.add(scrollPane);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
        inputPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        newRestArea = new JTextArea(2, 20);
        newRestArea.setLineWrap(true);
        JScrollPane inputScroll = new JScrollPane(newRestArea);

        addDietResButton = new JButton("Add Restriction");

        inputPanel.add(inputScroll);
        inputPanel.add(addDietResButton);

        this.add(inputPanel);

        addDietResButton.addActionListener(e -> {
            String ingredientName = newRestArea.getText().trim();
            if (!ingredientName.isEmpty()) {
                addDietResController.execute(ingredientName);
                newRestArea.setText("");
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() == this.dietResViewModel) {
            if (evt.getNewValue() instanceof ViewRestrictionsState) {
                ViewRestrictionsState state = (ViewRestrictionsState) evt.getNewValue();
                if (state.getError() != null) {
                    JOptionPane.showMessageDialog(this, state.getError());
                    state.setError(null);
                }
                refreshList(state.getRestrictions());
            }
        }

        if (evt.getSource() == this.addDietResViewModel) {
            if (AddDietResViewModel.RESTRICTION_ADDED.equals(evt.getPropertyName())) {
                viewRestrictionsController.execute(); // Refresh list
                JOptionPane.showMessageDialog(this, "Ingredient added successfully.");
            } else if (AddDietResViewModel.RESTRICTION_ADD_FAILED.equals(evt.getPropertyName())) {
                AddDietResState state = (AddDietResState) evt.getNewValue();
                JOptionPane.showMessageDialog(this, state.getStatusMessage());
            }
        }

        if (evt.getSource() == this.removeDietResViewModel) {
            if (RemoveDietResViewModel.RESTRICTION_REMOVED.equals(evt.getPropertyName())) {
                viewRestrictionsController.execute(); // Refresh list
            } else if (RemoveDietResViewModel.RESTRICTION_REMOVE_FAILED.equals(evt.getPropertyName())) {
                RemoveDietResState state = (RemoveDietResState) evt.getNewValue();
                JOptionPane.showMessageDialog(this, state.getMessage());
            }
        }
    }

    private void refreshList(List<String> restrictions) {
        restIngrPanel.removeAll();
        if (restrictions != null) {
            for (String restriction : restrictions) {
                restIngrPanel.add(new DietResVisual(restriction, removeDietResController));
            }
        }
        restIngrPanel.revalidate();
        restIngrPanel.repaint();
    }
}