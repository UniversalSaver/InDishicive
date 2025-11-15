package interface_adapter.add_favorite;

import interface_adapter.ViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the AddFavoritePresenter.
 * Tests that the presenter correctly updates the view model and fires property changes.
 */
class AddFavoritePresenterTest {

    private AddFavoritePresenter presenter;
    private AddFavoriteViewModel viewModel;
    private MockPropertyChangeListener listener;

    @BeforeEach
    void setUp() {
        viewModel = new AddFavoriteViewModel();
        presenter = new AddFavoritePresenter(viewModel);
        listener = new MockPropertyChangeListener();
        viewModel.addPropertyChangeListener(listener);
    }

    @Test
    void testPrepareSuccessViewUpdatesState() {
        presenter.prepareSuccessView();

        // verify state is updated correctly
        AddFavoriteState state = viewModel.getState();
        assertTrue(state.isSuccess());
        assertEquals("Recipe added to favorites!", state.getStatusMessage());
    }

    @Test
    void testPrepareSuccessViewFiresPropertyChange() {
        presenter.prepareSuccessView();

        // verify property change was fired
        assertTrue(listener.propertyChangeFired);
        assertEquals(AddFavoriteViewModel.FAVORITE_ADDED, listener.propertyName);
    }

    @Test
    void testPrepareFailViewUpdatesState() {
        String errorMessage = "Already in Favorites!";

        presenter.prepareFailView(errorMessage);

        // verify state is updated correctly
        AddFavoriteState state = viewModel.getState();
        assertFalse(state.isSuccess());
        assertEquals(errorMessage, state.getStatusMessage());
    }

    @Test
    void testPrepareFailViewFiresPropertyChange() {
        presenter.prepareFailView("Already in Favorites!");

        // verify property change was fired
        assertTrue(listener.propertyChangeFired);
        assertEquals(AddFavoriteViewModel.FAVORITE_FAILED, listener.propertyName);
    }

    @Test
    void testPrepareFailViewWithCustomErrorMessage() {
        String customError = "Database error occurred";

        presenter.prepareFailView(customError);

        // verify custom error message is set
        AddFavoriteState state = viewModel.getState();
        assertEquals(customError, state.getStatusMessage());
        assertFalse(state.isSuccess());
    }

    @Test
    void testMultipleSuccessCallsUpdateState() {
        // call success view multiple times
        presenter.prepareSuccessView();
        AddFavoriteState state1 = viewModel.getState();
        assertTrue(state1.isSuccess());

        // reset listener
        listener.reset();

        presenter.prepareSuccessView();
        AddFavoriteState state2 = viewModel.getState();
        assertTrue(state2.isSuccess());

        // verify property change was fired again
        assertTrue(listener.propertyChangeFired);
    }

    @Test
    void testSuccessFollowedByFail() {
        // call success first
        presenter.prepareSuccessView();
        assertTrue(viewModel.getState().isSuccess());

        // then call fail
        presenter.prepareFailView("Error occurred");
        assertFalse(viewModel.getState().isSuccess());
        assertEquals("Error occurred", viewModel.getState().getStatusMessage());
    }

    @Test
    void testViewModelInitialState() {
        // verify initial state before any presenter calls
        AddFavoriteState state = viewModel.getState();
        assertNotNull(state);
        assertFalse(state.isSuccess());
        assertEquals("", state.getStatusMessage());
    }

    /**
     * Mock PropertyChangeListener to track property change events.
     */
    private static class MockPropertyChangeListener implements PropertyChangeListener {
        boolean propertyChangeFired = false;
        String propertyName = null;
        Object newValue = null;

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            propertyChangeFired = true;
            propertyName = evt.getPropertyName();
            newValue = evt.getNewValue();
        }

        void reset() {
            propertyChangeFired = false;
            propertyName = null;
            newValue = null;
        }
    }
}
