package com.example.backendhii.controllers.basic;

import com.example.backendhii.basess.BaseResponseDto;
import com.example.backendhii.dto.consume.UserConsumeDto;
import com.example.backendhii.entities.UserEntity;
import com.example.backendhii.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${BASE_API}/basic/user")
public class UserController {

    private final UserService mUserService;

    @PostMapping
    public ResponseEntity<BaseResponseDto> createRegister(@RequestBody UserConsumeDto userConsumeDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponseDto.success
                (mUserService.register(userConsumeDto),"successful"));
    }
//    @PostMapping("/{abc}")
//    public ResponseEntity<BaseResponseDto> register(@PathVariable Long abc){
//    }

//    @PostMapping("/{id}")
//    public ResponseEntity<BaseResponseDto> register(@RequestParam String lan,
//                                                    @RequestParam String cuong,
//                                                    @PathVariable Long id) {
//    }
}
