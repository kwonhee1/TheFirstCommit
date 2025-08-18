package TheFirstCommit.demo.payment.entity;

import TheFirstCommit.demo.BasedEntity;
import TheFirstCommit.demo.user.entity.UserEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "card")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@SQLDelete(sql = "UPDATE card SET delete_at = NOW() WHERE id = ?")
@SQLRestriction("delete_at is null")
public class CardEntity extends BasedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String billingKey;

    @Column(unique = true)
    private String customerKey;

    @Column
    private String cardNumber;

    @Column
    private String cardCompany;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "card", cascade = {CascadeType.REMOVE})
    private Set<PaymentEntity> paymentList;

}
