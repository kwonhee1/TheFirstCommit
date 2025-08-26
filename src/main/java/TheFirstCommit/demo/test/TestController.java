package TheFirstCommit.demo.test;

import TheFirstCommit.demo.user.entity.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/public/test")
    public ResponseEntity publicTest() {
        Authentication authentic = SecurityContextHolder.getContext().getAuthentication();

        if(authentic.getAuthorities().contains("ROLE_"+UserRole.ADMIN.getAuthorities()))
            return ResponseEntity.ok().body(UserRole.ADMIN.name());
        if(authentic.getAuthorities().contains("ROLE_"+UserRole.USER.getAuthorities()))
            return ResponseEntity.ok().body(UserRole.USER.name());
        if(authentic.getAuthorities().contains("ROLE_"+UserRole.SOCIAL.getAuthorities()))
            return ResponseEntity.ok().body(UserRole.SOCIAL.name());

        return ResponseEntity.ok().body("no");
    }

}
