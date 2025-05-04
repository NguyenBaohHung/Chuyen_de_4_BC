package com.group.EstateAngencyProject.message;

public class UserMessage {
    public static final String NOT_NULL_PHONE_NUMBER = "Số điện thoại không được để trống";
    public static final String NOT_NULL_FULL_NAME = "Họ và tên không được để trống";
    public static final String NOT_NULL_EMAIL = "Email không được để trống";
    public static final String DUPLICATED_PHONE_NUMBER = "Đã tồn tại người dùng với số điện thoại này";
    public static final String DUPLICATED_EMAIL = "Đã tồn tại người dùng với email này";
    public static final String DUPLICATED_USER_NAME = "Đã tồn tại người dùng với tên tài khoản này";
    public static final String CUSTOMER_EXIST = "Người dùng đã tồn tại";

    public static final String INVALID_PHONE_NUMBER = "Số điện thoại phải là số và đúng định dạng";

    public static final String INVALID_USER_NAME = "Tên Tài khoản không được chứa ký tự đặc biệt, ngoại trừ '_', phải có ít nhất 5 ký tự và tối đa 24 ký tự";

    public static final String INVALID_EMAIL = "Email không hợp lệ";
}
