package view.diet_res_view;

import interface_adapter.DietResViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class DietResViewManager implements PropertyChangeListener {
    public final String changeView;

    private final CardLayout cardLayout;
    private final JPanel views;
    private final DietResViewManagerModel dietResViewManagerModel;

    public DietResViewManager(CardLayout cardLayout, JPanel views, DietResViewManagerModel dietResViewManagerModel) {
        this.cardLayout = cardLayout;
        this.views = views;
        this.dietResViewManagerModel = dietResViewManagerModel;

        dietResViewManagerModel.addPropertyChangeListener(this);

        changeView = DietResViewManagerModel.CHANGE_VIEW;
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals(changeView)) {
            this.cardLayout.show(views, (String) event.getNewValue());
        }

    }
}
