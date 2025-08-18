package TheFirstCommit.demo.exception;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity handleCustomException(CustomException e) {
        return ResponseEntity.status(e.errorCode.statusCode).body(Map.of("code", e.errorCode.customErrorCode, "message", e.errorMessage));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(500).body(e.getMessage());
    }
}
