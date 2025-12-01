package view.diet_res_view;

import java.awt.CardLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

import adapters.DietResViewManagerModel;

/**
 * The listener responsible for switching between views in the Dietary Restrictions window.
 * It reacts to property changes in the DietResViewManagerModel.
 */
public class DietResViewManager implements PropertyChangeListener {
    private final CardLayout cardLayout;
    private final JPanel views;

    /**
     * Creates a new DietResViewManager.
     * @param cardLayout The layout manager controlling the views
     * @param views The parent panel containing the different view cards
     * @param dietResViewManagerModel The model determining which view to show
     */
    public DietResViewManager(CardLayout cardLayout, JPanel views, DietResViewManagerModel dietResViewManagerModel) {
        this.cardLayout = cardLayout;
        this.views = views;
        dietResViewManagerModel.addPropertyChangeListener(this);
    }

    /**
     * Handles property changes to switch the active view.
     * @param event A PropertyChangeEvent object describing the event source
     *        and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals(DietResViewManagerModel.CHANGE_VIEW)) {
            final String viewModelName = (String) event.getNewValue();
            cardLayout.show(views, viewModelName);
        }
    }
}
