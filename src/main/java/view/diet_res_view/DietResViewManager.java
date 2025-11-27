package view.diet_res_view;

import adapters.DietResViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DietResViewManager implements PropertyChangeListener {
    private final CardLayout cardLayout;
    private final JPanel views;
    private final DietResViewManagerModel dietResViewManagerModel;

    public DietResViewManager(CardLayout cardLayout, JPanel views, DietResViewManagerModel dietResViewManagerModel) {
        this.cardLayout = cardLayout;
        this.views = views;
        this.dietResViewManagerModel = dietResViewManagerModel;
        this.dietResViewManagerModel.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals(DietResViewManagerModel.CHANGE_VIEW)) {
            String viewModelName = (String) event.getNewValue();
            cardLayout.show(views, viewModelName);
        }
    }
}