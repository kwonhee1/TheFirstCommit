package TheFirstCommit.demo.family.entity;

import TheFirstCommit.demo.CustomEntity;
import TheFirstCommit.demo.family.PaymentDay;
import TheFirstCommit.demo.payment.entity.CardEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "family")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class FamilyEntity extends CustomEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentDay paymentDay;

    @Column
    private String familyName;

    @OneToOne(mappedBy = "family", targetEntity = ElderEntity.class)
    private ElderEntity elder;

}
