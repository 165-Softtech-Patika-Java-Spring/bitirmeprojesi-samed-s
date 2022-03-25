package com.softtech.graduationproject.usr.enums;

import com.softtech.graduationproject.gen.enums.BaseErrorMessage;

public enum UsrErrorMessage implements BaseErrorMessage {

    USER_NOT_FOUND("User not found!"),
    USERNAME_CANNOT_BE_USED("Username cannot be used!")
    ;

    private String message;
    UsrErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
