package data_access.user_recipe;

public class InaccessibleFileException extends RuntimeException {
    public InaccessibleFileException(String message) {
        super(message + "\nThis file couldn't be changed or read, please change permissions and try again.");
    }
}
