package com.group.EstateAngencyProject.entity;


import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "transaction")
public class TransactionEntity extends BaseEntity{
    @Column(name = "transaction_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transactionId;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "building_id")
    private BuildingEntity buildingEntity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
}
