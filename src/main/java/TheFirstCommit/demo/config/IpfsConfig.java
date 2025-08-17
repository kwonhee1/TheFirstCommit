package TheFirstCommit.demo.config;

import io.ipfs.api.IPFS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IpfsConfig {

    @Value("${IPFS_MULTI-ADDRESS}")
    private String ipfsMultiAddress;

    @Bean
    public IPFS ipfs() {
        return new IPFS(ipfsMultiAddress);
    }
}