package com.example.backendhii.controllers.basic;

import com.example.backendhii.basess.BaseResponseDto;
import com.example.backendhii.dto.consume.ActiveUserConsumeDto;
import com.example.backendhii.dto.consume.LoginConsumeDto;
import com.example.backendhii.dto.consume.UserConsumeDto;
import com.example.backendhii.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

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

    @PostMapping("/reset_password")
    public ResponseEntity<BaseResponseDto> resetPassword(@RequestBody UserConsumeDto userConsumeDto){
        return ResponseEntity.ok().body(BaseResponseDto.success
                (mUserService.resetPassword(userConsumeDto), "Change password successfully"));
    }
    @PostMapping("/get_code")
    public ResponseEntity<BaseResponseDto> getCodePassword(@RequestBody LoginConsumeDto loginConsumeDto) throws MessagingException {
        mUserService.getCodePassword(loginConsumeDto);
        return ResponseEntity.ok().body(BaseResponseDto.success("get verification code successfully"));
    }
}
