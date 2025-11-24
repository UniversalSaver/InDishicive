package use_case.add_recipe;

public interface AddRecipeOutputBoundary {
	void prepareSuccessView();
	void prepareFailureView(String message);
}
