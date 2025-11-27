package use_case.dietary_restriction.add_restrictions;

public interface AddDietResInputBoundary {
    /**
     * Executes the add restriction use case.
     * @param inputData the input data containing the ingredient to add to restrictions list
     */
    void execute(AddDietResInputData inputData);
}