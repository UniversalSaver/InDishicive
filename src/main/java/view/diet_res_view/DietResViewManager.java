package view.diet_res_view;

import java.awt.CardLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

import adapters.DietResViewManagerModel;

public class DietResViewManager implements PropertyChangeListener {
    private final CardLayout cardLayout;
    private final JPanel views;

    public DietResViewManager(CardLayout cardLayout, JPanel views, DietResViewManagerModel dietResViewManagerModel) {
        this.cardLayout = cardLayout;
        this.views = views;
        dietResViewManagerModel.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals(DietResViewManagerModel.CHANGE_VIEW)) {
            final String viewModelName = (String) event.getNewValue();
            cardLayout.show(views, viewModelName);
        }
    }
}
