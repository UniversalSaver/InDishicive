package use_case.get_available_cuisines;

import java.util.ArrayList;
import java.util.List;

public class GetAvailableCuisinesInteractor implements GetAvailableCuisinesInputBoundary {
    private final GetAvailableCuisinesDataAccessInterface dao;
    private final GetAvailableCuisinesOutputBoundary presenter;

    public GetAvailableCuisinesInteractor(GetAvailableCuisinesDataAccessInterface dao,
                                          GetAvailableCuisinesOutputBoundary presenter) {
        this.dao = dao;
        this.presenter = presenter;
    }

    @Override
    public void execute() {
        List<String> result = dao.getAvailableCuisines();
        if (result == null) result = new ArrayList<>();
        presenter.present(new GetAvailableCuisinesOutputData(result));
    }
}