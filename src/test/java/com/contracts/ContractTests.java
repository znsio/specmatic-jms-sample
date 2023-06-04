package com.contracts;

import com.Jms.JmsApplication;

import in.specmatic.jms.mock.JmsMock;
import in.specmatic.stub.ContractStub;
import in.specmatic.test.SpecmaticJUnitSupport;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;

import static in.specmatic.stub.API.createStub;

@SpringBootTest(classes = JmsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ContractTests extends SpecmaticJUnitSupport {
    private static final String SPECMATIC_STUB_HOST = "localhost";
    private static final int SPECMATIC_STUB_PORT = 9000;
    private static ContractStub stub;
    private static JmsMock jmsMock;

    @BeforeAll
    public static void setUp() {
        System.setProperty("host", "localhost");
        System.setProperty("port", "8080");
        System.setProperty("spring.profiles.active", "contract-tests");
        System.setProperty("CUSTOM_RESPONSE", "true");
        System.setProperty("endpointsAPI", "http://localhost:8080/actuator/mappings");
        stub = createStub(SPECMATIC_STUB_HOST, SPECMATIC_STUB_PORT);
        jmsMock = new JmsMock(new ArrayList<String>() {{
            add("src/test/resources/async-api.yaml");
        }}, "localhost", 61616);
        jmsMock.start();
    }

    @AfterAll
    public static void tearDown() throws IOException {
        if (stub != null) {
            stub.close();
        }
        if (jmsMock != null) {
            jmsMock.stop();
        }
    }
}