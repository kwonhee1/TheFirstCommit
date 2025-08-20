package TheFirstCommit.demo.img;

import TheFirstCommit.demo.exception.CustomException;
import TheFirstCommit.demo.exception.ErrorCode;
import io.ipfs.api.IPFS;
import io.ipfs.api.NamedStreamable;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

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
    public ImgEntity getImg(String imgURL) {
        return null;
    }

    @Override
    public ImgEntity save(MultipartFile file) {
        String cid;
        try {
            NamedStreamable streamable = new NamedStreamable.ByteArrayWrapper(file.getBytes());
            cid = ipfs.add(streamable).get(0).hash.toString();
        } catch (IOException e) {
            throw new CustomException(ErrorCode.SAVE_FAIL);
        }
        return ImgEntity.builder().cid(cid).build();
    }

    @Override
    public void update(ImgEntity old, MultipartFile file) {
        try {
            ipfs.pin.rm(io.ipfs.multihash.Multihash.fromBase58(old.getCid()));
        } catch (IOException e) {
            throw new CustomException(ErrorCode.REMOVE_FAIL);
        }

        String cid;
        try {
            NamedStreamable streamable = new NamedStreamable.ByteArrayWrapper(file.getBytes());
            cid = ipfs.add(streamable).get(0).hash.toString();
        } catch (IOException e) {
            throw new CustomException(ErrorCode.SAVE_FAIL);
        }

        old.updateCid(cid);
    }

    @Override
    public void delete(ImgEntity img) {
        try {
            ipfs.pin.rm(io.ipfs.multihash.Multihash.fromBase58(img.getCid()));
        } catch (IOException e) {
            throw new CustomException(ErrorCode.REMOVE_FAIL);
        }
        imgRepository.deleteById(img.getId());
    }
}

