package databases.user_recipe;

public class CorruptDataException extends RuntimeException {
    public CorruptDataException(String message) {
        super(message);
    }
}
