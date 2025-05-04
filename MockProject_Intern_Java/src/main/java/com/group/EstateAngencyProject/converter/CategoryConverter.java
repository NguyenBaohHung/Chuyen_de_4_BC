package com.group.EstateAngencyProject.converter;

import com.group.EstateAngencyProject.dto.CategoryDTO;
import com.group.EstateAngencyProject.entity.CategoryEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter {
    private ModelMapper modelMapper;

    @Autowired
    public CategoryConverter(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    public CategoryEntity toCategoryEntity(CategoryDTO categoryDTO){
        return modelMapper.map(categoryDTO, CategoryEntity.class);
    }


    public CategoryDTO toCategoryDTO(CategoryEntity categoryEntity){
        return modelMapper.map(categoryEntity, CategoryDTO.class);
    }
}
