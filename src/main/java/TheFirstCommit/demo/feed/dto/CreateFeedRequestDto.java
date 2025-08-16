package TheFirstCommit.demo.feed.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateFeedRequestDto {
    private String text;
    private Integer layout;
}