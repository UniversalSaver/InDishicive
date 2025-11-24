package window;

import interface_adapter.DietResViewManagerModel;
import interface_adapter.view_diet_res.DietResViewModel;
import interface_adapter.view_diet_res.DietResWindowModel;
import view.diet_res_view.DietResView;
import view.diet_res_view.DietResViewManager;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DietResWindow extends JFrame implements PropertyChangeListener {

    private final JPanel cardPanel;
    private final CardLayout cardLayout;
    private DietResWindowModel dietResWindowModel;

    private DietResView dietResView;
    private DietResViewModel dietResViewModel;

    private DietResViewManagerModel dietResViewManagerModel;
    private DietResViewManager dietResViewManager;

    public DietResWindow(JPanel cardPanel, CardLayout cardLayout,
                         DietResViewManager dietResViewManager,
                         DietResViewManagerModel dietResViewManagerModel,
                         DietResWindowModel dietResWindowModel) {
        super("Dietary Restricted Ingredients");

        this.setSize(600, 400);

        this.cardPanel = cardPanel;
        this.cardLayout = cardLayout;
        this.dietResViewManager = dietResViewManager;
        this.dietResViewManagerModel = dietResViewManagerModel;
        this.dietResWindowModel = dietResWindowModel;


        this.add(cardPanel);
    }

    public void addDietResView(DietResView dietResView, DietResViewModel dietResViewModel) {
        this.dietResView = dietResView;
        this.dietResViewModel = dietResViewModel;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if  (evt.getPropertyName().equals(DietResWindowModel.SET_VISIBLE)) {
            this.setVisible(true);
        }
    }
}
