package TheFirstCommit.demo.news.service;

import TheFirstCommit.demo.family.entity.FamilyEntity;
import TheFirstCommit.demo.img.ImgEntity;
import TheFirstCommit.demo.img.ImgService;
import TheFirstCommit.demo.news.entity.NewsEntity;
import TheFirstCommit.demo.news.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final ImgService imgService;

    @Override
    @Transactional
    public void createNews(MultipartFile pdfFile, FamilyEntity family) throws IOException {
        // 1. 업로드된 PDF 파일을 ImgEntity로 저장 (IPFS 연동은 Mock 상태)
        ImgEntity pdfImgEntity = imgService.save(pdfFile);

        // 2. News 엔티티를 생성하고 저장
        NewsEntity news = NewsEntity.builder()
                .family(family)
                .img(pdfImgEntity) // ImgEntity 자체를 저장
                .publishedAt(LocalDate.now().withDayOfMonth(1))
                .build();
        newsRepository.save(news);
    }
}