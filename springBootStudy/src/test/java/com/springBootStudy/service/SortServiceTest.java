package com.springBootStudy.service;

import com.springBootStudy.logic.JavaSort;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SortServiceTest {

    private SortService sut = new SortService(new JavaSort<String>());

    @Test
    void test(){
        // given

        // when
        List<String> actual = sut.doSort(Arrays.asList("3", "2", "1"));

        // then
        assertEquals(Arrays.asList("1", "2", "3"), actual);

    }

}