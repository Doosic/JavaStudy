package com.fc_study.monsterGrowth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MMakerErrorCode {

    NO_MONSTER("해당되는 몬스터가 없습니다."),
    DUPLICATE_SSN("중복되는 몬스터가 있습니다."),
    LEVEL_EXPERIENCE_AGE_NOT_MATCHED("몬스터의 레벨과 나이가 맞지 않습니다."),
    INTERNAL_SERVER_ERROR("서버에 오류가 발생했습니다."),
    INVALID_REQUEST("잘못된 요청입니다.");

    private final String message;
}
