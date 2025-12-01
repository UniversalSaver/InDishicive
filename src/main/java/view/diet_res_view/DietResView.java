package view.diet_res_view;

import adapters.dietary_restriction.add_diet_res.AddDietResController;
import adapters.dietary_restriction.add_diet_res.AddDietResState;
import adapters.dietary_restriction.add_diet_res.AddDietResViewModel;
import adapters.dietary_restriction.remove_diet_res.RemoveDietResController;
import adapters.dietary_restriction.remove_diet_res.RemoveDietResState;
import adapters.dietary_restriction.remove_diet_res.RemoveDietResViewModel;
import adapters.dietary_restriction.view_diet_res.DietResViewModel;
import adapters.dietary_restriction.view_diet_res.ViewRestrictionsController;
import adapters.dietary_restriction.view_diet_res.ViewRestrictionsState;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class DietResView extends JPanel implements PropertyChangeListener {
    private final String viewName;

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
        String propertyName = evt.getPropertyName();

        if ("state".equals(propertyName)) {
            if (evt.getNewValue() instanceof ViewRestrictionsState) {
                ViewRestrictionsState state = (ViewRestrictionsState) evt.getNewValue();
                if (state.getError() != null) {
                    JOptionPane.showMessageDialog(this, state.getError());
                    state.setError(null);
                }
                refreshList(state.getRestrictions());
            }
        }
        else if (AddDietResViewModel.RESTRICTION_ADDED.equals(propertyName)) {
            viewRestrictionsController.execute();
            JOptionPane.showMessageDialog(this, "Ingredient added successfully.");
        }
        else if (AddDietResViewModel.RESTRICTION_ADD_FAILED.equals(propertyName)) {
            AddDietResState state = (AddDietResState) evt.getNewValue();
            JOptionPane.showMessageDialog(this, state.getStatusMessage());
        }
        else if (RemoveDietResViewModel.RESTRICTION_REMOVED.equals(propertyName)) {
            viewRestrictionsController.execute(); // Refresh list
        }
        else if (RemoveDietResViewModel.RESTRICTION_REMOVE_FAILED.equals(propertyName)) {
            RemoveDietResState state = (RemoveDietResState) evt.getNewValue();
            JOptionPane.showMessageDialog(this, state.getMessage());
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

    public String getViewName() {
        return viewName;
    }
}