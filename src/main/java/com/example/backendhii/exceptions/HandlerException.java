package com.example.backendhii.exceptions;

import com.example.backendhii.basess.BaseResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerException {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BaseResponseDto> MyException(RuntimeException e) {
        if (e instanceof BadRequestException) {
            return new ResponseEntity<>(BaseResponseDto.error(e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
        if (e instanceof UnauthorizedException) {
            return new ResponseEntity<>(BaseResponseDto.error(e.getMessage(), 401), HttpStatus.UNAUTHORIZED);
        }
        if (e instanceof ForbiddenException) {
            return new ResponseEntity<>(BaseResponseDto.error(e.getMessage(), 403), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(BaseResponseDto.error(e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @ExceptionHandler(BindException.class)
//    public ResponseEntity<BaseResponseDto> handleValidationExceptions(BindException e) {
//        return new ResponseEntity<>(BaseResponseDto.error(
//                "wrong format",
//                400,
//                new ValidProduceDto(e.getBindingResult().getAllErrors().stream()
//                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
//                        .collect(Collectors.toList()))
//        ), HttpStatus.BAD_REQUEST);
//    }
}
