package TheFirstCommit.demo.imgFeed;

import TheFirstCommit.demo.feed.entity.FeedEntity;
import TheFirstCommit.demo.img.ImgEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "img_feed")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImgFeedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    private FeedEntity feed;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "img_id")
    private ImgEntity img;

    @Builder
    public ImgFeedEntity(FeedEntity feed, ImgEntity img) {
        this.feed = feed;
        this.img = img;
    }
}