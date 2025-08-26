package TheFirstCommit.demo.payment.repository;

import TheFirstCommit.demo.payment.entity.PaymentEntity;
import TheFirstCommit.demo.user.entity.UserEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    @Query("select u from UserEntity u left join fetch u.family left join fetch u.card where u.isLeader is true and u.family is not null")
    List<UserEntity> findAllLeader();

    @Modifying
    @Query("update PaymentEntity p set p.card.id = null where p.card.id = :cardId")
    void updateCardNull(@Param("cardId") Long cardId);

    // check is payment family this month (가족별 이번달 결제 완료 됬는지 확인)
    @Query("select p from PaymentEntity p where p.family.id = :familyId and p.createdAt between :start and :end ")
    Optional<PaymentEntity> isPaymentByFamily(Long familyId, LocalDateTime start, LocalDateTime end);
}
