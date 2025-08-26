package TheFirstCommit.demo.feed.entity;

import TheFirstCommit.demo.BasedEntity;
import TheFirstCommit.demo.family.entity.FamilyEntity;
import TheFirstCommit.demo.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "feed")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@SQLDelete(sql = "UPDATE feed SET delete_at = NOW() WHERE id = ?")
//@SQLRestriction("delete_at is null") chane to hard delete by start up request
public class FeedEntity extends BasedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column
    private Integer layout;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "family_id")
    private FamilyEntity family;

    @OneToMany(mappedBy = "feed", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ImgFeedEntity> imgFeeds = new ArrayList<>();

    @Builder
    public FeedEntity(String text, UserEntity user, FamilyEntity family, Integer layout) {
        this.text = text;
        this.layout = layout;
        this.user = user;
        this.family = family;
    }
    public void updateText(String text) { this.text = text; }
    public void updateLayout(Integer layout) { this.layout = layout; }
}