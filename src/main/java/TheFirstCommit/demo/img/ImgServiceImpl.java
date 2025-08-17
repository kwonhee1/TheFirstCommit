package TheFirstCommit.demo.img;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImgServiceImpl implements ImgService {

    private final ImgRepository imgRepository;

    @Override
    public ImgEntity getImg(String imgURL) {
        return null;
    }

    @Override
    public ImgEntity save(MultipartFile file) {
        return ImgEntity.builder().cid("cid").build();
    }

    @Override
    public ImgEntity update(ImgEntity old, MultipartFile file) {
        return old;
    }

    @Override
    public void delete(ImgEntity img) {

    }
}
