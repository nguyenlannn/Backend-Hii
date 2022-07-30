package com.example.backendhii.controllers;

import com.example.backendhii.basess.BaseResponseDto;
import com.example.backendhii.config.TokenConfig;
import com.example.backendhii.config.UserDetailServiceConfig;
import com.example.backendhii.dto.consume.LoginConsumeDto;
import com.example.backendhii.dto.produce.TokenProduceDto;
import com.example.backendhii.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("${BASE_API}/basic")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailServiceConfig mUserDetailServiceConfig;
    private final TokenConfig mTokenConfig;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginConsumeDto loginConsumeDto,
                                                 HttpServletRequest httpServletRequest) {
        UserDetails userDetails = mUserDetailServiceConfig.loadUserByUsername(loginConsumeDto.getEmail());
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userDetails.getUsername(),
                    loginConsumeDto.getPassword()));
        } catch (Exception e) {
            throw new BadRequestException("incorrect password");
        }
        TokenProduceDto tokenProduceDto = mTokenConfig.generateToken(userDetails, httpServletRequest);
//        mDeviceService.updateToken(httpServletRequest, tokenProduceDto, userDetails.getUsername());
        return ResponseEntity.ok(BaseResponseDto.success(tokenProduceDto, "login successful"));
    }
}
