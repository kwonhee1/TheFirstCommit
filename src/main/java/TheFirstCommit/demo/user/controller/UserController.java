package TheFirstCommit.demo.user.controller;

import TheFirstCommit.demo.user.dto.RequestUpdateUserInfoDto;
import TheFirstCommit.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/user/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PatchMapping("")
    public ResponseEntity updateUserInfo(@RequestBody RequestUpdateUserInfoDto dto) {
        return null;
    }
}
