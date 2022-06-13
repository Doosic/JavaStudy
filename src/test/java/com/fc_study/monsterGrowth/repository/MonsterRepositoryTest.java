package com.fc_study.monsterGrowth.repository;

import com.fc_study.monsterGrowth.code.StatusCode;
import com.fc_study.monsterGrowth.entity.MonsterEntity;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.fc_study.monsterGrowth.entity.MonsterEntity.MonsterLevel.BABY;
import static com.fc_study.monsterGrowth.entity.MonsterEntity.MonsterType.FLY;
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
                .id(1L)
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
    @DisplayName("findMonster Test")
    void getMonster() {
        // given
        final MonsterEntity result = monsterRepository.save(getDefaultMonster(1L,"애기몬스터", "96050312341234"));
        final MonsterEntity result2 = monsterRepository.save(getDefaultMonster(2L,"응애몬스터", "96050312341233"));

        // when
        MonsterEntity getMonster = monsterRepository.findBySsn(result.getSsn())
                .orElseThrow(() -> new IllegalArgumentException("no such data"));
        MonsterEntity getMonster2 = monsterRepository.findBySsn(result2.getSsn())
                .orElseThrow(() -> new IllegalArgumentException("no such data"));

        // given
        assertThat(getMonster.getSsn()).isEqualTo(result.getSsn());
        assertThat(getMonster2.getSsn()).isEqualTo(result2.getSsn());
    }

    @Test
    @DisplayName("allListMonster Test")
    void getAllList(){
        // given
        monsterRepository.save(getDefaultMonster(1L,"애기몬스터", "96050312341234"));
        monsterRepository.save(getDefaultMonster(2L,"응애몬스터", "96050312341233"));
        monsterRepository.save(getDefaultMonster(3L,"응몬스터", "96050312341232"));

        // when
        List<MonsterEntity> resultList = monsterRepository.findAll();

        // then
        log.info("resultList: "+ resultList.get(0).getName());
        log.info("resultList: "+ resultList.get(1).getName());
//        assertThat(resultList.size()).isEqualTo(2);

    }

    @Test
    @DisplayName("createMonster Test")
    void createMonster() {
        // given
        // getDefaultMonster 를 공통적으로 사용하기 위해 작성해두었다.
        MonsterEntity babyMonster = getDefaultMonster(1L,"애기몬스터", "96050312341234");

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
    @DisplayName("updateMonster Test")
    void updateMonster() {
        // given


        // when


        // then


    }

}