package com.softtech.graduationproject.sec.controller;

import com.softtech.graduationproject.gen.dto.RestResponse;
import com.softtech.graduationproject.sec.dto.SecurityLoginRequestDto;
import com.softtech.graduationproject.sec.service.AuthenticationService;
import com.softtech.graduationproject.usr.dto.UsrUserDto;
import com.softtech.graduationproject.usr.dto.UsrUserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody SecurityLoginRequestDto securityLoginRequestDto) {
        String token = authenticationService.login(securityLoginRequestDto);
        return ResponseEntity.ok(RestResponse.of(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UsrUserSaveRequestDto usrUserSaveRequestDto) {
        UsrUserDto usrUserDto = authenticationService.register(usrUserSaveRequestDto);
        return ResponseEntity.ok(RestResponse.of(usrUserDto));
    }
}
