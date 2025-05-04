package com.group.EstateAngencyProject.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "category")
public class CategoryEntity extends BaseEntity{
    @Column(name = "category_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    @Column(name = "category_code")
    private String categoryCode;

    @Column(name = "category_description")
    private String categoryDes;

//    @ManyToOne
//    @JoinColumn(name = "building_id")
//    private BuildingEntity buildingEntity;

    @ManyToMany(mappedBy = "categoryList")
    List<BuildingEntity> buildingList = new ArrayList<>();

}
