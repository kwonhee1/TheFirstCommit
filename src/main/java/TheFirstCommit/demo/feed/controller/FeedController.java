package TheFirstCommit.demo.feed.controller;

import TheFirstCommit.demo.common.SuccessResponse;
import TheFirstCommit.demo.feed.service.FeedService;
import TheFirstCommit.demo.feed.dto.CreateFeedRequestDto;
import TheFirstCommit.demo.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/feeds")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createFeed(
            @AuthenticationPrincipal UserEntity user,
            @RequestPart("request") CreateFeedRequestDto requestDto,
            @RequestPart(value = "images", required = false) List<MultipartFile> imageFiles) {

        feedService.createFeed(requestDto, imageFiles, user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessResponse("Feed created successfully.", null));
    }
}