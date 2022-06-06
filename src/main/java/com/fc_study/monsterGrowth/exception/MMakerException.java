package com.fc_study.monsterGrowth.exception;

import lombok.Getter;

@Getter
public class MMakerException extends RuntimeException{

    private MMakerErrorCode mMakerErrorCode;
    private String detailMessage;

    // errorCode 만 받고 기본 메세지를 보여준다.
    public MMakerException(MMakerErrorCode errorCode){
        super(errorCode.getMessage());
        this.mMakerErrorCode = errorCode;
        this.detailMessage = errorCode.getMessage();
    }

    // errorCode 와 Massage 를 따로 받아서 보여준다.
    public MMakerException(MMakerErrorCode errorCode, String detailMessage){
        // super() 을 통해 부모클래스의 생성자를 호출하여 원하는 Massage 로 초기화 후 get 으로 꺼내서 사용
        super(detailMessage);
        this.mMakerErrorCode = errorCode;
        this.detailMessage = errorCode.getMessage();
    }

}
