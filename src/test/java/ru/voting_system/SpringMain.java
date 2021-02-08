package ru.voting_system;

import java.time.*;
import java.util.Calendar;
import java.util.Date;

import static java.time.LocalDate.now;

public class SpringMain {

    public static final LocalTime TIME_LIMIT = LocalTime.of(11, 0);
    public static LocalDate date = LocalDate.now();

    public static void main(String[] args) {
//        try ( GenericXmlApplicationContext appCtx = new GenericXmlApplicationContext()) {
//            appCtx.load("spring/spring-app.xml", "spring/spring-db.xml");
//            appCtx.refresh();
//
//            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
//
//            System.out.println(appCtx.getBeanDefinitionNames().length);
//            System.out.println("=================================================");
//            System.out.println("=================================================");
//            VoteService service = (VoteService) appCtx.getBean("voteService");
//
//            service.getAllByUserId(100000).forEach(System.out::println);
//
//            System.out.println("=================================================");
//            System.out.println("=================================================");
//
//
//        }


    }

}
