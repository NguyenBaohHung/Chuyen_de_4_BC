package com.group.EstateAngencyProject.service.impl;

import com.group.EstateAngencyProject.converter.UserConverter;
import com.group.EstateAngencyProject.dto.UserDTO;
import com.group.EstateAngencyProject.dto.paging.APIPageableDTO;
import com.group.EstateAngencyProject.entity.RoleEntity;
import com.group.EstateAngencyProject.entity.UserEntity;
import com.group.EstateAngencyProject.exception.DuplicatedException;
import com.group.EstateAngencyProject.message.GlobalMessage;
import com.group.EstateAngencyProject.message.UserMessage;
import com.group.EstateAngencyProject.repository.RoleRepository;
import com.group.EstateAngencyProject.repository.UserRepository;
import com.group.EstateAngencyProject.response.APIResponse;
import com.group.EstateAngencyProject.service.IUserService;
import com.group.EstateAngencyProject.utils.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
    private UserConverter userConverter;
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserConverter userConverter, PasswordEncoder passwordEncoder,RoleRepository roleRepository){
        this.userConverter = userConverter;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

//    @Override
//    public UserDTO register(UserDTO userDTO) {
//        UserValidator.validCustomerDTO(userDTO);
//        checkDuplicate(userDTO);
//
//
//        UserEntity userEntity = userConverter.toUserEntity(userDTO);
//        String hashPwd = passwordEncoder.encode(userDTO.getPassword());
//        userEntity.setPassword(hashPwd);
//        RoleEntity roleEntity = roleRepository.findById(3).get();
//        userEntity.setRole(roleEntity);
//         UserEntity   storedCustomerEntity = userRepository.save(userEntity);
//        return userConverter.toUserDTO(userEntity);
//    }

    @Override
    public ResponseEntity<Object> registerUser(UserDTO userDTO) {
        // Kiểm tra dữ liệu hợp lệ
        if(UserValidator.validUserDTO(userDTO)!=null){
            // Xử lý các lỗi validate
            if(UserValidator.validUserDTO(userDTO).equals(UserMessage.NOT_NULL_EMAIL))
                return APIResponse.responseBuilder(HttpStatus.BAD_REQUEST, GlobalMessage.ERROR,UserMessage.NOT_NULL_EMAIL);
            else if(UserValidator.validUserDTO(userDTO).equals(UserMessage.NOT_NULL_FULL_NAME))
                return APIResponse.responseBuilder(HttpStatus.BAD_REQUEST, GlobalMessage.ERROR,UserMessage.NOT_NULL_FULL_NAME);
            else if(UserValidator.validUserDTO(userDTO).equals(UserMessage.NOT_NULL_PHONE_NUMBER))
                return APIResponse.responseBuilder(HttpStatus.BAD_REQUEST, GlobalMessage.ERROR,UserMessage.NOT_NULL_PHONE_NUMBER);
            else if(UserValidator.validUserDTO(userDTO).equals(UserMessage.INVALID_EMAIL))
                return APIResponse.responseBuilder(HttpStatus.BAD_REQUEST,GlobalMessage.ERROR,UserMessage.INVALID_EMAIL);
            else if(UserValidator.validUserDTO(userDTO).equals(UserMessage.INVALID_USER_NAME))
                return APIResponse.responseBuilder(HttpStatus.BAD_REQUEST,GlobalMessage.ERROR,UserMessage.INVALID_USER_NAME);
            else if(UserValidator.validUserDTO(userDTO).equals(UserMessage.INVALID_PHONE_NUMBER))
                return APIResponse.responseBuilder(HttpStatus.BAD_REQUEST,GlobalMessage.ERROR,UserMessage.INVALID_PHONE_NUMBER);
        }

        // Kiểm tra trùng lặp
        if(checkDuplicate(userDTO)!=null){
            if(checkDuplicate(userDTO).equals(UserMessage.DUPLICATED_USER_NAME))
                return APIResponse.responseBuilder(HttpStatus.BAD_REQUEST,GlobalMessage.ERROR,UserMessage.DUPLICATED_USER_NAME);
            else if(checkDuplicate(userDTO).equals(UserMessage.DUPLICATED_EMAIL))
                return APIResponse.responseBuilder(HttpStatus.BAD_REQUEST,GlobalMessage.ERROR,UserMessage.DUPLICATED_EMAIL);
            else if(checkDuplicate(userDTO).equals(UserMessage.DUPLICATED_PHONE_NUMBER))
                return APIResponse.responseBuilder(HttpStatus.BAD_REQUEST,GlobalMessage.ERROR,UserMessage.DUPLICATED_PHONE_NUMBER);
        }

        // Tạo mới người dùng và mã hóa mật khẩu
        UserEntity userEntity = userConverter.toUserEntity(userDTO);
        String hashPwd = passwordEncoder.encode(userDTO.getPassword());
        userEntity.setPassword(hashPwd);

        // Gán Role cho người dùng (role có thể được thay đổi tại đây, ví dụ: cho phép đăng bài)
        RoleEntity roleEntity = roleRepository.findById(3).get(); // Giả sử role với id 2 là role cho phép đăng bài
        userEntity.setRole(roleEntity);

        // Lưu người dùng vào database
        userEntity.setIsActive(true);
        UserEntity storedUserEntity = userRepository.save(userEntity);

        // Trả về thông tin người dùng sau khi tạo
        return APIResponse.responseBuilder(HttpStatus.OK, GlobalMessage.SUCCESS, userConverter.toUserDTO(userEntity));
    }


    @Override
    public String deleteCustomer(Integer id) {
        UserEntity userEntity = userRepository.findById(id).get();
        userEntity.setIsActive(false);
        UserEntity storedCustomerEntity = userRepository.save(userEntity);
        return "success";
    }

    @Override
    public UserDTO findById(Integer id) {
        return userConverter.toUserDTO(userRepository.findById(id).get());
    }

    @Override
    public UserEntity findByUserName(String userName) {
        return userRepository.findByUserNameAndIsActive(userName,true).get();
    }

    @Override
    public List<UserDTO> getAllUsers(Pageable pageable) {
        Page<UserEntity> userEntityPage = userRepository.findAllByIsActive(true,pageable);
        List<UserDTO> contents = userEntityPage.stream().map(userConverter::toUserDTO).collect(Collectors.toList());
        APIPageableDTO apiPageableDTO = new APIPageableDTO();
        apiPageableDTO.responsePageBuilder(userEntityPage,contents);
        return contents;
    }

    @Override
    public UserDTO createOrUpdateUser(UserDTO userDTO) {
        UserEntity userEntity = userConverter.toUserEntity(userDTO);
        userRepository.save(userEntity);
        return userConverter.toUserDTO(userEntity);
    }

    private String  checkDuplicate(UserDTO customerCheck) throws DuplicatedException {
        List<UserEntity> userList = userRepository.findAll();

        for(UserEntity userEntity : userList){
            if(userEntity.getPhone().equals(customerCheck.getPhone()))
//                throw new DuplicatedException(UserMessage.DUPLICATED_PHONE_NUMBER);
                return UserMessage.DUPLICATED_PHONE_NUMBER;
            else if(userEntity.getEmail().equals(customerCheck.getEmail()))
                return UserMessage.DUPLICATED_EMAIL;
//                throw new DuplicatedException(UserMessage.DUPLICATED_EMAIL);
            else if(userEntity.getUserName().equals(customerCheck.getUserName()))
                return UserMessage.DUPLICATED_USER_NAME;
//                throw new DuplicatedException(UserMessage.DUPLICATED_USER_NAME);
        }
        return null;
    }


}
