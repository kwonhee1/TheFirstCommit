package TheFirstCommit.demo.exception;

public class CustomException extends RuntimeException {
    public ErrorCode errorCode;
    public String errorMessage;

    public CustomException(ErrorCode errorCode, String... args) {
        super(String.format("%s", args));
        this.errorCode = errorCode;
        this.errorMessage = String.format("%s", args);
    }
}