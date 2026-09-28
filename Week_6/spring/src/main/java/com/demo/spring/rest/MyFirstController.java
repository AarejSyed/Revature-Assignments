package com.demo.spring.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class MyFirstController {
    private static final Logger logger = LoggerFactory.getLogger(MyFirstController.class);

    /*
    CHALLENGE: Custom Endpoint and Logging

    - Create a new endpoint which returns a string = “My name is: VALUE”
    - Where “VALUE” is dynamically injected from a custom property in your ‘application.properties’ file
    - Log an info message to a file when someone makes a GET request on our endpoint
    */

    @Value("${webmaster.name}")
    private String webmasterName;

    @GetMapping("/name")
    public String sayName() {
        logger.info("GET request on /name");
        return "My name is: " + webmasterName;
    }

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
