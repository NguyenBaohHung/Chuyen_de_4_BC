package com.group.EstateAngencyProject.converter;

import com.group.EstateAngencyProject.dto.BuildingDTO;
import com.group.EstateAngencyProject.entity.BuildingEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BuildingConverter {
    private ModelMapper modelMapper;

    public BuildingEntity toBuildingEntity(BuildingDTO buildingDTO){
        return modelMapper.map(buildingDTO, BuildingEntity.class);
    }

    public BuildingDTO toBuildingDTO(BuildingEntity buildingEntity){
        return modelMapper.map(buildingEntity, BuildingDTO.class);
    }
}
