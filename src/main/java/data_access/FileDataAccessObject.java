package data_access;

import entity.Ingredient;
import entity.UserRecipe;
import org.jetbrains.annotations.NotNull;
import use_case.add_recipe.AddRecipeDataAccessInterface;
import use_case.view_recipes.ViewRecipesDataAccessInterface;

import java.io.*;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileDataAccessObject implements ViewRecipesDataAccessInterface, AddRecipeDataAccessInterface {

	private final String filePath;
    static final String USER_RECIPE_FILE_HEADER = "title\tingredients\tsteps\tdescription";
    static final int HEADER_LENGTH = 4;
    private final List<UserRecipe> userRecipes = new ArrayList<>();

	public FileDataAccessObject(String filePath) {
		this.filePath = Objects.requireNonNullElse(filePath, "user_recipes.tsv");
		updateUserRecipes();
	}

	@Override
	public String addRecipe(UserRecipe recipe) {
		if (recipeExists(recipe)) {
			return "Recipe with that name already exists";
		}

		try (BufferedWriter writer = new BufferedWriter(new  FileWriter(filePath, true))) {
			String name = replaceWithEscapes(recipe.getTitle());
			String description = replaceWithEscapes(recipe.getDescription());
			String steps = replaceWithEscapes(recipe.getSteps());
			String ingredients = getIngredientsString(recipe);

			if (ingredients.equals("Please choose a name without special characters") ||
					ingredients.equals("Please choose an amount without special characters")) {
				return ingredients;
			}

			String recipeString = name + '\t' +
					ingredients + '\t' +
					steps + '\t' +
					description + '\n';

			writer.append(recipeString);
		} catch (IOException e) {
			return "File corrupted";
		}

		return AddRecipeDataAccessInterface.ADDED_MESSAGE;
	}

	@Override
	public List<UserRecipe> getUserRecipes() {
		this.updateUserRecipes();
		return this.userRecipes;
	}

	private static String getIngredientsString(UserRecipe recipe) {
		StringBuilder result = new StringBuilder();
		for (Ingredient ingredient : recipe.getIngredients()) {
			if (ingredient.getName().contains("=") || ingredient.getName().contains(",")) {
				return "Please choose a name without special characters";
			} else if (ingredient.getAmount().contains("=") ||  ingredient.getAmount().contains(",")) {
				return "Please choose an amount without special characters";
			}
			result.append(replaceWithEscapes(ingredient.getName()))
					.append("=")
					.append(replaceWithEscapes(ingredient.getAmount()))
					.append(",");
		}
		result.deleteCharAt(result.length() - 1);
		return result.toString();
	}

	private boolean recipeExists(UserRecipe recipe) {
		updateUserRecipes();

		for (UserRecipe userRecipe : userRecipes) {
			if (userRecipe.getTitle().equals(recipe.getTitle())) {
				return true;
			}
		}
		return false;
	}

    private void updateUserRecipes() {

        userRecipes.clear();

        try (BufferedReader userRecipeFileReader =
                     new BufferedReader(getFileReader(filePath))) {
			checkHeader(userRecipeFileReader);

			addUserRecipes(userRecipeFileReader);

		} catch (IOException e) {
            throw new InaccessibleFileException(e.getMessage());
        }
    }

	private void checkHeader(BufferedReader userRecipeFileReader) throws IOException {
		String header = userRecipeFileReader.readLine();

		if (header == null || !header.equals(USER_RECIPE_FILE_HEADER)) {
			throw new CorruptDataException("File " + filePath + " is corrupted. Please fix, then try again.");
		}
	}

	private void addUserRecipes(BufferedReader userRecipeFileReader) throws IOException {
		String currentTabbedRecipe;

		while ((currentTabbedRecipe = userRecipeFileReader.readLine()) != null) {
			String[] separatedRecipe = currentTabbedRecipe.split("\t");

			if (separatedRecipe.length != HEADER_LENGTH) {
				throw new CorruptDataException("File " + filePath + " is corrupted. " +
						"User Recipe data is not consistent with header\n" + Arrays.toString(separatedRecipe));
			}

			String title = getTitle(separatedRecipe);
			String steps = getSteps(separatedRecipe);
			String description = getDescription(separatedRecipe);
			List<Ingredient> ingredients = getIngredients(separatedRecipe);

			userRecipes.add(new UserRecipe(title, ingredients, steps, description));
		}
	}

	@NotNull
	private static String getDescription(String[] separatedRecipe) {
		return replaceEscapes(separatedRecipe[3].replace("\\\\", "\\"));
	}

	@NotNull
	private static String getSteps(String[] separatedRecipe) {
		return replaceEscapes(separatedRecipe[2].replace("\\\\", "\\"));
	}

	@NotNull
	private static String getTitle(String[] separatedRecipe) {
		return replaceEscapes(separatedRecipe[0]);
	}

	private static String replaceEscapes(String input) {
		return input.replace("\\n", "\n").replace("\\t", "\t");
	}

	private static String replaceWithEscapes(String input) {
		return input.replace("\n", "\\n").replace("\t", "\\t");
	}

	@NotNull
	private static List<Ingredient> getIngredients(String[] separatedRecipe) {
		List<Ingredient> ingredients = new ArrayList<>();

		for (String ingredient : separatedRecipe[1].split(",")) {
			String name = replaceEscapes(ingredient.split("=")[0]);
			String quantity = replaceEscapes(ingredient.split("=")[1]);

			ingredients.add(new Ingredient(name, quantity));
		}
		return ingredients;
	}

	/**
	 * Returns a file reader given a file name.
	 * If the file doesn't already exist, creates a new file with that name.
	 *
	 * @param filepath name of file to create
	 * @return FileReader with path of filepath
	 */
    private FileReader getFileReader(String filepath) {
        FileReader fileReader;
        Logger logger = Logger.getLogger(getClass().getName());

        try {
            fileReader = new FileReader(filepath);
        } catch (FileNotFoundException e) {
            logger.info(e.getMessage());
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
