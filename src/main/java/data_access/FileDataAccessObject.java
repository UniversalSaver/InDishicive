package data_access;

import entity.Ingredient;
import entity.UserRecipe;
import use_case.view_recipes.ViewRecipesDataAccessInterface;

import java.io.*;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileDataAccessObject implements ViewRecipesDataAccessInterface {

    static final String USER_RECIPE_FILE_PATH = "user_recipes.tsv";
    static final String USER_RECIPE_FILE_HEADER = "title\tingredients\tsteps\tdescription";
    static final int HEADER_LENGTH = 4;
    private final List<UserRecipe> userRecipes = new ArrayList<>();

    public void updateUserRecipes() {

        userRecipes.clear();

        try (BufferedReader userRecipeFileReader =
                     new BufferedReader(getFileReader(USER_RECIPE_FILE_PATH, USER_RECIPE_FILE_HEADER))) {
            String header = userRecipeFileReader.readLine();

            if (!header.equals(USER_RECIPE_FILE_HEADER)) {

                throw new CorruptDataException("File " + USER_RECIPE_FILE_PATH + " is corrupted. Please fix, then try again.");
            }

            String currentTabbedRecipe;

            while ((currentTabbedRecipe = userRecipeFileReader.readLine()) != null) {
                String[] separatedRecipe = currentTabbedRecipe.split("\t");

                if (separatedRecipe.length != HEADER_LENGTH) {
                    throw new CorruptDataException("File " + USER_RECIPE_FILE_PATH + " is corrupted. " +
                            "User Recipe data is not consistent with header\n" + Arrays.toString(separatedRecipe));
                }

                String title = separatedRecipe[0];
                String steps = separatedRecipe[2];
                String description = separatedRecipe[3];

                List<Ingredient> ingredients = new ArrayList<>();

                for (String ingredient : separatedRecipe[1].split(",")) {
                    String name = ingredient.split("=")[0];
                    String quantity = ingredient.split("=")[1];

                    ingredients.add(new Ingredient(name, quantity));
                }

                userRecipes.add(new UserRecipe(title, ingredients, steps, description));
            }

        } catch (IOException e) {
            throw new InaccessibleFileException(e.getMessage());
        }
    }

    /**
     * Returns a file reader given a file name.
     * If the file doesn't already exist, creates a new file with that name.
     * @param filepath name of file to create
     * @param initialMessage initial message to write if the file doesn't already exist
     * @return FileReader with path of filepath
     */
    private FileReader getFileReader(String filepath, String initialMessage) {
        FileReader fileReader;
        Logger logger = Logger.getLogger(getClass().getName());

        try {
            fileReader = new FileReader(filepath);
        } catch (FileNotFoundException e) {
            logger.info(e.getMessage());
            logger.info("Creating new file, and using that");

            try (FileWriter fileWriter = new FileWriter(filepath)) {
                fileWriter.write(initialMessage);
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

    @Override
    public List<UserRecipe> getUserRecipes() {
        return this.userRecipes;
    }
}
