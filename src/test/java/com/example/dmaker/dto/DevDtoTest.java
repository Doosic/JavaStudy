package com.example.dmaker.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DevDtoTest {
    @Test
    void test() {
        DevDto devDto = new DevDto("slow", 21);

        devDto.setName("snow");
        devDto.setAge(30);
        devDto.setStartAt(LocalDateTime.now());
        System.out.println(devDto);
    }
}