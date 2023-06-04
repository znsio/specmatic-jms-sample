package com.Jms.listener;

import com.Jms.model.Employee ;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmployeeListener {
    @JmsListener(destination = "${emp.jms.topic}", containerFactory = "empJmsContFactory")
    public void getEmployeeListener1(Employee emp) {
        log.info("Employee listener received JMS message -> " + emp);
    }
}
