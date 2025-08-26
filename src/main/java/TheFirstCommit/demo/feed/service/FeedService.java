package TheFirstCommit.demo.feed.service;

import TheFirstCommit.demo.family.entity.FamilyEntity;
import TheFirstCommit.demo.feed.dto.CreateFeedRequestDto;
import TheFirstCommit.demo.feed.dto.ResponseFeedDto;
import TheFirstCommit.demo.feed.dto.UpdateFeedRequestDto;
import TheFirstCommit.demo.user.entity.UserEntity;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface FeedService {
    void createFeed(CreateFeedRequestDto requestDto, UserEntity user);
    void updateFeed(Long feedId, UpdateFeedRequestDto requestDto, UserEntity user);
    void deleteFeed(Long feedId, UserEntity user);
    List<ResponseFeedDto> getFeedDtoList(FamilyEntity family);
    void canFeed(UserEntity user);
}