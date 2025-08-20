package TheFirstCommit.demo.news.controller;

import TheFirstCommit.demo.news.dto.NewsListResponseDto;
import TheFirstCommit.demo.news.service.NewsQueryService;
import TheFirstCommit.demo.news.service.NewsService;
import TheFirstCommit.demo.user.entity.UserEntity;
import TheFirstCommit.demo.user.repository.UserRepository; // UserRepository 임포트
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsQueryService newsQueryService;
    private final NewsService newsService;
    private final UserRepository userRepository; // UserRepository 주입

    @GetMapping
    public ResponseEntity<?> getNewsList(@AuthenticationPrincipal UserEntity user) {
        // LAZY 로딩 문제를 해결하기 위해 DB에서 사용자 정보를 다시 조회
        UserEntity fullUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다. ID: " + user.getId()));

        if (fullUser.getFamily() == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("소속된 가족이 없어 소식지를 조회할 수 없습니다.");
        }

        List<NewsListResponseDto> newsList = newsQueryService.findAllNews(fullUser.getFamily());
        return ResponseEntity.ok(newsList);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createNews(
            @AuthenticationPrincipal UserEntity user,
            @RequestPart("file") MultipartFile pdfFile) {

        // LAZY 로딩 문제를 해결하기 위해 DB에서 사용자 정보를 다시 조회
        UserEntity fullUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다. ID: " + user.getId()));

        if (fullUser.getFamily() == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("로그인 정보 또는 소속된 가족 정보가 없습니다.");
        }

        if (pdfFile == null || pdfFile.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("PDF 파일이 첨부되지 않았습니다.");
        }

        try {
            newsService.createNews(pdfFile, fullUser.getFamily());
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("소식지가 성공적으로 생성되었습니다.");
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("소식지를 생성하는 중 오류가 발생했습니다.");
        }
    }
}