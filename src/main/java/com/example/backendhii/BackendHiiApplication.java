package com.example.backendhii;

import com.example.backendhii.entities.RoleEntity;
import com.example.backendhii.entities.UserEntity;
import com.example.backendhii.enums.RoleEnum;
import com.example.backendhii.services.impl.RoleServiceImpl;
import com.example.backendhii.services.impl.UserServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackendHiiApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(BackendHiiApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BackendHiiApplication.class);
    }

    @Bean
    CommandLineRunner run(RoleServiceImpl roleService, UserServiceImpl mUserService) {
        return args -> {
            try {
                roleService.create(RoleEntity.builder().name(RoleEnum.ROLE_ADMIN).build());
            } catch (Exception ignored) {
            }
            try {
                roleService.create(RoleEntity.builder().name(RoleEnum.ROLE_USER).build());
            } catch (Exception ignored) {
            }
            try {
                mUserService.createAdmin(UserEntity.builder()
                        .email("cuongnh2k@gmail.com")
                        .password("123")
                        .build());
            } catch (Exception ignored) {
            }
            try {
                mUserService.createUser(UserEntity.builder()
                        .email("ct030408@actvn.edu.vn")
                        .password("123")
                        .build());
            } catch (Exception ignored) {
            }
        };
    }
}
