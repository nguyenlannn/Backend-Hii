package com.example.backendhii.dto.produce;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenProduceDto implements Serializable {

    private String accessToken;

    private String refreshToken;
}
