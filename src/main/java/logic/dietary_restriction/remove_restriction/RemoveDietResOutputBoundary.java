package logic.dietary_restriction.remove_restriction;

public interface RemoveDietResOutputBoundary {
    /**
     * Prepares the success view when an ingredient is successfully removed.
     */
    void prepareSuccessView();

    /**
     * Prepares the fail view when removal fails.
     * @param errorMessage the error message to display
     */
    void prepareFailView(String errorMessage);
}
