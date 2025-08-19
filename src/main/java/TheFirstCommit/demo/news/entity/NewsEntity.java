package TheFirstCommit.demo.news.entity;

import TheFirstCommit.demo.family.entity.FamilyEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "news")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NewsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "family_id")
    private FamilyEntity family;

    @Column
    private String pdfCid; // 다운로드용 원본 PDF의 CID

    @Column(nullable = false)
    private LocalDate publishedAt;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("pageNumber ASC")
    private List<NewsImgEntity> newsImgs = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @Builder
    public NewsEntity(FamilyEntity family, String pdfCid, LocalDate publishedAt) {
        this.family = family;
        this.pdfCid = pdfCid;
        this.publishedAt = publishedAt;
    }
}