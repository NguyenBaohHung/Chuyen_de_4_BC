package com.group.EstateAngencyProject.config;


import com.group.EstateAngencyProject.entity.UserEntity;
import com.group.EstateAngencyProject.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUserNameAndIsActive(username,true).orElseThrow(()->
                new UsernameNotFoundException("User details not found for the user "+username));
        List<GrantedAuthority>authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userEntity.getRole().getRoleCode()));
//        int t = authorities.size();
        return new User(userEntity.getUserName(), userEntity.getPassword(),authorities);
    }
}
