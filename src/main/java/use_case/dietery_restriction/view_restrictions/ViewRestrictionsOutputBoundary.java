package use_case.dietery_restriction.view_restrictions;

public interface ViewRestrictionsOutputBoundary {
    /**
     * Prepares the success view with the list of restricted ingredients.
     * @param outputData contains the list of restricted ingredients
     */
    void prepareSuccessView(ViewRestrictionsOutputData outputData);
}