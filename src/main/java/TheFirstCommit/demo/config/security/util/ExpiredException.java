package TheFirstCommit.demo.config.security.util;

public class ExpiredException extends RuntimeException {

    public ExpiredException() {
        super("token expired");
    }
}
