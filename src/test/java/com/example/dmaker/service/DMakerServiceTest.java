package com.example.dmaker.service;

import com.example.dmaker.code.StatusCode;
import com.example.dmaker.dto.CreateDeveloper;
import com.example.dmaker.dto.DeveloperDetailDto;
import com.example.dmaker.dto.EditDeveloper;
import com.example.dmaker.entity.Developer;
import com.example.dmaker.exception.DMakerErrorCode;
import com.example.dmaker.exception.DMakerException;
import com.example.dmaker.repository.DeveloperRepository;
import com.example.dmaker.repository.RetiredDeveloperRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.example.dmaker.type.DeveloperLevel.JUNGNIOR;
import static com.example.dmaker.type.DeveloperLevel.SENIOR;
import static com.example.dmaker.type.DeveloperSkillType.BACK_END;
import static com.example.dmaker.type.DeveloperSkillType.FRONT_END;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class DMakerServiceTest {
/*
        DMakerService 는 Repository, DTO 많은것을 의존하고 있다. 그렇기에 아래와 같이 사용하기는 힘들다.
        private DMakerService dMakerService = new DMakerService();
        그래서 @SpringBootTest 라는걸 사용한다.

        @SpringBootTest
        우리가 애플리케이션을 직접 띄우는것과 유사하게 기본적으로 모든 Bean 들을 띄워
        실행환경과 비슷하게 테스트 할 수 있게 만들어 준다.
 */

//    @Autowired
//    private DMakerService dMakerService;
    /*
        Mock(가짜)
        가짜를 넣어준다 라는 의미이다.
        @Mock, @InjectMocks

        DMakerService 가 의존하는 레포지토리들에 가짜 데이터를 넣어준다.
        private final DeveloperRepository developerRepository;
        private final RetiredDeveloperRepository retiredDeveloperRepository;
    */
    @Mock
    private DeveloperRepository developerRepository;

    @Mock
    private RetiredDeveloperRepository retiredDeveloperRepository;

    @InjectMocks
    private DMakerService dMakerService;

    private Developer defaultDeveloper = Developer.builder()
            .developerLevel(SENIOR)
                    .developerSkillType(FRONT_END)
                    .experienceYears(12)
                    .statusCode(StatusCode.EMPLOYED)
                    .name("name")
                    .age(22)
                    .build();

    private final CreateDeveloper.Request defaultCreateRequest = CreateDeveloper.Request.builder()
            .developerLevel(SENIOR)
            .developerSkillType(FRONT_END)
            .experienceYears(12)
            .memberId("memberId")
            .name("name")
            .age(22)
            .build();

    private final EditDeveloper.Request defaultEditRequest = EditDeveloper.Request.builder()
            .developerLevel(SENIOR)
            .developerSkillType(BACK_END)
            .experienceYears(12)
            .build();

    @Test
    public void testSomething(){
        /*
            import static org.mockito.BDDMockito.given
            developerRepository.findByMemberId(anyString()) 에 아무 문자열이나 주면
            어떠한 동작을 하게 해놓겠다 라고 정의해 놓는것 given()
            dMakerService.getAllDeveloperDetail 는 developerRepository.findByMemberId(anyString()) 를 호출하기 때문에
            미리 어떤 동작을 할 지 정의해 두는것이다.
        */
        given(developerRepository.findByMemberId(anyString()))
            .willReturn(Optional.of(defaultDeveloper));
        DeveloperDetailDto developerDetail = dMakerService.getAllDeveloperDetail("memberId");

        assertEquals(SENIOR, developerDetail.getDeveloperLevel());
        assertEquals(FRONT_END, developerDetail.getDeveloperSkillType());
        assertEquals(12, developerDetail.getExperienceYears());
    }

/*
    @Test
    public void testSomething(){

        assert 시리즈는 Junit 의 jupiter 시리즈에서 나오는 검증 방법이다
        Ctrl + shift + F10 단축키로 간편하게 실행가능
        assertEquals("예상값", "hello world!") 왼쪽은 예상값, 오른쪽은 실제값을 넣어준다.
        테스트 성공 : 아무런 오류도 뱉지않고 v체크만을 해준다.
        테스트 실패 : 콘솔창에 오류를 뱉는다 expected: <hello world> but was: <hello world!>

        @SpringBootTest
        SpringBootTest 를 사용하게 되면 기본적으로 모든 Bean 들을 띄워준다.
        Test worker 가 우리의 Application 을 ContextLoader 를 통해 동작할 수 있게 해준다.
        테스트용 ApplicationContext를 만들어준다.
        ContextLoader = 우리 애플리케이션에 Bean 과 설정값들을 담아주는 통, 동작하기 위한 원리, 전략을 세우는 부분

        dMakerService.createDeveloper(CreateDeveloper.Request.builder()
                .developerLevel(DeveloperLevel.SENIOR)
                .developerSkillType(DeveloperSkillType.FRONT_END)
                .experienceYears(12)
                .memberId("name")
                .age(22)
                .build());
        // dMakerService.getAllEmployedDevelopers() 에 Ctrl + Alt + V를 눌러주면 자동으로 지역변수를 생성해준다.
        List<DeveloperDto> allEmployedDevelopers = dMakerService.getAllEmployedDevelopers();
        // H2 DB 라 담긴것이 없기때문에 나오는 것이 없다. 그래서 실행전 CreateDeveloper 을 해줘 개발자를 생성해준다.
        // 그리고 이렇게 조회하면 @ToString 을 붙여줘야 의미있는 값을 볼 수 있다.
        System.out.println("======================================");
        System.out.println(allEmployedDevelopers);
        System.out.println("======================================");
    }
*/

    @Test
    void editDeveloperTest_success(){
        /*
             given : 테스트 메서드에다가 시나리오 진행에 필요한 조건을 미리 설정해두는 단계이다.
             * willReturn : 메서드가 호출될 때 반환될 값을 설정합니다.
             * OptionalClass : Null이나 Null이 아닌 값을 담을 수 있다. Wrapper 클래스이며, 참조하더라도 NPE가 발생하지 않도록 도와준다.
                    - Optional.empty() : 빈 Optional 객체를 생성한다.
                    - Optional.ofNullable(developer) : value가 null인 경우 비어있는 Optional을 반환한다. 값이 null일수도 있는 것에 사용

             1. DMakerService에 editDeveloper() 를 보면 validation이 가장먼저 진행되지만 특별한 조회가 없다.
             2. 개발자를 수정하기 위해서는 해당하는 개발자가 있는지 조회를 해야한다.
                    - 개발자가 있는지 조회를 하기 위해서 해당 개발자를 생성하여준다.
             3. 개발자의 정보를 수정해준다. editDeveloper은 memberId와 수정될 RequestValue값을 매개변수로 받는다.
             4. 개발자의 정보가 원하는대로 수정되었는지 확인한다.

        */
        // 2번에 해당. 개발자 조회시 value가 있을수도 있고 null일수도 있기에 ofNullable을 사용
        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.ofNullable(defaultDeveloper));


        // 3번에 해당. 개발자의 정보를 수정해준다. 수정후에는 수정된 개발자를 return 해준다.
        DeveloperDetailDto editDeveloperValue = dMakerService.editDeveloper(anyString(), defaultEditRequest);

        // 4번에 해당. 수정된 정보를 가진 개발자의 정보를 확인한다.
        assertEquals(SENIOR, editDeveloperValue.getDeveloperLevel());
        assertEquals(BACK_END, editDeveloperValue.getDeveloperSkillType());
        assertEquals(12, editDeveloperValue.getExperienceYears());
    }


    @Test
    void createDeveloperTest_success(){
        //given
        //지역변수 및 테스트에 활용될 메서드들을 만들어둔다
        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.empty());

        given(developerRepository.save(any()))
                .willReturn(defaultDeveloper);
        // ArgumentCaptor<저장하게 될 타입>
        // create 할 때에는 ArgumentCaptor 를 통해 저장되는 데이터를 가져올 수 있다.
        // DB에 저장하는 데이터나 외부 API 로 호출을 날릴때에 데이터가 어떤것이 날아가는지 확인하고 싶을때에 captor 을 사용한다.
        ArgumentCaptor<Developer> captor =
                ArgumentCaptor.forClass(Developer.class);

        //-when
        //테스트하고자 하는 동작과 그 동작의 결과값, 테스트하며 Mocking 해야 할 지점을 찾아가야한다.
        dMakerService.createDeveloper(defaultCreateRequest);
