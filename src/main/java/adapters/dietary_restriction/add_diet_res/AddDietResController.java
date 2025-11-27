package adapters.dietary_restriction.add_diet_res;

import entity.Ingredient;
import logic.dietary_restriction.add_restrictions.AddDietResInputBoundary;
import logic.dietary_restriction.add_restrictions.AddDietResInputData;

public class AddDietResController {
    private final AddDietResInputBoundary interactor;

    public AddDietResController(AddDietResInputBoundary interactor){
        this.interactor = interactor;
    }

    public void execute(Ingredient ingredient){
        AddDietResInputData inputData = new AddDietResInputData(ingredient);
        interactor.execute(inputData);
    }

    public void execute(String ingredientName){
        AddDietResInputData inputData = new AddDietResInputData(ingredientName);
        interactor.execute(inputData);
    }
}