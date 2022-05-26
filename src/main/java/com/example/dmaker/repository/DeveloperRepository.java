package com.example.dmaker.repository;

import com.example.dmaker.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Spring Jap기능을 활용하여 간단하게 db에 저장할 수 있는 인터페이스를 구현
@Repository
public interface DeveloperRepository
        extends JpaRepository <Developer, Long>{
    // Optional 이 뭔지 정리하기
    // jpa에서는 메서드 명만가지고도 특정 컬럼의 검색이 가능하다.
    Optional<Developer> findByMemberId(String memberId);
}
