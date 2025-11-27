package adapters.dietary_restriction.view_diet_res;

import logic.dietary_restriction.view_restrictions.ViewRestrictionsInputBoundary;

public class ViewRestrictionsController {
    private final ViewRestrictionsInputBoundary viewRestrictionsUseCaseInteractor;

    public ViewRestrictionsController(ViewRestrictionsInputBoundary loginUseCaseInteractor) {
        viewRestrictionsUseCaseInteractor = loginUseCaseInteractor;
    }

    public void execute() {
        viewRestrictionsUseCaseInteractor.execute();
    }
}
