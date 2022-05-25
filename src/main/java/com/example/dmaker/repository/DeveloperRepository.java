package com.example.dmaker.repository;

import com.example.dmaker.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Spring Jap기능을 활용하여 간단하게 db에 저장할 수 있는 인터페이스를 구현
@Repository
public interface DeveloperRepository
        extends JpaRepository <Developer, Long>{
}
