package data_access;

import entity.Ingredient;
import entity.UserRecipe;
import use_case.view_recipes.ViewRecipesDataAccessInterface;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MemoryDataAccessObject implements ViewRecipesDataAccessInterface {

    private final String userRecipeFilepath = "user_recipes.tsv";
    private final String userRecipeFileHeader = "title\tingredients\tsteps\tdescription";
    private final int headerLength = 4;
    private List<UserRecipe> userRecipes = new ArrayList<>();


    public void updateUserRecipes() {
        BufferedReader userRecipeFileReader = new BufferedReader(getFileReader(userRecipeFilepath,
                userRecipeFileHeader));

        userRecipes.clear();

        try {
            String header = userRecipeFileReader.readLine();

            if (!header.equals(userRecipeFileHeader)) {

                throw new CorruptDataException("File " + userRecipeFilepath + " is corrupted. Please fix, then try again.");
            }

            String currentTabbedRecipe;

            while ((currentTabbedRecipe = userRecipeFileReader.readLine()) != null) {
                String[] separatedRecipe = currentTabbedRecipe.split("\t");

                if (separatedRecipe.length != headerLength) {
                    throw new CorruptDataException("File " + userRecipeFilepath + " is corrupted. " +
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

        try {
            fileReader = new FileReader(filepath);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            System.err.println("Creating new file, and using that");

            try (FileWriter fileWriter = new FileWriter(filepath)) {
                fileWriter.write(initialMessage);
            } catch (IOException ex) {
                throw new InaccessibleFileException(ex.getMessage());
            }

            try {
                fileReader = new FileReader(filepath);
            } catch (FileNotFoundException ex) {
                System.err.println("This is an error that should never appear");

                throw new InaccessibleFileException(ex.getMessage());
            }
        }

        return fileReader;
    }

    @Override
    public List<UserRecipe> getUserRecipes() {
//        List<UserRecipe> recipes = new ArrayList<>();
//
//        ArrayList<Ingredient> ingredients = new ArrayList<>();
//        ingredients.add(new Ingredient("Pasta", "5 tablespoons"));
//        recipes.add(new UserRecipe("Bolognese", ingredients, "Add pasta lol", "This is pasta you know?"));
//
//        ingredients = new ArrayList<>();
//        ingredients.add(new Ingredient("Cheese", "5 Cups"));
//        recipes.add(new UserRecipe("CHEESE", ingredients, "Add cheese", "This is cheese you know?"));
//
//        ingredients = new ArrayList<>();
//        ingredients.add(new Ingredient("water", "50 gallons"));
//        recipes.add(new UserRecipe("water",  ingredients, "Add water", "A LOT OF WATER AAAAAAAAAA"));
//
//        recipes.add(new UserRecipe("water", ingredients, "Add water", "A LOT OF WATER AAAAAAAAAA"));


//        return recipes;
        return this.userRecipes;
    }
}
