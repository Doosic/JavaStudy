package com.fc_study.monsterGrowth.exception;

import com.fc_study.monsterGrowth.dto.MMakerErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import static com.fc_study.monsterGrowth.exception.MMakerErrorCode.INTERNAL_SERVER_ERROR;
import static com.fc_study.monsterGrowth.exception.MMakerErrorCode.INVALID_REQUEST;

@Slf4j
@RestControllerAdvice
public class MMakerExceptionHandler {

    // MMakerException.class 에러 반환
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(MMakerException.class)
    public MMakerErrorResponse handleException(
            MMakerException e,
            HttpServletRequest request
    ){
        log.info("errorConde: {}, url: {}, message: {}",
                e.getMMakerErrorCode(), request.getRequestURI(), e.getDetailMessage());
        return MMakerErrorResponse.builder()
                .errorCode(e.getMMakerErrorCode())
                .errorMessage(e.getDetailMessage())
                .build();
    }

    // post 메핑인데 get 이라던지... 벨리데이션 할때에 @NotNull 등 컨트롤러 진입전 문제되는것들
    @ExceptionHandler(value = {
            HttpRequestMethodNotSupportedException.class,
            MethodArgumentNotValidException.class
    })
    public MMakerErrorResponse handleBadRequest(
            Exception e,
            HttpServletRequest request
    ){
        log.info(" url: {}, message: {}",
                 request.getRequestURI(), e.getMessage());
        return MMakerErrorResponse.builder()
                .errorCode(INVALID_REQUEST)
                .errorMessage(INVALID_REQUEST.getMessage())
                .build();
    }

    // 진짜 알 수 없는 오류
    @ExceptionHandler(MMakerException.class)
    public MMakerErrorResponse handleException(
            Exception e,
            HttpServletRequest request
    ){
        log.info(" url: {}, message: {}",
                request.getRequestURI(), e.getMessage());
        return MMakerErrorResponse.builder()
                .errorCode(INTERNAL_SERVER_ERROR)
                .errorMessage(INTERNAL_SERVER_ERROR.getMessage())
                .build();
    }
}
