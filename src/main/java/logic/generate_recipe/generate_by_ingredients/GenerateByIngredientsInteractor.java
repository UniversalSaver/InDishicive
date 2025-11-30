package logic.generate_recipe.generate_by_ingredients;

import entity.Recipe;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
        List<String> cleaned = getStrings(inputData);

        List<Recipe> recipes = gateway.findByIngredients(cleaned);

        List<String> titles = new ArrayList<>();
        for (Recipe r : recipes) {
            titles.add(r.getTitle());
        }

        GenerateByIngredientsOutputData out =
                new GenerateByIngredientsOutputData(titles);

        presenter.present(out);
    }

    private static List<String> getStrings(GenerateByIngredientsInputData inputData) {
        List<String> rawIngredients =
                inputData != null ? inputData.getIngredients() : null;

        Set<String> unique = new LinkedHashSet<>();
        if (rawIngredients != null) {
            for (String raw : rawIngredients) {
                if (raw == null) {
                    continue;
                }
                String trimmed = raw.trim();
                if (!trimmed.isEmpty()) {
                    unique.add(trimmed);
                }
            }
        }

        return new ArrayList<>(unique);
    }
}