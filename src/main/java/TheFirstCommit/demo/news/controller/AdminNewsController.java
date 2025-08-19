package TheFirstCommit.demo.news.controller;

import TheFirstCommit.demo.news.service.NewsService;
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

import java.io.IOException;

@RestController
@RequestMapping("/admin/news") // 관리자용 URL
@RequiredArgsConstructor
public class AdminNewsController {

    private final NewsService newsService; // 생성/수정/삭제를 담당하는 서비스

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createNews(
            @AuthenticationPrincipal UserEntity user,
            @RequestPart("file") MultipartFile pdfFile) {


        if (user == null || user.getFamily() == null) {
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
            newsService.createNews(pdfFile, user.getFamily());
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("소식지가 성공적으로 생성되었습니다.");
        } catch (IOException e) {
            // log.error("파일 처리 중 오류", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("파일을 처리하는 중 오류가 발생했습니다.");
        } catch (Exception e) {
            // log.error("소식지 생성 중 알 수 없는 오류", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("소식지를 생성하는 중 알 수 없는 오류가 발생했습니다.");
        }
    }
}