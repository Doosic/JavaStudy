package com.example.dmaker.exception;

import com.example.dmaker.dto.DMakerErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import static com.example.dmaker.exception.DMakerErrorCode.INTERNAL_SERVER_ERROR;
import static com.example.dmaker.exception.DMakerErrorCode.INVALID_REQUEST;

@Slf4j
@RestControllerAdvice // 각 컨트롤러에게 조언을 해주는, 컨트롤러에 어드바이스 역할을 해주는 컨트롤러 / Bean으로 등록
public class DMakerExceptionHandler {

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

    // 가끔 도저히 잡을 수 없는 Exception 들을 잡을 떄에 사용
    // HttpRequestMethodNotSupportedException.class = Post 메서드를 사용해야 할 곳에 Post 메서드가 아닌 다른 메서드를 사용했을때
    // MethodArgumentNotValidException.class = 자바 Bean 벨리데이션을 할 때에 @NotNull,@Min 등... 컨트롤러 내부에 진입하기전에 문제되는 것들을 잡아준다.
    // 여기서는 DMakerException 을 잡는게 아닌 일반적인 여러가지의 Exception을 다 가지고있기에 공통적으로 가지고 있거나 활용할 수 있는 애러들을 잡아야한다.
    @ExceptionHandler(value = {
            HttpRequestMethodNotSupportedException.class,
            MethodArgumentNotValidException.class
    })
    public DMakerErrorResponse handleBadRequest(
            Exception e, HttpServletRequest request
    ){
        log.error("url: {}, message: {}",
                 request.getRequestURI(), e.getMessage());
        return DMakerErrorResponse.builder()
                .errorCode(INVALID_REQUEST)
                .errorMessage(INVALID_REQUEST.getMessage())
                .build();
    }

    // 진짜 어떤 오류가 날라올지 알 수 없는 경우
    @ExceptionHandler(Exception.class)
    public DMakerErrorResponse handleException(
            Exception e, HttpServletRequest request
    ){
        log.error("url: {}, message: {}", request.getRequestURI(), e.getMessage());

        return DMakerErrorResponse.builder()
                .errorCode(INTERNAL_SERVER_ERROR)
                .errorMessage(INTERNAL_SERVER_ERROR.getMessage())
                .build();
    }
}
