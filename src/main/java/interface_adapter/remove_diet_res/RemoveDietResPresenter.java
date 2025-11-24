package interface_adapter.remove_diet_res;

import use_case.remove_restriction.RemoveDietResOutputBoundary;

public class RemoveDietResPresenter implements RemoveDietResOutputBoundary {
    private final RemoveDietResViewModel viewModel;

    public RemoveDietResPresenter(RemoveDietResViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView() {
        RemoveDietResState state = viewModel.getState();
        state.setSuccess(true);
        state.setMessage("Ingredient removed from restrictions list.");
        viewModel.setState(state);
        viewModel.firePropertyChange(RemoveDietResViewModel.RESTRICTION_REMOVED);
    }

    @Override
    public void prepareFailView(String errorMessage) {
        RemoveDietResState state = viewModel.getState();
        state.setSuccess(false);
        state.setMessage(errorMessage);
        viewModel.setState(state);
        viewModel.firePropertyChange(RemoveDietResViewModel.RESTRICTION_REMOVE_FAILED);
    }
}