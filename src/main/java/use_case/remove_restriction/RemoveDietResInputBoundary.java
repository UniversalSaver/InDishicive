package use_case.remove_restriction;

public interface RemoveDietResInputBoundary {
    /**
     * Executes the remove restriction use case.
     * @param inputData the input data containing the ingredient to remove from restrictions list
     */
    void execute(RemoveDietResInputData inputData);
}