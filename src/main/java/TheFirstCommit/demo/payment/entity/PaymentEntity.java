package TheFirstCommit.demo.payment.entity;

import TheFirstCommit.demo.BasedEntity;
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
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "payment")
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE payment SET delete_at = NOW() WHERE id = ?")
@SQLRestriction("delete_at is null")
public class PaymentEntity extends BasedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(targetEntity = CardEntity.class)
    @JoinColumn(nullable = true, name = "card_id") // when delete card entity : set cardId null
    private CardEntity card;

    @ManyToOne(targetEntity = FamilyEntity.class)
    @JoinColumn(nullable = false, name="family_id")
    private FamilyEntity family;

}
