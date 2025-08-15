package TheFirstCommit.demo.config.security.util;

public class NoTokenException extends RuntimeException {
    public NoTokenException() {
        super("no input token");
    }
}
