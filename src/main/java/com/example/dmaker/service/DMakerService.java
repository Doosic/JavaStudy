package com.example.dmaker.service;

import com.example.dmaker.dto.CreateDeveloper;
import com.example.dmaker.entity.Developer;
import com.example.dmaker.exception.DMakerErrorCode;
import com.example.dmaker.exception.DMakerException;
import com.example.dmaker.repository.DeveloperRepository;
import com.example.dmaker.type.DeveloperLevel;
import com.example.dmaker.type.DeveloperSkillType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;

import java.util.Optional;

import static com.example.dmaker.exception.DMakerErrorCode.DUPLICATED_MEMBER_ID;
import static com.example.dmaker.exception.DMakerErrorCode.LEVEL_EXPERIENCE_YEARS_NOT_MATCHED;

@Service
@RequiredArgsConstructor
public class DMakerService {
    /*
        @RequiredArgsConstructor
        Service나 Controller는 위의 어노테이션을 사용하면 편리하다.
        자동으로 DeveloperRepository를 자동으로 Injection 해준다.

        과거에는 @Autowired, @Inject를 사용했어야 한다.
        @Autowired
        @Inject
        private DeveloperRepository developerRepository;
        이걸 사용시 서비스 코드가 어노테이션에 종속적으로 만들어져있어 서비스를 단독으로
        테스트를 하고싶어도 하기 어려워지는 문제가 있었다.

        그래서 개선방안으로 나온게 생성자를 통한 주입 방식이였다
        그런데 이런 방식에 문제도 서비스와 Reposiroty가 여러개일때에 고치기 힘들기에
        다음과 같은 방식이 나온것이다.
        public DMakerService(DeveloperRepository developerRepository){
        this.developerRepository = developerRepository;
    }
     */

    // final이 붙은애는 무조건 있어야 하기 때문에 final이 붙은 기본 생성자를 만들어준다.
    private final DeveloperRepository developerRepository;
    // private final EntityManager em; 데이터베이스를 추상화 한것
//    private final EntityManager em;

    // 컨트롤 단에서@Valid를 실행했으므로 Service에서는 안해도됨
    // CreateDeveloper.@Valid Request request -> CreateDeveloper.Request request
    @Transactional
    public CreateDeveloper.Response createDeveloper(CreateDeveloper.Request request){
        validateCreateDeveloperRequest(request);
//        EntityTransaction transaction = em.getTransaction();
//        try{
//            transaction.begin();

            // buisness logic start
            Developer developer = Developer.builder()
                    .developerLevel(request.getDeveloperLevel())
                    .developerSkillType(request.getDeveloperSkillType())
                    .experienceYears(request.getExperienceYears())
                    .memberId(request.getMemberId())
                    .name(request.getName())
                    .age(request.getAge())
                    .build();
            developerRepository.save(developer);
            return CreateDeveloper.Response.fromEntity(developer);

            // A - > B 1만원 송금
            // A 계좌에서 1만원 줄임
//            developerRepository.save(developer);
            // B 계좌에서 1만원 늘림
//            developerRepository.delete(developer);
            // business logic end
            // AOP가 이런때에 적용된다. 특정 우리가 넣고싶은 곳에 넣을때에 넣을수 있는데(포인트컷)
            // 좀 더 많이쓰이는것은 Annotation 기반의 포인트 컷이다.
            /*
                @Transactional을 클래스나 메서드 위에 붙여주면 다음과같은
                EntityTransaction transaction = em.getTransaction();
                transaction.begin();
                transaction.commit();
                transaction.rollback();
                귀찮은 과정들을 쓰지 않아도된다.
             */

            // 위와같은 작업을 하다가 중간에 뻑나면 커밋이 아닌 롤백을 해야함
//            transaction.commit();
//        }catch (Exception err){
//            transaction.rollback();
//            throw err;
//
//        }
    }

    private void validateCreateDeveloperRequest(CreateDeveloper.Request request) {
        // business validation
        // 개발 레밸이 시니어면서 나이가 18세 미만이라면 예외를 던져준다.
        // 기본적으로 예외를 던질때에는 다양한 익셉션을 사용할 수 있지만
        // 회사에서 만든 애플리케이션의 특정 비즈니스의 예외때는 직접 만드는 커스텀 Exception 을 사용하는것이
        // 표현력이 풍부하고 원하는 기능을 넣을 수 있어 직접 정의해 주는것이 좋다.
        DeveloperLevel developerLevel = request.getDeveloperLevel();
        Integer experienceYears = request.getExperienceYears();
        if(developerLevel == DeveloperLevel.SENIOR
                && experienceYears < 18){

            // 해당 Enum값을 import static해서 사용해주는것도 좋다.
            // 전 : DMakerErrorCode.LEVEL_EXPERIENCE_YEARS_NOT_MATCHED
            // 후 : LEVEL_EXPERIENCE_YEARS_NOT_MATCHED
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }
        // 인텔리제이를 활용한 반복코드 지역변수로 활용하기
        // Ctrl + Alt + v 를 사용하면 반복되는 코드들을 지역변수로 만들어주고 전부 변경시켜준다.
        if(developerLevel == DeveloperLevel.JUNGIOR
                &&(experienceYears < 4 || experienceYears > 10)){
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }
        if(developerLevel == DeveloperLevel.JUNGIOR && experienceYears > 4){
            throw  new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }
        // 동일한 memberID를 막기위해 조회하여 중복체크를 하기위함
        developerRepository.findByMemberId(request.getMemberId())
                .ifPresent((developer -> {
                    throw new DMakerException(DUPLICATED_MEMBER_ID);
                }));

        /*
            기존에는 아래 코드와 같은 방식을 사용했지만 자바8에서는 위와같은 다른 방식을 지원해준다.(지역변수 안써도됨)
            Optional<Developer> developer =  developerRepository.findByMemberId(request.getMemberId());
                if(developer.isPresent())
                    throw new DMakerException(DUPLICATED_MEMBER_ID);
         */

    }


}
