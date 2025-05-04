package com.group.EstateAngencyProject.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDTO {
    private String status;
    private String jwtToken;
//    private String refreshToken;
}
