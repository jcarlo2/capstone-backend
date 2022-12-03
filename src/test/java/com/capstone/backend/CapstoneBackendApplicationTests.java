package com.capstone.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = CapstoneBackendApplicationTests.class, loader=CustomSpringApplicationContextLoader.class)
@SpringBootTest
class CapstoneBackendApplicationTests {

    @Test
    void contextLoads() {
    }

}
