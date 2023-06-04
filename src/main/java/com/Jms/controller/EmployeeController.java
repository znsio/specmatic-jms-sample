package com.Jms.controller;

import com.Jms.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Topic;

@Slf4j
@RestController
public class EmployeeController {

    @Autowired
    JmsTemplate jmsTemplate;

    /**
     * Create new employee
     *
     * @param employee
     * @return ResponseEntity
     */
    @PostMapping("/employee")
    public ResponseEntity<Employee> newEmployee(@RequestBody Employee employee) {
        try {
            Topic empTopic = jmsTemplate.getConnectionFactory().createConnection()
                    .createSession().createTopic("EmpTopic");
            int empId = (int)(Math.random() * 50 + 1);
            Employee emp = Employee.builder().id(empId).name(employee.getName()).role(employee.getRole()).build();
            log.info("Sending Employee Object to JMS Server: " + emp);
            jmsTemplate.convertAndSend(empTopic, emp);
            return new ResponseEntity<>(emp, HttpStatus.OK);

        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}