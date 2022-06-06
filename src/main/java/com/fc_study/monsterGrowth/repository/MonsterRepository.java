package com.fc_study.monsterGrowth.repository;

import com.fc_study.monsterGrowth.entity.MonsterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MonsterRepository
        extends JpaRepository<MonsterEntity, Long> {

    Optional<MonsterEntity> findByMonsterSsn(String monsterId);
}
