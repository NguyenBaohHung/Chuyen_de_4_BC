package com.group.EstateAngencyProject.repository.custom;

import com.group.EstateAngencyProject.dto.request.HomeBuildingSearchRequest;
import com.group.EstateAngencyProject.entity.BuildingEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BuildingRepositoryCustom {
    List<BuildingEntity> searchHomeBuilding(HomeBuildingSearchRequest buildingSearchRequest);
    Page<BuildingEntity> getPageSearchBuilding(HomeBuildingSearchRequest homeBuildingSearchRequest, Pageable pageable);
}
