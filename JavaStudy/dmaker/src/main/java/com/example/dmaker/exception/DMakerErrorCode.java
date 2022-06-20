package com.example.dmaker.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DMakerErrorCode {
    /*
        기본적으로 예외를 던질때에는 다양한 익셉션을 사용할 수 있지만
        회사에서 만든 애플리케이션의 특정 비즈니스의 예외때는 직접 만드는 커스텀 Exception 을 사용하는것이
        표현력이 풍부하고 원하는 기능을 넣을 수 있어 직접 정의해 주는것이 좋다.

        API를 호출했을때 우리가 알수 없는 오류가 발생했을때를 대비한 문구도 만들어줘야한다.

        각 에러코드들을 정의해주고 기본이 되는 메세지들을 프로퍼티에 메세지로 담아주면 편리하다.
    */
    NO_DEVELOPER("해당되는 개발자가 없습니다."),
    DUPLICATED_MEMBER_ID("MemberId가 중복되는 개발자가 있습니다."),
    LEVEL_EXPERIENCE_YEARS_NOT_MATCHED("개발자 레벨과 연차가 맞지 않습니다."),
    INTERNAL_SERVER_ERROR("서버에 오류가 발생했습니다."),
    INVALID_REQUEST("잘못된 요청입니다.");

    private final String message;
}
