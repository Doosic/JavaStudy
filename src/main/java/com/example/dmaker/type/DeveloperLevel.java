package com.example.dmaker.type;

import com.example.dmaker.exception.DMakerErrorCode;
import com.example.dmaker.exception.DMakerException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Function;

import static com.example.dmaker.constant.DMakerConstant.MAX_JUNIOR_EXPERIENCE_YEARS;
import static com.example.dmaker.constant.DMakerConstant.MIN_SENIOR_EXPERIENCE_YEARS;
import static com.example.dmaker.exception.DMakerErrorCode.LEVEL_EXPERIENCE_YEARS_NOT_MATCHED;

@AllArgsConstructor
@Getter
public enum DeveloperLevel {
    NEW("신입 개발자", years -> years == 0),
    JUNIOR("주니어 개발자", years -> years == MAX_JUNIOR_EXPERIENCE_YEARS),
    JUNGNIOR("중니어 개발자", years -> years > MAX_JUNIOR_EXPERIENCE_YEARS
           && years < MIN_SENIOR_EXPERIENCE_YEARS),
    SENIOR("시니어 개발자", years -> years >= MIN_SENIOR_EXPERIENCE_YEARS)
    ;

    private final String description;
    /*
        @FunctionalInterface
        Function 은 함수형 프로그래밍을 지원해주기 위한 인터페이스이다.
        어떠한 타입의 값을 받아서 어떤 타입의 return 값을 주는 그런 함수다.
     */
    private final Function<Integer, Boolean> validateFunction;

    /*
        Function 은 private 이기 때문에 외부에 돌려주기 위한
     */
    public void ValidateExperienceYears(Integer years){
        // 조건 실패시
        if(!validateFunction.apply(years))
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
    }
}
