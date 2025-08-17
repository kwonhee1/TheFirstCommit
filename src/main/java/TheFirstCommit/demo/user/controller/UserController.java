package TheFirstCommit.demo.user.controller;

import TheFirstCommit.demo.common.SuccessResponse;
import TheFirstCommit.demo.user.dto.RequestUpdateUserInfoDto;
import TheFirstCommit.demo.user.entity.UserEntity;
import TheFirstCommit.demo.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PatchMapping
    public ResponseEntity updateUserInfo(@AuthenticationPrincipal UserEntity user, @ModelAttribute RequestUpdateUserInfoDto dto) {
        log.info(dto.toString());
        userService.update(user, dto);
        return ResponseEntity.ok().body(new SuccessResponse("success", null));
    }
}
