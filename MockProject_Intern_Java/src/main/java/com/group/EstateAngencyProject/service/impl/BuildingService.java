package com.group.EstateAngencyProject.service.impl;

import com.group.EstateAngencyProject.converter.BuildingConverter;
import com.group.EstateAngencyProject.converter.HomeBuildingSearchConverter;
import com.group.EstateAngencyProject.dto.BuildingDTO;
import com.group.EstateAngencyProject.dto.paging.APIPageableDTO;
import com.group.EstateAngencyProject.dto.request.HomeBuildingSearchRequest;
import com.group.EstateAngencyProject.dto.response.HomeBuildingSearchResponse;
import com.group.EstateAngencyProject.entity.BuildingEntity;
import com.group.EstateAngencyProject.entity.CategoryEntity;
import com.group.EstateAngencyProject.entity.TransactionEntity;
import com.group.EstateAngencyProject.repository.BuildingRepository;
import com.group.EstateAngencyProject.repository.CategoryRepository;
import com.group.EstateAngencyProject.repository.TransactionRepository;
import com.group.EstateAngencyProject.service.IBuildingService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BuildingService implements IBuildingService {
    private final BuildingRepository buildingRepository;
    private final HomeBuildingSearchConverter homeBuildingSearchConverter;
    private final BuildingConverter buildingConverter;
    private final CategoryRepository categoryRepository;


    public Page<HomeBuildingSearchResponse> searchBuildings(HomeBuildingSearchRequest request, Pageable pageable) {
//        List<BuildingEntity>list = buildingRepository.searchHomeBuilding(request);
        Page<BuildingEntity> buildingEntityPage = buildingRepository.getPageSearchBuilding(request,pageable);
//        List<HomeBuildingSearchResponse> responseList = new ArrayList<>();
//        for(BuildingEntity item : list){
//            HomeBuildingSearchResponse response = homeBuildingSearchConverter.toBuildingSearchResponse(item);
//            responseList.add(response);
//        }
        return buildingEntityPage.map(homeBuildingSearchConverter::toBuildingSearchResponse);
    }

    @Override
    public BuildingDTO findById(Integer id) {
        BuildingEntity buildingEntity = buildingRepository.findById(id).get();
        BuildingDTO buildingDTO = buildingConverter.toBuildingDTO(buildingEntity);
        buildingDTO.setCategoryId(buildingEntity.getCategoryList().stream().map(it->it.getCategoryId()).collect(Collectors.toList()));
//        return buildingConverter.toBuildingDTO(buildingEntity);
        return buildingDTO;
    }

    @Transactional
    @Override
    public void deleteBuilding(Integer id) {
//        buildingRepository.deleteByBuildingIdIn(ids);
        BuildingEntity buildingEntity = buildingRepository.findById(id).get();
        buildingRepository.delete(buildingEntity);
    }

    @Override
    public BuildingDTO createOrUpdateBuilding(BuildingDTO buildingDTO) {
        BuildingEntity buildingEntity = buildingConverter.toBuildingEntity(buildingDTO);
        List<Integer> categoryIdList = buildingDTO.getCategoryId();
        List<CategoryEntity> categoryEntities = new ArrayList<>();
        for(Integer item : categoryIdList){
            CategoryEntity categoryEntity = categoryRepository.findById(item).get();
            categoryEntities.add(categoryEntity);
        }
        buildingEntity.setCategoryList(categoryEntities);
        BuildingEntity storedEntity = buildingRepository.save(buildingEntity);
        BuildingDTO buildingAfterCreate = buildingConverter.toBuildingDTO(storedEntity);
        buildingAfterCreate.setCategoryId(categoryEntities.stream().map(it->it.getCategoryId()).collect(Collectors.toList()));
//        return buildingConverter.toBuildingDTO(storedEntity);
        return buildingAfterCreate;
    }

    @Override
    public APIPageableDTO searchBuilding(HomeBuildingSearchRequest request, Pageable pageable) {
        Page<HomeBuildingSearchResponse> buildingSearchResponses = searchBuildings(request,pageable);
        List<HomeBuildingSearchResponse> contents = buildingSearchResponses.stream().collect(Collectors.toList());
        APIPageableDTO apiPageableDTO = new APIPageableDTO();
         apiPageableDTO.responsePageBuilder(buildingSearchResponses,contents);
         return  apiPageableDTO;
    }

    private Page<BuildingEntity> findAllBuilding(Pageable pageable){
        return buildingRepository.findAll(pageable);
    }

    @Override
    public APIPageableDTO getAllBuilding(Pageable pageable) {
        Page<BuildingEntity> buildingEntityPage = findAllBuilding(pageable);
        List<HomeBuildingSearchResponse> contents =   buildingEntityPage.stream().map(homeBuildingSearchConverter::toBuildingSearchResponse).collect(Collectors.toList());
        APIPageableDTO apiPageableDTO = new APIPageableDTO();
        apiPageableDTO.responsePageBuilder(buildingEntityPage,contents);
        return apiPageableDTO;
    }

    @Override
    public HomeBuildingSearchResponse getDetailBuilding(Integer id) {
        BuildingEntity buildingEntity = buildingRepository.findById(id).get();
        return homeBuildingSearchConverter.toBuildingSearchResponse(buildingEntity);
    }

    @Override
    public List<HomeBuildingSearchResponse> getAllBuildingDashBoard(Pageable pageable) {
        Page<BuildingEntity> buildingEntityPage = findAllBuilding(pageable);
        return buildingEntityPage.stream().map(homeBuildingSearchConverter::toBuildingSearchResponse).collect(Collectors.toList());
    }

//    @Override
//    public Map<String, Object> searchBuilding(HomeBuildingSearchRequest request, Pageable pageable) {
//        if(request==null)
//            throw new BadCredentialsException("Not Null");
//        Map<String,Object> response = new HashMap<>();
//        Page<HomeBuildingSearchResponse> page = searchBuildingHome(request,pageable);
//        APIPageableDTO apiPageableDTO = new APIPageableDTO(page);
//        response.put("page",apiPageableDTO);
//        response.put("buildings",page.getContent());
//        return response;
//    }
}
