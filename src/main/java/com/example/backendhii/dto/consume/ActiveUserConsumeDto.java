package com.example.backendhii.dto.consume;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActiveUserConsumeDto implements Serializable {

    private Integer code;

    private String email;
}
