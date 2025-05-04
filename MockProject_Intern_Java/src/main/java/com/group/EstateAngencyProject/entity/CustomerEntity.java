//package com.group.EstateAngencyProject.entity;
//
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Getter
//@Setter
//@Table(name = "customer")
//public class CustomerEntity extends BaseEntity{
//    @Column(name = "customer_id")
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer customerId;
//
//    @Column(name = "user_name")
//    private String userName;
//
//    @Column(name = "password")
//    private String password;
//
//    @Column(name = "full_name")
//    private String fullName;
//
//    @Column(name = "phone")
//    private String phone;
//
//    @Column(name = "is_active")
//    private Boolean isActive;
//
//    @Column(name = "email")
//    private String email;
//
//    @ManyToMany
//    @JoinTable(name = "customermanagement",
//    joinColumns = @JoinColumn(name = "customer_id"),
//    inverseJoinColumns = @JoinColumn(name = "user_id"))
//    List<UserEntity> userList = new ArrayList<>();
//
//}
