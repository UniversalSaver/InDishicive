package data_access_object;


import data_access.user_recipe.CorruptDataException;
import data_access.user_recipe.FileDataAccessObject;
import entity.Ingredient;
import entity.UserRecipe;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import use_case.user_recipe.add_recipe.AddRecipeDataAccessInterface;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileDataAccessObjectTest {
	@AfterEach
	void deleteTestFile() {
		File closeFile = new File("test_DAO_file.tsv");
		closeFile.delete();
	}

	@Test
	void getRecipeTest() {
		try (FileWriter writer = new FileWriter("test_DAO_file.tsv")) {
			writer.write("title\tingredients\tsteps\tdescription\n" +
					"Hot Chocolate\tMilk=4 cups,Unsweetened cocoa powder=1/4 cup,Granulated sugar=1/4 cup," +
					"Chocolate chips=1/4 cup,Vanilla Extract=1/2 cup\tMake it\tA cool hot chocolate recipe");
		} catch (IOException e) {
			fail(e.getMessage());
		}
		UserRecipe expectedUserRecipe = new UserRecipe("Hot Chocolate",
				Arrays.stream((new Ingredient[]
						{new Ingredient("Milk", "4 cups"),
								new Ingredient("Unsweetened cocoa powder", "1/4 cup"),
								new Ingredient("Granulated sugar", "1/4 cup"),
								new Ingredient("Chocolate chips", "1/4 cup"),
								new Ingredient("Vanilla Extract", "1/2 cup")})).toList(),
				"Make it", "A cool hot chocolate recipe");

		FileDataAccessObject fileDataAccessObject = new FileDataAccessObject("test_DAO_file.tsv");

		List<UserRecipe> userRecipeList = fileDataAccessObject.getUserRecipes();

		assertEquals(1, userRecipeList.size());
		UserRecipe gottenUserRecipe = userRecipeList.get(0);
		assertEquals(expectedUserRecipe, gottenUserRecipe);
	}

	@Test
	void newLineRecipeTest() {
		try (FileWriter writer = new FileWriter("test_DAO_file.tsv")) {
			writer.write("title\tingredients\tsteps\tdescription\n" +
					"Hot Chocolate\tMilk=4 cups,Unsweetened cocoa powder=1/4 cup,Granulated sugar=1/4 cup," +
					"Chocolate chips=1/4 cup,Vanilla Extract=1/2 cup\tMake it\\nThen bake it\t" +
					"A cool hot chocolate recipe");
		} catch (IOException e) {
			fail(e.getMessage());
		}
		UserRecipe expectedUserRecipe = new UserRecipe("Hot Chocolate",
				Arrays.stream((new Ingredient[]
						{new Ingredient("Milk", "4 cups"),
								new Ingredient("Unsweetened cocoa powder", "1/4 cup"),
								new Ingredient("Granulated sugar", "1/4 cup"),
								new Ingredient("Chocolate chips", "1/4 cup"),
								new Ingredient("Vanilla Extract", "1/2 cup")})).toList(),
				"Make it\nThen bake it", "A cool hot chocolate recipe");

		FileDataAccessObject fileDataAccessObject = new FileDataAccessObject("test_DAO_file.tsv");

		List<UserRecipe> userRecipeList = fileDataAccessObject.getUserRecipes();

		assertEquals(1, userRecipeList.size());
		UserRecipe gottenUserRecipe = userRecipeList.get(0);
		assertEquals(expectedUserRecipe, gottenUserRecipe);
	}

	@Test
	void badHeaderTest() {
		try (FileWriter writer = new FileWriter("test_DAO_file.tsv")) {
			writer.write("title\tingredients\tsteps\tdescripti\n" +
					"Hot Chocolate\tMilk=4 cups,Unsweetened cocoa powder=1/4 cup,Granulated sugar=1/4 cup," +
					"Chocolate chips=1/4 cup,Vanilla Extract=1/2 cup\tMake it\tA cool hot chocolate recipe");
		} catch (IOException e) {
			fail(e.getMessage());
		}
		assertThrows(CorruptDataException.class, () -> new FileDataAccessObject("test_DAO_file.tsv"));
	}

	@Test
	void addValidRecipeTest() {
		UserRecipe expectedUserRecipe = new UserRecipe("Hot Chocolate",
				Arrays.stream((new Ingredient[]
						{new Ingredient("Milk", "4 cups"),
								new Ingredient("Unsweetened cocoa powder", "1/4 cup"),
								new Ingredient("Granulated sugar", "1/4 cup"),
								new Ingredient("Chocolate chips", "1/4 cup"),
								new Ingredient("Vanilla Extract", "1/2 cup")})).toList(),
				"Make it\nBake it", "A cool hot chocolate recipe");
		FileDataAccessObject fileDataAccessObject = new FileDataAccessObject("test_DAO_file.tsv");
		String expectedFile = """
				title\tingredients\tsteps\tdescription
				Hot Chocolate\tMilk=4 cups,Unsweetened cocoa powder=1/4 cup,Granulated sugar=1/4 cup,\
				Chocolate chips=1/4 cup,\
				Vanilla Extract=1/2 cup\tMake it\\nBake it\tA cool hot chocolate recipe
				""";

		assertEquals(AddRecipeDataAccessInterface.ADDED_MESSAGE, fileDataAccessObject.addRecipe(expectedUserRecipe));

		String actualFile = "Failed";
		try (BufferedReader reader = new BufferedReader(new FileReader("test_DAO_file.tsv"))) {
			StringBuilder stringBuilder = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line)
						.append("\n");
			}
			actualFile = stringBuilder.toString();
		} catch (IOException e) {
			fail(e.getMessage());
		}

		assertEquals(expectedFile, actualFile);
	}

	@Test
	void addInvalidRecipeTest() {
		UserRecipe expectedUserRecipe = new UserRecipe("Hot Chocolate",
				Arrays.stream((new Ingredient[]
						{new Ingredient("Milk", "4 cups"),
								new Ingredient("Unsweetened cocoa powder", "1/4 cup"),
								new Ingredient("Granulated sugar", "1/4=cup"),
								new Ingredient("Chocolate chips", "1/4 cup"),
								new Ingredient("Vanilla Extract", "1/2 cup")})).toList(),
				"Make it\nBake it", "A cool hot chocolate recipe");
		FileDataAccessObject fileDataAccessObject = new FileDataAccessObject("test_DAO_file.tsv");

		assertEquals("Please choose an amount without special characters",
				fileDataAccessObject.addRecipe(expectedUserRecipe));
	}

	@Test
	void createFileWithHeaderTest() {
		FileDataAccessObject fileDataAccessObject = new FileDataAccessObject("test_DAO_file.tsv");

		String expectedFile =  "title\tingredients\tsteps\tdescription\n";

		String actualFile = "Failed";
		try (BufferedReader reader = new BufferedReader(new FileReader("test_DAO_file.tsv"))) {
			StringBuilder stringBuilder = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line)
						.append("\n");
			}
			actualFile = stringBuilder.toString();
		} catch (IOException e) {
			fail(e.getMessage());
		}

		assertEquals(expectedFile, actualFile);
	}

	@Test
	void addExistingRecipeTest() {
		try (FileWriter writer = new FileWriter("test_DAO_file.tsv")) {
			writer.write("title\tingredients\tsteps\tdescription\n" +
					"Hot Chocolate\tMilk=4 cups,Unsweetened cocoa powder=1/4 cup,Granulated sugar=1/4 cup," +
					"Chocolate chips=1/4 cup,Vanilla Extract=1/2 cup\tMake it\tA cool hot chocolate recipe");
		} catch (IOException e) {
			fail(e.getMessage());
		}
		UserRecipe userRecipe = new UserRecipe("Hot Chocolate",
				Arrays.stream((new Ingredient[]
						{new Ingredient("Milk", "4 cups"),
								new Ingredient("Unsweetened cocoa powder", "1/4 cup"),
								new Ingredient("Granulated sugar", "1/4 cup"),
								new Ingredient("Chocolate chips", "1/4 cup"),
								new Ingredient("Vanilla Extract", "1/2 cup")})).toList(),
				"Make it", "A cool hot chocolate recipe");
		String expectedErrorMessage = "Recipe with that name already exists";

		FileDataAccessObject fileDataAccessObject = new FileDataAccessObject("test_DAO_file.tsv");

		assertEquals(expectedErrorMessage, fileDataAccessObject.addRecipe(userRecipe));
	}
}
