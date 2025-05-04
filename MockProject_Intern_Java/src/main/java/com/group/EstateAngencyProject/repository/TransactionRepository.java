package com.group.EstateAngencyProject.repository;

import com.group.EstateAngencyProject.entity.TransactionEntity;
import com.group.EstateAngencyProject.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity,Integer> {
    Page<TransactionEntity> findByUserEntity(UserEntity userEntity, Pageable pageable);
    void deleteByTransactionIdIn(List<Integer> ids);
}
