package com.fc_study.monsterGrowth.entity;

import com.fc_study.monsterGrowth.code.StatusCode;
import com.fc_study.monsterGrowth.type.MonsterLevel;
import com.fc_study.monsterGrowth.type.MonsterType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class MonsterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Enumerated(EnumType.STRING)
    private MonsterLevel monsterLevel;

    @Enumerated(EnumType.STRING)
    private MonsterType monsterType;

    @Enumerated(EnumType.STRING)
    private StatusCode statusCode;

    private String ssn;
    private String name;
    private Integer age;
    private Integer height;
    private Integer weight;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

}
