package com.example.dmaker.controller;

import com.example.dmaker.dto.DeveloperDto;
import com.example.dmaker.service.DMakerService;
import com.example.dmaker.type.DeveloperLevel;
import com.example.dmaker.type.DeveloperSkillType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
    @WebMvcTest()
    컨트롤러 관련 빈들만 활성화 시켜서 사용할 수 있다.
    괄호안에 클래스명을 적어주면 해당 클래스만 띄울수 있다.
    그러나 해당 클래스를 띄우는데 필요한 클래스는 기본적으로 올려준다.
 */
@WebMvcTest(DMakerController.class)
//@AutoConfigureMockMvc
class DMakerControllerTest {
  /*
      controller 에 있는 메서드를 가상으로 호출할 수 있다.
   */
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private DMakerService dMakerService;

  /*
        사용하게될 미디어 타입들.
   */
  protected MediaType contentType =
          new MediaType(MediaType.APPLICATION_JSON.getType(),
                  MediaType.APPLICATION_JSON.getSubtype(),
                  StandardCharsets.UTF_8);

  @Test
  void getAllDevelopers() throws Exception{
        DeveloperDto juniorDeveloperDto = DeveloperDto.builder()
                .developerLevel(DeveloperLevel.JUNIOR)
                .developerSkillType(DeveloperSkillType.BACK_END)
                .memberId("memberId1")
                .build();
      DeveloperDto seniorDeveloperDto = DeveloperDto.builder()
              .developerLevel(DeveloperLevel.SENIOR)
              .developerSkillType(DeveloperSkillType.FRONT_END)
              .memberId("memberId2")
              .build();
        // CoreMatchers
        given(dMakerService.getAllEmployedDevelopers())
                .willReturn(Arrays.asList(juniorDeveloperDto,seniorDeveloperDto));

        /*
               가짜 MockMvc 가 get 으로  developers를 호출하고 컨텐츠 타입은
               json 으로 주고 받는다고 설정
         */
        mockMvc.perform(get("/developers").contentType(contentType))
                .andExpect(status().isOk()) // status 가 ok 라는것을 예상하고 있다.
                .andExpect(
                        (ResultMatcher) jsonPath("$.[0].developerSkillType",
                               is(DeveloperSkillType.BACK_END.name()))
                ).andExpect(
                        (ResultMatcher) jsonPath("$.[1].developerSkillType",
                             is(DeveloperSkillType.FRONT_END.name()))
                );

  }

}