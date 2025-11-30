package databases.user_recipe;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import entity.Ingredient;
import entity.UserRecipe;
import logic.user_recipe.add_recipe.AddRecipeDataAccessInterface;
import logic.user_recipe.view_recipes.ViewRecipesDataAccessInterface;
import logic.user_recipe.view_recipes.view_detailed_recipe.ViewUserRecipeDetailsDataAccessInterface;

/**
 * A class to access the files for user created recipes.
 */
public class FileDataAccessObject implements ViewRecipesDataAccessInterface,
        AddRecipeDataAccessInterface, ViewUserRecipeDetailsDataAccessInterface {

    static final String USER_RECIPE_FILE_HEADER = "title\tingredients\tsteps\tdescription";
    static final int HEADER_LENGTH = 4;

    static final char NAME_AMOUNT_SEPARATOR = '=';
    static final char INGREDIENT_SEPARATOR = ',';
    static final Pattern SPECIAL_CHARACTERS_REGEX = Pattern.compile(".*[,=].*");

    static final char COMPONENT_SEPARATOR = '\t';
    static final char RECIPE_SEPARATOR = '\n';

    private final Map<String, Integer> headerPositions = new HashMap<>();

    private final String filePath;
    private final List<UserRecipe> userRecipes = new ArrayList<>();

    /**
    * Initializes the Data Access Object with a filepath to take files from.
    * @param filePath file path to access data from
    */
    public FileDataAccessObject(String filePath) {
		this.filePath = Objects.requireNonNullElse(filePath, "user_recipes.tsv");

        final String[] headerComponents = USER_RECIPE_FILE_HEADER.split(Character.toString(COMPONENT_SEPARATOR));

        for (int i = 0; i < headerComponents.length; i++) {
            headerPositions.put(headerComponents[i], i);
        }

        updateUserRecipes();
	}

	@Override
	public String addRecipe(UserRecipe recipe) {
		String returnMessage = AddRecipeDataAccessInterface.ADDED_MESSAGE;

        if (recipeExists(recipe)) {
			returnMessage = "Recipe with that name already exists";
		} else {
            returnMessage = addRecipeToDatabase(recipe, returnMessage);
        }

		return returnMessage;
	}

    private String addRecipeToDatabase(UserRecipe recipe, String returnMessage) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            final String name = replaceWithEscapes(recipe.getTitle());
            final String description = replaceWithEscapes(recipe.getDescription());
            final String steps = replaceWithEscapes(recipe.getSteps());
            final String ingredients = getIngredientsString(recipe);

            if ("Please choose a name without special characters".equals(ingredients)
                    || "Please choose an amount without special characters".equals(ingredients)) {
                returnMessage = ingredients;
            }

            final String recipeString = name + '\t'
                    + ingredients + '\t'
                    + steps + '\t'
                    + description + '\n';

            writer.append(recipeString);
        } catch (IOException fileReadingException) {
            returnMessage = "File corrupted";
        }
        return returnMessage;
    }

    @Override
	public List<UserRecipe> getUserRecipes() {
		this.updateUserRecipes();
		return this.userRecipes;
	}

    @Override
    public UserRecipe getRecipeByTitle(String recipeTitle) {
        UserRecipe result = null;
        final List<UserRecipe> updatedUserRecipes = getUserRecipes();

        for (UserRecipe userRecipe : updatedUserRecipes) {
            if (userRecipe.getTitle().equals(recipeTitle)) {
                result = userRecipe;
            }
        }
        return result;
    }

	private static String getIngredientsString(UserRecipe recipe) {
        StringBuilder result = new StringBuilder();
		for (Ingredient ingredient : recipe.getIngredients()) {
            final String checkString = checkSpecialCharacters(ingredient);
            if (checkString != null) {
                result = new StringBuilder(checkString);
                break;
            }
            result.append(replaceWithEscapes(ingredient.getName()))
					.append(NAME_AMOUNT_SEPARATOR)
					.append(replaceWithEscapes(ingredient.getAmount()))
					.append(INGREDIENT_SEPARATOR);
		}
		if (result.charAt(result.length() - 1) == INGREDIENT_SEPARATOR) {
            result.deleteCharAt(result.length() - 1);
        }
		return result.toString();
	}

    @Nullable
    private static String checkSpecialCharacters(Ingredient ingredient) {
        String result = null;
        if (SPECIAL_CHARACTERS_REGEX.matcher(ingredient.getName()).matches()) {
            result = "Please choose a name without special characters";
        } else if (SPECIAL_CHARACTERS_REGEX.matcher(ingredient.getAmount()).matches()) {
            result = "Please choose an amount without special characters";
        }
        return result;
    }

    private boolean recipeExists(UserRecipe recipe) {
		updateUserRecipes();

        boolean recipeExists = false;

		for (UserRecipe userRecipe : userRecipes) {
			if (userRecipe.getTitle().equals(recipe.getTitle())) {
				recipeExists = true;
                break;
			}
		}
		return recipeExists;
	}

    private void updateUserRecipes() {

        userRecipes.clear();

        try (BufferedReader userRecipeFileReader =
                     new BufferedReader(getFileReader(filePath))) {
			checkHeader(userRecipeFileReader);

			addUserRecipes(userRecipeFileReader);

		} catch (IOException badFileException) {
            throw new InaccessibleFileException(badFileException.getMessage());
        }
    }

	private void checkHeader(BufferedReader userRecipeFileReader) throws IOException {
		final String header = userRecipeFileReader.readLine();

		if (header == null || !header.equals(USER_RECIPE_FILE_HEADER)) {
			throw new CorruptDataException("File " + filePath
                    + " is corrupted. Please fix, then try again.");
		}
	}

	private void addUserRecipes(BufferedReader userRecipeFileReader) throws IOException {
		String currentTabbedRecipe;

		while ((currentTabbedRecipe = userRecipeFileReader.readLine()) != null) {
			final String[] separatedRecipe =
                    currentTabbedRecipe.split(Character.toString(COMPONENT_SEPARATOR));

			if (separatedRecipe.length != HEADER_LENGTH) {
				throw new CorruptDataException("File " + filePath + " is corrupted. "
                        + "User Recipe data is not consistent with header\n" + Arrays.toString(separatedRecipe));
			}

			final String title = getTitle(separatedRecipe);
			final String steps = getSteps(separatedRecipe);
			final String description = getDescription(separatedRecipe);
			final List<Ingredient> ingredients = getIngredients(separatedRecipe);

			userRecipes.add(new UserRecipe(title, ingredients, steps, description));
		}
	}

	@NotNull
	private String getDescription(String[] separatedRecipe) {
		return replaceEscapes(separatedRecipe[headerPositions.get("description")]
                .replace("\\\\", "\\"));
	}

	@NotNull
	private String getSteps(String[] separatedRecipe) {
		return replaceEscapes(separatedRecipe[headerPositions.get("steps")]
                .replace("\\\\", "\\"));
	}

	@NotNull
	private String getTitle(String[] separatedRecipe) {
		return replaceEscapes(separatedRecipe[headerPositions.get("title")]);
	}

	private static String replaceEscapes(String input) {
		return input.replace("\\n", Character.toString(RECIPE_SEPARATOR))
                .replace("\\t", Character.toString(COMPONENT_SEPARATOR));
	}

	private static String replaceWithEscapes(String input) {
		return input.replace(Character.toString(RECIPE_SEPARATOR), "\\n")
                .replace(Character.toString(COMPONENT_SEPARATOR), "\\t");
	}

	@NotNull
	private static List<Ingredient> getIngredients(String[] separatedRecipe) {
		final List<Ingredient> ingredients = new ArrayList<>();

		for (String ingredient : separatedRecipe[1].split(",")) {
			final String name = replaceEscapes(ingredient.split("=")[0]);
			final String quantity = replaceEscapes(ingredient.split("=")[1]);

			ingredients.add(new Ingredient(name, quantity));
		}
		return ingredients;
	}

    private FileReader getFileReader(String filepath) {
        FileReader fileReader;
        final Logger logger = Logger.getLogger(getClass().getName());

        try {
            fileReader = new FileReader(filepath);
        } catch (FileNotFoundException fileNotFoundException) {
            logger.info(fileNotFoundException.getMessage());
            logger.info("Creating new file, and using that");

            try (FileWriter fileWriter = new FileWriter(filepath)) {
                fileWriter.write(FileDataAccessObject.USER_RECIPE_FILE_HEADER + "\n");
            } catch (IOException ex) {
                throw new InaccessibleFileException(ex.getMessage());
            }

            try {
                fileReader = new FileReader(filepath);
            } catch (FileNotFoundException ex) {
                logger.severe("This is an error that should never appear");

                throw new InaccessibleFileException(ex.getMessage());
            }
        }

        return fileReader;
    }
}
