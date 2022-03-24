package com.softtech.graduationproject.usr.entity;

import com.softtech.graduationproject.gen.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "USR_USER")
@Getter
@Setter
public class UsrUser extends BaseEntity {

    @Id
    @SequenceGenerator(name = "UsrUser", sequenceName = "USR_USER_ID_SEQ")
    @GeneratedValue(generator = "UsrUser")
    private Long id;

    @Column(name = "USERNAME", nullable = false, unique = true, length = 100)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @Column(name = "SURNAME", nullable = false, length = 100)
    private String surname;
}
