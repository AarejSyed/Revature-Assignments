package com.demo.spring.coaches;

import com.demo.spring.Coach;
import org.springframework.stereotype.Component;

@Component
public class CricketCoach implements Coach {
    @Override
    public String getDailyWorkout() {
        return "Practice your batting for 30 minutes";
    }
}
