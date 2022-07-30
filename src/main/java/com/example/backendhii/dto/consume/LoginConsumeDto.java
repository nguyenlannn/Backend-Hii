package com.example.backendhii.dto.consume;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginConsumeDto implements Serializable {

    private String email;

    private String password;
}
