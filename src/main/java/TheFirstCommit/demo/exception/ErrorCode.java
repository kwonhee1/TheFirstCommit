package TheFirstCommit.demo.exception;

import java.util.Map;
import org.springframework.http.HttpStatus;

public enum ErrorCode {
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "00000", "권한 없음"),
    SOCIAL_AUTHORIZED(HttpStatus.FORBIDDEN, "00009", "가족구성을 완성해주세요"),

    NOT_FOUND(HttpStatus.NOT_FOUND, "00001", "Not Found %s"),
    ALREADY_EXIST(HttpStatus.BAD_REQUEST, "00002", "already exist %s") ,

    // token 관련
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "10001", "Invalid token"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "10002", "Expired token"),

    // 결제 관련
    FAIL_SAVE_CARD(HttpStatus.BAD_REQUEST, "20001", "fail save card"),
    NOT_EXIST_CARD(HttpStatus.BAD_REQUEST, "20002", "not exist card"),

    // family 관련 500??
    NOT_LEADER(HttpStatus.BAD_REQUEST, "50001", "not leader"),
    ;

    public HttpStatus statusCode;
    public String CustomErrorCode;
    public String errorMessage;

    ErrorCode(HttpStatus statusCode, String CustomErrorCode, String errorMessage) {
        this.statusCode = statusCode; this.errorMessage = errorMessage; this.CustomErrorCode = CustomErrorCode;
    }
}
