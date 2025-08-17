package TheFirstCommit.demo.img;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import TheFirstCommit.demo.infra.IpfsService;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ImgServiceImpl implements ImgService {

    private final ImgRepository imgRepository;
    private final IpfsService ipfsService;

    @Value("${PINATA_API_KEY}")
    private String pinataApiKey;

    @Value("${PINATA_API_SECRET}")
    private String pinataApiSecret;

    private final String PINATA_API_URL = "https://api.pinata.cloud/pinning/pinFileToIPFS";

    @Override
    public ImgEntity getImg(String imgURL) {
        return null;
    }

    @Override
    public ImgEntity save(MultipartFile file) {
        try {
            String cid = ipfsService.uploadFile(file); // 업로드 로직 호출
            ImgEntity newImg = ImgEntity.builder().cid(cid).build();
            return imgRepository.save(newImg);
        } catch (IOException e) {
            throw new RuntimeException("IPFS file upload failed", e);
        }
    }

    @Override
    public ImgEntity update(ImgEntity old, MultipartFile file) {
        return old;
    }

    @Override
    public void delete(ImgEntity img) {

    }
}
