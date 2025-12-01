package view.diet_res_view;

import java.awt.Component;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import adapters.dietary_restriction.add_diet_res.AddDietResController;
import adapters.dietary_restriction.add_diet_res.AddDietResState;
import adapters.dietary_restriction.add_diet_res.AddDietResViewModel;
import adapters.dietary_restriction.remove_diet_res.RemoveDietResController;
import adapters.dietary_restriction.remove_diet_res.RemoveDietResState;
import adapters.dietary_restriction.remove_diet_res.RemoveDietResViewModel;
import adapters.dietary_restriction.view_diet_res.DietResViewModel;
import adapters.dietary_restriction.view_diet_res.ViewRestrictionsController;
import adapters.dietary_restriction.view_diet_res.ViewRestrictionsState;

public class DietResView extends JPanel implements PropertyChangeListener {
    private final String viewName;

    private final transient AddDietResController addDietResController;
    private final transient ViewRestrictionsController viewRestrictionsController;
    private final transient RemoveDietResController removeDietResController;

    private final JPanel restIngrPanel;
    private final JTextField newRestField;

    public DietResView(DietResViewModel dietResViewModel,
                       AddDietResViewModel addDietResViewModel,
                       RemoveDietResViewModel removeDietResViewModel,
                       AddDietResController addDietResController,
                       ViewRestrictionsController viewRestrictionsController,
                       RemoveDietResController removeDietResController) {

        this.addDietResController = addDietResController;
        this.viewRestrictionsController = viewRestrictionsController;
        this.removeDietResController = removeDietResController;

        this.viewName = DietResViewModel.VIEW_NAME;

        dietResViewModel.addPropertyChangeListener(this);
        addDietResViewModel.addPropertyChangeListener(this);
        removeDietResViewModel.addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        final int padding = 10;
        this.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));

        final JLabel title = new JLabel(DietResViewModel.RESTRICTIONS_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(title);

        this.add(Box.createVerticalStrut(padding));

        restIngrPanel = new JPanel();
        restIngrPanel.setLayout(new BoxLayout(restIngrPanel, BoxLayout.Y_AXIS));

        final JScrollPane scrollPane = new JScrollPane(restIngrPanel);

        this.add(scrollPane);

        this.add(Box.createVerticalStrut(padding));

        final JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));

        final int heightInputPanel = 30;
        inputPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, heightInputPanel));

        final int columns = 20;

        newRestField = new JTextField(columns);
        final JButton addDietResButton = new JButton("Add");

        inputPanel.add(newRestField);

        final int widthH = 5;

        inputPanel.add(Box.createHorizontalStrut(widthH));
        inputPanel.add(addDietResButton);

        this.add(inputPanel);

        addDietResButton.addActionListener(event -> handleAdd());

        newRestField.addActionListener(event -> handleAdd());
    }

    private void handleAdd() {
        final String ingredientName = newRestField.getText().trim();
        if (!ingredientName.isEmpty()) {
            addDietResController.execute(ingredientName);
            newRestField.setText("");
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final String propertyName = evt.getPropertyName();

        if ("state".equals(propertyName)) {
            if (evt.getNewValue() instanceof ViewRestrictionsState state) {
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
            final AddDietResState state = (AddDietResState) evt.getNewValue();
            JOptionPane.showMessageDialog(this, state.getStatusMessage());
        }
        else if (RemoveDietResViewModel.RESTRICTION_REMOVED.equals(propertyName)) {
            viewRestrictionsController.execute();
        }
        else if (RemoveDietResViewModel.RESTRICTION_REMOVE_FAILED.equals(propertyName)) {
            final RemoveDietResState state = (RemoveDietResState) evt.getNewValue();
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
