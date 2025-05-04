package com.group.EstateAngencyProject.repository;

import com.group.EstateAngencyProject.entity.NewsEntity;
import com.group.EstateAngencyProject.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<NewsEntity, Integer> {
//    Page<NewsEntity> findAll(Pageable pageable);
    List<NewsEntity> findByTitleContainingIgnoreCase(String keyword);
    Page<NewsEntity> findByUserEntity(UserEntity userEntity, Pageable pageable);
    void deleteByNewsIdIn(List<Integer> ids);
}