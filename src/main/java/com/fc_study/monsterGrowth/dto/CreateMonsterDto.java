package com.fc_study.monsterGrowth.dto;

import com.fc_study.monsterGrowth.code.StatusCode;
import com.fc_study.monsterGrowth.entity.MonsterEntity;
import com.fc_study.monsterGrowth.type.MonsterLevel;
import com.fc_study.monsterGrowth.type.MonsterType;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateMonsterDto {


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @EqualsAndHashCode
    public static class Request{

        @NotNull
        private MonsterLevel monsterLevel;

        @NotNull
        private MonsterType monsterType;

        @NotNull
        private StatusCode statusCode;

        @NotNull
        @Size(min = 13, max = 15, message = "ssn max_size 14")
        private String ssn;

        @NotNull
        @Size(min = 3, max = 100, message = "name size must 3~100")
        private String name;

        @NotNull
        @Min(0)
        @Max(100)
        private Integer age;

        @NotNull
        @Min(150)
        @Max(300)
        private Integer height;

        @NotNull
        @Min(50)
        @Max(500)
        private Integer weight;
    }

    // @Data 생성자에 관여하지 않음...
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @EqualsAndHashCode
    public static class Response{
        private MonsterLevel monsterLevel;
        private MonsterType monsterType;
        private StatusCode statusCode;
        private String ssn;
        private String name;
        private Integer age;
        private Integer height;
        private Integer weight;

        public static Response fromEntity(@NotNull MonsterEntity monsterEntity){
            return Response.builder()
                    .monsterLevel(monsterEntity.getMonsterLevel())
                    .monsterType(monsterEntity.getMonsterType())
                    .statusCode(monsterEntity.getStatusCode())
                    .name(monsterEntity.getName())
                    .build();
        }
    }

    // @Data 생성자에 관여하지 않음...
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @EqualsAndHashCode
    public static class TestResponse{
        private MonsterLevel monsterLevel;
        private MonsterType monsterType;
        private StatusCode statusCode;
        private String ssn;
        private String name;
        private Integer age;
        private Integer height;
        private Integer weight;

        public static Response fromEntity(@NotNull MonsterEntity monsterEntity){
            return Response.builder()
                    .monsterLevel(monsterEntity.getMonsterLevel())
                    .monsterType(monsterEntity.getMonsterType())
                    .statusCode(monsterEntity.getStatusCode())
                    .ssn(monsterEntity.getSsn())
                    .name(monsterEntity.getName())
                    .age(monsterEntity.getAge())
                    .height(monsterEntity.getHeight())
                    .weight(monsterEntity.getWeight())
                    .build();
        }

    }
}
