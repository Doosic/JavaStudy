package com.springBootStudy.service;

import com.springBootStudy.logic.JavaSort;
import com.springBootStudy.logic.Sort;

import java.util.List;

public class SortService {

    // DI: Dependencies Injection

    private final Sort<String> sort;

    public SortService(Sort<String> sort) {
        this.sort = sort;
        System.out.println("implementation: "+ sort.getClass().getName());
    }

    public List<String> doSort(List<String> list){
        return sort.sort(list);
    }
}
