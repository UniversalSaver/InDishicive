package adapters.dietary_restriction.view_diet_res;

import logic.dietary_restriction.view_restrictions.ViewRestrictionsInputBoundary;

/**
 * Controller for the View Dietary Restrictions Use Case.
 * This class serves as the interface adapter that initiates the request to view the list of
 * current dietary restrictions. It acts as the bridge between the View (UI) and the Interactor.
 */
public class ViewRestrictionsController {
    private final ViewRestrictionsInputBoundary viewRestrictionsUseCaseInteractor;

    /**
     * Constructs a new ViewRestrictionsController.
     *
     * @param loginUseCaseInteractor the Input Boundary (interactor) for the View Restrictions use case
     */
    public ViewRestrictionsController(ViewRestrictionsInputBoundary loginUseCaseInteractor) {
        viewRestrictionsUseCaseInteractor = loginUseCaseInteractor;
    }

    /**
     * Executes the view restrictions use case.
     * Calling this method triggers the interactor to retrieve the restricted ingredients
     * and prepare the data for presentation.
     */
    public void execute() {
        viewRestrictionsUseCaseInteractor.execute();
    }
}
