package com.fc_study.monsterGrowth.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fc_study.monsterGrowth.dto.CreateMonsterDto;
import com.fc_study.monsterGrowth.dto.DetailMonsterDto;
import com.fc_study.monsterGrowth.dto.MonsterDto;
import com.fc_study.monsterGrowth.service.MMakerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MMakerController {
    private final MMakerService mMakerService;

    /*
        @Valid: 바디에 들어온 값들을 담아주면서 벨리데이션을 해주고 문제가 생기면 메서드 진입전에 Exception 을 일으켜준다.
        @RequestBody: 파라미터의 값과 이름을 함께 전달하는 방식으로 게시판 등에서 페이지 및 검색 정보를
                      함께 전달하는 방식을 사용할때 많이 사용된다.
        @PathVariable: Rest api 에서 값을 호출할 때 주로 많이 사용한다.

        둘중 하나만 사용하는 것이 아닌 복합적으로 사용할때도 있으며 방법의 차이일뿐 자신의 개발 프로젝트의
        방향에 맞게끔 사용하면 된다.
    */
    @PostMapping("/create-monster")
    public CreateMonsterDto.Response createMonster(
            @Valid @RequestBody CreateMonsterDto.Request request
    ){
        return mMakerService.createMonster(request);
    }

    @GetMapping("/detail-monster")
    public DetailMonsterDto getMonsterDetail(
            @PathVariable final String monsterSsn
    ){
        return mMakerService.getDetailMonster(monsterSsn);
    }

}
