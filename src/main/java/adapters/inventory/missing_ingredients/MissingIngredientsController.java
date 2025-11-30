package adapters.inventory.missing_ingredients;

import logic.inventory.missing_ingredients.MissingIngredientsInputBoundary;
import java.util.List;

public class MissingIngredientsController {
    
    private final MissingIngredientsInputBoundary interactor;
    
    public MissingIngredientsController(MissingIngredientsInputBoundary interactor) {
        this.interactor = interactor;
    }
    
    public void execute(List<String> recipeIngredients) {
        interactor.execute(recipeIngredients);
    }
}

