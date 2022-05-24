package com.example.dmaker.dto;

import lombok.*;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
//@Setter
//@Getter
//@ToString
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Slf4j
@UtilityClass
public class DevDto {

        public static void printLog(){
                System.out.println(LocalDateTime.of(2022,7,21,3,15));
        }
}
