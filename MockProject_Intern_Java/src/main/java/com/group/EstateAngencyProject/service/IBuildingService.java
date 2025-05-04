package com.group.EstateAngencyProject.service;

import com.group.EstateAngencyProject.dto.BuildingDTO;
import com.group.EstateAngencyProject.dto.paging.APIPageableDTO;
import com.group.EstateAngencyProject.dto.request.HomeBuildingSearchRequest;
import com.group.EstateAngencyProject.dto.response.HomeBuildingSearchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface IBuildingService {
//    Page<HomeBuildingSearchResponse> searchBuilding(HomeBuildingSearchRequest request, Pageable pageable);

    BuildingDTO findById(Integer id);

    void deleteBuilding(Integer id);

    BuildingDTO createOrUpdateBuilding(BuildingDTO buildingDTO);

    APIPageableDTO searchBuilding(HomeBuildingSearchRequest request,Pageable pageable);

    APIPageableDTO getAllBuilding(Pageable pageable);

    HomeBuildingSearchResponse getDetailBuilding(Integer id);

    List<HomeBuildingSearchResponse> getAllBuildingDashBoard(Pageable pageable);
//    Map<String,Object> searchBuilding(HomeBuildingSearchRequest request,Pageable pageable);
}
