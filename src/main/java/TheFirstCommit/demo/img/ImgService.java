package TheFirstCommit.demo.img;

import org.springframework.web.multipart.MultipartFile;

public interface ImgService {
    ImgEntity getImg(String imgURL); // save img from online URL :: for social login
    ImgEntity save(MultipartFile file);
    ImgEntity update(ImgEntity old, MultipartFile file); // remove old img(by cid) and create new img :: update ImgEntity cid
    void delete(ImgEntity img);
}
