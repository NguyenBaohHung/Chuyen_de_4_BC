package com.group.EstateAngencyProject.config;

import com.group.EstateAngencyProject.exception.UserBasicAuthenticationEntryPoint;
import com.group.EstateAngencyProject.filter.CsrfCookieFilter;
import com.group.EstateAngencyProject.filter.JwtGeneratorFilter;
import com.group.EstateAngencyProject.filter.JwtValidatorFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;


@Configuration
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();
        http.sessionManagement(sessionConfig->sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(corsConfig->corsConfig.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Arrays.asList("http://localhost:3838", "http://localhost:3000","http://127.0.0.1:5501"));
                        config.setAllowedMethods(Arrays.asList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Arrays.asList("*"));
                        config.setExposedHeaders(Arrays.asList("Authorization"));
                        config.setMaxAge(3600L);
                        return config;
                    }
                }))
                .requiresChannel(rcc->rcc.anyRequest().requiresInsecure())
                .csrf(csrfConfig->csrfConfig.disable())
//                .csrf(csrfConfig->csrfConfig.csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
//                        .ignoringRequestMatchers("/auth/register","/auth/login","/home/**")
//                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
//                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new JwtGeneratorFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new JwtValidatorFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests((request)->
                        request.requestMatchers("/auth/register","/auth/login","/home/**")
                                .permitAll()
                                .requestMatchers("/category/**").hasRole("MANAGER")
                                .requestMatchers("/api/building/**","/api/news/**","/user-manage/**").hasAnyRole("MANAGER","STAFF","CUSTOMER")
                                .requestMatchers("/customer/**").hasRole("CUSTOMER"));
//        http.formLogin(Customizer.withDefaults());
//        http.httpBasic(hbc->hbc.authenticationEntryPoint(new UserBasicAuthenticationEntryPoint()));
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManage(UserDetailsService userDetailsService,PasswordEncoder passwordEncoder){
        CustomAuthenticationProvider customAuthenticationProvider =new CustomAuthenticationProvider(userDetailsService,passwordEncoder);
        ProviderManager providerManager =new ProviderManager(customAuthenticationProvider);
        providerManager.setEraseCredentialsAfterAuthentication(false);
        return providerManager;
    }
}
