package com.fc_study.monsterGrowth.repository;

import com.fc_study.monsterGrowth.code.StatusCode;
import com.fc_study.monsterGrowth.entity.MonsterEntity;
import com.fc_study.monsterGrowth.exception.MMakerException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;

import static com.fc_study.monsterGrowth.entity.MonsterEntity.MonsterLevel.BABY;
import static com.fc_study.monsterGrowth.entity.MonsterEntity.MonsterType.FLY;
import static com.fc_study.monsterGrowth.exception.MMakerErrorCode.NO_MONSTER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataJpaTest
@ExtendWith(MockitoExtension.class)
class MonsterRepositoryTest {

    @Autowired
    MonsterRepository monsterRepository;

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


    @Test
    @DisplayName("findMonster TDDTest")
    void getDetailMonster() {
        // given
        final MonsterEntity resultFirst = monsterRepository.save(
                getDefaultMonster(1L, "First 몬스터", "96050312341234")
        );
        final MonsterEntity resultSecond = monsterRepository.save(
                getDefaultMonster(2L, "Second 몬스터", "96050312341233")
        );

        // when
        MonsterEntity getMonster = monsterRepository.findBySsn(resultFirst.getSsn())
                .orElseThrow(() -> new IllegalArgumentException("no such data"));
        MonsterEntity getMonster2 = monsterRepository.findBySsn(resultSecond.getSsn())
                .orElseThrow(() -> new IllegalArgumentException("no such data"));

        // given
        assertThat(getMonster.getSsn()).isEqualTo(resultFirst.getSsn());
        assertThat(getMonster2.getSsn()).isEqualTo(resultSecond.getSsn());
    }

    @Test
    @DisplayName("allListMonster TDDTest")
    void getAllList() {
        // given
        // 테스트 코드를 짜며 주의할 것: 메소드에서 반복되는 객체생성을 인스턴수 변수로 만들때에는 id 값을 인자값으로 받아 중복되지 않게 주의하자.
        monsterRepository.save(getDefaultMonster(
                1L, "First 몬스터", "96050312341234")
        );
        monsterRepository.save(getDefaultMonster(
                2L, "Second 몬스터", "96050312341233")
        );
        monsterRepository.save(getDefaultMonster(
                3L, "Third 몬스터", "96050312341232")
        );

        // when
        List<MonsterEntity> resultList = monsterRepository.findAll();

        // then
        log.info("resultList: " + resultList.get(0).getName());
        log.info("resultList: " + resultList.get(1).getName());
        log.info("resultList: " + resultList.get(2).getName());
        assertThat(resultList.size()).isEqualTo(3);

    }

    @Test
    @DisplayName("createMonster TDDTest")
    void createMonster() {
        // given
        // getDefaultMonster 를 공통적으로 사용하기 위해 작성해두었다.
        MonsterEntity babyMonster = getDefaultMonster(
                1L, "First 몬스터", "96050312341234"
        );

        // when
        final MonsterEntity result = monsterRepository.save(babyMonster);

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getMonsterLevel()).isEqualTo(babyMonster.getMonsterLevel());
        assertThat(result.getMonsterType()).isEqualTo(babyMonster.getMonsterType());
        assertThat(result.getSsn()).isEqualTo(babyMonster.getSsn());
        assertThat(result.getName()).isEqualTo(babyMonster.getName());
    }

    @Test
    @DisplayName("updateMonster TDDTest")
    void updateMonster() {
        // given
        MonsterEntity resultMonster = monsterRepository.save(
                getDefaultMonster(1L, "FirstMonster", "9605031123456")
        );
        log.info("resultMonster.name: " + resultMonster.getName());
        // when
        resultMonster.setName("UpdateMonster");
        MonsterEntity findUpdateMonster = monsterRepository.findBySsn(resultMonster.getSsn())
                .orElseThrow(() -> new IllegalArgumentException("no such data"));
        log.info("findUpdateMonster.name: " + findUpdateMonster.getName());
        // then
        assertThat(findUpdateMonster.getName()).isEqualTo("UpdateMonster");
    }


    @Test
    @DisplayName("deleteMonster TDDTest")
    void deleteMonster() {
        // given
        MonsterEntity result = monsterRepository.save(
                getDefaultMonster(1L, "deleteMonster", "96050312341234")
        );

        // when
        monsterRepository.deleteById(result.getId());

        MMakerException exception = assertThrows(MMakerException.class, ()->{
            monsterRepository.findById(1L)
                    .orElseThrow(() -> new MMakerException(NO_MONSTER));
        });

        // then
        assertThat(exception.getMMakerErrorCode()).isEqualTo(NO_MONSTER);
    }


}