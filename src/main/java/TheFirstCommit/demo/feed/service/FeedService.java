package TheFirstCommit.demo.feed.service;

import TheFirstCommit.demo.feed.dto.CreateFeedRequestDto;
import TheFirstCommit.demo.feed.dto.UpdateFeedRequestDto;
import TheFirstCommit.demo.user.entity.UserEntity;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface FeedService {
    void createFeed(CreateFeedRequestDto requestDto, List<MultipartFile> imageFiles, UserEntity user);
    void updateFeed(Long feedId, UpdateFeedRequestDto requestDto, List<MultipartFile> addImageFiles, UserEntity user);
}