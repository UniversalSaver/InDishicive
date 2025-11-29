package use_case.get_available_cuisines;

import java.util.List;

public class GetAvailableCuisinesOutputData {
    private final List<String> cuisines;

    public GetAvailableCuisinesOutputData(List<String> cuisines) {
        this.cuisines = cuisines;
    }

    public List<String> getCuisines() {
        return cuisines;
    }
}