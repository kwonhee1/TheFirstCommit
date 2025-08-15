package TheFirstCommit.demo.common;

import lombok.Getter;

@Getter
public class SuccessResponse {

    private String message;
    private Object data;

    public SuccessResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }

}