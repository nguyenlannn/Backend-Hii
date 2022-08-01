package com.example.backendhii.controllers;

import com.example.backendhii.basess.BaseResponseDto;
import com.example.backendhii.dto.consume.UserConsumeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${BASE_API}/user")
public class UserController {

    @PostMapping("/signup")
    public ResponseEntity<BaseResponseDto> signup(@RequestBody UserConsumeDto userConsumeDto)
}
