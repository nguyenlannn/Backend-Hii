package com.example.backendhii.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.backendhii.dto.produce.TokenProduceDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenConfig {

    @Value("${JWT_SECRET}")
    private String JWT_SECRET;

    @Value("${JWT_TIME_ACCESS_TOKEN}")
    private Long JWT_TIME_ACCESS_TOKEN;

    @Value("${JWT_TIME_REFRESH_TOKEN}")
    private Long JWT_TIME_REFRESH_TOKEN;

    public TokenProduceDto generateToken(UserDetails userDetails, HttpServletRequest request) {
        Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET.getBytes());
        return TokenProduceDto.builder()
                .accessToken(JWT.create()
                        .withSubject(userDetails.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + JWT_TIME_ACCESS_TOKEN))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim(
                                "roles",
                                userDetails.getAuthorities().stream()
                                        .map(GrantedAuthority::getAuthority)
                                        .collect(Collectors.toList()))
                        .withClaim("type", "access")
                        .sign(algorithm))
                .refreshToken(JWT.create()
                        .withSubject(userDetails.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + JWT_TIME_REFRESH_TOKEN))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("type", "refresh")
                        .sign(algorithm))
                .build();
    }
}
