package com.example.backendhii.dto.consume;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EditUserConsumeDto implements Serializable {

    private String password;

    private String newPassword;

    private String firstName;

    private String middleName;

    private String lastName;
}
