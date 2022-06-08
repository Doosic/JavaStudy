package com.fc_study.monsterGrowth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fc_study.monsterGrowth.code.StatusCode;
import com.fc_study.monsterGrowth.dto.CreateMonsterDto;
import com.fc_study.monsterGrowth.entity.MonsterEntity;
import com.fc_study.monsterGrowth.service.MMakerService;
import com.fc_study.monsterGrowth.type.MonsterType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static com.fc_study.monsterGrowth.type.MonsterLevel.BABY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(MMakerController.class)
class MMakerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MMakerService mMakerService;

    private MonsterEntity defaultMonster = MonsterEntity.builder()
                .monsterLevel(BABY)
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
                        .monsterLevel(BABY)
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
    @DisplayName("Monster Created Test")
    void createMonster() throws Exception{
        // TODO : create-monster 호출시 mMakerService.createMonster()을 리턴. 리턴값은 CreateMonsterDto.TestResponse
        // TODO : given(준비) = 어떠한 데이터가 준비되었을 때
        given(mMakerService.createMonster(getCreateRequest()))
                .willReturn(CreateMonsterDto.TestResponse.fromEntity(defaultMonster));


        // when(실행): 어떠한 함수를 실행하면
        // andExpect : 기대하는 값이 나왔는지 체크해볼 수 있는 메소드드
        CreateMonsterDto.Response result = mMakerService.createMonster(getCreateRequest());
        // then(검증): 어떠한 결과가 나와야 한다.
        // verify : 해당 객체의 메소드가 실행되었는지 체크해줌
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(
                        post("/create-monster")
                                .contentType(contentType)
                                .content(mapper.writeValueAsString(result)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }
}