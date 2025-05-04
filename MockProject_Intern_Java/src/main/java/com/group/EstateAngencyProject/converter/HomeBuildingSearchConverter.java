package com.group.EstateAngencyProject.converter;

import com.group.EstateAngencyProject.dto.response.HomeBuildingSearchResponse;
import com.group.EstateAngencyProject.entity.BuildingEntity;
import com.group.EstateAngencyProject.enums.EDistrictCode;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class HomeBuildingSearchConverter {
    private ModelMapper modelMapper;

    @Autowired
    public HomeBuildingSearchConverter(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    public HomeBuildingSearchResponse toBuildingSearchResponse(BuildingEntity buildingEntity){
        HomeBuildingSearchResponse buildingSearchResponse = modelMapper.map(buildingEntity, HomeBuildingSearchResponse.class);
        Map<String,String> districtList = EDistrictCode.getDistrict();
        String districtName = "";
        for(Map.Entry<String,String> it : districtList.entrySet()){
            if(it.getKey().equals(buildingEntity.getDistrict()))
                districtName = it.getValue();
        }
        String address = String.join(",",buildingEntity.getStreet(),buildingEntity.getWard(),districtName);
        List<Integer> categoryIds = buildingEntity.getCategoryList().stream().map(it->it.getCategoryId()).collect(Collectors.toList());
        buildingSearchResponse.setCategoryId(categoryIds);
        String fullName = buildingEntity.getUserEntityList().stream().map(it->it.getFullName()).collect(Collectors.joining());
        buildingSearchResponse.setMangerName(fullName);
        buildingSearchResponse.setAddress(address);
        return buildingSearchResponse;
    }
}
