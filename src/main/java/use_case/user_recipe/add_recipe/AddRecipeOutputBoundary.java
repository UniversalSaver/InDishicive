package use_case.user_recipe.add_recipe;

public interface AddRecipeOutputBoundary {
	void prepareSuccessView();
	void prepareFailureView(String message);
}
