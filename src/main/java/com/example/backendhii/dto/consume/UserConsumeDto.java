package com.example.backendhii.dto.consume;

import com.example.backendhii.entities.UserEntity;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserConsumeDto implements Serializable {

    private  String email;

    private  String password;

    private  String firstName;

    private  String middleName;

    private  String lastName;

    public  UserEntity toUserEntity() {
        return UserEntity.builder()
                .email(email)
                .password(password)
                .firstName(firstName)
                .middleName(middleName)
                .lastName(lastName)
                .build();
    }
}
