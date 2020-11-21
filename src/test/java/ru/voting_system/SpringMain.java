package ru.voting_system;

import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.env.Profiles;
import ru.voting_system.model.Role;
import ru.voting_system.model.User;

import java.util.Arrays;

public class SpringMain {

    public static void main(String[] args) {
        try ( GenericXmlApplicationContext appCtx = new GenericXmlApplicationContext()) {
            appCtx.load("spring/spring-app.xml", "spring/spring-db.xml");

            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));

            System.out.println(appCtx.getBeanDefinitionNames().length);

        }
    }

}
