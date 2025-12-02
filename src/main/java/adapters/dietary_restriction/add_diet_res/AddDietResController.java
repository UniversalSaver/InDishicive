package adapters.dietary_restriction.add_diet_res;

import entity.Ingredient;
import logic.dietary_restriction.add_restrictions.AddDietResInputBoundary;
import logic.dietary_restriction.add_restrictions.AddDietResInputData;

/**
 * Controller for the Add Dietary Restriction Use Case.
 * This class serves as the interface adapter that receives user input (either as an Ingredient object
 * or a raw string name), converts it into the appropriate Input Data, and invokes the Interactor.
 */
public class AddDietResController {
    private final AddDietResInputBoundary interactor;

    /**
     * Constructs a new AddDietResController with the specified interactor.
     *
     * @param interactor the Input Boundary for the Add Dietary Restriction use case
     */
    public AddDietResController(AddDietResInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Executes the add dietary restriction use case with an Ingredient object.
     *
     * @param ingredient the Ingredient entity to be added to the restricted list
     */
    public void execute(Ingredient ingredient) {
        final AddDietResInputData inputData = new AddDietResInputData(ingredient);
        interactor.execute(inputData);
    }

    /**
     * Executes the add dietary restriction use case with an ingredient name.
     * This convenience method creates an Input Data object directly from the string name.
     *
     * @param ingredientName the name of the ingredient to be added to the restricted list
     */
    public void execute(String ingredientName) {
        final AddDietResInputData inputData = new AddDietResInputData(ingredientName);
        interactor.execute(inputData);
    }
}
