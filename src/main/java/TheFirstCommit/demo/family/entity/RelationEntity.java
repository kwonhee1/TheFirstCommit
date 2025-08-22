package TheFirstCommit.demo.family.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "relation")
public class RelationEntity {

    @Id
    private String relation;

    @Column
    private Long level;

    /*
    아들 딸     1
    며느리 사위  2
    손자 손녀   3
     */

}
