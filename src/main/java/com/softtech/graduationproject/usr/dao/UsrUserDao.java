package com.softtech.graduationproject.usr.dao;

import com.softtech.graduationproject.usr.entity.UsrUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsrUserDao extends JpaRepository<UsrUser, Long> {

    UsrUser findByUsername(String username);
}
