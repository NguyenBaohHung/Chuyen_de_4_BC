package com.group.EstateAngencyProject.entity;

import lombok.*;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Table(name = "building")
@Setter
@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BuildingEntity extends BaseEntity{
    @Column(name = "building_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer buildingId;

    @Column(name = "building_name")
    private String buildingName;

    @Column(name = "street")
    private String street;

    @Column(name = "ward")
    private String ward;

    @Column(name = "district")
    private String district;

    @Column(name = "area")
    private Float area;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "price_description")
    private String priceDescription;

    @Column(name = "bed_room")
    private Integer bedRoom;

    @Column(name = "bath_room")
    private Integer bathRoom;

    @Column(name = "direction")
    private String direction;

    @Column(name = "juridical")
    private String juridical;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "is_rent")
    private Boolean isRent;

    @Column(name = "is_sell")
    private Boolean isSell;

    @Column(name = "image")
    private String image;

    @Column(name = "map")
    private String map;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "buildingmanagement",
    joinColumns = @JoinColumn(name = "building_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<UserEntity> userEntityList = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "buildingtype",
    joinColumns = @JoinColumn(name = "building_id"),
    inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<CategoryEntity> categoryList = new ArrayList<>();


    @OneToMany(fetch = FetchType.LAZY,mappedBy = "buildingEntity")
    private List<TransactionEntity> transactionEntities = new ArrayList<>();
}
