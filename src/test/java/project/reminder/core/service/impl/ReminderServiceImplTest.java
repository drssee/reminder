package project.reminder.core.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import project.reminder.common.aop.LoggingAspect;

@SpringBootTest
class ReminderServiceImplTest {

    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void test() {
        LoggingAspect loggingAspect = applicationContext.getBean("loggingAspect", LoggingAspect.class);
        System.out.println("loggingAspect = " + loggingAspect);
    }
}