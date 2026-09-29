package com.demo.spring.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.Coach;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class MyFirstController {
    // CODE-ALONG
    
    private static final Logger logger = LoggerFactory.getLogger(MyFirstController.class);
    private Coach myCoach;
    
    /*
    CHALLENGE: Create endpoints for different types of coaches
    */

    private Coach soccerCoach;
    private Coach rugbyCoach;
    private Coach cricketCoach;

    // Soccer coach endpoint
    @GetMapping("/soccer")
    public String soccerWorkout() {
        return soccerCoach.getDailyWorkout();
    }

    // Rugby coach endpoint
    @GetMapping("/rugby")
    public String rugbyWorkout() {
        return rugbyCoach.getDailyWorkout();
    }

    // Cricket coach endpoint
    @GetMapping("/cricket")
    public String cricketWorkout() {
        return cricketCoach.getDailyWorkout();
    }

    // CODE-ALONG
    
    @Autowired
    public void setCoaches(
        @Qualifier("coachImpl") Coach c,
        @Qualifier("soccerCoachImpl") Coach soccerCoach,
        @Qualifier("rugbyCoachImpl") Coach rugbyCoach,
        @Qualifier("cricketCoachImpl") Coach cricketCoach
    ) {
        myCoach = c;
        this.soccerCoach = soccerCoach;
        this.rugbyCoach = rugbyCoach;
        this.cricketCoach = cricketCoach;
    }

    @GetMapping("/workout")
    public String workout() {
        return myCoach.getDailyWorkout();
    }

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
