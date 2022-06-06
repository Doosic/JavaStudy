package com.fc_study.monsterGrowth.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MonsterType {
    FLY("비행 몬스터"),
    GROUND("육지 몬스터"),
    SEA("해상 몬스터");

    private final String description;
}
