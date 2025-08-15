package TheFirstCommit.demo.common;

import TheFirstCommit.demo.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;

public class FailResponseUtil {

    public static void writeResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(), errorCode.toResponseBody());
    }

}
