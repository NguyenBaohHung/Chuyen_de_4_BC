package com.group.EstateAngencyProject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO extends AbstractDTO {
    private Integer userId;
    private String userName;
    private String password;
    private String fullName;
    private Boolean isActive;
    private String email;
    private String phone;
    private Integer roleId;
}
