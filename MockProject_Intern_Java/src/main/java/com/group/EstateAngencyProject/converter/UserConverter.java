package com.group.EstateAngencyProject.converter;


import com.group.EstateAngencyProject.dto.UserDTO;
import com.group.EstateAngencyProject.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    private ModelMapper modelMapper;

    @Autowired
    public UserConverter(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    public UserEntity toUserEntity(UserDTO userDTO){
        return modelMapper.map(userDTO,UserEntity.class);
    }

    public UserDTO toUserDTO(UserEntity userEntity){
        return modelMapper.map(userEntity, UserDTO.class);
    }
}
