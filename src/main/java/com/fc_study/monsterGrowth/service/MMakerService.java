package com.fc_study.monsterGrowth.service;

import com.fc_study.monsterGrowth.dto.CreateMonsterDto;
import com.fc_study.monsterGrowth.entity.MonsterEntity;
import com.fc_study.monsterGrowth.exception.MMakerException;
import com.fc_study.monsterGrowth.repository.MonsterRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.fc_study.monsterGrowth.exception.MMakerErrorCode.DUPLICATE_SSN;

@Slf4j
@Service
@RequiredArgsConstructor
public class MMakerService {

    private final MonsterRepository monsterRepository;

    /*
        createMonsterFromRequest 를 만드는 이유
            - MonsterEntity 를 인자로 넘겨주기로 했기 때문에
              monsterRepository<MonsterEntity, Long>

        Entity 클래스에 만들어두면 사용하기 편하지 않을까?
            - Entity 클래스는 DB, ORM 관련 클래스이기 때문에
              메서드가 들어가는건 바람직 하지 못하다.
            - 특정 Dto 하나만을 위한 메서드를 만드는것도 좋지 않다.
              (공통적으로 쓸수 있는걸 만들수도 있겠지만)
    */
    private MonsterEntity createMonsterFromRequest(CreateMonsterDto.Request request){
        return MonsterEntity.builder()
                .monsterLevel(request.getMonsterLevel())
                .monsterType(request.getMonsterType())
                .statusCode(request.getStatusCode())
                .ssn(request.getSsn())
                .name(request.getName())
                .age(request.getAge())
                .height(request.getHeight())
                .weight(request.getWeight())
                .build();
    }

    public CreateMonsterDto.Response createMonster(
            CreateMonsterDto.Request request
    ){
        validateCreateMonsterRequest(request);
        // save 메서드는 Entity 에 들어온 값을 다시 리턴해준다.
        return CreateMonsterDto.Response.fromEntity(
                monsterRepository.save(
                        createMonsterFromRequest(request)
                )
        );
    }

    private void validateCreateMonsterRequest(
            @NonNull CreateMonsterDto.Request request
    ){
        request.getMonsterLevel().ValidateAge(
                        request.getAge()
                );
        monsterRepository.findByMonsterSsn(request.getSsn())
                .ifPresent((monsterEntity -> {
                    throw new MMakerException(DUPLICATE_SSN);
                }));
    }
}
