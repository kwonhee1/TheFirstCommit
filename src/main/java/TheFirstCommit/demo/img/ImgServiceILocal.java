package TheFirstCommit.demo.img;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
public class ImgServiceILocal implements ImgService {

    private final ImgRepository imgRepository;


    @Override
    public ImgEntity getImg(String imgURL) {
        return null;
    }

    @Override
    public ImgEntity save(MultipartFile file) {
        return ImgEntity.builder().cid("local").build();
    }

    @Override
    public void update(ImgEntity old, MultipartFile file) {
        return;
    }

    @Override
    public void delete(ImgEntity img) {
        ;
    }
}
