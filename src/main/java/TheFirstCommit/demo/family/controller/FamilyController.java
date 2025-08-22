package TheFirstCommit.demo.family.controller;

import TheFirstCommit.demo.common.SuccessResponse;
import TheFirstCommit.demo.family.dto.request.RequestElderDto;
import TheFirstCommit.demo.family.dto.request.RequestJoinFamilyDto;
import TheFirstCommit.demo.family.dto.request.RequestNewFamilyDto;
import TheFirstCommit.demo.family.dto.page.FamilyPageDto;
import TheFirstCommit.demo.family.dto.page.HomePageDto;
import TheFirstCommit.demo.family.service.FamilyPageDtoService;
import TheFirstCommit.demo.family.service.FamilyService;
import TheFirstCommit.demo.user.entity.UserEntity;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class FamilyController {

    private final FamilyService familyService;
    private final FamilyPageDtoService familyPageDtoService;

    @PatchMapping("/api/family")
    public ResponseEntity updateFamilyPayment(@AuthenticationPrincipal UserEntity user, @RequestBody RequestNewFamilyDto dto) {
        familyService.updateFamilyPaymentDay(user, dto.getPaymentDay());
        return ResponseEntity.ok().body(new SuccessResponse("success", null));
    }

    @PostMapping("/social/family")
    public ResponseEntity createNewFamily(
        @ModelAttribute RequestNewFamilyDto dto ,
        @RequestPart(required = true, name = "elder") RequestElderDto elderDto ,
        @RequestPart(required = true, name = "elderImg") MultipartFile elderImgFile,
        @AuthenticationPrincipal UserEntity user)
    {
        familyService.save(user, dto, elderDto, elderImgFile);
        return ResponseEntity.ok().body(new SuccessResponse("success", null));
    }

    @PatchMapping("/api/family/elder")
    public ResponseEntity updateFamily(
        @AuthenticationPrincipal UserEntity user,
        @ModelAttribute RequestElderDto dto ,
        @RequestPart(name="elderImg", required = false) MultipartFile elderImgFile)
    {
        familyService.updateFamily(user, dto, elderImgFile);
        return ResponseEntity.ok().body(new SuccessResponse("success", null));
    }

    @GetMapping("/api/family/invite")
    public ResponseEntity getFamilyCode(@AuthenticationPrincipal UserEntity user) {
        String familyCode = familyService.getFamilyCode(user);
        Long familyCount = familyService.getFamilyDto(user).getMemberCount();
        return ResponseEntity.ok().body(new SuccessResponse("success", Map.of("familyCode", familyCode, "familyCount", familyCount)));
    }

    @PostMapping("/social/family/invite")
    public ResponseEntity joinFamily(@AuthenticationPrincipal UserEntity user, @RequestBody RequestJoinFamilyDto dto) {
        familyService.joinFamily(user, dto);
        return ResponseEntity.ok().body(new SuccessResponse("success", null));
    }

    @PutMapping("/social/family/invite")
    public ResponseEntity checkJoinFamily(
        @RequestBody Map<String, String> body
    ) {
        if(familyService.getFamilyByCode(body.get("familyCode")).isPresent())
            return ResponseEntity.ok().body(new SuccessResponse("exist", null));
        else 
            return ResponseEntity.ok().body(new SuccessResponse("not exist", null));
    }
    
    @GetMapping("/api/family")
    public ResponseEntity getFamilyData(@AuthenticationPrincipal UserEntity user) {
        FamilyPageDto dto = familyPageDtoService.getFamilyPage(user);
        return ResponseEntity.ok().body(new SuccessResponse("success", dto));
    }

    @GetMapping("/api/family/home")
    public ResponseEntity getFamilyHomePageDto(@AuthenticationPrincipal UserEntity user) {
        HomePageDto dto = familyPageDtoService.getHomePageDto(user);
        return ResponseEntity.ok().body(new SuccessResponse("success", dto));
    }
}
