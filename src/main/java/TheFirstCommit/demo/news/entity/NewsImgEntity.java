package TheFirstCommit.demo.news.entity;

import TheFirstCommit.demo.img.ImgEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "news_img")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NewsImgEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id")
    private NewsEntity news;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "img_id")
    private ImgEntity img;

    @Column(nullable = false)
    private int pageNumber;

    @Builder
    public NewsImgEntity(NewsEntity news, ImgEntity img, int pageNumber) {
        this.news = news;
        this.img = img;
        this.pageNumber = pageNumber;
    }
}