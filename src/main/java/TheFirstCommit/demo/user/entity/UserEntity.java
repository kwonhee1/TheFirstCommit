package TheFirstCommit.demo.user.entity;

import TheFirstCommit.demo.BasedEntity;
import TheFirstCommit.demo.family.entity.ElderEntity;
import TheFirstCommit.demo.family.entity.FamilyEntity;
import TheFirstCommit.demo.feed.entity.FeedEntity;
import TheFirstCommit.demo.img.ImgEntity;
import TheFirstCommit.demo.payment.entity.CardEntity;
import TheFirstCommit.demo.user.dto.request.RequestUpdateUserInfoDto;
import TheFirstCommit.demo.user.dto.request.UpdateUserFamilyDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BasedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private String number;

    @Column
    private String birth;

    @Column(nullable = false)
    private String provider;

    @Column(nullable = false, unique = true)
    private String socialId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.SOCIAL;

    @ManyToOne(targetEntity = FamilyEntity.class)
    @JoinColumn(name = "family_id", nullable = true)
    private FamilyEntity family; // 코드로 삭제

    @Column(nullable = false)
    private boolean isLeader = false;

    @Column
    private String relation;

    @OneToOne(targetEntity = ImgEntity.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "img_id", nullable = true)
    private ImgEntity img;

    @OneToMany(targetEntity = CardEntity.class, mappedBy = "user", cascade = {CascadeType.REMOVE})
    private Set<CardEntity> card;

    @OneToMany(targetEntity = FeedEntity.class, mappedBy = "user", cascade = {CascadeType.REMOVE})
    private List<FeedEntity> feeds;

    public void update(RequestUpdateUserInfoDto dto) {
        if(dto.getBirth() != null && !dto.getBirth().isEmpty())
            this.birth = dto.getBirth();
        if(dto.getName() != null && !dto.getName().isEmpty())
            this.name = dto.getName();
        if(dto.getRelation() != null && !dto.getRelation().isEmpty())
            this.relation = dto.getRelation();
        if(dto.getNumber() != null && !dto.getNumber().isEmpty())
            this.number = dto.getNumber();
    }

    public void update(ImgEntity img) { this.img = img; }
    public void update(UpdateUserFamilyDto dto) { this.family = dto.getFamily(); this.isLeader = dto.isLeader(); this.relation=dto.getRelation(); this.role = UserRole.USER; }
    public void updateLeader() {this.isLeader = true;}

//    public boolean validate() {
//        if( //이름/프로필사진/생년월일/전화번호/받는 분과의 관계
//            name != null && !name.isEmpty() &&
//            img != null &&
//            number != null && !number.isEmpty() &&
//            birth != null && !birth.isEmpty() &&
//            relation != null && !relation.isEmpty())
//                return true;
//        return false;
//    }

    @Builder
    public UserEntity(long id, String name, String number, String birth, String provider,
        String socialId, FamilyEntity family, String relation,
        ImgEntity img) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.birth = birth;
        this.provider = provider;
        this.socialId = socialId;
        this.family = family;
        this.relation = relation;
        this.img = img;
    }
}
