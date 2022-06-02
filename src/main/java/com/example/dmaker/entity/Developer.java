package com.example.dmaker.entity;

import com.example.dmaker.code.StatusCode;
import com.example.dmaker.dto.CreateDeveloper;
import com.example.dmaker.type.DeveloperLevel;
import com.example.dmaker.type.DeveloperSkillType;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
/*
    builder 사용시
    @NoArgsConstructor, @AllArgsConstructor 을
    필수적으로 써주는게 오류가 날 가능성이 적다 이유는 나중에 알아보자
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Developer {
    /*
        @Entity
        entity는 특수한 형태의 클래스이기 때문에 몇가지 규약에 맞춰 클래스를 구성해줘야한다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Enumerated(EnumType.STRING)
    private DeveloperLevel developerLevel;

    @Enumerated(EnumType.STRING)
    private DeveloperSkillType developerSkillType;

    private Integer experienceYears;
    private String memberId;
    private String name;
    private Integer age;

    @Enumerated(EnumType.STRING)
    private StatusCode statusCode;

    @CreatedDate // 오디팅이라는 기능 자동으로 생성시점과 수정시점을 저장해준다.
    private LocalDateTime createAt;

    @LastModifiedDate
    private LocalDateTime updateAt;

    /*
        * Entity 내부에 특정 Dto 하나만을 위한 메서드를 만드는것은 좋지 않다.
        * DB, ORM 관련 클래스이기 때문에 이곳에 메서드가 들어가는건 바람직 하지 못하다.
    public static Developer from(CreateDeveloper.Request request){
        return Developer.builder()
                .developerLevel(request.getDeveloperLevel())
                .developerSkillType(request.getDeveloperSkillType())
                .experienceYears(request.getExperienceYears())
                .memberId(request.getMemberId())
                .statusCode(StatusCode.EMPLOYED)
                .name(request.getName())
                .age(request.getAge())
                .build();
    }*/
}
