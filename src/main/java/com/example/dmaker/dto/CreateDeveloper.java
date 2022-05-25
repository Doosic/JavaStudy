package com.example.dmaker.dto;

import com.example.dmaker.type.DeveloperLevel;
import com.example.dmaker.type.DeveloperSkillType;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateDeveloper {
    // 클래스에 하위 스태틱 클래스를 만드는 것, 취향차이
    // 데이터 검증을 이곳에서 실행

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class Request{
        // Request (요청)에 값을 담아주며 데이터를 검증하여준다.
        @NotNull
        private DeveloperLevel developerLevel;
        @NotNull
        private DeveloperSkillType developerSkillType;
        @NotNull
        @Min(0)
        @Max(20)
        private Integer experienceYears;

        @NotNull
        @Size(min = 3, max = 50, message = "memberId size must 3~50")
        private String memberId;
        @NotNull
        @Size(min = 3, max = 50, message = "name size must 3~50")
        private String name;

        // NotNull 을 안붙였기에 값이 없을수도 있지만 있다면 18세 이하
        @Min(18)
        private Integer age;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response{
        private DeveloperLevel developerLevel;
        private DeveloperSkillType developerSkillType;
        private Integer experienceYears;
        // 개발자 생성에 대한 응답에서는 이름, 나이는 개인정보라 제외
        private String memberId;
    }
}
