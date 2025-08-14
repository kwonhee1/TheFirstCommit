package TheFirstCommit.demo.exception;

import java.util.Map;
import org.springframework.http.HttpStatus;

public enum ErrorCode {
    TEST_ERROR(HttpStatus.BAD_REQUEST, "00000", "Test error %s"),
    ;

    public HttpStatus statusCode;
    public String CustomErrorCode;
    public String errorMessage;

    ErrorCode(HttpStatus statusCode, String CustomErrorCode, String errorMessage) {
        this.statusCode = statusCode; this.errorMessage = errorMessage; this.CustomErrorCode = CustomErrorCode;
    }
    public Object toResponseBody() {
        return Map.of("code", CustomErrorCode, "message", errorMessage);
    }
}
