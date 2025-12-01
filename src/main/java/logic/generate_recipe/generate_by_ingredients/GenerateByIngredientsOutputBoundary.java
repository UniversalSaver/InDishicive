package logic.generate_recipe.generate_by_ingredients;

public interface GenerateByIngredientsOutputBoundary {
    /**
     * Presents the output data produced by the use case.
     *
     * @param outputData the data containing the generated recipe titles
     */
    void present(GenerateByIngredientsOutputData outputData);
}
