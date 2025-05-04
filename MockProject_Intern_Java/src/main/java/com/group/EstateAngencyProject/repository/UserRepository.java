package com.group.EstateAngencyProject.repository;

import com.group.EstateAngencyProject.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    Optional<UserEntity> findByUserNameAndIsActive(String userName,Boolean isActive);
    Page<UserEntity> findAllByIsActive(Boolean isActive, Pageable pageable);
}
