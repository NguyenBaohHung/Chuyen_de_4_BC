package com.group.EstateAngencyProject.service;

import com.group.EstateAngencyProject.dto.UserDTO;
import com.group.EstateAngencyProject.dto.paging.APIPageableDTO;
import com.group.EstateAngencyProject.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    ResponseEntity<Object> registerUser(UserDTO userDTO);
    String deleteCustomer(Integer id);

    UserDTO findById(Integer id);

    UserEntity findByUserName(String userName);

    List<UserDTO> getAllUsers(Pageable pageable);

    UserDTO createOrUpdateUser(UserDTO userDTO);


}
