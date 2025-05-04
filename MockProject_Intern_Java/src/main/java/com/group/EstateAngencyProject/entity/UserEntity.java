package com.group.EstateAngencyProject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
public class UserEntity extends BaseEntity{
    @Column(name = "user_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity role;


    @ManyToMany(mappedBy = "userEntityList")
    List<BuildingEntity> buildingEntityList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "userEntity")
    private List<TransactionEntity> transactionList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "userEntity")
    private List<NewsEntity> newsEntityList = new ArrayList<>();
}
