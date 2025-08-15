package TheFirstCommit.demo.exception;

import java.util.Map;
import org.springframework.http.HttpStatus;

public enum ErrorCode {
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "00000", "권한 없음"),
    SOCIAL_AUTHORIZED(HttpStatus.FORBIDDEN, "00009", "가족구성을 완성해주세요"),

    NOT_FOUND(HttpStatus.NOT_FOUND, "00001", "Not Found %s"),

    // token 관련
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "10001", "Invalid token"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "10002", "Expired token"),
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