/*
        -then
        예상한 동작대로 동작하는지 검증하는 단계
        검증해야 할것 = Service 단의 로직을 validation 이라던지 save 라던지.
        verify = 특정 Mock 이 몇번 호출됬는지 검증을 해준다.
        import static org.mockito.Mockito.times;
        import static org.mockito.Mockito.verify;
*/
        // api 나 db에 저장되는 데이터를 이곳에서 받아서 꺼내 확인할 수 있다.
        verify(developerRepository, times(1))
                .save(captor.capture());
        // 캡쳐된 결과를 꺼낼 수 있다.
        Developer savedDeveloper = captor.getValue();
        assertEquals(SENIOR, savedDeveloper.getDeveloperLevel());
        assertEquals(FRONT_END, savedDeveloper.getDeveloperSkillType());
        assertEquals(12, savedDeveloper.getExperienceYears());
    }

    // Exception 검증.
    @Test
    void createDeveloperTest_failed_with_duplicated(){
        //given
        //지역변수 및 테스트에 활용될 메서드들을 만들어둔다
        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.of(defaultDeveloper));
        // ArgumentCaptor<저장하게 될 타입>
        // create 할 때에는 ArgumentCaptor 를 통해 저장되는 데이터를 가져올 수 있다.
//        ArgumentCaptor<Developer> captor =
//                ArgumentCaptor.forClass(Developer.class);

        //-when
        //테스트하고자 하는 동작과 그 동작의 결과값, 테스트하며 Mocking 해야 할 지점을 찾아가야한다.
