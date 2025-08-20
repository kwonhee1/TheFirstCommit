package TheFirstCommit.demo.feed.controller;

import TheFirstCommit.demo.common.SuccessResponse;
import TheFirstCommit.demo.feed.dto.UpdateFeedRequestDto;
import TheFirstCommit.demo.feed.service.FeedService;
import TheFirstCommit.demo.feed.dto.CreateFeedRequestDto;
import TheFirstCommit.demo.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
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
            @ModelAttribute CreateFeedRequestDto requestDto) {

        feedService.createFeed(requestDto, user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessResponse("Feed created successfully.", null));
    }

    @PatchMapping("/{feedId}")
    public ResponseEntity<?> updateFeed(
            // 여기에도 ("feedId")를 명시해주어야 합니다.
            @PathVariable("feedId") Long feedId,
            @AuthenticationPrincipal UserEntity user,
            @ModelAttribute UpdateFeedRequestDto requestDto) {

        feedService.updateFeed(feedId, requestDto, user);

        return ResponseEntity.ok(new SuccessResponse("Feed updated successfully.", null));
    }
    @DeleteMapping("/{feedId}")
    public ResponseEntity<?> deleteFeed(@PathVariable("feedId") Long feedId,
                                        @AuthenticationPrincipal UserEntity user){
        feedService.deleteFeed(feedId, user);
        return ResponseEntity.ok(new SuccessResponse("Feed deleted successfully.",null));
    }
}