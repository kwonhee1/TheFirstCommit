package TheFirstCommit.demo.exception;

public class CustomException extends RuntimeException {
    public ErrorCode errorCode;
    public String errorMessage;

    public CustomException(ErrorCode errorCode, String... args) {
        //super(String.format(errorCode.customMessage, args));
        this.errorCode = errorCode;
        this.errorMessage = String.format(errorCode.customMessage, args);
    }
}