package adapters.inventory.search_ingredients;

import java.util.ArrayList;
import java.util.List;

public class SearchIngredientsState {
    private List<String> ingredients = new ArrayList<>();
    private String error = null;

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

