package TheFirstCommit.demo.news.controller;

import TheFirstCommit.demo.common.SuccessResponse;
import TheFirstCommit.demo.family.entity.FamilyEntity;
import TheFirstCommit.demo.news.dto.ResponseNewsBoxDto;
import TheFirstCommit.demo.news.service.NewsQueryService;
import TheFirstCommit.demo.news.service.NewsService;
import TheFirstCommit.demo.user.entity.UserEntity;
import TheFirstCommit.demo.user.service.UserValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsQueryService newsQueryService;
    private final NewsService newsService;
    private final UserValidateService userValidateService;

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<?> getNewsList(@AuthenticationPrincipal UserEntity user) {
        FamilyEntity family = userValidateService.getFamily(user);

        ResponseNewsBoxDto response = newsQueryService.getNewsBox(family);
        return ResponseEntity.ok().body(new SuccessResponse("success", response));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional
    public ResponseEntity<?> createNews(
            @AuthenticationPrincipal UserEntity user,
            @RequestPart("file") MultipartFile pdfFile) {

        FamilyEntity family = userValidateService.getFamily(user);

        newsService.createNews(pdfFile, family);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new SuccessResponse("success", null));
    }
}