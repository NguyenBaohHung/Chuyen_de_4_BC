package com.group.EstateAngencyProject.utils.validator;


import com.group.EstateAngencyProject.dto.UserDTO;
import com.group.EstateAngencyProject.exception.InvalidException;
import com.group.EstateAngencyProject.exception.NotNullException;
import com.group.EstateAngencyProject.message.UserMessage;
import com.group.EstateAngencyProject.utils.ValidatorUtils;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class UserValidator {
    private static boolean notNullFullName(String fullName) throws NotNullException {
        //            throw new NotNullException(UserMessage.NOT_NULL_FULL_NAME);
        return fullName != null && !fullName.trim().isEmpty();
    }

    private static boolean notNullPhoneNumber(String phoneNumber) throws NotNullException {
        //            throw new NotNullException(UserMessage.NOT_NULL_PHONE_NUMBER);
        return phoneNumber != null && !phoneNumber.trim().isEmpty();
    }

    private static boolean notNullEmail(String email) throws NotNullException{
        //            throw new NotNullException(UserMessage.NOT_NULL_EMAIL);
        return email != null && !email.trim().isEmpty();
    }

    private static boolean invalidPhoneNumber(String phone) throws InvalidException{
        //            throw new InvalidException(UserMessage.INVALID_PHONE_NUMBER);
        return ValidatorUtils.isValidPhoneNumber(phone);
    }

    private static boolean invalidUserName(String userName) throws InvalidException{
//        if(!ValidatorUtils.isValidUserName(userName))
//                throw new InvalidException(UserMessage.INVALID_USER_NAME);
        return ValidatorUtils.isValidUserName(userName);
    }

    private static boolean invalidEmail(String email){
        return ValidatorUtils.isValidEmail(email);
    }

    public static String validUserDTO(UserDTO userDTO){
        if(!notNullEmail(userDTO.getEmail())){
            return UserMessage.NOT_NULL_FULL_NAME;
        }
        if(!notNullFullName(userDTO.getFullName())){
            return UserMessage.NOT_NULL_FULL_NAME;
        }
        if(!notNullPhoneNumber(userDTO.getPhone())){
            return UserMessage.DUPLICATED_PHONE_NUMBER;
        }

//        invalidPhoneNumber(userDTO.getPhone());
//        invalidUserName(userDTO.getUserName());
        if(!invalidEmail(userDTO.getEmail())){
            return UserMessage.INVALID_EMAIL;
        }
        if(!invalidPhoneNumber(userDTO.getPhone())){
            return UserMessage.INVALID_PHONE_NUMBER;
        }
        if(!invalidUserName(userDTO.getUserName())){
            return UserMessage.INVALID_USER_NAME;
        }
        userDTO.setPhone(userDTO.getPhone().trim());
        userDTO.setEmail(userDTO.getEmail().trim());
        userDTO.setFullName(userDTO.getFullName().trim());
        return null;
    }
}
