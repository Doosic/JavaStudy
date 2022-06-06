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
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class Request{

        @NotNull
        private MonsterLevel monsterLevel;

        @NotNull
        private MonsterType monsterType;

        @NotNull
        private StatusCode statusCode;

        @NotNull
        @Size(min = 14, max = 14, message = "ssn max_size 14")
        private String ssn;

        @NotNull
        @Size(min = 3, max = 100, message = "name size must 3~100")
        private String name;

        @NotNull
        @Min(0)
        @Max(100)
        private Integer age;

        @NotNull
        @Size(min = 50, max = 1000, message = "height size must 50~1000")
        private Integer height;

        @NotNull
        @Size(min = 50, max = 5000, message = "height size must 50~5000")
        private Integer weight;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class Response{
        private MonsterLevel monsterLevel;
        private MonsterType monsterType;
        private StatusCode statusCode;
        private String name;

        public static Response fromEntity(@NotNull MonsterEntity monsterEntity){
            return Response.builder()
                    .monsterLevel(monsterEntity.getMonsterLevel())
                    .monsterType(monsterEntity.getMonsterType())
                    .statusCode(monsterEntity.getStatusCode())
                    .name(monsterEntity.getName())
                    .build();
        }

    }
}
