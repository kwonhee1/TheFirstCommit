package TheFirstCommit.demo.test;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/api/test")
    public ResponseEntity test() {
        return ResponseEntity.ok("Hello World");
    }

    @GetMapping("/public/test")
    public ResponseEntity publicTest() {
        return ResponseEntity.ok("Hello World");
    }
}
