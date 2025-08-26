package TheFirstCommit.demo.news.service;

import TheFirstCommit.demo.family.entity.FamilyEntity;
import TheFirstCommit.demo.news.entity.DeliveryStatus;
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
    public void createNews(MultipartFile pdfFile, FamilyEntity family) {
        ImgEntity pdfImgEntity = imgService.save(pdfFile);

        NewsEntity news = NewsEntity.builder()
                .family(family)
                .img(pdfImgEntity)
                .build();

        newsRepository.save(news);
    }
}