package logic.user_recipe.add_recipe.view_recipe_creator;

public class ViewCreatorInteractor implements ViewCreatorInputBoundary {

	private final ViewCreatorOutputBoundary viewCreatorPresenter;

	public ViewCreatorInteractor(ViewCreatorOutputBoundary viewCreatorPresenter) {
		this.viewCreatorPresenter = viewCreatorPresenter;
	}


	@Override
	public void execute() {
		this.viewCreatorPresenter.prepareSuccessView();
	}
}
