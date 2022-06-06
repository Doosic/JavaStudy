package com.fc_study.monsterGrowth.type;

import com.fc_study.monsterGrowth.exception.MMakerException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Function;

import static com.fc_study.monsterGrowth.constant.MMakerConstant.*;
import static com.fc_study.monsterGrowth.exception.MMakerErrorCode.LEVEL_EXPERIENCE_AGE_NOT_MATCHED;

@AllArgsConstructor
@Getter
public enum MonsterLevel {
    BABY("아기 몬스터", age -> age < MAX_BABY_AGE),
    CHILDREN("어린이 몬스터", age ->
            age > MAX_BABY_AGE && age < MAX_CHILDREN_AGE),
    TEENAGER("청소년 몬스터", age ->
            age > MAX_CHILDREN_AGE && age < MAX_TEENAGER_AGE),
    ADULT("어른 몬스터", age ->
            age > MAX_TEENAGER_AGE && age < MAX_ADULT_AGE),
    OLD("늙은 몬스터", age ->
            age > MAX_ADULT_AGE && age < MAX_OLD_AGE);

    private final String description;
    /*
        @FunctionalInterface
        Function 은 함수형 프로그래밍을 지원해주기 위한 인터페이스이다.
        어떠한 타입의 값을 받아서 어떤 타입의 return 값을 주는 함수다.
     */
    private final Function<Integer, Boolean> validateFunction;

    public void ValidateAge(Integer age){
        // 조건 실패시
        if(!validateFunction.apply(age))
            throw new MMakerException(LEVEL_EXPERIENCE_AGE_NOT_MATCHED);
    }
}
