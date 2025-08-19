package TheFirstCommit.demo.news.service;

import TheFirstCommit.demo.family.entity.FamilyEntity;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface NewsService {
    void createNews(MultipartFile pdfFile, FamilyEntity family) throws IOException;
}