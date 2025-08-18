/*
package TheFirstCommit.demo;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class IpfsService {

    private final IPFS ipfs; // Config에서 Bean으로 등록된 IPFS 객체 주입

    public String uploadFile(MultipartFile file) throws IOException {
        NamedStreamable.ByteArrayWrapper stream =
                new NamedStreamable.ByteArrayWrapper(file.getOriginalFilename(), file.getBytes());
        MerkleNode addResult = ipfs.add(stream).get(0);
        return addResult.hash.toString(); // CID 반환
    }
}*/
