package TheFirstCommit.demo.news.entity;

import TheFirstCommit.demo.BasedEntity;
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
public class NewsEntity extends BasedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "family_id")
    private FamilyEntity family;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JoinColumn(name = "img_id")
    private ImgEntity img;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryStatus deliveryStatus = DeliveryStatus.COMPLETED_PRODUCTION;

    @Builder
    public NewsEntity(Long id, FamilyEntity family, ImgEntity img) {
        this.id = id;
        this.family = family;
        this.img = img;
    }
}