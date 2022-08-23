package com.example.backendhii.controllers.user;

import com.example.backendhii.basess.BaseResponseDto;
import com.example.backendhii.dto.consume.EditUserConsumeDto;
import com.example.backendhii.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("${BASE_API}/user")
public class UserController {

    private final UserService mUserService;

    @PatchMapping
    public ResponseEntity<BaseResponseDto> editUser(
            @RequestBody EditUserConsumeDto editConsumeDto, HttpServletRequest request) {
        return ResponseEntity.ok().body(BaseResponseDto.success
                (mUserService.editUser(editConsumeDto), "edit user successful"));
    }
    @PostMapping("/update-avatar")
    public ResponseEntity<BaseResponseDto> uploadImage(@RequestParam MultipartFile avatar) throws IOException {
        return ResponseEntity.ok().body(BaseResponseDto.success
                (mUserService.uploadImage(avatar),"upload avatar successful"));
    }
}
