package adapters.dietary_restriction.add_diet_res;

import logic.dietary_restriction.add_restrictions.AddDietResOutputBoundary;

public class AddDietResPresenter implements AddDietResOutputBoundary {
    private final AddDietResViewModel viewModel;

    public AddDietResPresenter(AddDietResViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView() {
        AddDietResState state = viewModel.getState();
        state.setStatusMessage("Ingredient added to restrictions list");
        state.setSuccess(true);
        viewModel.setState(state);
        viewModel.firePropertyChange(AddDietResViewModel.RESTRICTION_ADDED);
    }

    @Override
    public void prepareFailView(String errorMessage) {
        AddDietResState state = viewModel.getState();
        state.setStatusMessage(errorMessage);
        state.setSuccess(false);
        viewModel.setState(state);
        viewModel.firePropertyChange(AddDietResViewModel.RESTRICTION_ADD_FAILED);
    }
}
