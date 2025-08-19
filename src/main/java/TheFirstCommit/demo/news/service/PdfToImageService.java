package TheFirstCommit.demo.services; // 경로는 프로젝트에 맞게 생성

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PdfToImageService {

    public List<byte[]> convert(MultipartFile pdfFile) throws IOException {
        List<byte[]> imageList = new ArrayList<>();

        // PDFBox 3.x 버전에서는 Loader 클래스를 사용해야 합니다.
        // try-with-resources 구문으로 PDDocument가 자동으로 닫히도록 합니다.
        try (PDDocument document = Loader.loadPDF(pdfFile.getBytes())) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);

            for (int i = 0; i < document.getNumberOfPages(); i++) {
                BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(i, 150);
                try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                    ImageIO.write(bufferedImage, "PNG", baos);
                    imageList.add(baos.toByteArray());
                }
            }
        }
        return imageList;
    }
}