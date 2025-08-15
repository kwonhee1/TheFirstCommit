package TheFirstCommit.demo.exception;

import java.util.Map;
import org.springframework.http.HttpStatus;

public enum ErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "00000", "Not Found %s"),

    // token 관련
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "00001", "Invalid token"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "00002", "Expired token"),
    ;

    public HttpStatus statusCode;
    public String CustomErrorCode;
    public String errorMessage;

    ErrorCode(HttpStatus statusCode, String CustomErrorCode, String errorMessage) {
        this.statusCode = statusCode; this.errorMessage = errorMessage; this.CustomErrorCode = CustomErrorCode;
    }
    public Map<String, String> toResponseBody() {
        return Map.of("code", CustomErrorCode, "message", errorMessage);
    }
}
