package use_case.add_restrictions;

public interface AddDietResOutputBoundary {
    /**
     * Prepares the success view when an ingredient is successfully added to restrictions list.
     */
    void prepareSuccessView();

    /**
     * Prepares the fail view when adding to restrictions list fails.
     * @param errorMessage the error message to display
     */
    void prepareFailView(String errorMessage);

}
