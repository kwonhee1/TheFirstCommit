package TheFirstCommit.demo.family.entity;

import TheFirstCommit.demo.CustomEntity;
import TheFirstCommit.demo.payment.entity.CardEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
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
    private LocalDateTime payDay;

    @OneToOne(mappedBy = "family")
    private CardEntity card;

}
