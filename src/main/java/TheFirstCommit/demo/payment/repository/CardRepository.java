package TheFirstCommit.demo.payment.repository;

import TheFirstCommit.demo.payment.entity.CardEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, Long> {

    @Modifying
    @Query
    void deleteById(@Param("cardId") Long cardId);

    @Query("select c from CardEntity c where c.user.id = :userId")
    List<CardEntity> findAllByUserId(@Param("userId") Long userId);

}
