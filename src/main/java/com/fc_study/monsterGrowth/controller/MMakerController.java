package com.fc_study.monsterGrowth.controller;

import com.fc_study.monsterGrowth.dto.CreateMonsterDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MMakerController {

    /*
        @Valid: 바디에 들어온 값들을 담아주면서 벨리데이션을 해주고 문제가 생기면 메서드 진입전에 Exception 을 일으켜준다.
        @RequestBody: CreateDeveloper.Request 에 담아주겠다 라는 의미
    */
    @PostMapping("/create-monster")
    public CreateMonsterDto.Response createMonster(
            @Valid @RequestBody final CreateMonsterDto.Request request
    ){
        log.info("request : ", request);
        return;
    }

}
