package com.fc_study.monsterGrowth.dto;

import com.fc_study.monsterGrowth.code.StatusCode;
import com.fc_study.monsterGrowth.entity.MonsterEntity;
import com.fc_study.monsterGrowth.entity.MonsterEntity.MonsterLevel;
import com.fc_study.monsterGrowth.entity.MonsterEntity.MonsterType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class DetailMonsterDto {

    private Long id;
    private MonsterLevel monsterLevel;
    private MonsterType monsterType;
    private String name;
    private StatusCode statusCode;

    public static DetailMonsterDto fromEntity(MonsterEntity monsterEntity){
        return DetailMonsterDto.builder()
                .id(monsterEntity.getId())
                .monsterLevel(monsterEntity.getMonsterLevel())
                .monsterType(monsterEntity.getMonsterType())
                .name(monsterEntity.getName())
                .statusCode(monsterEntity.getStatusCode())
                .build();
    }
}
