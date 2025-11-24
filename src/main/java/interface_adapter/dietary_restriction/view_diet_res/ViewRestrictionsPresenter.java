package interface_adapter.dietary_restriction.view_diet_res;

import interface_adapter.DietResViewManagerModel;
import use_case.dietary_restriction.view_restrictions.ViewRestrictionsOutputBoundary;
import use_case.dietary_restriction.view_restrictions.ViewRestrictionsOutputData;

public class ViewRestrictionsPresenter implements ViewRestrictionsOutputBoundary {
    private final DietResWindowModel dietResWindowModel;
    private final DietResViewManagerModel dietResViewManagerModel;
    private final DietResViewModel dietResViewModel;

    public ViewRestrictionsPresenter(DietResWindowModel dietResWindowModel,
                                     DietResViewManagerModel dietResViewManagerModel,
                                     DietResViewModel dietResViewModel) {
        this.dietResWindowModel = dietResWindowModel;
        this.dietResViewManagerModel = dietResViewManagerModel;
        this.dietResViewModel = dietResViewModel;
    }

    @Override
    public void prepareSuccessView(ViewRestrictionsOutputData outputData) {
        ViewRestrictionsState state = dietResViewModel.getState();

        state.setRestrictions(outputData.getRestrictions());
        this.dietResViewModel.setState(state);

        this.dietResViewModel.firePropertyChange("state");

        this.dietResWindowModel.firePropertyChange(DietResWindowModel.SET_VISIBLE);
        this.dietResViewManagerModel.setState(DietResViewModel.VIEW_NAME);
        this.dietResViewManagerModel.firePropertyChange(DietResViewManagerModel.CHANGE_VIEW);
    }
}
