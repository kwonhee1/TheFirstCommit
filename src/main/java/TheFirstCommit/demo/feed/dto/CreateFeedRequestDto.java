package TheFirstCommit.demo.feed.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class CreateFeedRequestDto {
    private String text;
    private Integer layout;
    private List<MultipartFile> imageFiles;
}