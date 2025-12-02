package logic.favorites.favorite_recipes;

/**
 * Data Access Interface for managing favorite recipes.
 * Extends all segregated interfaces following the Interface Segregation Principle.
 */
public interface FavoriteDataAccessInterface extends FavoriteReaderInterface,
        FavoriteWriterInterface, FavoriteRemoverInterface {
}

