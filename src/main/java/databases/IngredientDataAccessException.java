package data_access;

/**
 * Exception thrown when there is a failure accessing ingredient data from an external source.
 */
public class IngredientDataAccessException extends Exception {
    
    /**
     * Constructs an IngredientDataAccessException with the specified detail message.
     * @param message the detail message
     */
    public IngredientDataAccessException(String message) {
        super(message);
    }
    
    /**
     * Constructs an IngredientDataAccessException with the specified detail message and cause.
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public IngredientDataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}



