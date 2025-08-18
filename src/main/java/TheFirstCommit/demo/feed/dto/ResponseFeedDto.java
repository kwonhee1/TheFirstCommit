package TheFirstCommit.demo.feed.dto;

import TheFirstCommit.demo.feed.entity.FeedEntity;
import TheFirstCommit.demo.img.ImgEntity;
import TheFirstCommit.demo.imgFeed.ImgFeedEntity;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class ResponseFeedDto {

    private List<String> imgs;
    private String text;

    private LocalDateTime createAt;

    public static ResponseFeedDto of(FeedEntity feed) {
        ResponseFeedDto dto = new ResponseFeedDto();
        dto.text = feed.getText();
        dto.createAt = feed.getCreatedAt();
        dto.imgs = feed.getImgFeeds().stream()
                        .map(ImgFeedEntity::getImg)
                        .map(ImgEntity::getCid)
                        .toList();
        return dto;
    }
}
