package com.group.EstateAngencyProject.utils;

import com.group.EstateAngencyProject.constant.SystemConstant;

import java.util.regex.Pattern;

public class ValidatorUtils {

//    public static boolean isValidPhoneNumber(String phone){
//        return Pattern.matches()
//    }

    public static boolean isValidEmail(String email){
        return Pattern.compile(SystemConstant.REGEX_VALID_EMAIL).matcher(email).matches();
    }

    public static boolean isValidPhoneNumber(String phone){
        return Pattern.compile(SystemConstant.REGEX_VALID_PHONE_NUMBER).matcher(phone).matches();
    }

    public static boolean isValidUserName(String userName){
        return Pattern.compile(SystemConstant.REGEX_VALID_USERNAME).matcher(userName).matches();
    }
}
