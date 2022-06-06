package com.fc_study.monsterGrowth.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MonsterLevel {
    BABY("아기 몬스터"),
    CHILDREN("어린이 몬스터"),
    TEENAGER("청소년 몬스터"),
    ADULT("어른 몬스터"),
    OLD("늙은 몬스터");

    private final String description;
    
}
