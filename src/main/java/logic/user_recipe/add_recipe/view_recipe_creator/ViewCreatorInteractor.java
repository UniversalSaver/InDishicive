package logic.user_recipe.add_recipe.view_recipe_creator;

/**
 * An implementation of the respective interface.
 */
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
