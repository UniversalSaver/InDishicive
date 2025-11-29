package adapters;

import java.beans.*;

public class ViewModel<T> {

    private final String viewName;

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private T state;

    public ViewModel(String viewName) {
        this.viewName = viewName;
    }

    public void setState(T state) {
        this.state = state;
    }

    public T getState() {
        return state;
    }

    public void firePropertyChange(String propertyName) {
        this.support.firePropertyChange(propertyName, null, state);
    }
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }
}
