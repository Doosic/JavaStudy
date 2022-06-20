//package com.springBootStudy;
//
//import com.springBootStudy.config.Config;
//import com.springBootStudy.logic.JavaSort;
//import com.springBootStudy.logic.Sort;
//import com.springBootStudy.service.SortService;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//
//import java.util.Arrays;
//
//public class Main {
//    public static void main(String[] args) {
//        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
///*
//* Original
//* Sort<String> sort = new JavaSort<>();
//*
//* can't Not Sort Service. bring your own implementation
//* Sort<String> sort = context.getBean(Sort.class);
//*/
//
//        SortService sortService = context.getBean(SortService.class);
//
//        System.out.println("[result] " + sortService.doSort(Arrays.asList(args)));
//    }
//}
