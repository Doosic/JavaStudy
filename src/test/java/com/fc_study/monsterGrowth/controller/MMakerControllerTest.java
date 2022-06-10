package com.fc_study.monsterGrowth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fc_study.monsterGrowth.code.StatusCode;
import com.fc_study.monsterGrowth.dto.CreateMonsterDto;
import com.fc_study.monsterGrowth.entity.MonsterEntity;
import com.fc_study.monsterGrowth.service.MMakerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static com.fc_study.monsterGrowth.entity.MonsterEntity.MonsterLevel.BABY;
import static com.fc_study.monsterGrowth.entity.MonsterEntity.MonsterType.FLY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.refEq;
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

    private MonsterEntity defaultMonster = MonsterEntity.builder()
                .id(1L)
                .monsterLevel(BABY)
                .monsterType(FLY)
                .statusCode(StatusCode.HEALTHY)
                .ssn("96050311082045")
                .name("기븐입니다.")
                .age(3)
                .height(170)
                .weight(73)
                .build();

    private MonsterEntity defaultMonster1 = MonsterEntity.builder()
            .id(1L)
            .monsterLevel(BABY)
            .monsterType(FLY)
            .statusCode(StatusCode.HEALTHY)
            .ssn("96050311082045")
            .name("덴입니다.")
            .age(3)
            .height(170)
            .weight(73)
            .build();

    // id의 값이 다르면 현재 잡아주질 못함 여기서 원인이 파악된다. 왜냐
    // 현재 @EqualsAndHashCode 를 사용하고 있기에 내용물(id)이 같다면
    // 같다고 판단을 내려주게 해뒀는데 body를 가져오지 못함. 즉, @EqualsAndHashCode 를 사용할때에는
    // 주소 참조값으로 비교하여 실패를 했었다는것이고 @EqualsAndHashCode 사용 이후에는 내용물로 비교를 하였다는게 증명되는 것이다.
    // 그 위치만 찾으면 됡거같은데...!
    // 위치는 save 이후 반환하는 부분의 문제로 보임
    //
    private CreateMonsterDto.Request getCreateRequest(){
                return CreateMonsterDto.Request.builder()
                        .id(1L)
                        .monsterLevel(BABY)
                        .monsterType(FLY)
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

        // TODO : when(실행) = 어떠한 함수를 실행하면, andExpect : 기대하는 값이 나왔는지 체크해볼 수 있는 메소드드
        // 여기서 result 반환을 못해준다.
        CreateMonsterDto.Response result = mMakerService.createMonster(any());
        // 검증 Then 에서는 기븐입니다(monster1)을 보냈고 준비를하고 결과를 도출하는 과정에서는
        // @EqualsAndHashCode 를 사용하지 않으면 주소 참조값으로 비교하기 때문에 올바른 결과가 나온것으로
        // 되지 않는것이다. 보낸것과 다른 결과물이 나왔기 때문에 body에 아무것도 실려있진 않은것.

        // TODO : then(검증) = 어떠한 결과가 나와야 한다.
        mockMvc.perform(
                        post("/create-monster")
                                .contentType(contentType)
                                .content(new ObjectMapper().writeValueAsString(defaultMonster1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        // TODO : times() 해당 메서드가 몇번 호출되었는지 검증
        // TODO : then(Mock객체).should(수행횟수 검증).수행 메서드와(인자값)
//        then(mMakerService).should(times(2)).createMonster(getCreateRequest());
    }
}