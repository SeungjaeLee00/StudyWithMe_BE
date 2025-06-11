package org.seungjae.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.seungjae.dto.ApiResponse;
import org.seungjae.dto.UserLoginRequestDto;
import org.seungjae.dto.UserLoginResponseDto;
import org.seungjae.dto.UserSignupRequestDto;
import org.seungjae.service.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UsersController {

    private final UsersService usersService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signup(@RequestBody @Valid UserSignupRequestDto dto) {
        usersService.signup(dto);
        return ResponseEntity.ok(new ApiResponse<>(true, "회원가입이 완료되었습니다."));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Void>> login(@RequestBody @Valid UserLoginRequestDto dto, HttpServletResponse response) {
        usersService.login(dto, response);
        return ResponseEntity.ok(new ApiResponse<>(true, "로그인에 성공하였습니다."));
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpServletRequest request, HttpServletResponse response) {
        usersService.logout(request, response);
        return ResponseEntity.ok(new ApiResponse<>(true, "로그아웃에 성공하였습니다."));
    }
}
