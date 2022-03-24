package com.softtech.graduationproject.usr.controller;

import com.softtech.graduationproject.gen.dto.RestResponse;
import com.softtech.graduationproject.usr.dto.UsrUserDto;
import com.softtech.graduationproject.usr.dto.UsrUserUpdateRequestDto;
import com.softtech.graduationproject.usr.service.UsrUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UsrUserController {

    private final UsrUserService usrUserService;

    @PutMapping
    public ResponseEntity update(@RequestBody UsrUserUpdateRequestDto usrUserUpdateRequestDto) {
        UsrUserDto usrUserDto = usrUserService.update(usrUserUpdateRequestDto);
        return ResponseEntity.ok(RestResponse.of(usrUserDto));
    }

    @DeleteMapping()
    public ResponseEntity delete() {
        usrUserService.delete();
        return ResponseEntity.ok(RestResponse.empty());
    }
}
