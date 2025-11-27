package use_case.dietary_restriction.view_restrictions;

import java.util.List;

public class ViewRestrictionsOutputData {
    private final List<String> restrictions;

    public ViewRestrictionsOutputData(List<String> restrictions) {
        this.restrictions = restrictions;
    }

    public List<String> getRestrictions() {
        return restrictions;
    }
}
