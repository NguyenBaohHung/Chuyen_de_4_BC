package com.group.EstateAngencyProject.converter;

import com.group.EstateAngencyProject.dto.NewsDTO;
import com.group.EstateAngencyProject.entity.NewsEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NewsConverter {
    private ModelMapper modelMapper;

    @Autowired
    public NewsConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public NewsEntity toNewsEntity(NewsDTO newsDTO) {
        return modelMapper.map(newsDTO, NewsEntity.class);
    }

    public NewsDTO toNewsDTO(NewsEntity newsEntity) {
        NewsDTO newsDTO = modelMapper.map(newsEntity, NewsDTO.class);
        return modelMapper.map(newsEntity, NewsDTO.class);
    }

}