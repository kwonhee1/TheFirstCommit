package TheFirstCommit.demo.family.service;

import TheFirstCommit.demo.exception.CustomException;
import TheFirstCommit.demo.exception.ErrorCode;
import TheFirstCommit.demo.family.FamilyCodeUtil;
import TheFirstCommit.demo.family.dto.request.RequestElderDto;
import TheFirstCommit.demo.family.dto.request.RequestJoinFamilyDto;
import TheFirstCommit.demo.family.dto.request.RequestNewFamilyDto;
import TheFirstCommit.demo.family.dto.response.ResponseFamilyDto;
import TheFirstCommit.demo.family.entity.ElderEntity;
import TheFirstCommit.demo.family.entity.FamilyEntity;
import TheFirstCommit.demo.family.repository.ElderRepository;
import TheFirstCommit.demo.family.repository.FamilyRepository;
import TheFirstCommit.demo.img.ImgEntity;
import TheFirstCommit.demo.img.ImgService;
import TheFirstCommit.demo.user.dto.UpdateUserFamilyDto;
import TheFirstCommit.demo.user.entity.UserEntity;
import TheFirstCommit.demo.user.service.UserService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FamilyService {

    private final FamilyRepository familyRepository;
    private final ElderRepository elderRepository;
    private final UserService userService;
    private final ImgService imgService;

    public void save(UserEntity user, RequestNewFamilyDto dto, MultipartFile elderImgFile) {
        // family 저장, elder 저장, user family update
        ImgEntity imgEntity = imgService.save(elderImgFile);
        FamilyEntity family = familyRepository.save(dto.toFamilyEntity());
        elderRepository.save(dto.toElderEntity(family, imgEntity));
        userService.updateUserFamily(user, new UpdateUserFamilyDto(family, dto.getRelation(), true));
    }

    // elder 정보 수정 (leader 만 가능!)
    public void updateFamily(UserEntity user, RequestElderDto dto, MultipartFile elderImgFile) {
        if(!user.isLeader())
            throw new CustomException(ErrorCode.NOT_LEADER);
        ElderEntity elder = elderRepository.findByUserId(user.getId()).orElseThrow(
            ()->{return new CustomException(ErrorCode.NOT_FOUND, "elder");}
        );

        elder.update(dto);
        if(elderImgFile != null) {
            imgService.update(elder.getImg(), elderImgFile);
        }
        elderRepository.save(elder);
    }

    public String getFamilyCode(UserEntity user) {
        if(user.getFamily() == null)
            throw new CustomException(ErrorCode.NOT_FOUND, "family");
        return FamilyCodeUtil.encode(user.getFamily().getId());
    }

    // join with family code
    public void joinFamily(UserEntity user, RequestJoinFamilyDto dto) {
        FamilyEntity family = getFamilyByCode(dto.getCode()).orElseThrow(()->{return new CustomException(ErrorCode.NOT_FOUND, "family");});
        userService.updateUserFamily(user, new UpdateUserFamilyDto(family, dto.getRelation(), false));
    }

    // 유져별 가족 정보 확인 (main page에서 사용, 모든 정보 렌더링)
    public ResponseFamilyDto getFamilyData(UserEntity user) {
        return null;
    }

    // --------- validate

    public Optional<FamilyEntity> getFamilyByCode(String familyCode){
        return familyRepository.findById(FamilyCodeUtil.decode(familyCode));
    }

    public List<FamilyEntity> findAll() {
        return familyRepository.findAll();
    }

}
