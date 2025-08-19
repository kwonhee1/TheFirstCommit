package TheFirstCommit.demo.news.service;

import TheFirstCommit.demo.family.entity.FamilyEntity;
import TheFirstCommit.demo.img.ImgEntity;
import TheFirstCommit.demo.img.ImgService;
import TheFirstCommit.demo.news.entity.NewsEntity;
import TheFirstCommit.demo.news.entity.NewsImgEntity;
import TheFirstCommit.demo.news.repository.NewsImgRepository;
import TheFirstCommit.demo.news.repository.NewsRepository;
import TheFirstCommit.demo.services.PdfToImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final ImgService imgService;
    private final PdfToImageService pdfToImageService;
    // NewsImgRepository는 이제 직접 사용하지 않으므로 제거해도 됩니다.

    @Override
    @Transactional
    public void createNews(MultipartFile pdfFile, FamilyEntity family) throws IOException {

        // 1. 원본 PDF의 임시 CID 확보
        // ImgServiceImpl의 Mock 구현에 따라 pdfImgEntity는 DB에 저장된 상태
        ImgEntity pdfImgEntity = imgService.save(pdfFile);
        String pdfCid = pdfImgEntity.getCid();

        // 2. News 엔티티를 '생성'만 하고 아직 저장하지 않음
        NewsEntity news = NewsEntity.builder()
                .family(family)
                .pdfCid(pdfCid)
                .publishedAt(LocalDate.now().withDayOfMonth(1))
                .build();

        // 3. PDF를 페이지별 이미지 데이터로 변환
        List<byte[]> pageImages = pdfToImageService.convert(pdfFile);

        // 4. 각 페이지 이미지를 엔티티로 만들어 News 엔티티의 리스트에 추가
        for (int i = 0; i < pageImages.size(); i++) {
            byte[] imageData = pageImages.get(i);
            int pageNumber = i + 1;

            // 4-1. byte[]를 MockMultipartFile 객체로 변환
            MultipartFile imageFile = new MockMultipartFile(
                    "page_" + pageNumber,           // name
                    "page_" + pageNumber + ".png",  // originalFilename
                    "image/png",                   // contentType
                    imageData                      // content
            );

            // 4-2. ImgService를 통해 페이지 이미지 ImgEntity 생성 및 저장
            ImgEntity pageImgEntity = imgService.save(imageFile);

            // 4-3. News와 Img를 연결하는 NewsImgEntity 생성
            NewsImgEntity newsImgEntity = NewsImgEntity.builder()
                    .news(news)
                    .img(pageImgEntity)
                    .pageNumber(pageNumber)
                    .build();

            // 4-4. 부모인 news 엔티티의 자식 리스트에 추가
            news.getNewsImgs().add(newsImgEntity);
        }

        // 5. 마지막에 News 엔티티를 '한 번만' 저장
        // NewsEntity의 Cascade 옵션 덕분에 자식인 NewsImgEntity들이 이때 함께 DB에 저장됨
        newsRepository.save(news);
    }
}