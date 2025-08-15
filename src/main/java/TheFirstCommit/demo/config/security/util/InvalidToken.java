package TheFirstCommit.demo.config.security.util;

public class InvalidToken extends RuntimeException {

    public InvalidToken() {
        super("invalid token");
    }
}
