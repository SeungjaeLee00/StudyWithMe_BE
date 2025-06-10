package org.seungjae.controller;

import lombok.RequiredArgsConstructor;
import org.seungjae.dto.ApiResponse;
import org.seungjae.service.EmailVerificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth/email")
@RequiredArgsConstructor
public class EmailVerificationController {

    private final EmailVerificationService emailVerificationService;

    @PostMapping("/send-verification-code")
    public ResponseEntity<ApiResponse<Void>> sendVerificationCode(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        ApiResponse<Void> response = emailVerificationService.sendVerificationCode(email);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-code")
    public ResponseEntity<ApiResponse<Void>> verifyCode(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String code = request.get("code");
        ApiResponse<Void> response = emailVerificationService.verifyCode(email, code);
        return ResponseEntity.ok(response);
    }
}
