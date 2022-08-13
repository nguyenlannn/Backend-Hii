package com.example.backendhii.controllers.user;

import com.example.backendhii.basess.BaseResponseDto;
import com.example.backendhii.dto.consume.EditConsumeDto;
import com.example.backendhii.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("${BASE_API}/user")
public class UserController {

    private final UserService mUserService;

    @PatchMapping
    public ResponseEntity<BaseResponseDto> editUser(@RequestBody EditConsumeDto editConsumeDto,
                                                    HttpServletRequest request) {
        return ResponseEntity.ok(BaseResponseDto.success
                (mUserService.editUser(editConsumeDto, request), "edit user successful"));
    }
}
