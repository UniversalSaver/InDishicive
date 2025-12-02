package adapters.dietary_restriction.view_diet_res;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import adapters.ViewModel;

public class DietResViewModel extends ViewModel<ViewRestrictionsState> {
    public static final String VIEW_NAME = "dietary_restrictions";
    public static final String RESTRICTIONS_LABEL = "Current Restrictions";

    private ViewRestrictionsState state = new ViewRestrictionsState();
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

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

    @Override
    public void firePropertyChange(String propertyName) {
        support.firePropertyChange(propertyName, null, this.state);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
