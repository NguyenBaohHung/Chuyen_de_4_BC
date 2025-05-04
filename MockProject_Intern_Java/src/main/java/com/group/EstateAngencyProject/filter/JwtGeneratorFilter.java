package com.group.EstateAngencyProject.filter;

import com.group.EstateAngencyProject.constant.SystemConstant;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

public class JwtGeneratorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null){
            Environment env = getEnvironment();
            if(env!=null){
                String secret = env.getProperty("JWT_SECRET",SystemConstant.JWT_SECRET_DEFAULT_VALUE);
                SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                String jwt = Jwts.builder().issuer("Mock Project").subject("JWT Token")
                        .claim("username",authentication.getName())
                        .claim("authorties",authentication.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority).collect(Collectors.joining("")))
                        .issuedAt(new Date())
                        .expiration(new Date(System.currentTimeMillis()+30000000))
                        .signWith(secretKey).compact();
                response.setHeader(SystemConstant.JWT_HEADER,jwt);

            }
        }
        filterChain.doFilter(request,response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/auth/user");
    }
}
