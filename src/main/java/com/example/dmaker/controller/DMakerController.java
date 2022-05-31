package com.example.dmaker.controller;

import com.example.dmaker.dto.*;
import com.example.dmaker.exception.DMakerException;
import com.example.dmaker.service.DMakerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/*
    RestController 타입의 Bean으로 등록하여 사용한다는 의미
    @Controller에 ResponseBody를 달아준다는 것이다.
    API는 주로 json으로 요청을 줘서 json으로 응답받게 되는데 ResponseBody를 쓰게되면
    json으로 응답을 받는것을 상정하고 활용을 하게된다.

    요청하는곳에서 Assept하는 컨텐츠 타입이 무엇이냐에 따라 자동으로 바꿔주지만 기본적으로는
    json이 표준이기에 json으로 줄것이다 라고 한다.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class DMakerController {
    private final DMakerService dMakerService;
    /*
        DMakerController(Bean)   DMakerService(Bean)   DeveloperRepository(Bean)
        =======================Spring Application Contexts=======================
        스프링이라는 컨텍스트 위에 Bean들을 구성하고 컨트롤러가 서비스를 가져다 쓰고싶다면
        private final을 통해 호출한다. 그리고 @RequiredArgsConstructor 를 붙여주면
        service를 Controller에 넣어준다고 생각하면 된다.
     */

    // 사용자의 요청이 우리 서버로 들어와 developers라는 주소로 들어온다면 이곳으로 들어오게 된다.
    @GetMapping("/developers")
    public List<DeveloperDto> getAllDevelopers() {
        // 여러명의 정보를 가져오기 때문에 List를 사용한다.
        log.info("GET /developers HTTP/1.1");

        return dMakerService.getAllEmployedDevelopers();
    }

    // 현재 리스트와 상세정보를 보여주는 dto를 분리하여 사용함.
    @GetMapping("/developer/{memberId}")
    public DeveloperDetailDto getDeveloperDetail(
            @PathVariable String memberId
    ){
        log.info("GET /developer HTTP/1.1");

        return dMakerService.getAllDeveloperDetail(memberId);
    }

    //POST맨으로 호출할 수 있지만 Intellij안에 내장되어있는걸 사용
    @PostMapping("/create-developers")
    public CreateDeveloper.Response createDevelopers(
           @Valid @RequestBody CreateDeveloper.Request request
            ) {
        // @Valid 바디에 들어온 값들을 담아주면서 벨리데이션을 해주고 문제가 생기면 메서드 진입전에 익셉션을 일으켜준다.
        // @RequestBody CreateDeveloper에 Request에 담아주겠다 라는 의미
        log.info("request : ",request);
        // Get은 무언가를 받아올때에 사용하는것이 옳다.
        // 변경이 있을때에는 Post를 사용하는 것이 좋다.
//        dMakerService.createDeveloper(request);

        // 단일 객체를 들고있을때에는 singletonList를 사용하는 것이 좋다.
        return dMakerService.createDeveloper(request);
    }

    @PutMapping("/developer/{memberId}")
    public DeveloperDetailDto editDeveloper(
            @PathVariable String memberId,
            @Valid @RequestBody EditDeveloper.Request request
    ){
        log.info("GET /developer HTTP/1.1");

        return dMakerService.editDeveloper(memberId, request);
    }

    @DeleteMapping ("/developer/{memberId}")
    public DeveloperDetailDto deleteDeveloper(
            @PathVariable String memberId
    ){
        log.info("GET /developer HTTP/1.1");

        return dMakerService.deleteDeveloper(memberId);
    }

    // () 사이에 원하는 Exception 을 넣어줄 수 있다.
    // HttpStatus.CONFLICT 는 상수로 지정되어있어서 적절히 가져다 쓸수있다.
    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(DMakerException.class)
    public DMakerErrorResponse handleException(
            DMakerException e,
            HttpServletRequest request){
        log.error("errorCode: {}, url: {}, message: {}",
                e.getDMakerErrorCode(), request.getRequestURI(), e.getDetailMessage());
        return DMakerErrorResponse.builder()
                .errorCode(e.getDMakerErrorCode())
                .errorMessage(e.getDetailMessage())
                .build();
    }
}
