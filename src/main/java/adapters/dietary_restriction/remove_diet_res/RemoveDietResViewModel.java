package adapters.dietary_restriction.remove_diet_res;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import adapters.ViewModel;

public class RemoveDietResViewModel extends ViewModel<RemoveDietResState> {
    public static final String RESTRICTION_REMOVED = "restriction_removed";
    public static final String RESTRICTION_REMOVE_FAILED = "restriction_remove_failed";

    private RemoveDietResState state = new RemoveDietResState();
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

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

    @Override
    public void firePropertyChange(String propertyName) {
        support.firePropertyChange(propertyName, null, this.state);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
