package view;

import interface_adapter.generate_with_inventory.GenerateWithInventoryController;
import interface_adapter.generate_with_inventory.GenerateWithInventoryViewModel;
import interface_adapter.view_recipe_details.ViewRecipeDetailsController;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class GenerateByInventoryPanel extends JPanel implements PropertyChangeListener {

    private final DefaultListModel<String> model = new DefaultListModel<>();
    private final JList<String> list = new JList<>(model);

    private final GenerateWithInventoryViewModel viewModel;

    public GenerateByInventoryPanel(GenerateWithInventoryController controller,
                                    GenerateWithInventoryViewModel vm,
                                    ViewRecipeDetailsController detailsController) {

        this.viewModel = vm;

        setLayout(new BorderLayout());

        JLabel title = new JLabel("Recipes you can make with your inventory");
        JButton generate = new JButton("Generate Recipes");

        JPanel top = new JPanel(new BorderLayout());
        top.add(title, BorderLayout.CENTER);
        top.add(generate, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        add(new JScrollPane(list), BorderLayout.CENTER);

        JButton details = new JButton("View Details");
        details.setEnabled(false);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(details);
        add(bottom, BorderLayout.SOUTH);

        vm.addPropertyChangeListener(this);

        generate.addActionListener(e -> controller.execute());

        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                details.setEnabled(list.getSelectedValue() != null);
            }
        });

        details.addActionListener(e -> {
            String selected = list.getSelectedValue();
            if (selected != null) {
                detailsController.execute(selected);
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String name = evt.getPropertyName();

        if ("recipes".equals(name)) {
            @SuppressWarnings("unchecked")
            List<String> titles = (List<String>) evt.getNewValue();
            model.clear();
            titles.forEach(model::addElement);

        } else if ("error".equals(name)) {
            String msg = viewModel.getErrorMessage();
            if (msg != null && !msg.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        msg,
                        "No Recipes Available",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
            model.clear();
        }
    }

}