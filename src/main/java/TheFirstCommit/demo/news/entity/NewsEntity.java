package TheFirstCommit.demo.news.entity;

import TheFirstCommit.demo.family.entity.FamilyEntity;
import TheFirstCommit.demo.img.ImgEntity; // ImgEntity 임포트
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    // ImgEntity와 1:1 관계 설정
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "img_id")
    private ImgEntity img; // PDF 파일 정보를 담는 ImgEntity

    @Column(nullable = false)
    private LocalDate publishedAt;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryStatus deliveryStatus;

    @PrePersist
    protected void onCreate() { this.createdAt = LocalDateTime.now(); }

    @Builder
    public NewsEntity(FamilyEntity family, ImgEntity img, LocalDate publishedAt, DeliveryStatus deliveryStatus) { // deliveryStatus 추가
        this.family = family;
        this.img = img;
        this.publishedAt = publishedAt;
        this.deliveryStatus = deliveryStatus; // deliveryStatus 추가
    }
}