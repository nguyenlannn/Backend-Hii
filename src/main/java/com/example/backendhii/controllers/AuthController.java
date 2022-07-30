package com.example.backendhii.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${base_api}/basic")
public class AuthController {

//    private final AuthenticationManager authenticationManager;
//    private final UserDetailServiceConfig mUserDetailServiceConfig;
//    private final TokenConfig mTokenConfig;


//    @PostMapping()
//    public ResponseEntity<?> login(@RequestBody LoginConsumeDto loginConsumeDto,
//                                                 HttpServletRequest httpServletRequest) {
//        UserDetails userDetails = mUserDetailServiceConfig.loadUserByUsername(loginConsumeDto.getEmail());
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                    userDetails.getUsername(),
//                    loginConsumeDto.getPassword()));
//        } catch (Exception e) {
////            throw new BadRequestException("incorrect password");
//        }
//        TokenProduceDto tokenProduceDto = mTokenConfig.generateToken(userDetails, httpServletRequest);
////        mDeviceService.updateToken(httpServletRequest, tokenProduceDto, userDetails.getUsername());
//        return ResponseEntity.ok(BaseResponseDto.success(tokenProduceDto, "login successful"));
//        return ResponseEntity.ok().body("");
//    }

    @GetMapping
    public ResponseEntity<?> aaaaa() {
        return ResponseEntity.ok().body("ok");
    }
}
