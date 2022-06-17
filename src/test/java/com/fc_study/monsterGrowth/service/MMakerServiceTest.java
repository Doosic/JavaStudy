package com.fc_study.monsterGrowth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fc_study.monsterGrowth.code.StatusCode;
import com.fc_study.monsterGrowth.dto.CreateMonsterDto;
import com.fc_study.monsterGrowth.dto.DetailMonsterDto;
import com.fc_study.monsterGrowth.dto.UpdateMonsterDto;
import com.fc_study.monsterGrowth.entity.MonsterEntity;
import com.fc_study.monsterGrowth.exception.MMakerErrorCode;
import com.fc_study.monsterGrowth.exception.MMakerException;
import com.fc_study.monsterGrowth.repository.MonsterRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.h2.command.dml.Update;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.xml.soap.Detail;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.fc_study.monsterGrowth.entity.MonsterEntity.MonsterLevel.BABY;
import static com.fc_study.monsterGrowth.entity.MonsterEntity.MonsterLevel.CHILDREN;
import static com.fc_study.monsterGrowth.entity.MonsterEntity.MonsterType.FLY;
import static com.fc_study.monsterGrowth.exception.MMakerErrorCode.NO_MONSTER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Slf4j
@ExtendWith(MockitoExtension.class)
class MMakerServiceTest {

    @Mock
    private MonsterRepository monsterRepository;

    @InjectMocks
    private MMakerService mMakerService;

    private MonsterEntity getDefaultMonster(Long id, String name, String ssn) {
        return MonsterEntity.builder()
                .id(id)
                .monsterLevel(BABY)
                .monsterType(FLY)
                .statusCode(StatusCode.HEALTHY)
                .ssn(ssn)
                .name(name)
                .age(3)
                .height(170)
                .weight(73)
                .build();
    }
    private MonsterEntity defaultMonster = MonsterEntity.builder()
                .id(1L)
                .monsterLevel(BABY)
                .monsterType(FLY)
                .statusCode(StatusCode.HEALTHY)
                .ssn("96050312341234")
                .name("피죤투")
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
                .ssn("96050311082045")
                .name("애기몬스터")
                .age(3)
                .height(170)
                .weight(73)
                .build();
    }

    private UpdateMonsterDto.Request defaultUpdateMonster =
            UpdateMonsterDto.Request.builder()
                    .monsterLevel(CHILDREN)
                    .monsterType(FLY)
                    .statusCode(StatusCode.HEALTHY)
                    .age(8)
                    .height(200)
                    .weight(127)
                    .build();

    @Test
    @DisplayName("detailMonster BDDTest")
    void getDetailMonster() {
        // given: 어떤 데이터가 준비되어있을때
        given(monsterRepository.findBySsn(anyString()))
                .willReturn(Optional.of(getDefaultMonster(1L, "First Monster", "96050312341234")));

        // when: 어떤 함수를 실행하면
        DetailMonsterDto detailMonsterDto = mMakerService.getDetailMonster("96050312341234");


        // then: 어떤 결과가 나와야 한다.
        then(monsterRepository).should(times(1)).findBySsn("96050312341234");
        assertThat(detailMonsterDto.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("allListMonster BDDTest")
    void getAllListMonster() {
        // given: 어떤 데이터가 준비되어있을때
        List<MonsterEntity> monsterList  = new ArrayList<>();
        monsterList.add(getDefaultMonster(1L, "First Monster", "96050312341231"));
        monsterList.add(getDefaultMonster(2L, "Second Monster", "96050312341232"));
        monsterList.add(getDefaultMonster(3L, "Third Monster", "96050312341233"));

        given(monsterRepository.findAll())
                .willReturn(monsterList);

        // when: 어떤 함수를 실행하면
        List<DetailMonsterDto> detailMonsterDtoList = mMakerService.getAllDetailMonster();

        // then: 어떤 결과가 나와야 한다.
        then(monsterRepository).should(times(1)).findAll();
        assertThat(detailMonsterDtoList.get(0).getId()).isEqualTo(1L);
        assertThat(detailMonsterDtoList.get(1).getId()).isEqualTo(2L);
        assertThat(detailMonsterDtoList.get(2).getId()).isEqualTo(3L);

    }


    @Test
    @DisplayName("createMonster BDDTest")
    void createMonster() throws JsonProcessingException {
        // create-monster 호출시 mMakerService.createMonster()을 리턴. 리턴값은 CreateMonsterDto.TestResponse
        // given(준비) = 어떠한 데이터가 준비되었을 때
        given(monsterRepository.findBySsn(anyString()))
                .willReturn(Optional.empty());

        given(monsterRepository.save(any()))
                .willReturn(getDefaultMonster(1L, "defaultMonster", "96050312341234"));
        // ArgumentCaptor<저장하게 될 타입>. create 할 때에 ArgumentCaptor 를 통해 데이터를 저장
        ArgumentCaptor<MonsterEntity> captor =
                ArgumentCaptor.forClass(MonsterEntity.class);

        // when(실행) = 어떠한 함수를 실행하면, andExpect : 기대하는 값이 나왔는지 체크해볼 수 있는 메소드드
        CreateMonsterDto.Response result = mMakerService.createMonster(getCreateRequest());

        // then(검증) = 어떠한 결과가 나와야 한다.
        then(monsterRepository)
                .should(times(1))
                .save(captor.capture());
        // captor.getValue() = 캡쳐된 결과를 꺼낼 수 있다
        MonsterEntity saveMonster = captor.getValue();

        log.info("saveMonster: "+ new ObjectMapper().writeValueAsString(saveMonster));
        assertEquals(BABY, saveMonster.getMonsterLevel());
    }

    @Test
    @DisplayName("updateMonster BDDTest")
    void updateMonster(){
        given(monsterRepository.findBySsn(anyString()))
                .willReturn(Optional.ofNullable(defaultMonster));

        DetailMonsterDto detailMonsterDto = mMakerService.updateMonster(anyString(), defaultUpdateMonster);

        then(monsterRepository).should(times(1)).findBySsn(anyString());
        assertThat(detailMonsterDto.getMonsterLevel()).isEqualTo(CHILDREN);
        assertThat(detailMonsterDto.getMonsterType()).isEqualTo(defaultUpdateMonster.getMonsterType());
    }
}