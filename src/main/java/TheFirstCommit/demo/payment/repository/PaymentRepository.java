package TheFirstCommit.demo.payment.repository;

import TheFirstCommit.demo.payment.entity.PaymentEntity;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    // check is payment family this month (가족별 이번달 결제 완료 됬는지 확인)
    @Query("select p from PaymentEntity p where p.card.family.id = :familyId and p.createdAt between :start and :end ")
    Optional<PaymentEntity> isPaymentByFamily(Long familyId, LocalDateTime start, LocalDateTime end);
}
