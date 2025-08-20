package TheFirstCommit.demo.img;

import io.ipfs.api.IPFS;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@RequiredArgsConstructor
@Slf4j
@Component
public class ImgServiceConfig {

    @Value("${ipfs}")
    private String ipfsUrl;
    private final ImgRepository imgRepository;

    @Bean
    public ImgService imgService() {
        IPFS ipfs;
        try {
            ipfs = new IPFS(ipfsUrl);
            log.info("ipfs server");
            return new ImgServiceIpfs(ipfs, imgRepository);
        }catch (Exception e) {
            e.printStackTrace();
            log.info("no ipfs server");
            return new ImgServiceILocal(imgRepository);
        }
    }
}