package com.group.EstateAngencyProject.constant;

public class SystemConstant {
    public static final String  REGEX_VALID_PHONE_NUMBER= "0(3[2-9]|5[689]|7[06-9]|8[1-68-9]|9[0-46-9])[0-9]{7}";
    public static final String REGEX_VALID_EMAIL = "[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+";

    public final static String REGEX_VALID_USERNAME = "[A-Za-z][A-Za-z0-9_]{4,24}";
    public static final int DEFAULT_LENGTH_PASSWORD = 8;

    public static final String JWT_SECRET_DEFAULT_VALUE = "TaqlmGv1iEDMRiFp/pHuID1+T84IABfuA0xXh4GhiUI=";
    public static final String JWT_HEADER = "Authorization";
    public static final String JWT_SECRET_KEY = "JWT_SECRET";

}
