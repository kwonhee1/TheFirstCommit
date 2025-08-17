package TheFirstCommit.demo.payment.entity;

import TheFirstCommit.demo.CustomEntity;
import TheFirstCommit.demo.family.entity.FamilyEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payment")
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentEntity extends CustomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(targetEntity = CardEntity.class)
    @JoinColumn(nullable = false, name = "card_id")
    private CardEntity card;

    @ManyToOne(targetEntity = FamilyEntity.class)
    @JoinColumn(nullable = false, name="family_id")
    private FamilyEntity family;

}
