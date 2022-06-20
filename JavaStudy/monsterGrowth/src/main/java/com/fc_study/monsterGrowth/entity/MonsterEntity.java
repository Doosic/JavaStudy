package com.fc_study.monsterGrowth.entity;

import com.fc_study.monsterGrowth.code.StatusCode;
import com.fc_study.monsterGrowth.exception.MMakerException;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.function.Function;

import static com.fc_study.monsterGrowth.constant.MMakerConstant.*;
import static com.fc_study.monsterGrowth.exception.MMakerErrorCode.LEVEL_EXPERIENCE_AGE_NOT_MATCHED;

@Getter
@Setter
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

    // TODO : 원래는 Enum 을 Type 디렉토리를 만들어 다른곳에 정의하여 사용했으나 enum 들을 클래스와 관련있는 Entity 로 옮겨 직접적으로 관계가 있음을 코드상으로 표현해주었다.
    // TODO : type 디렉토리에는 모든 클래스에서 공통적으로 사용할 것만 넣을예정, 특정 Entity 클래스와 관련있는것은 Entity 클래스 내부에 enum 작성
    @AllArgsConstructor
    @Getter
    public enum MonsterLevel {
        BABY("아기 몬스터", age -> age < MAX_BABY_AGE),
        CHILDREN("어린이 몬스터", age ->
                age > MAX_BABY_AGE && age < MAX_CHILDREN_AGE),
        TEENAGER("청소년 몬스터", age ->
                age > MAX_CHILDREN_AGE && age < MAX_TEENAGER_AGE),
        ADULT("어른 몬스터", age ->
                age > MAX_TEENAGER_AGE && age < MAX_ADULT_AGE),
        OLD("늙은 몬스터", age ->
                age > MAX_ADULT_AGE && age < MAX_OLD_AGE);

        public final String description;
        /*
            @FunctionalInterface
            Function 은 함수형 프로그래밍을 지원해주기 위한 인터페이스이다.
            어떠한 타입의 값을 받아서 어떤 타입의 return 값을 주는 함수다.
         */
        private final Function<Integer, Boolean> validateFunction;

        public void ValidateAge(Integer age){
            // 조건 실패시
            if(!validateFunction.apply(age))
                throw new MMakerException(LEVEL_EXPERIENCE_AGE_NOT_MATCHED);
        }
    }

    @AllArgsConstructor
    @Getter
    public enum MonsterType {
        FLY("비행 몬스터"),
        GROUND("육지 몬스터"),
        SEA("해상 몬스터");

        private final String description;
    }

}
