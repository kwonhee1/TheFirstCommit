package TheFirstCommit.demo;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class IpfsService {

    // 팀원이 실제 IPFS 연동을 구현하기 전까지 사용할 임시 메소드
    public String uploadFile(MultipartFile file) throws IOException {
        // 어떤 파일이 오든, 실제 업로드 없이 항상 "cid" 문자열만 반환
        return "cid";
    }
}