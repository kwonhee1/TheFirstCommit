package TheFirstCommit.demo.exception;

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
    TOO_MANY_CARDS(HttpStatus.BAD_REQUEST, "20003", "too many cards"),

    // img 관련
    SAVE_FAIL(HttpStatus.BAD_REQUEST, "30001", "save fail"),
    REMOVE_FAIL(HttpStatus.BAD_REQUEST, "30002", "remove fail"),

    // feed 관련
    NEW_LEADER(HttpStatus.BAD_REQUEST, "40001", "new leader"),
    LEADER_CHANGED(HttpStatus.BAD_REQUEST, "40002", "leader changed"),
    NOT_AUTHOR(HttpStatus.BAD_REQUEST, "40003", "not author"),
    MAX_FEED(HttpStatus.BAD_REQUEST, "40004", "max feed"),

    // family 관련 500??
    NOT_LEADER(HttpStatus.BAD_REQUEST, "50001", "not leader"),
    CHOOSE_NEXT_LEADER(HttpStatus.BAD_REQUEST, "50002", "choose next leader"),
    ;

    public HttpStatus statusCode;
    public String customErrorCode;
    public String customMessage;

    ErrorCode(HttpStatus statusCode, String CustomErrorCode, String customMessage) {
        this.statusCode = statusCode; this.customMessage = customMessage; this.customErrorCode = CustomErrorCode;
    }
}
