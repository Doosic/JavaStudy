package com.fc_study.monsterGrowth.repository;

import com.fc_study.monsterGrowth.entity.MonsterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MonsterRepository
        extends JpaRepository<MonsterEntity, Long> { //  findByMonsterSsn

    Optional<MonsterEntity> findBySsn(String ssn);

    MonsterEntity deleteBySsn(String ssn);
}
