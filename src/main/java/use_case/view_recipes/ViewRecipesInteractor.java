package use_case.view_recipes;

public class ViewRecipesInteractor implements ViewRecipesInputBoundary {

    private ViewRecipesOutputBoundary ViewRecipesPresenter;

    public ViewRecipesInteractor(ViewRecipesOutputBoundary ViewRecipesPresenter) {
        this.ViewRecipesPresenter = ViewRecipesPresenter;
    }

    @Override
    public void execute() {
        ViewRecipesPresenter.prepareSuccessView();
    }
}
