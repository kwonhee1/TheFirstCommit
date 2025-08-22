package TheFirstCommit.demo.family.repository;

import TheFirstCommit.demo.family.entity.FamilyEntity;
import TheFirstCommit.demo.user.entity.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FamilyRepository extends JpaRepository<FamilyEntity,Long> {

    @Query("select u "
        + "from UserEntity u left join RelationEntity r on r.relation=u.relation "
        + "where u.family.id = :id and u.isLeader is false "
        + "order by r.level, u.createdAt desc")
    List<UserEntity> getFamilyMember(@Param("id") Long userId); // 관계순 정렬 , 동일시 가입순 별 정렬 !! 추가 구현 필요

    @Query("select u from UserEntity u where u.family.id = :id and u.isLeader is true")
    UserEntity getFamilyLeader(@Param("id") Long userId);

    @Query("select count(*) from UserEntity u where u.family.id = :id")
    Long getMemberCount(@Param("id") Long userId);

}
