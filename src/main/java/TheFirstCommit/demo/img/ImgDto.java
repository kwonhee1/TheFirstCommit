package TheFirstCommit.demo.img;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class ImgDto {

    private String cid;
    private Long id;

    public static ImgDto of(ImgEntity img) {
        ImgDto dto = new ImgDto();
        if(img == null) {
            return defaultImg();
        }
        dto.id = img.getId();
        dto.cid = img.getCid();
        return dto;
    }

    private static ImgDto defaultImg() {
        ImgDto dto = new ImgDto();
        dto.cid = "기본이미지 url 추후 수정";
        dto.id = 0L;
        return dto;
    }
}
