package com.group.EstateAngencyProject.controller;


import com.group.EstateAngencyProject.constant.SystemConstant;
import com.group.EstateAngencyProject.dto.UserLoginDTO;
import com.group.EstateAngencyProject.dto.response.LoginResponseDTO;
import com.group.EstateAngencyProject.entity.UserEntity;
import com.group.EstateAngencyProject.message.GlobalMessage;
import com.group.EstateAngencyProject.dto.UserDTO;
import com.group.EstateAngencyProject.response.APIResponse;
import com.group.EstateAngencyProject.service.impl.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private UserService userService;
    private AuthenticationManager authentication;
    private Environment env;

    @Autowired
    public AuthenticationController(UserService userService, AuthenticationManager authentication, Environment env){
        this.userService = userService;
        this.env = env;
        this.authentication = authentication;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> createOrUpdateCustomer(@RequestBody UserDTO userDTO){
//        return APIResponse.responseBuilder(HttpStatus.CREATED, GlobalMessage.SUCCESS,userService.registerUser(customerDTO));
        return userService.registerUser(userDTO);
    }

//    @PostMapping
//    public ResponseEntity<Object> UpdateCustomer(@RequestBody UserDTO customerDTO){
//        return APIResponse.responseBuilder(HttpStatus.CREATED, GlobalMessage.SUCCESS,userService.createOrUpdateCustomer(customerDTO));
//    }
//
//    @DeleteMapping
//    public ResponseEntity<Object> deleteCustomer(@RequestBody UserDTO customerDTO){
//        return APIResponse.responseBuilder(HttpStatus.OK,GlobalMessage.SUCCESS,userService.deleteCustomer(customerDTO));
//    }


    @RequestMapping("/user")
    public UserEntity getUserDetailsAfterAuthen(Authentication authentication){
        return userService.findByUserName(authentication.getName());
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody UserLoginDTO userLoginDTO){
        String jwt = "";
//        String refreshToken = "";
        try{

        Authentication authenticationObj = UsernamePasswordAuthenticationToken.unauthenticated(userLoginDTO.getUserName(),userLoginDTO.getPassword());
        Authentication authenticationResponse = authentication.authenticate(authenticationObj);
        if(authenticationResponse!=null&&authenticationResponse.isAuthenticated()){
            if(env!=null){
                String secret = env.getProperty(SystemConstant.JWT_SECRET_KEY,SystemConstant.JWT_SECRET_DEFAULT_VALUE);
                SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                UserEntity userEntity = userService.findByUserName(authenticationResponse.getName());
                jwt = Jwts.builder().issuer("Mock Project").subject("JWT Token")
                        .claim("username",authenticationResponse.getName())
                        .claim("userId", userEntity.getUserId())
                        .claim("authorities",authenticationResponse.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority).collect(Collectors.joining("")))
                        .issuedAt(new Date())
                        .expiration(new Date(System.currentTimeMillis()+30000000))
                        .signWith(secretKey).compact();


//                refreshToken = Jwts.builder().issuer("Mock Project").subject("Refresh Token")
//                        .claim("username", authenticationResponse.getName())
//                        .claim("authorities",authenticationResponse.getAuthorities().stream()
//                                .map(GrantedAuthority::getAuthority).collect(Collectors.joining("")))
//                        .issuedAt(new Date())
//                        .expiration(new Date(System.currentTimeMillis() + 864000000))
//                        .signWith(secretKey).compact();
            }
        }
        return ResponseEntity.status(HttpStatus.OK).header(SystemConstant.JWT_HEADER,jwt)
                .body(new LoginResponseDTO(HttpStatus.OK.toString(),jwt));
        }catch (AuthenticationException ex) {
            return APIResponse.responseBuilder(HttpStatus.UNAUTHORIZED, GlobalMessage.ERROR, "Invalid username or password");
        }
    }


//    @PostMapping("/refresh-token")
//    public ResponseEntity<Object> refreshToken(@RequestBody RefreshTokenDTO refreshTokenDTO){
//        String refreshToken = refreshTokenDTO.getRefreshToken();
//        String newAccessToken = "";
//        try {
//            String secret = env.getProperty(SystemConstant.JWT_SECRET_KEY,SystemConstant.JWT_SECRET_DEFAULT_VALUE);
//            SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
//            Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(refreshToken).getPayload();
//            String userName = String.valueOf(claims.get("username"));
//            String authorities = String.valueOf(claims.get("authorities"));
//            if(claims.getExpiration().after(new Date())){
//                newAccessToken = Jwts.builder().issuer("Mock Project").subject("JWT Token")
//                        .claim("username",userName)
//                        .claim("authorities",authorities)
//                        .issuedAt(new Date())
//                        .expiration(new Date(System.currentTimeMillis()+30000000))
//                        .signWith(secretKey).compact();
//            }else{
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh Token has expired");
//            }
//        }catch (Exception ex){
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
//        }
//        return ResponseEntity.status(HttpStatus.OK).header(SystemConstant.JWT_HEADER, newAccessToken)
//                .body(new LoginResponseDTO(HttpStatus.OK.toString(),null, newAccessToken));
//    }

    @GetMapping("/dashboard")
    public String getDashBoard(){
        return "Here is dashboard from DB";
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Object> findUserById(@PathVariable("id") Integer id){
//        return APIResponse.responseBuilder(HttpStatus.OK,GlobalMessage.SUCCESS,userService.findById(id));
//    }
}
