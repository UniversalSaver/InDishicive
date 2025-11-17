package data_access;

public class CorruptDataException extends RuntimeException {
    public CorruptDataException(String message) {
        super(message);
    }
}
