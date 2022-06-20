package com.fc_study.monsterGrowth.service;

import com.fc_study.monsterGrowth.code.StatusCode;
import com.fc_study.monsterGrowth.dto.CreateMonsterDto;
import com.fc_study.monsterGrowth.dto.DetailMonsterDto;
import com.fc_study.monsterGrowth.dto.UpdateMonsterDto;
import com.fc_study.monsterGrowth.entity.DeadMonsterEntity;
import com.fc_study.monsterGrowth.entity.MonsterEntity;
import com.fc_study.monsterGrowth.exception.MMakerException;
import com.fc_study.monsterGrowth.repository.DeadMonsterRepository;
import com.fc_study.monsterGrowth.repository.MonsterRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.fc_study.monsterGrowth.code.StatusCode.DEAD;
import static com.fc_study.monsterGrowth.exception.MMakerErrorCode.DUPLICATE_SSN;
import static com.fc_study.monsterGrowth.exception.MMakerErrorCode.NO_MONSTER;

@Slf4j
@Service
@RequiredArgsConstructor
public class MMakerService {

    private final MonsterRepository monsterRepository;
    private final DeadMonsterRepository deadMonsterRepository;

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
    private MonsterEntity createMonsterFromRequest(CreateMonsterDto.Request request) {
        return MonsterEntity.builder()
                .id(1L)
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

    // 해당 몬스터가 이미 존재하는지 중복체크
    private MonsterEntity getMonsterBySsn(String ssn) {
        return monsterRepository.findBySsn(ssn)
                .orElseThrow(() -> new MMakerException(NO_MONSTER));
    }

    // ssn 이 같은 몬스터를 가져온다
    public DetailMonsterDto getDetailMonster(
            String monsterSsn
    ) {
        return DetailMonsterDto.fromEntity(getMonsterBySsn(monsterSsn));
    }

    /*
        * Java 의 Stream
        자바의 스트림은 Java8 부터 지원되기 시작한 기능이다. 컬렉션에 저장되어 있는 엘리먼트들을 하나씩 순회하면서
        처리할 수 있는 코드패턴이다. 람다식과 함께 사용되어 컬렉션에 들어있는 데이터에 대한 처리를 매우 간결한 표현으로
        작성할 수 있다. 또 한, 내부 반복자를 사용하기 때문에 병렬처리가 쉽다는 점이 있다.

        ** Map
        map() 메소드는 값을 변환해주는 람다식을 인자로 받는다. 스트림에서 생성된 데이터에 map() 메소드의 인자로 받은 람다식을
        적용해 새로운 데이터를 만들어낸다.

        ** Collect
        자바 스트림을 이용하는 가장 많은 패턴중 하나는 엘리먼트 중 일부를 필터링하고, 값을 변형해서 또 다른
        컬랙션으로 만드는 것이다.
        자바 스트림은 collect() 메소드를 이용해 뽑아져 나오는 데이터들을 컬렉션으로 모아 둘 수 있다. collect() 메소드에는 Collector 메소드를
        사용할 수 있다. Collector 클래스에 있는 정적 메소드를 이용해서 뽑아져나오는 객체들을 원하는 컬랙션으로 만들 수 있다.
        Collector.toList() 를 호출하면 리스트로 만들고, Collector.toSet() 을 호출하면 Set 으로 만들어준다.
    */
    // 존재하는 모든 몬스터를 불러온다
    public List<DetailMonsterDto> getAllDetailMonster() {
        return monsterRepository.findAll()
                .stream().map(DetailMonsterDto::fromEntity)
                .collect(Collectors.toList());
    }

    // 몬스터 생성
    public CreateMonsterDto.Response createMonster(
            CreateMonsterDto.Request request
    ) {
        validateCreateMonsterRequest(request);

        return CreateMonsterDto.Response.fromEntity(
                monsterRepository.save(
                        createMonsterFromRequest(request)
                )
        );
    }

    // 몬스터가 이미 존재하는지 그리고 Enum 의 조건에 맞는지 Validation
    private void validateCreateMonsterRequest(
            @NonNull CreateMonsterDto.Request request
    ) {
        request.getMonsterLevel().ValidateAge(
                request.getAge()
        );
        monsterRepository.findBySsn(request.getSsn())
                .ifPresent((monsterEntity -> {
                    throw new MMakerException(DUPLICATE_SSN);
                }));
    }

    /*
        Update 를 어떻게할지에 대한 방법에 대한 고민중
        1. getUpdateMonsterFromRequest() 메서드를 만들어 검증과 동시에 받은 객체를
           set 하여 더티체킹을 통해 update 하는 방법
        2. getUpdateMonsterFromRequest() 를 사용하지 않고 검증해온 값을 받아 set
           하여 더티체킹을 통해 update 하는 방법

        첫번째 방법은 코드가 깔끔하지만 다른사람이 내 코드를 볼때에 어려움이 있을것같다.
        그러나 장점으로는 코드가 강하게 결합되어있어 내 코드를 이해하기 위해서 코드를 더 자세히
        분석해야함으로 더 정확히 코드의 흐름을 파악하고도록 유도할 수 있다.

        두번째는 다른사람이 내 코드를 파악하기가 쉽다
        개인적으로는 두번째 방법이 더 가독성이 높다고 생각된다.
        그러나
    */
    // 몬스터의 정보 업데이트
    @Transactional
    public DetailMonsterDto updateMonster(
            String ssn,
            UpdateMonsterDto.Request request
    ) {
        request.getMonsterLevel().ValidateAge(
                request.getAge());

//        MonsterEntity monster = getMonsterBySsn(request.getSsn());
//        monster.setMonsterLevel(request.getMonsterLevel());
//        monster.setMonsterType(request.getMonsterType());
//        monster.setStatusCode(request.getStatusCode());
//        monster.setAge(request.getAge());
//        monster.setHeight(request.getHeight());
//        monster.setWeight(request.getWeight());

        return DetailMonsterDto.fromEntity(
                getUpdateMonsterFromRequest(
                        request,
                        getMonsterBySsn(ssn)
                )
        );
    }

    // update 코드를 강하게 결합하기 위해 만들어둔 메소드
    private MonsterEntity getUpdateMonsterFromRequest(
            UpdateMonsterDto.Request request,
            MonsterEntity monsterEntity
    ) {
        monsterEntity.setMonsterLevel(request.getMonsterLevel());
        monsterEntity.setMonsterType(request.getMonsterType());
        monsterEntity.setStatusCode(request.getStatusCode());
        monsterEntity.setAge(request.getAge());
        monsterEntity.setHeight(request.getHeight());
        monsterEntity.setWeight(request.getWeight());
        return monsterEntity;
    }

    /*
        현실적인 delete
        어느곳이든 사용자의 정보를 delete 시 바로 삭제하는것이 아닌
        혹시모를 복구와 문의를 대비해 일정 기간이상 보관후 삭제한다.
        그렇기에 Monster 가 살아있는지 죽었는지 체크를 해주도록 하겠다.
    */
    @Transactional
    public DetailMonsterDto deleteMonster(
        String ssn
    ){
        // 상태 영면으로 변경
        MonsterEntity monster = monsterRepository.findBySsn(ssn)
                .orElseThrow(() -> new MMakerException(NO_MONSTER));
        monster.setStatusCode(DEAD);
        // 영면상태가 된 몬스터는 다른 저장소에서 관리된다.
        DeadMonsterEntity deadMonster = DeadMonsterEntity.builder()
                .statusCode(monster.getStatusCode())
                .ssn(monster.getSsn())
                .name(monster.getName())
                .build();

        deadMonsterRepository.save(deadMonster);
        return DetailMonsterDto.fromEntity(monster);
    }

}
