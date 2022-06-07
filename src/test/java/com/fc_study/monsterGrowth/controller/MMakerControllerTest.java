package com.fc_study.monsterGrowth.controller;

import com.fc_study.monsterGrowth.code.StatusCode;
import com.fc_study.monsterGrowth.dto.CreateMonsterDto;
import com.fc_study.monsterGrowth.entity.MonsterEntity;
import com.fc_study.monsterGrowth.repository.MonsterRepository;
import com.fc_study.monsterGrowth.service.MMakerService;
import com.fc_study.monsterGrowth.type.MonsterLevel;
import com.fc_study.monsterGrowth.type.MonsterType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.MultiValueMap;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(MMakerController.class)
class MMakerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MMakerService mMakerService;

    @MockBean
    private MonsterRepository monsterRepository;

    private MonsterEntity defaultMonster = MonsterEntity.builder()
                .monsterLevel(MonsterLevel.BABY)
                .monsterType(MonsterType.FLY)
                .statusCode(StatusCode.HEALTHY)
                .ssn("96050311082045")
                .name("애기몬스터")
                .age(3)
                .height(170)
                .weight(73)
                .build();

    private CreateMonsterDto.Request getCreateRequest(){
                return CreateMonsterDto.Request.builder()
                        .monsterLevel(MonsterLevel.BABY)
                        .monsterType(MonsterType.FLY)
                        .statusCode(StatusCode.HEALTHY)
                        .ssn("96050311082045")
                        .name("애기몬스터")
                        .age(3)
                        .height(170)
                        .weight(73)
                        .build();
    }

    /*
          사용하게될 미디어 타입들.
     */
    protected MediaType contentType =
            new MediaType(MediaType.APPLICATION_JSON.getType(),
                    MediaType.APPLICATION_JSON.getSubtype(),
                    StandardCharsets.UTF_8);

    @Test
    void createMonster() throws Exception{
        //given(준비) : 어떠한 데이터가 준비되었을 때
        // validation 준비
        given( monsterRepository.findBySsn(anyString()))
                .willReturn(Optional.ofNullable(defaultMonster));

        // baby 몬스터를 만들어준다.
        // when(실행): 어떠한 함수를 실행하면
        CreateMonsterDto.Response createMonsterDto =  mMakerService.createMonster(getCreateRequest());
        log.info("====================================================================================");
        log.info("createMonsterDto : ",createMonsterDto);
        log.info("====================================================================================");

        // then(검증): 어떠한 결과가 나와야 한다.
        // 가짜 mockMvc가 post 방식으로 create-monster을 호출하고 컨텐츠 타입은 json
        mockMvc.perform(post("/create-monster").contentType(contentType)
                .content(String.valueOf(createMonsterDto)))
                .andExpect(status().isCreated())
                .andDo(print());
    }
}