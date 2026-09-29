package com.demo.spring.coaches;

import com.demo.spring.Coach;
import org.springframework.stereotype.Component;

@Component
public class RugbyCoach implements Coach {
    @Override
    public String getDailyWorkout() {
        return "Run for 15 minutes and then do 10 push-ups";
    }
}
