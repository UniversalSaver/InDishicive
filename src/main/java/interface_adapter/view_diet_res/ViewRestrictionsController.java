package interface_adapter.view_diet_res;

import use_case.view_restrictions.ViewRestrictionsInputBoundary;

public class ViewRestrictionsController {
    private final ViewRestrictionsInputBoundary viewRestrictionsUseCaseInteractor;

    public ViewRestrictionsController(ViewRestrictionsInputBoundary loginUseCaseInteractor) {
        viewRestrictionsUseCaseInteractor = loginUseCaseInteractor;
    }

    public void execute() {
        viewRestrictionsUseCaseInteractor.execute();
    }
}
