package com.group.EstateAngencyProject.repository;

import com.group.EstateAngencyProject.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Integer> {
    void deleteByCategoryIdIn(List<Integer> ids);
}
