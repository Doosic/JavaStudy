package com.example.dmaker.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
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
public class DMakerController {

    // 사용자의 요청이 우리 서버로 들어와 developers라는 주소로 들어온다면 이곳으로 들어오게 된다.
    @GetMapping("/developers")
    public List<String> getAllDevelopers(){
      log.info("GET /developers HTTP/1.1");

      return Arrays.asList("snow", "elsa", "Olaf");
    }
}
