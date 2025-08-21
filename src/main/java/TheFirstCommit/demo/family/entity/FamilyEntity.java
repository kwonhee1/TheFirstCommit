package TheFirstCommit.demo.family.entity;

import TheFirstCommit.demo.BasedEntity;
import TheFirstCommit.demo.family.PaymentDay;
import TheFirstCommit.demo.payment.entity.PaymentEntity;
import TheFirstCommit.demo.user.entity.UserEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "family")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE family SET delete_at = NOW() WHERE id = ?")
@SQLRestriction("delete_at is null")
public class FamilyEntity extends BasedEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column
    @Enumerated(EnumType.STRING)
    private PaymentDay paymentDay;

    public void updatePaymentDay(PaymentDay paymentDay) { this.paymentDay = paymentDay; }

    @Column
    private String familyName;

    @Column
    private boolean isChanged = false;

    @Column
    private LocalDate sincePayDay;

    public void payNow() { this.sincePayDay = LocalDate.now(); }
    public LocalDate getThisMonthPaymentDate() {
        return getLastDay(LocalDate.now(), sincePayDay.getDayOfMonth());
    }
    public LocalDate getNextPayDate() {
        LocalDate today = LocalDate.now();
        LocalDate thisMonthPayDay = getLastDay(today, sincePayDay.getDayOfMonth());
        if(thisMonthPayDay.isBefore(today) || thisMonthPayDay.isEqual(today))
            return getLastDay(today.plusMonths(1), sincePayDay.getDayOfMonth());
        return thisMonthPayDay;
    }
    private LocalDate getLastDay(LocalDate date, int day) {
        YearMonth thisMonth = YearMonth.from(date);
        return thisMonth.atDay(Math.min(date.getDayOfMonth(), thisMonth.lengthOfMonth()));
    }

    @OneToOne(mappedBy = "family", targetEntity = ElderEntity.class, cascade = {CascadeType.REMOVE})
    private ElderEntity elder;

    @OneToMany(targetEntity = UserEntity.class, mappedBy = "family", cascade = {CascadeType.REMOVE})
    private List<UserEntity> member;

    @OneToMany(targetEntity = PaymentEntity.class, mappedBy = "family", cascade = {CascadeType.REMOVE})
    private List<PaymentEntity> payment;

//    @OneToMany
//    private List<NewsEntity>

    public void setIsChanged(boolean isChanged) { this.isChanged = isChanged; }

    @Builder
    public FamilyEntity(ElderEntity elder, List<UserEntity> member, String familyName,
        PaymentDay paymentDay, long id) {
        this.elder = elder;
        this.member = member;
        this.familyName = familyName;
        this.paymentDay = paymentDay;
        this.id = id;
    }
}
