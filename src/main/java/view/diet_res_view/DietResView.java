package view.diet_res_view;

import interface_adapter.view_diet_res.DietResViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DietResView extends JPanel implements PropertyChangeListener {
    private final String viewName;

    private final JScrollPane scrollPane;

    private final DietResViewModel dietResViewModel;

    private final JButton addDietResButton;

    public DietResView(DietResViewModel dietResViewModel) {
        this.dietResViewModel = dietResViewModel;
        viewName = dietResViewModel.VIEW_NAME;

        this.addDietResButton = new JButton("Add Dietary Restriction");

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(new JLabel(viewName));

        JPanel restIngr = new JPanel();
        restIngr.setLayout(new BoxLayout(restIngr, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(restIngr);

        restIngr.add(new DietResVisual("Tomato"));
        restIngr.add(new DietResVisual("Beef"));
        restIngr.add(new DietResVisual("Chicken"));
        restIngr.add(new DietResVisual("Potato"));
        restIngr.add(new DietResVisual("Sugar"));


        JTextArea newRestArea = new JTextArea(10, 2);
        newRestArea.setLineWrap(true);
        newRestArea.setEditable(true);
        newRestArea.setMaximumSize(new Dimension(300, 20));
        JPanel addRestrictionPanel = new JPanel();
        addRestrictionPanel.setLayout(new BoxLayout(addRestrictionPanel, BoxLayout.X_AXIS));

        addRestrictionPanel.add(newRestArea);
        addRestrictionPanel.add(addDietResButton);

        addDietResButton.addActionListener(e -> {
            restIngr.add(new DietResVisual(newRestArea.getText()));
            Container parent = this.getParent();
            parent.revalidate();
            parent.repaint();

        });

        this.add(scrollPane);


        JPanel nmPanel = new JPanel();
        nmPanel.add(new JLabel(String.format("You have added %s ingredients to your restricted list.", restIngr.getComponentCount())), BorderLayout.CENTER);

        JPanel pageBottom = new JPanel();
        pageBottom.setLayout(new BoxLayout(pageBottom, BoxLayout.Y_AXIS));
        pageBottom.setMaximumSize(new Dimension(300, 100));
        pageBottom.add(nmPanel);
        pageBottom.add(addRestrictionPanel);

        this.add(pageBottom);
    }

    public String getViewName() {
        return viewName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
