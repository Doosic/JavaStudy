package com.example.dmaker.service;

import com.example.dmaker.code.StatusCode;
import com.example.dmaker.dto.CreateDeveloper;
import com.example.dmaker.dto.DeveloperDetailDto;
import com.example.dmaker.dto.DeveloperDto;
import com.example.dmaker.entity.Developer;
import com.example.dmaker.repository.DeveloperRepository;
import com.example.dmaker.repository.RetiredDeveloperRepository;
import com.example.dmaker.type.DeveloperLevel;
import com.example.dmaker.type.DeveloperSkillType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static com.example.dmaker.type.DeveloperLevel.SENIOR;
import static com.example.dmaker.type.DeveloperSkillType.FRONT_END;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

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
            .willReturn(Optional.of(Developer.builder()
                    .developerLevel(SENIOR)
                    .developerSkillType(FRONT_END)
                    .experienceYears(12)
                    .statusCode(StatusCode.EMPLOYED)
                    .name("name")
                    .age(22)
                    .build()));
        DeveloperDetailDto developerDetail = dMakerService.getAllDeveloperDetail("memberId");

        assertEquals(SENIOR, developerDetail.getDeveloperLevel());
        assertEquals(FRONT_END, developerDetail.getDeveloperSkillType());
        assertEquals(12, developerDetail.getExperienceYears());
    }

/*
    @Test
    public void testSomething(){

        assert 시리즈는 Junit 의 jupiter 시리즈에서 나오는 검증 방법이다.
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
}