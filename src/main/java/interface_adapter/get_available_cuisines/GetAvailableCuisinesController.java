package interface_adapter.get_available_cuisines;

import use_case.get_available_cuisines.GetAvailableCuisinesInputBoundary;

public class GetAvailableCuisinesController {

    private final GetAvailableCuisinesInputBoundary interactor;

    public GetAvailableCuisinesController(GetAvailableCuisinesInputBoundary interactor) {
        this.interactor = interactor;
    }

    /** Triggers fetching the cuisines from the DAO (list.php?a=list). */
    public void execute() {
        interactor.execute();
    }
}