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
            return dto;
        }
        dto.id = img.getId();
        dto.cid = img.getCid();
        return dto;
    }
}
