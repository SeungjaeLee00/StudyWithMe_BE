package org.seungjae.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.seungjae.dto.ApiResponse;
import org.seungjae.dto.UserSignupRequestDto;
import org.seungjae.service.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UsersController {

    private final UsersService usersService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signup(@RequestBody @Valid UserSignupRequestDto dto) {
        usersService.signup(dto);
        return ResponseEntity.ok(new ApiResponse<>(true, "회원가입이 완료되었습니다."));
    }
}
