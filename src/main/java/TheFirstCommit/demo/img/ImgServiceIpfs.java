package TheFirstCommit.demo.img;

import TheFirstCommit.demo.exception.CustomException;
import TheFirstCommit.demo.exception.ErrorCode;
import io.ipfs.api.IPFS;
import io.ipfs.api.NamedStreamable;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
public class ImgServiceIpfs implements ImgService {

    private final ImgRepository imgRepository;
    private final IPFS ipfs;

    public ImgServiceIpfs(
        IPFS ipfs,
        ImgRepository imgRepository
    ) {
        this.ipfs = ipfs;
        this.imgRepository = imgRepository;
    }

    @Override
    @Transactional
    public ImgEntity getImg(String imgURL) {
        try {
            byte[] imageBytes = downloadImage(imgURL);
            NamedStreamable.ByteArrayWrapper file = new NamedStreamable.ByteArrayWrapper(imageBytes);
            String cid = ipfs.add(file).get(0).hash.toString();
            return ImgEntity.builder().cid(cid).build();
        } catch (Exception e){
            return null;
        }
    }

    private byte[] downloadImage(String imageUrl) throws Exception {
        try (InputStream in = new URL(imageUrl).openStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            return out.toByteArray();
        }
    }

    @Override
    @Transactional
    public ImgEntity save(MultipartFile file) {
        String cid;
        try {
            NamedStreamable streamable = new NamedStreamable.ByteArrayWrapper(file.getBytes());
            cid = ipfs.add(streamable).get(0).hash.toString();
            log.info("ipfs :: save " + cid);
        } catch (IOException e) {
            throw new CustomException(ErrorCode.SAVE_FAIL);
        }
        return ImgEntity.builder().cid(cid).build();
    }

    @Override
    @Transactional
    public void update(ImgEntity old, MultipartFile file) {
        try {
            if(old.getCid() != null)
                ipfs.pin.rm(io.ipfs.multihash.Multihash.fromBase58(old.getCid()));
            log.info("ipfs :: remove " + old.getId() + "," + old.getCid());
        } catch (IOException e) {
            throw new CustomException(ErrorCode.REMOVE_FAIL);
        }

        String cid;
        try {
            NamedStreamable streamable = new NamedStreamable.ByteArrayWrapper(file.getBytes());
            cid = ipfs.add(streamable).get(0).hash.toString();
            log.info("ipfs :: save " + cid);
        } catch (IOException e) {
            throw new CustomException(ErrorCode.SAVE_FAIL);
        }

        old.updateCid(cid);
    }

    @Override
    @Transactional
    public void delete(ImgEntity img) {
        try {
            if(img.getCid() != null)
                ipfs.pin.rm(io.ipfs.multihash.Multihash.fromBase58(img.getCid()));
            log.info("ipfs :: remove " + img.getId() + "," + img.getCid());
        } catch (IOException e) {
            throw new CustomException(ErrorCode.REMOVE_FAIL);
        }
        imgRepository.deleteById(img.getId());
    }
}

