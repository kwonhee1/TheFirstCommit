package TheFirstCommit.demo.family;

import TheFirstCommit.demo.CustomEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "family")
public class FamilyEntity extends CustomEntity {

    @Id
    @GeneratedValue
    private long id;

}
