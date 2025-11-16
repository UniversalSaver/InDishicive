package interface_adapter.generate_with_inventory;

import interface_adapter.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GenerateWithInventoryViewModel extends ViewModel<List<String>> {

    private List<String> allTitles = new ArrayList<>();
    private int offset = 0;
    private String errorMessage = "";

    public GenerateWithInventoryViewModel() {
        super("generate_with_inventory");
    }

    public void resetTitles(List<String> titles) {
        this.allTitles = new ArrayList<>(titles);
        this.offset = 0;
    }

    public List<String> getNextPage(int pageSize) {
        if (allTitles.isEmpty() || offset >= allTitles.size()) {
            return Collections.emptyList();
        }
        int to = Math.min(offset + pageSize, allTitles.size());
        List<String> page = new ArrayList<>(allTitles.subList(offset, to));
        offset = to;
        return page;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isInitialized() {
        return !allTitles.isEmpty();
    }
}