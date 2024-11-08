package com.efs.backend.Exception;


import lombok.Getter;

@Getter
public enum MessageType {

    NO_RECORD_EXIST("1004" , "could not find any records"),
    GENERAL_EXCEPTION("9999" , "General error"),
    TOKEN_IS_EXPIRED("1005","The token has expired"),
    USERNAME_NOT_FOUND("1006","Username not found"),
    REFRESH_TOKEN_NOT_FOUND("1008","Refresh token not found"),
    REFRESH_TOKEN_HAS_EXPIRED("1009","Refresh token has expired"),
    USERNAME_OR_PASSWORD_INVALID("1007","Username or password invalid");



    private String code;
    private String message;


    MessageType(String code , String message) {
        this.code = code;
        this.message = message;
    }

}
