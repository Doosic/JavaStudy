package com.fc_study.monsterGrowth.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusCode {
    HEALTHY("건강"),
    SICK("아픔"),
    RECOVERING("회복중"),
    HIBERNATION("동면"),
    DEAD("영면");

    private String description;



}
