package com.fc_study.monsterGrowth.service;

import com.fc_study.monsterGrowth.code.StatusCode;
import com.fc_study.monsterGrowth.dto.CreateMonsterDto;
import com.fc_study.monsterGrowth.entity.MonsterEntity;
import com.fc_study.monsterGrowth.repository.MonsterRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.fc_study.monsterGrowth.entity.MonsterEntity.MonsterLevel.BABY;
import static com.fc_study.monsterGrowth.entity.MonsterEntity.MonsterType.FLY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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

    private MonsterEntity defaultMonster = MonsterEntity.builder()
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

    @Test
    void createMonsterTdd(){
        // given
        // defaultMonster 인스턴스 변수를 사용 (공통적으로 사용하기 위해 빼두었다.)
        final MonsterEntity defaultMonster1 = MonsterEntity.builder()
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

        // when
        final MonsterEntity result = monsterRepository.save(defaultMonster1);

        // then
        log.info("result: "+result);

    }

    @Test
    void createMonster(){
        // TODO : create-monster 호출시 mMakerService.createMonster()을 리턴. 리턴값은 CreateMonsterDto.TestResponse
        // TODO : given(준비) = 어떠한 데이터가 준비되었을 때
        given(monsterRepository.findBySsn(anyString()))
                .willReturn(Optional.empty());

        given(monsterRepository.save(any()))
                .willReturn(defaultMonster);
        // TODO : ArgumentCaptor<저장하게 될 타입>. create 할 때에 ArgumentCaptor 를 통해 데이터를 저장
        ArgumentCaptor<MonsterEntity> captor =
                ArgumentCaptor.forClass(MonsterEntity.class);

        // TODO : when(실행) = 어떠한 함수를 실행하면, andExpect : 기대하는 값이 나왔는지 체크해볼 수 있는 메소드드
        CreateMonsterDto.Response result = mMakerService.createMonster(getCreateRequest());

        // TODO : then(검증) = 어떠한 결과가 나와야 한다.
        then(monsterRepository)
                .should(times(1))
                .save(captor.capture());
        // TODO : captor.getValue() = 캡쳐된 결과를 꺼낼 수 있다
        MonsterEntity saveMonster = captor.getValue();


        assertEquals(BABY, saveMonster.getMonsterLevel());
    }

    @Test
    void updateMonster(){

    }
}