package com.fc_study.monsterGrowth.service;

import com.fc_study.monsterGrowth.dto.CreateMonsterDto;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class MMakerService {

    public CreateMonsterDto.Response createMonster(
            CreateMonsterDto.Request request
    ){

    }

    private void validateCreateMonsterRequest(
            @NonNull CreateMonsterDto.Request request
    ){
        request.getMonsterLevel().
    }
}
