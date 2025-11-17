package use_case.view_restrictions;

public class ViewRestrictionsInteractor implements ViewRestrictionsInputBoundary {

    private ViewRestrictionsOutputBoundary ViewRestrictionsPresenter;

    public ViewRestrictionsInteractor(ViewRestrictionsOutputBoundary ViewRestrictionsPresenter) {
        this.ViewRestrictionsPresenter = ViewRestrictionsPresenter;
    }

    @Override
    public void execute() {
        ViewRestrictionsPresenter.prepareSuccessView();
    }
}
