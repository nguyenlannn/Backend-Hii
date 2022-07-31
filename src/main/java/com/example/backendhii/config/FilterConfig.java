package com.example.backendhii.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.backendhii.basess.BaseResponseDto;
import com.example.backendhii.repository.DeviceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.USER_AGENT;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
@Transactional
public class FilterConfig extends OncePerRequestFilter {

    private final DeviceRepository mDeviceRepository;

    @Value("${JWT_SECRET}")
    private String JWT_SECRET;

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest
            , HttpServletResponse httpServletResponse
            , FilterChain filterChain) throws ServletException, IOException {
        if (httpServletRequest.getServletPath().contains("basic")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            httpServletResponse.setContentType(APPLICATION_JSON_VALUE);
            httpServletResponse.setStatus(UNAUTHORIZED.value());
            String authorizationHeader = httpServletRequest.getHeader(AUTHORIZATION);
            if (Objects.nonNull(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
                try {
                    String token = authorizationHeader.substring("Bearer ".length());
                    Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET.getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);
                    if (decodedJWT.getClaim("type").asString().equalsIgnoreCase("access")) {
                        String email = decodedJWT.getSubject();
                        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                        stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(email, null, authorities);
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        if (mDeviceRepository.existsByUserAgentAndAccessToken(
                                httpServletRequest.getHeader(USER_AGENT),
                                token)) {
                            filterChain.doFilter(httpServletRequest, httpServletResponse);
                        } else {
                            new ObjectMapper().writeValue(
                                    httpServletResponse.getOutputStream()
                                    , BaseResponseDto.error("expired version", 401));
                        }
                        filterChain.doFilter(httpServletRequest, httpServletResponse);
                    } else {
                        new ObjectMapper().writeValue(
                                httpServletResponse.getOutputStream()
                                , BaseResponseDto.error("unauthorized", 401));
                    }
                } catch (Exception exception) {
                    new ObjectMapper().writeValue(
                            httpServletResponse.getOutputStream()
                            , BaseResponseDto.error(exception.getMessage(), 401));
                }
            } else {
                new ObjectMapper().writeValue(
                        httpServletResponse.getOutputStream()
                        , BaseResponseDto.error("unauthorized", 401));
            }
        }
    }
}