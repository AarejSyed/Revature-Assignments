package com.demo.spring.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class MyFirstController {
    private static final Logger logger = LoggerFactory.getLogger(MyFirstController.class);

    // Code-along
    
    @GetMapping("/")
    public String sayHello() {
        logger.info("GET request on /");
        return "Hello World!";
    }

    @GetMapping("/test")
    public String sayTestingHotReload() {
        logger.info("GET request on /test");

        return "Hot Reload Works!";
    }
}
