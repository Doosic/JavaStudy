package com.example.dmaker.exception;

import lombok.Getter;

@Getter
public class DMakerException extends RuntimeException{
    // 메세지를 담아줄 변수 선언.
    private DMakerErrorCode dMakerErrorCode;
    private String detailMessage;

    // 일반적인 경우에 사용
    // errorCode만 받아 기본 메세지만 담아준다.
    public DMakerException(DMakerErrorCode errorCode){
        super(errorCode.getMessage());
        this.dMakerErrorCode = errorCode;
        this.detailMessage = errorCode.getMessage();
    }

    // errorCode와 메세지를 따로받아 따로보여준다.
    public DMakerException(DMakerErrorCode errorCode, String detailMessage){
        super(detailMessage);
        this.dMakerErrorCode = errorCode;
        this.detailMessage = errorCode.getMessage();
    }

}
