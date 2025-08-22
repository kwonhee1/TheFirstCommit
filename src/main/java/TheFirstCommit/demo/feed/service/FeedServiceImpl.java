package TheFirstCommit.demo.feed.service;

import TheFirstCommit.demo.exception.CustomException;
import TheFirstCommit.demo.exception.ErrorCode;
import TheFirstCommit.demo.family.PaymentDay;
import TheFirstCommit.demo.family.entity.FamilyEntity;
import TheFirstCommit.demo.feed.dto.CreateFeedRequestDto;
import TheFirstCommit.demo.feed.dto.ResponseFeedDto;
import TheFirstCommit.demo.feed.dto.UpdateFeedRequestDto;
import TheFirstCommit.demo.feed.entity.FeedEntity;
import TheFirstCommit.demo.feed.repository.FeedRepository;
import TheFirstCommit.demo.img.ImgEntity;
import TheFirstCommit.demo.img.ImgService;
import TheFirstCommit.demo.feed.entity.ImgFeedEntity;
import TheFirstCommit.demo.feed.repository.ImgFeedRepository;
import TheFirstCommit.demo.payment.service.PaymentService;
import TheFirstCommit.demo.user.entity.UserEntity;
import TheFirstCommit.demo.user.service.UserValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {

    private final FeedRepository feedRepository;
    private final ImgService imgService;
    private final ImgFeedRepository imgFeedRepository;
    private final UserValidateService userValidateService;
    private final PaymentService paymentService;

    @Override
    @Transactional
    public void createFeed(CreateFeedRequestDto requestDto, UserEntity user) {
        List<MultipartFile> imageFiles = requestDto.getImageFiles();
        FamilyEntity family = userValidateService.getFamily(user);

        canFeed(user); // check can feed

        // 1. 피드 텍스트 내용 저장
        FeedEntity feed = FeedEntity.builder()
                .text(requestDto.getText())
                .layout(requestDto.getLayout())
                .user(user)
                .family(family)
                .build();
        feedRepository.save(feed);

        // 2. 이미지 파일들을 IPFS(Pinata)에 업로드하고 DB에 정보 저장
        if (imageFiles != null && !imageFiles.isEmpty()) {
            for (MultipartFile imageFile : imageFiles) {
                // ImgService를 통해 이미지를 IPFS에 업로드하고 ImgEntity를 반환받음
                ImgEntity imgEntity = imgService.save(imageFile);

                // 3. 피드와 이미지를 중간 테이블(ImgFeedEntity)로 연결
                if (imgEntity != null) {
                    ImgFeedEntity imgFeed = ImgFeedEntity.builder()
                            .feed(feed)
                            .img(imgEntity)
                            .build();
                    imgFeedRepository.save(imgFeed);
                }
            }
        }
    }

    @Override
    @Transactional
    public void updateFeed(Long feedId, UpdateFeedRequestDto requestDto, UserEntity user) {
        List<MultipartFile> addImageFiles = requestDto.getImageFiles();
        // 1. 피드 조회 및 작성자 확인
        FeedEntity feed = feedRepository.findById(feedId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "feed") );

        if (feed.getUser().getId() != user.getId()) {
            new CustomException(ErrorCode.NOT_AUTHOR); // CustomException으로 변경 권장
        }

        // 2. 텍스트 정보 업데이트 (요청에 값이 있을 경우에만 변경)
        if (requestDto.getText() != null) feed.updateText(requestDto.getText());
        if (requestDto.getLayout() != null) feed.updateLayout(requestDto.getLayout());

        // 3. 기존 이미지 삭제
        if (requestDto.getDeleteImgIds() != null && !requestDto.getDeleteImgIds().isEmpty()) {
            List<ImgFeedEntity> toDelete = imgFeedRepository.findAllByFeedIdAndImgIdIn(feedId, requestDto.getDeleteImgIds());
            for (ImgFeedEntity imgFeed : toDelete) {
                imgService.delete(imgFeed.getImg()); // Pinata에서 파일 삭제 및 ImgEntity DB에서 삭제
            }
            imgFeedRepository.deleteAll(toDelete);
        }

        // 4. 새로운 이미지 추가
        if (addImageFiles != null && !addImageFiles.isEmpty()) {
            for (MultipartFile imageFile : addImageFiles) {
                ImgEntity imgEntity = imgService.save(imageFile);
                if (imgEntity != null) {
                    ImgFeedEntity imgFeed = ImgFeedEntity.builder()
                            .feed(feed)
                            .img(imgEntity)
                            .build();
                    imgFeedRepository.save(imgFeed);
                }
            }
        }
    }

    @Override
    @Transactional
    public void deleteFeed(Long feedId, UserEntity user){
        FeedEntity feed = feedRepository.findById(feedId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "feed"));

        if (feed.getUser().getId() != user.getId()){
            throw new CustomException(ErrorCode.NOT_AUTHOR);
        }

        List<ImgFeedEntity> imgFeedsToDelete = List.copyOf(feed.getImgFeeds());
        for(ImgFeedEntity imgFeed : imgFeedsToDelete){
            imgService.delete(imgFeed.getImg());
        }

        feedRepository.delete(feed);
    }

    @Override
    public void canFeed(UserEntity user) {
        FamilyEntity family = userValidateService.getFamily(user);

        if(family.isChanged() && user.isLeader())
            throw new CustomException(ErrorCode.NEW_LEADER);

        if(family.isChanged())
            throw new CustomException(ErrorCode.LEADER_CHANGED);

        if(getFeedCountByFamily(family) >= 3 && paymentService.getCardOpt(userValidateService.findLeader(user)).isEmpty()){
            throw new CustomException(ErrorCode.NOT_EXIST_CARD);
        }

        if(getFeedCountByFamily(family) >= 20)
            throw new CustomException(ErrorCode.MAX_FEED);
    }

    @Override
    public List<ResponseFeedDto> getFeedDtoList(FamilyEntity family){
        List<FeedEntity> feedList = feedRepository.findByFamily(family.getId());
        return feedList.stream().map(ResponseFeedDto::of).toList();
    }

    private Long getFeedCountByFamily(FamilyEntity family){
        return feedRepository.countByFamily(family.getId());
    }

    @Scheduled(cron = "${payment.second-week-sunday}")
    public void deleteAllFeeds() {
        feedRepository.deleteAll(PaymentDay.SECOND_SUNDAY.name());
    }
    @Scheduled(cron = "${payment.four-week-sunday}")
    public void deleteAllFeeds2() {
        feedRepository.deleteAll(PaymentDay.FOURTH_SUNDAY.name());
    }
}