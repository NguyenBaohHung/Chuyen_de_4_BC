package com.group.EstateAngencyProject.repository;

import com.group.EstateAngencyProject.entity.BuildingEntity;
import com.group.EstateAngencyProject.repository.custom.BuildingRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuildingRepository extends JpaRepository<BuildingEntity,Integer>, BuildingRepositoryCustom {
    void deleteByBuildingIdIn(List<Integer> ids);
}
