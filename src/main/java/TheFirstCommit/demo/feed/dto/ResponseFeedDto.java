package TheFirstCommit.demo.feed.dto;

import TheFirstCommit.demo.feed.entity.FeedEntity;
import TheFirstCommit.demo.img.ImgEntity;
import TheFirstCommit.demo.imgFeed.ImgFeedEntity;
import TheFirstCommit.demo.user.dto.response.ResponseUserProfileDto;
import TheFirstCommit.demo.user.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("author")
    private ResponseUserProfileDto author;
    @JsonProperty("isAuthor")
    private boolean isAuthor;

    private LocalDateTime createAt;

    public static ResponseFeedDto of(FeedEntity feed, UserEntity author) {
        ResponseFeedDto dto = new ResponseFeedDto();
        dto.text = feed.getText();
        dto.createAt = feed.getCreatedAt();
        dto.isAuthor = author.getId() == feed.getUser().getId();
        dto.author = ResponseUserProfileDto.of(author);
        dto.imgs = feed.getImgFeeds().stream()
                        .map(ImgFeedEntity::getImg)
                        .map(ImgEntity::getCid)
                        .toList();
        return dto;
    }
}
