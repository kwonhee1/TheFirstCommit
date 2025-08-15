package TheFirstCommit.demo.payment.entity;

import TheFirstCommit.demo.CustomEntity;
import TheFirstCommit.demo.family.entity.FamilyEntity;
import TheFirstCommit.demo.user.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "card")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class CardEntity extends CustomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String billingKey;

    @Column
    private String customerKey;

    @OneToOne(targetEntity = FamilyEntity.class)
    @JoinColumn(name = "family_id")
    private FamilyEntity family;

}
