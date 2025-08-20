package TheFirstCommit.demo.feed.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class UpdateFeedRequestDto {
    // 수정할 텍스트 정보
    private String text;
    private Integer layout;
    // 삭제할 기존 이미지들의 ID 목록
    private List<Long> deleteImgIds;
    private List<MultipartFile> imageFiles;
}