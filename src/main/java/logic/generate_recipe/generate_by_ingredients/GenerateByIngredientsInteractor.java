package logic.generate_recipe.generate_by_ingredients;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import entity.Recipe;

public class GenerateByIngredientsInteractor implements GenerateByIngredientsInputBoundary {

    private final RecipeByIngredientsGateway gateway;
    private final GenerateByIngredientsOutputBoundary presenter;

    public GenerateByIngredientsInteractor(RecipeByIngredientsGateway gateway,
                                           GenerateByIngredientsOutputBoundary presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

    @Override
    public void execute(GenerateByIngredientsInputData inputData) {
        final List<String> cleaned = getStrings(inputData);

        final List<Recipe> recipes = gateway.findByIngredients(cleaned);

        final List<String> titles = new ArrayList<>();
        for (Recipe r : recipes) {
            titles.add(r.getTitle());
        }

        final GenerateByIngredientsOutputData out =
                new GenerateByIngredientsOutputData(titles);

        presenter.present(out);
    }

    private static List<String> getStrings(GenerateByIngredientsInputData inputData) {
        List<String> rawIngredients = null;
        if (inputData != null) {
            rawIngredients = inputData.getIngredients();
        }

        final Set<String> unique = new LinkedHashSet<>();
        if (rawIngredients != null) {
            for (String raw : rawIngredients) {
                if (raw == null) {
                    continue;
                }
                final String trimmed = raw.trim();
                if (!trimmed.isEmpty()) {
                    unique.add(trimmed);
                }
            }
        }

        return new ArrayList<>(unique);
    }
}
