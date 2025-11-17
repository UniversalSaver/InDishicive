package interface_adapter.view_diet_res;

import interface_adapter.*;
import use_case.view_restrictions.ViewRestrictionsOutputBoundary;


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

    public void prepareSuccessView() {

        dietResWindowModel.firePropertyChange(DietResWindowModel.SET_VISIBLE);

        dietResViewManagerModel.setState(DietResViewModel.VIEW_NAME);
        dietResViewManagerModel.firePropertyChange(DietResViewManagerModel.CHANGE_VIEW);
    }

}
