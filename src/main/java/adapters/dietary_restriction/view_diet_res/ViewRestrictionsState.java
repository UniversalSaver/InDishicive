package adapters.dietary_restriction.view_diet_res;

import java.util.ArrayList;
import java.util.List;

public class ViewRestrictionsState {
    private List<String> restrictions = new ArrayList<>();
    private String error = null;

    public ViewRestrictionsState(ViewRestrictionsState copy) {
        this.restrictions = new ArrayList<>(copy.restrictions);
        this.error = copy.error;
    }

    public ViewRestrictionsState() {}

    public List<String> getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(List<String> restrictions) {
        this.restrictions = restrictions;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}