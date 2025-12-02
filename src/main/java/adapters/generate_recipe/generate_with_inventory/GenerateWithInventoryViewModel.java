package adapters.generate_recipe.generate_with_inventory;

import adapters.ViewModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GenerateWithInventoryViewModel extends ViewModel<List<String>> {

    private List<String> allTitles = new ArrayList<>();
    private List<String> baseTitles = new ArrayList<>();
    private List<String> cuisines = new ArrayList<>();
    private int offset;
    private String errorMessage = "";

    public GenerateWithInventoryViewModel() {
        super("generate_with_inventory");
        setState(List.of());
    }

    public void resetTitles(final List<String> titles) {
        this.allTitles = new ArrayList<>(titles);
        this.offset = 0;
        setState(new ArrayList<>(titles));
    }

    public List<String> getAllTitles() {
        return new ArrayList<>(allTitles);
    }

    public List<String> getBaseTitles() {
        return new ArrayList<>(baseTitles);
    }

    public void setBaseTitles(final List<String> titles) {
        this.baseTitles = (titles == null) ? new ArrayList<>() : new ArrayList<>(titles);
    }

    public List<String> getNextPage(final int pageSize) {
        final List<String> page;
        if (allTitles.isEmpty() || offset >= allTitles.size()) {
            page = Collections.emptyList();
        } else {
            final int to = Math.min(offset + pageSize, allTitles.size());
            page = new ArrayList<>(allTitles.subList(offset, to));
            offset = to;
        }
        return page;
    }

    public void setErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setRecipes(final List<String> titles) {
        this.allTitles = new ArrayList<>(titles);
        this.offset = 0;
        setState(new ArrayList<>(titles));
        firePropertyChange("recipes");
    }

    public void setCuisines(final List<String> cuisines) {
        this.cuisines = (cuisines == null) ? new ArrayList<>() : new ArrayList<>(cuisines);
        firePropertyChange("cuisines");
    }

    public List<String> getCuisines() {
        return new ArrayList<>(cuisines);
    }
}