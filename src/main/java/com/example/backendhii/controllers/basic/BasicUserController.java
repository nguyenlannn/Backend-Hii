package com.example.backendhii.controllers.basic;

import com.example.backendhii.basess.BaseResponseDto;
import com.example.backendhii.dto.consume.ActiveUserConsumeDto;
import com.example.backendhii.dto.consume.UserConsumeDto;
import com.example.backendhii.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("${BASE_API}/basic/user")
public class BasicUserController {

    private final UserService mUserService;

    @PostMapping
    public ResponseEntity<BaseResponseDto> register(@RequestBody UserConsumeDto userConsumeDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponseDto.success
                (mUserService.register(userConsumeDto), "register successful"));
    }

    @PostMapping("/active")
    public ResponseEntity<BaseResponseDto> activeUser(@RequestBody ActiveUserConsumeDto activeUserConsumeDto) {
        mUserService.active(activeUserConsumeDto);
        return ResponseEntity.ok().body(BaseResponseDto.success("active user successful"));
    }
}
