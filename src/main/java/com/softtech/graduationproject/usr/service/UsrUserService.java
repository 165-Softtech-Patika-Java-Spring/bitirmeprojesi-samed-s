package com.softtech.graduationproject.usr.service;

import com.softtech.graduationproject.gen.exceptions.GenBusinessException;
import com.softtech.graduationproject.usr.converter.UsrUserMapper;
import com.softtech.graduationproject.usr.dto.UsrUserDto;
import com.softtech.graduationproject.usr.dto.UsrUserSaveRequestDto;
import com.softtech.graduationproject.usr.dto.UsrUserUpdateRequestDto;
import com.softtech.graduationproject.usr.entity.UsrUser;
import com.softtech.graduationproject.usr.enums.UsrErrorMessage;
import com.softtech.graduationproject.usr.service.entityservice.UsrUserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UsrUserService {

    private final UsrUserEntityService usrUserEntityService;
    private final PasswordEncoder passwordEncoder;

    public List<UsrUserDto> findAll() {
        List<UsrUser> usrUserList = usrUserEntityService.findAll();
        List<UsrUserDto> usrUserDtoList = UsrUserMapper.INSTANCE.convertToUsrUserDtoList(usrUserList);
        return usrUserDtoList;
    }

    public UsrUserDto save(UsrUserSaveRequestDto usrUserSaveRequestDto) {
        String username = usrUserSaveRequestDto.getUsername();
        UsrUser existsUser = usrUserEntityService.findByUsername(username);
        if(existsUser != null) {
            throw new GenBusinessException(UsrErrorMessage.USERNAME_CANNOT_BE_USED);
        }

        UsrUser usrUser = UsrUserMapper.INSTANCE.convertToUsrUser(usrUserSaveRequestDto);

        String password = passwordEncoder.encode(usrUser.getPassword());
        usrUser.setPassword(password);

        usrUser = usrUserEntityService.save(usrUser);
        UsrUserDto usrUserDto = UsrUserMapper.INSTANCE.convertToUsrUserDto(usrUser);
        return usrUserDto;
    }

    public UsrUserDto update(UsrUserUpdateRequestDto usrUserUpdateRequestDto) {
        UsrUser currentUser = usrUserEntityService.getCurrentUser();

        UsrUser usrUser = UsrUserMapper.INSTANCE.convertToUsrUser(usrUserUpdateRequestDto);
        usrUser.setId(currentUser.getId());

        String password = passwordEncoder.encode(usrUser.getPassword());
        usrUser.setPassword(password);
        usrUser.setUsername(currentUser.getUsername());

        usrUser = usrUserEntityService.save(usrUser);

        UsrUserDto usrUserDto = UsrUserMapper.INSTANCE.convertToUsrUserDto(usrUser);

        return usrUserDto;
    }

    public void delete() {
        UsrUser currentUser = usrUserEntityService.getCurrentUser();
        usrUserEntityService.delete(currentUser);
    }
}
