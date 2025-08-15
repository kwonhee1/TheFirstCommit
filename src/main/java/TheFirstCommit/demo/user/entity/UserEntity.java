package TheFirstCommit.demo.user.entity;

import TheFirstCommit.demo.CustomEntity;
import TheFirstCommit.demo.family.FamilyEntity;
import TheFirstCommit.demo.img.ImgEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends CustomEntity {

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

    @OneToOne(targetEntity = FamilyEntity.class)
    @JoinColumn(name = "family_id", nullable = true)
    private FamilyEntity family;

    @Column(nullable = false)
    private boolean isLeader = false;

    @Column
    private String relation;

    @OneToOne(targetEntity = ImgEntity.class)
    @JoinColumn(name = "img_id", nullable = true)
    private ImgEntity img;

    // update user info
    public void updateName(String newName) {this.name = newName;}
    public void updateRelation(String newRelation) {this.relation = newRelation;}
    public void updateFamily(FamilyEntity newFamily) {this.family = newFamily; this.role=UserRole.USER; }
    public void updateIsLeader(boolean isLeader) {this.isLeader = isLeader;}
}
