package window;

import interface_adapter.dietary_restriction.view_diet_res.DietResWindowModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DietResWindow extends JFrame implements PropertyChangeListener {

    private final DietResWindowModel dietResWindowModel;

    /**
     * @param cardPanel The panel containing the views (managed by DietResViewManager)
     * @param dietResWindowModel The model deciding if this window is visible
     */
    public DietResWindow(JPanel cardPanel, DietResWindowModel dietResWindowModel) {
        super("Dietary Restricted Ingredients");

        this.dietResWindowModel = dietResWindowModel;

        this.dietResWindowModel.addPropertyChangeListener(this);

        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setLayout(new BorderLayout());

        if (cardPanel != null) {
            this.add(cardPanel, BorderLayout.CENTER);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (DietResWindowModel.SET_VISIBLE.equals(evt.getPropertyName())) {
            this.setVisible(true);
            this.toFront();
        }
    }
}
