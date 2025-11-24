package interface_adapter.dietery_restriction.view_diet_res;

import interface_adapter.ViewModel;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class DietResViewModel extends ViewModel<ViewRestrictionsState> {
    public static final String VIEW_NAME = "dietary_restrictions";
    public static final String RESTRICTIONS_LABEL = "Current Restrictions";

    private ViewRestrictionsState state = new ViewRestrictionsState();

    public DietResViewModel() {
        super(VIEW_NAME);
    }

    @Override
    public void setState(ViewRestrictionsState state) {
        this.state = state;
    }

    @Override
    public ViewRestrictionsState getState() {
        return state;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    @Override
    public void firePropertyChange(String propertyName) {
        support.firePropertyChange(propertyName, null, this.state);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}