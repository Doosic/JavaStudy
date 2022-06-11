package com.fc_study.monsterGrowth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fc_study.monsterGrowth.code.StatusCode;
import com.fc_study.monsterGrowth.dto.CreateMonsterDto;
import com.fc_study.monsterGrowth.entity.MonsterEntity;
import com.fc_study.monsterGrowth.repository.MonsterRepository;
import com.fc_study.monsterGrowth.service.MMakerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static com.fc_study.monsterGrowth.entity.MonsterEntity.MonsterLevel.ADULT;
import static com.fc_study.monsterGrowth.entity.MonsterEntity.MonsterLevel.BABY;
import static com.fc_study.monsterGrowth.entity.MonsterEntity.MonsterType.FLY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
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

    private MonsterEntity responseMonster = MonsterEntity.builder()
            .id(1L)
            .monsterLevel(BABY)
            .monsterType(FLY)
            .statusCode(StatusCode.HEALTHY)
            .ssn("12345612345123")
            .name("BabyMonster")
            .age(3)
            .height(170)
            .weight(73)
            .build();

    private CreateMonsterDto.Request getCreateRequest(){
                return CreateMonsterDto.Request.builder()
                        .id(1L)
                        .monsterLevel(BABY)
                        .monsterType(FLY)
                        .statusCode(StatusCode.HEALTHY)
                        .ssn("12345612345123")
                        .name("BabyMonster")
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
        // given: 어떠한 데이터가 준비되었을 때, 특정 메소드가 실행되는 경우 실제 Return 을 줄 수 없기
        //        때문에 아래와 같이 가정 사항을 만들어준다.
        given(mMakerService.createMonster(getCreateRequest()))
                .willReturn(CreateMonsterDto.TestResponse.fromEntity(responseMonster));

        // when: 어떠한 함수를 실행하면
        CreateMonsterDto.Response result = mMakerService.createMonster(getCreateRequest());

        // then: 어떠한 결과가 나와야 한다.
        mockMvc.perform(
                        post("/create-monster")
                                .contentType(contentType)
                                .content(new ObjectMapper().writeValueAsString(result)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        then(mMakerService).should(times(2)).createMonster(getCreateRequest());
    }
}