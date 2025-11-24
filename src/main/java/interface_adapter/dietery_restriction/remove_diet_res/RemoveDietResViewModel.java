package interface_adapter.dietery_restriction.remove_diet_res;

import interface_adapter.ViewModel;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class RemoveDietResViewModel extends ViewModel<RemoveDietResState> {
    public static final String RESTRICTION_REMOVED = "restriction_removed";
    public static final String RESTRICTION_REMOVE_FAILED = "restriction_remove_failed";

    private RemoveDietResState state = new RemoveDietResState();

    public RemoveDietResViewModel() {
        super("remove_restriction");
    }

    @Override
    public void setState(RemoveDietResState state) {
        this.state = state;
    }

    @Override
    public RemoveDietResState getState() {
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