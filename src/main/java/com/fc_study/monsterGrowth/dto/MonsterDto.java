package com.fc_study.monsterGrowth.dto;

import com.fc_study.monsterGrowth.code.StatusCode;
import com.fc_study.monsterGrowth.entity.MonsterEntity;
import com.fc_study.monsterGrowth.entity.MonsterEntity.MonsterLevel;
import com.fc_study.monsterGrowth.entity.MonsterEntity.MonsterType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MonsterDto {
    private Long id;
    private MonsterLevel monsterLevel;
    private MonsterType monsterType;
    private String name;

    public static MonsterDto fromEntity(MonsterEntity monsterEntity){
        return MonsterDto.builder()
                .id(monsterEntity.getId())
                .monsterLevel(monsterEntity.getMonsterLevel())
                .monsterType(monsterEntity.getMonsterType())
                .name(monsterEntity.getName())
                .build();
    }
}
