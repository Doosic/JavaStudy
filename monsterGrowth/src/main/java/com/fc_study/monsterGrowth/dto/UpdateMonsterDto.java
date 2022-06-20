package com.fc_study.monsterGrowth.dto;

import com.fc_study.monsterGrowth.code.StatusCode;
import com.fc_study.monsterGrowth.entity.MonsterEntity;
import com.fc_study.monsterGrowth.entity.MonsterEntity.MonsterLevel;
import com.fc_study.monsterGrowth.entity.MonsterEntity.MonsterType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UpdateMonsterDto {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Request{
        private MonsterLevel monsterLevel;

        private MonsterType monsterType;

        private StatusCode statusCode;

        @NotNull
        @Size(min = 13, max = 15, message = "ssn max_size 14")
        private String ssn;

        @Min(0)
        @Max(100)
        private Integer age;

        @Min(150)
        @Max(300)
        private Integer height;

        @Min(50)
        @Max(500)
        private Integer weight;
    }
}
