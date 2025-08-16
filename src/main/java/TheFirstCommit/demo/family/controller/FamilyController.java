package TheFirstCommit.demo.family.controller;

import TheFirstCommit.demo.common.SuccessResponse;
import TheFirstCommit.demo.family.dto.request.RequestElderDto;
import TheFirstCommit.demo.family.dto.request.RequestJoinFamilyDto;
import TheFirstCommit.demo.family.dto.request.RequestNewFamilyDto;
import TheFirstCommit.demo.family.service.FamilyService;
import TheFirstCommit.demo.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class FamilyController {

    private final FamilyService familyService;

    @PostMapping("/social/family")
    public ResponseEntity createNewFamily(
        @ModelAttribute RequestNewFamilyDto dto ,
        @RequestPart(required = true, name = "elderImg") MultipartFile elderImgFile,
        @AuthenticationPrincipal UserEntity user)
    {
        familyService.save(user, dto, elderImgFile);
        return ResponseEntity.ok().body(new SuccessResponse("success", null));
    }

    @PatchMapping("/api/family/elder")
    public ResponseEntity updateFamily(
        @AuthenticationPrincipal UserEntity user,
        @ModelAttribute RequestElderDto dto ,
        @RequestPart(name="elderImg") MultipartFile elderImgFile)
    {
        familyService.updateFamily(user, dto, elderImgFile);
        return ResponseEntity.ok().body(new SuccessResponse("success", null));
    }

    @GetMapping("/api/family/code")
    public ResponseEntity getFamilyCode(@AuthenticationPrincipal UserEntity user) {
        return ResponseEntity.ok().body(new SuccessResponse("success", familyService.getFamilyCode(user)));
    }

    @PostMapping("/social/family/join")
    public ResponseEntity joinFamily(@AuthenticationPrincipal UserEntity user, @RequestBody RequestJoinFamilyDto dto) {
        familyService.joinFamily(user, dto);
        return ResponseEntity.ok().body(new SuccessResponse("success", null));
    }

    @GetMapping("/social/family/join")
    public ResponseEntity checkJoinFamily(
        @RequestParam(name = "code") String familyCode
    ) {
        if(familyService.getFamilyByCode(familyCode).isPresent())
            return ResponseEntity.ok().body(new SuccessResponse("exist", null));
        else 
            return ResponseEntity.ok().body(new SuccessResponse("not exist", null));
    }
    
    @GetMapping("/api/family")
    // 추후 추가 수정
    public ResponseEntity getFamilyData(@AuthenticationPrincipal UserEntity user) {
        return ResponseEntity.ok().body(new SuccessResponse("success", familyService.getFamilyData(user)));
    }
}
