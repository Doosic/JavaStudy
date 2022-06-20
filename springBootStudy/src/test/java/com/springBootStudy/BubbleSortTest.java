package com.springBootStudy;

import com.springBootStudy.logic.BubbleSort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BubbleSortTest {

    @DisplayName("bubbleSort - show sorted results")
    @Test
    void given_List_WhenExecuting_thenReturnSortedList(){
        // List.of() method is available from Java 9 or higher
        // given
        BubbleSort<Integer> bubbleSort = new BubbleSort<>();

        // when
       List<Integer> actual = bubbleSort.sort(Arrays.asList(3, 2, 4, 5, 1));

        // then
        assertEquals(Arrays.asList(1,2,3,4,5), actual);

    }

}