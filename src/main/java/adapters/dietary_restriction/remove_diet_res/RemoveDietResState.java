package adapters.dietary_restriction.remove_diet_res;

public class RemoveDietResState {
    private String message = "";
    private boolean isSuccess;

    public RemoveDietResState() {
        // Nothing to initialize
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
