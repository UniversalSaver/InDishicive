package logic.generate_recipe.generate_by_ingredients;

public interface GenerateByIngredientsInputBoundary {

    /**
     * Executes the use case with the given input data.
     *
     * @param inputData the ingredients provided by the user
     */
    void execute(GenerateByIngredientsInputData inputData);
}
