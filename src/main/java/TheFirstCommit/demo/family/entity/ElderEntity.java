package TheFirstCommit.demo.family.entity;

import TheFirstCommit.demo.family.dto.request.RequestElderDto;
import TheFirstCommit.demo.img.ImgEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "elder")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class ElderEntity {

    @Id
    @Column
    private Long id;

    @OneToOne(targetEntity = FamilyEntity.class)
    @MapsId
    @JoinColumn(name = "family_id")
    private FamilyEntity family;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String birth;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String addressDetail;

    @Column(nullable = false)
    private String addressNumber;

    @ManyToOne(targetEntity = ImgEntity.class, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "img_id")
    private ImgEntity img;

    public void update(RequestElderDto dto) {
        if(dto.getName() != null && !dto.getName().isEmpty())
            this.name = dto.getName();
        if(dto.getBirth() != null && !dto.getBirth().isEmpty())
            this.birth = dto.getBirth();
        if(dto.getNumber() != null && !dto.getNumber().isEmpty())
            this.number = dto.getNumber();
        if(dto.getAddress() != null && !dto.getAddress().isEmpty())
            this.address = dto.getAddress();
        if(dto.getAddressDetail() != null && !dto.getAddressDetail().isEmpty())
            this.addressDetail = dto.getAddressDetail();
        if(dto.getAddressNumber() != null && !dto.getAddressNumber().isEmpty())
            this.addressNumber = dto.getAddressNumber();
    }
}