//        CreateDeveloper.Response developer = dMakerService.createDeveloper(defaultCreateRequest);
/*
        -then
        예상한 동작대로 동작하는지 검증하는 단계
        검증해야 할것 = Service 단의 로직을 validation 이라던지 save 라던지.
        verify = 특정 Mock 이 몇번 호출됬는지 검증을 해준다.
        import static org.mockito.Mockito.times;
        import static org.mockito.Mockito.verify;
*/
        // # assertThrows 처음 값으로 예상되는 exception 의 클래스의 종류를 받고 던지게될 동작을 받는다.
        // Exception 을 던지는것을 검증하는 단계. 실패한것을 검증하는 것이다.
        DMakerException dMakerException = assertThrows(DMakerException.class,
                () -> dMakerService.createDeveloper(defaultCreateRequest));

        /*
               테스트가 실패하게되면 왼쪽 하단에 !가 나오게됨. x 가 나와야하는데
         */
        assertEquals(DMakerErrorCode.DUPLICATED_MEMBER_ID, dMakerException.getDMakerErrorCode());

/*        verify(developerRepository, times(1))
                .save(captor.capture());
        // 캡쳐된 결과를 꺼낼 수 있다.
        Developer savedDeveloper = captor.getValue();
        assertEquals(SENIOR, savedDeveloper.getDeveloperLevel());
        assertEquals(FRONT_END, savedDeveloper.getDeveloperSkillType());
        assertEquals(12, savedDeveloper.getExperienceYears());*/
    }
}